

package org.dromara.maxkey.constants;

/**
 * 二次认证验证码
 */
public class ConstsTwoFactor {

	/**
	 * 无
	 */
    public static final int NONE 				= 0;
    
    /**
     * 动态令牌TOTP
     */
    public static final int TOTP 				= 1;
    /**
     * 邮件验证码
     */
    public static final int EMAIL 				= 2;
    /**
     * 短信验证码
     */
    public static final int SMS 				= 3;
}
