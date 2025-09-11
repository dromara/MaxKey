-- Passkey模块数据库表结构
-- 用于存储用户Passkey凭据和认证挑战信息

-- 用户Passkey凭据表
CREATE TABLE mxk_user_passkeys (
    ID VARCHAR(40) NOT NULL,
    USER_ID VARCHAR(40) NOT NULL COMMENT '用户ID',
    CREDENTIAL_ID VARCHAR(1024) NOT NULL COMMENT 'WebAuthn凭据ID',
    PUBLIC_KEY TEXT NOT NULL COMMENT '公钥信息',
    SIGNATURE_COUNT BIGINT DEFAULT 0 COMMENT '签名计数器', 
    AAGUID VARCHAR(100) COMMENT '认证器AAGUID',
    DISPLAY_NAME VARCHAR(200) COMMENT '显示名称',
    DEVICE_TYPE VARCHAR(50) DEFAULT 'platform' COMMENT '设备类型：platform/cross-platform',
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    LAST_USED_DATE DATETIME COMMENT '最后使用时间',
    STATUS INT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    INST_ID VARCHAR(40) DEFAULT '1' COMMENT '机构ID',
    PRIMARY KEY (ID),
    UNIQUE KEY UK_USER_CREDENTIAL (USER_ID, CREDENTIAL_ID),
    KEY IDX_USER_ID (USER_ID),
    KEY IDX_CREDENTIAL_ID (CREDENTIAL_ID(255)),
    KEY IDX_STATUS (STATUS),
    KEY IDX_INST_ID (INST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户Passkey凭据表';

-- 添加索引优化查询性能
CREATE INDEX IDX_USER_STATUS ON mxk_user_passkeys(USER_ID, STATUS);
CREATE INDEX IDX_CREATED_DATE ON mxk_user_passkeys(CREATED_DATE);
CREATE INDEX IDX_LAST_USED ON mxk_user_passkeys(LAST_USED_DATE);

-- Passkey认证挑战表
CREATE TABLE mxk_passkey_challenges (
    ID VARCHAR(40) NOT NULL,
    USER_ID VARCHAR(40) COMMENT '用户ID（可为空，支持无用户名登录）',
    CHALLENGE VARCHAR(1024) NOT NULL COMMENT '挑战字符串',
    CHALLENGE_TYPE VARCHAR(20) NOT NULL COMMENT '挑战类型：REGISTRATION/AUTHENTICATION',
    SESSION_ID VARCHAR(100) COMMENT '会话ID',
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    EXPIRE_DATE DATETIME NOT NULL COMMENT '过期时间',
    STATUS INT DEFAULT 0 COMMENT '状态：0-未使用，1-已使用',
    INST_ID VARCHAR(40) DEFAULT '1' COMMENT '机构ID',
    PRIMARY KEY (ID),
    KEY IDX_USER_ID (USER_ID),
    KEY IDX_CHALLENGE_TYPE (CHALLENGE_TYPE),
    KEY IDX_SESSION_ID (SESSION_ID),
    KEY IDX_EXPIRE_DATE (EXPIRE_DATE),
    KEY IDX_STATUS (STATUS),
    KEY IDX_INST_ID (INST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Passkey认证挑战表';

-- 添加复合索引优化查询
CREATE INDEX IDX_CHALLENGE_STATUS ON mxk_passkey_challenges(CHALLENGE_TYPE, STATUS);
CREATE INDEX IDX_USER_TYPE ON mxk_passkey_challenges(USER_ID, CHALLENGE_TYPE);
CREATE INDEX IDX_EXPIRE_STATUS ON mxk_passkey_challenges(EXPIRE_DATE, STATUS);

-- 为现有用户表添加Passkey相关字段（可选方案）
-- 如果选择在现有mxk_userinfo表中添加字段，可以使用以下SQL：
/*
ALTER TABLE mxk_userinfo ADD COLUMN PASSKEY_ENABLED INT DEFAULT 0 COMMENT 'Passkey功能是否启用：0-禁用，1-启用';
ALTER TABLE mxk_userinfo ADD COLUMN PASSKEY_COUNT INT DEFAULT 0 COMMENT '用户Passkey数量';
ALTER TABLE mxk_userinfo ADD COLUMN LAST_PASSKEY_LOGIN DATETIME COMMENT '最后一次Passkey登录时间';

-- 添加索引
CREATE INDEX IDX_PASSKEY_ENABLED ON mxk_userinfo(PASSKEY_ENABLED);
CREATE INDEX IDX_LAST_PASSKEY_LOGIN ON mxk_userinfo(LAST_PASSKEY_LOGIN);
*/

-- 创建清理过期挑战的存储过程
DELIMITER //
CREATE PROCEDURE CleanExpiredPasskeyChallenges()
BEGIN
    DECLARE affected_rows INT DEFAULT 0;
    
    -- 删除过期的挑战记录
    DELETE FROM mxk_passkey_challenges 
    WHERE EXPIRE_DATE < NOW();
    
    -- 获取影响的行数
    SET affected_rows = ROW_COUNT();
    
    -- 记录清理结果
    SELECT CONCAT('Cleaned ', affected_rows, ' expired passkey challenges') AS result;
END //
DELIMITER ;

-- 创建定时清理事件（可选）
/*
CREATE EVENT IF NOT EXISTS CleanPasskeyChallengesEvent
ON SCHEDULE EVERY 1 HOUR
DO
  CALL CleanExpiredPasskeyChallenges();
*/

-- 插入一些示例数据（仅用于测试）
/*
INSERT INTO mxk_user_passkeys (
    ID, USER_ID, CREDENTIAL_ID, PUBLIC_KEY, DISPLAY_NAME, DEVICE_TYPE, INST_ID
) VALUES (
    'test-passkey-001', 
    'admin', 
    'test-credential-id-001', 
    'test-public-key-data', 
    'Test Passkey Device', 
    'platform', 
    '1'
);
*/