/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

/**
 * 
 */
package org.maxkey.web.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * message类定义
 * 
 * @author Crystal.Sea
 *
 */
public class Message {
    static final  Logger _logger = LoggerFactory.getLogger(Message.class);
    // 服务名称
    private String serviceName;
    // 信息内容
    private String message;
    // 信息代码
    private String code;
    // 信息对象
    private Object messageObject;
    // 错误信息
    private ArrayList<HashMap<String, Object>> errors;
    // 类型
    private MessageType messageType = MessageType.info;
    // 操作类型
    private OperateType operateType = OperateType.unknown;
    // 范围
    MessageScope messageScope = MessageScope.JSON;

    public Message() {

    }

    public Message(String message) {
        this.message = message;
        this.messageType = MessageType.info;
    }

    public Message(BindingResult result) {
        setFieldErrors(result);
    }

    public Message(String message, String code) {
        this.message = message;
        this.code = code;
        this.messageType = MessageType.info;
    }

    public Message(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public Message(String message, BindingResult result) {
        this.message = message;
        setFieldErrors(result);
    }

    public Message(String message, String code, MessageType messageType) {
        this.message = message;
        this.code = code;
        this.messageType = messageType;
    }

    public Message(String message, Object messageObject, MessageType messageType, OperateType operateType) {
        this.message = message;
        this.messageType = messageType;
        this.operateType = operateType;
        this.messageObject = messageObject;
        WebContext.setMessage(this);
    }

    public Message(String message, Object messageObject, MessageType messageType, OperateType operateType,
            MessageScope messageScope) {
        this.message = message;
        this.messageObject = messageObject;
        this.messageType = messageType;
        this.operateType = operateType;
        this.messageScope = messageScope;
        WebContext.setMessage(this);
    }

    public Message(String message, Object messageObject, BindingResult result, MessageType messageType,
            OperateType operateType, MessageScope messageScope) {
        this.message = message;
        this.messageObject = messageObject;
        this.operateType = operateType;
        this.messageScope = messageScope;
        setFieldErrors(result);
        this.messageType = messageType;
        WebContext.setMessage(this);
    }

    public Message(String serviceName, String message, Object messageObject, BindingResult result,
            MessageType messageType, OperateType operateType, MessageScope messageScope) {
        this.serviceName = serviceName;
        this.message = message;
        this.messageObject = messageObject;
        this.operateType = operateType;
        this.messageScope = messageScope;
        setFieldErrors(result);
        this.messageType = messageType;
        WebContext.setMessage(this);
    }

    public Message(String serviceName, String message, Object messageObject, BindingResult result,
            MessageType messageType, OperateType operateType, MessageScope messageScope, String code) {
        this(serviceName, message, messageObject, result, messageType, operateType, messageScope);
        this.code = code;
    }

    /**
     * 验证错误组装
     * 
     * @param result
     */
    public void setFieldErrors(BindingResult result) {
        if (result == null)
            return;
        this.messageType = MessageType.error;
        this.errors = new ArrayList<HashMap<String, Object>>();
        List<FieldError> listFieldError = result.getFieldErrors();

        for (FieldError fieldError : listFieldError) {
            HashMap<String, Object> error = new HashMap<String, Object>();
            error.put("field", fieldError.getField());
            error.put("type", fieldError.getCode());
            error.put("objectName", fieldError.getObjectName());
            String defaultMessageSourceResolvable = fieldError.getCodes()[0];
            String errorMessage = WebContext.getI18nValue(defaultMessageSourceResolvable);
            if (errorMessage == null) {
                error.put("message", /* fieldError.getField()+" "+ */fieldError.getDefaultMessage());
            } else {
                error.put("message", errorMessage);
            }
            _logger.debug("" + error);
            this.errors.add(error);
        }
        _logger.debug("" + this.errors);
    }

    public void setApplication() {
        WebContext.setMessage(this);
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public ArrayList<HashMap<String, Object>> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<HashMap<String, Object>> errors) {
        this.errors = errors;
    }

    public OperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    public Object getMessageObject() {
        return messageObject;
    }

    public void setMessageObject(Object messageObject) {
        this.messageObject = messageObject;
    }

    public MessageScope getMessageScope() {
        return messageScope;
    }

    public void setMessageScope(MessageScope messageScope) {
        this.messageScope = messageScope;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
