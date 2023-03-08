package com.jeesite.modules.maxkey.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 长春叭哥
 * @version 2023-02-23
 */
public enum JustAuthPlatformInfo {

    /**
     * 平台
     */
    GITEE("Gitee", "", "", "v1.0.1", false),
    BAIDU("百度", "", "", "v1.0.1", false),
    CODING("coding", "", "", "v1.0.1", false),
    CSDN("CSDN", "", "", "v1.0.1", false),
    DINGTALK("钉钉扫码登录", "", "", "v1.0.1", false),
    GITHUB("Github", "", "", "v1.0.1", false),
    OSCHINA("开源中国", "", "", "v1.0.1", false),
    ALIPAY("支付宝", "", "", "v1.0.1", false),
    WEIBO("微博", "", "", "v1.0.1", false),

    DOUYIN("抖音", "", "", "v1.4.0", false),
    ELEME("饿了么", "", "", "v1.12.0", false),
    FACEBOOK("Facebook", "", "", "v1.3.0", false),
    GITLAB("Gitlab", "", "", "v1.11.0", false),
    GOOGLE("Google", "", "", "v1.3.0", false),
    HUAWEI("华为", "", "", "v1.10.0", false),
    JD("京东", "", "", "v1.15.1", false),
    KUJIALE("酷家乐", "", "", "v1.11.0", false),
    LINKEDIN("领英", "", "", "v1.4.0", false),
    MEITUAN("美团", "", "", "v1.12.0", false),
    MICROSOFT("微软", "", "", "v1.5.0", false),
    MI("小米", "", "", "v1.5.0", false),
    PINTEREST("Pinterest", "", "", "v1.9.0", false),
    QQ("QQ", "", "", "v1.1.0", false),
    RENREN("人人", "", "", "v1.9.0", false),
    STACK_OVERFLOW("Stack Overflow", "", "", "v1.9.0", false),
    TAOBAO("淘宝", "", "", "v1.2.0", false),
    TEAMBITION("Teambition", "", "", "v1.9.0", false),
    WECHAT_ENTERPRISE("企业微信二维码登录", "", "", "v1.10.0", false),
    WECHAT_MP("微信公众平台", "", "", "v1.14.0", false),
    WECHAT_OPEN("微信开放平台", "", "", "v1.1.0", false),
    TOUTIAO("今日头条", "", "", "v1.6.0-beta", false),
    TWITTER("推特", "", "", "v1.13.0", false),
    ALIYUN("阿里云", "", "", "v1.15.5", false),
    MYGITLAB("自定义的Gitlab", "", "", "v1.13.0", false),
    XMLY("喜马拉雅", "", "", "v1.15.9", false),
    WECHAT_ENTERPRISE_WEB("企业微信网页登录", "", "", "v1.15.9", false),
    FEISHU("飞书", "", "", "1.15.9", false),
    AMAZON("Amazon", "", "", "1.16.0", true),
    DINGTALK_ACCOUNT("钉钉账号登录", "", "", "v1.16.0", true),
    SLACK("slack 登录", "", "", "v1.16.0", true),
    LINE("line 登录", "", "", "v1.16.0", true),
    okta("Okta 登录", "", "", "v1.16.0", true),
    proginn("程序员客栈", "", "", "v1.16.2", true),
    ;

    // 平台名
    private final String name;
    // 帮助文档
    private final String readme;
    // 官网api文档
    private final String apiDoc;
    // 集成该平台的 版本
    private final String since;
    private final boolean latest;

    JustAuthPlatformInfo(String name, String readme, String apiDoc, String since, boolean latest) {
        this.name = name;
        this.readme = readme;
        this.apiDoc = apiDoc;
        this.since = since;
        this.latest = latest;
    }

    public static List<Map<String, Object>> getPlatformInfos() {
        List<Map<String, Object>> list = new LinkedList<>();
        Map<String, Object> map = null;
        JustAuthPlatformInfo[] justAuthPlatformInfos = JustAuthPlatformInfo.values();
        for (JustAuthPlatformInfo justAuthPlatformInfo : justAuthPlatformInfos) {
            map = new HashMap<>();
            map.put("name", justAuthPlatformInfo.getName());
            map.put("readme", justAuthPlatformInfo.getReadme());
            map.put("apiDoc", justAuthPlatformInfo.getApiDoc());
            map.put("since", justAuthPlatformInfo.getSince());
            map.put("enname", justAuthPlatformInfo.name().toLowerCase());
            map.put("isLatest", justAuthPlatformInfo.isLatest());
            list.add(map);
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public String getReadme() {
        return readme;
    }

    public String getApiDoc() {
        return apiDoc;
    }

    public String getSince() {
        return since;
    }

    public boolean isLatest() {
        return latest;
    }
}