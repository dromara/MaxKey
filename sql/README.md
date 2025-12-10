## MaxKey对应SQL版本

SQL版本命名 v[`x.y.z`] 其中`x`是主版本号，`y`是当前版本，`z`是修正版本号；例如v4.1.9

保留最近3的版本SQL，其他版本存放到`olderVersions`中；

如果没有最新版本对应的SQL版本，请用最新版本。

## 历史SQL版本

olderVersions历史版本SQL

## 数据库模型
MaxKey数据库建模使用PDManer元数建模，会定期更新建模的数据文件，如下：

MaxKey单点登录认证系统v[`version`].chnr.json

PDManer 参见 https://gitee.com/robergroup/pdmaner