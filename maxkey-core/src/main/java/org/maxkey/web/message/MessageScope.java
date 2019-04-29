package org.maxkey.web.message;

/**
 * message范围
 * @author Crystal.Sea
 *
 */
public enum MessageScope {
	JSON,//仅json
	DB,//插入数据库
	CLIENT,//仅前端页面
	DB_CLIENT//数据库和前端页面
}
