package org.dromara.maxkey.entity.dto;

public record TimeBasedDto(String displayName,String username,int digits,int period,String sharedSecret,String qrCode,String otpCode) {

}
