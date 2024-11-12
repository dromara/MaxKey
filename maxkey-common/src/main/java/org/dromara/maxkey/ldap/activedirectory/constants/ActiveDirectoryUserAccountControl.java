/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.ldap.activedirectory.constants;

/** userAccountControl值得说明
 * http://support.microsoft.com/zh-cn/kb/305144
 * https://docs.microsoft.com/en-us/troubleshoot/windows-server/identity/useraccountcontrol-manipulate-account-properties
 */
public class ActiveDirectoryUserAccountControl {
	
	//Property flag				Value in hexadecimal	Value in decimal
	public static final int	SCRIPT							=0x0001			;//		1
	public static final int	ACCOUNTDISABLE					=0x0002			;//		2
	public static final int	HOMEDIR_REQUIRED				=0x0008			;//		8
	public static final int	LOCKOUT							=0x0010			;//		16
	public static final int	PASSWD_NOTREQD					=0x0020			;//		32
	public static final int	PASSWD_CANT_CHANGE				=0x0040			;//		64    		You can't assign this permission by directly modifying the UserAccountControl attribute. For information about how to set the permission programmatically, see the Property flag descriptions section.
	public static final int	ENCRYPTED_TEXT_PWD_ALLOWED		=0x0080			;//		128
	public static final int	TEMP_DUPLICATE_ACCOUNT			=0x0100			;//		256
	public static final int	NORMAL_ACCOUNT					=0x0200			;//		512
	public static final int	INTERDOMAIN_TRUST_ACCOUNT		=0x0800			;//		2048
	public static final int	WORKSTATION_TRUST_ACCOUNT		=0x1000			;//		4096
	public static final int	SERVER_TRUST_ACCOUNT			=0x2000			;//		8192
	public static final int	DONT_EXPIRE_PASSWORD			=0x10000		;//		65536
	public static final int	MNS_LOGON_ACCOUNT				=0x20000		;//		131072
	public static final int	SMARTCARD_REQUIRED				=0x40000		;//		262144
	public static final int	TRUSTED_FOR_DELEGATION			=0x80000		;//		524288
	public static final int	NOT_DELEGATED					=0x100000		;//		1048576
	public static final int	USE_DES_KEY_ONLY				=0x200000		;//		2097152
	public static final int	DONT_REQ_PREAUTH				=0x400000		;//		4194304
	public static final int	PASSWORD_EXPIRED				=0x800000		;//		8388608
	public static final int	TRUSTED_TO_AUTH_FOR_DELEGATION	=0x1000000		;//		16777216
	public static final int	PARTIAL_SECRETS_ACCOUNT			=0x04000000		;//		67108864
	
}
