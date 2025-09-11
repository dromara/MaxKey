-- MaxKey Passkey 模块数据库表创建脚本
-- 创建用户 Passkey 表和挑战表

-- 用户 Passkey 表
CREATE TABLE IF NOT EXISTS mxk_user_passkeys (
    id VARCHAR(40) NOT NULL COMMENT '主键ID',
    user_id VARCHAR(40) NOT NULL COMMENT '用户ID',
    credential_id VARCHAR(500) NOT NULL COMMENT '凭据ID（Base64编码）',
    public_key TEXT NOT NULL COMMENT '公钥数据（Base64编码）',
    display_name VARCHAR(100) COMMENT '显示名称',
    device_type VARCHAR(50) DEFAULT 'unknown' COMMENT '设备类型',
    signature_count BIGINT DEFAULT 0 COMMENT '签名计数器',
    created_date DATETIME NOT NULL COMMENT '创建时间',
    last_used_date DATETIME COMMENT '最后使用时间',
    aaguid VARCHAR(100) COMMENT 'AAGUID',
    inst_id VARCHAR(40) DEFAULT '1' COMMENT '机构ID',
    status INT DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    PRIMARY KEY (id),
    UNIQUE KEY uk_credential_id (credential_id),
    KEY idx_user_id (user_id),
    KEY idx_inst_id (inst_id),
    KEY idx_created_date (created_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户Passkey凭据表';

-- Passkey 挑战表
CREATE TABLE IF NOT EXISTS mxk_passkey_challenges (
    id VARCHAR(40) NOT NULL COMMENT '挑战ID',
    user_id VARCHAR(40) COMMENT '用户ID（认证时可为空）',
    challenge TEXT NOT NULL COMMENT '挑战数据（Base64编码）',
    challenge_type VARCHAR(20) NOT NULL COMMENT '挑战类型：REGISTRATION-注册，AUTHENTICATION-认证',
    created_date DATETIME NOT NULL COMMENT '创建时间',
    expires_date DATETIME NOT NULL COMMENT '过期时间',
    status INT DEFAULT 0 COMMENT '状态：0-未使用，1-已使用',
    inst_id VARCHAR(40) DEFAULT '1' COMMENT '机构ID',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_challenge_type (challenge_type),
    KEY idx_expires_date (expires_date),
    KEY idx_inst_id (inst_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Passkey挑战表';

-- 创建索引以优化查询性能
CREATE INDEX idx_user_passkeys_user_status ON mxk_user_passkeys(user_id, status);
CREATE INDEX idx_passkey_challenges_expires_status ON mxk_passkey_challenges(expires_date, status);
CREATE INDEX idx_passkey_challenges_user_type ON mxk_passkey_challenges(user_id, challenge_type);

-- 插入示例数据（可选）
-- INSERT INTO mxk_user_passkeys (id, user_id, credential_id, public_key, display_name, device_type, signature_count, created_date, inst_id) 
-- VALUES ('test_passkey_1', 'test_user_1', 'test_credential_id_1', 'test_public_key_1', 'Test Device', 'platform', 0, NOW(), '1');

COMMIT;