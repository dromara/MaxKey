package org.dromara.maxkey.exception;

import java.io.Serial;

/**
 * @description:
 * @author: orangeBabu
 * @time: 16/8/2024 PM3:03
 */
public class BusinessException extends RuntimeException {
    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String message;


    public BusinessException() {
        super();
    }

    public BusinessException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
