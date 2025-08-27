---
title: GitLab集成指南
sidebar_position: 7
---

## GitLab 介绍

GitLab 是由 GitLab Inc.开发，使⽤ MIT 许可证的基于⽹络的 Git 仓库管理⼯具，且具有 wiki 和 issue
跟踪功能。使⽤ Git 作为代码管理⼯具，并在此基础上搭建起来的 web 服务。

官⽅⽹站地址：https://about.gitlab.com/

## GitLab 安装配置

### GitLab 安装

请参照官⽅⽂档 https://about.gitlab.com/install/

### 配置

具体可参照 https://docs.gitlab.com/ee/integration/oauth_provider.html

编辑 gitlab.rb

```sh
vim /etc/gitlab/gitlab.rb
```

增加 Oauth 配置：

```ini
gitlab_rails['omniauth_enabled'] = true
gitlab_rails['omniauth_allow_single_sign_on'] = ['oauth2_generic'] #跟下⾯的 name 对应，不建议修改
gitlab_rails['omniauth_block_auto_created_users'] = false # 是否⾃动创建账号
gitlab_rails['omniauth_providers'] = [
    {
        'name' => 'oauth2_generic', #此处跟maxke配置的回调地址有关系
        'label': 'SSO', # 此处显示在 SSO 授权登录的名称
         // highlight-start
        'app_id' => '9cdbccbe-47a0-4adb-9d3d-7e0eceacaace',
        'app_secret' => 'F3QOMTUwMzIwMjExMTMyMTAzNDknMW',
        // highlight-end
        'args' => {
            client_options: {
                 // highlight-start
                'site' => 'http://yourdomain', # maxkey 认证端的域名
                'authorize_url'=>'/sign/authz/oauth/v20/authorize',
                'token_url'=>'/sign/authz/oauth/v20/token',
                'user_info_url' => '/sign/api/oauth/v20/me'
                // highlight-end
            },
            user_response_structure: {
                root_path: [],
                 // highlight-start
                id_path: ['username'],
                // highlight-end
                attributes: { name: 'realname', email: 'username'}
            },
            #name: 'maxkey',
            strategy_class: "OmniAuth::Strategies::OAuth2Generic"
        }
    }
]
```
配置⽂件修改完成后， 重设配置：
```
gitlab-ctl reconfigure
```

重设完毕， 等待约 30 秒。

重新启动 gitliab
```
gitlab-ctl restart
```

### 创建账号
....略

### 创建⼀个账号 maxkey
....略

### 关联 Gitlab 账号

⽤户登录 gitlab 之后， 在 setting-Account 中点击 Connect 进⾏账户关联。
<img src="/doc/images/integration/gitlab/1.png"  />

<img src="/doc/images/integration/gitlab/2.png"  />

关联成功后， 即可使⽤登录⻚的 Oauth2 登录。

### 注意事项
Gitlab 必须要⼿动关联后， 才可单点登录。
https 需要配置 omiauth 的 provider_ignores_state:true， 同时需要把 maxkey 的证书放到 gitlab 的
trusted-certs 下， 然后 重新配置 gitlab-ctl reconfigure 就好了。

## MaxKey 配置及登录验证

### 应⽤配置

进⼊后台"应⽤管理" ，编辑应⽤
<img src="/doc/images/integration/gitlab/3.png"  />

进入"OAuth2.0 配置",配置如下
<img src="/doc/images/integration/gitlab/4.png"  />

### 应⽤访问赋权

如果不在该列表内，可以“新增成员” 

### 单点登录验证

重新登录 MaxKey，点击"Gitlab"图标单点登录
