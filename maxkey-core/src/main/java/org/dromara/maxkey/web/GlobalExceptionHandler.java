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
 

package org.dromara.maxkey.web;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.UnexpectedTypeException;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: orangeBabu
 * @time: 16/8/2024 PM3:02
 */

/**
 * 全局异常处理器
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 缺少请求体异常处理器
     * @param e 缺少请求体异常 使用get方式请求 而实体使用@RequestBody修饰
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Message<Void> parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求地址'{}',请求体缺失'{}'", requestURI, e.getMessage(),e);
        return new Message<>(Message.FAIL, "缺少请求体");
    }

    // get请求的对象参数校验异常
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Message<Void> bindExceptionHandler(MissingServletRequestParameterException e,HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求地址'{}',get方式请求参数'{}'必传", requestURI, e.getMessage(),e);
        return new Message<>(Message.FAIL, "请求的对象参数校验异常");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Message<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求地址 '{}',不支持'{}' 请求", requestURI, e.getMethod(),e);
        return new Message<>(HttpStatus.METHOD_NOT_ALLOWED.value(),HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }




    /**
     * 参数不正确
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Message<Void> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String error = String.format("%s 应该是 %s 类型", e.getName(), e.getRequiredType().getSimpleName());
        logger.error("请求地址'{}',{},参数类型不正确", requestURI,error,e);
        return new Message<>(Message.FAIL, "参数类型不正确");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Message<Void> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.info("Request IpAddress : {} " , WebContext.getRequestIpAddress(request));
        if(e instanceof NoHandlerFoundException) {
        	//NoHandlerFoundException
        }else {
        	logger.error("请求地址'{}',发生系统异常.", requestURI, e);
        }
        return new Message<>(Message.FAIL, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    /**
     * 捕获转换类型异常
     * @param e
     * @return
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    public Message<String> unexpectedTypeHandler(UnexpectedTypeException e)
    {
        logger.error("类型转换错误：{}",e.getMessage(), e);
        return  new Message<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
    }

    /**
     * 捕获转换类型异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Message<String> methodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        BindingResult bindingResult =  e.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        logger.error("参数验证异常：{}",e.getMessage(), e);
        if (!errors.isEmpty()) {
            // 只显示第一个错误信息
            return new Message<>(HttpStatus.BAD_REQUEST.value(), errors.get(0).getDefaultMessage());
        }
        return new Message<>(HttpStatus.BAD_REQUEST.value(),"MethodArgumentNotValid");
    }

    // 运行时异常
    @ExceptionHandler(RuntimeException.class)
    public Message<String> runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求地址'{}',捕获运行时异常'{}'", requestURI, e.getMessage(),e);
        return new Message<>(Message.FAIL, e.getMessage());
    }
    // 系统级别异常
    @ExceptionHandler(Throwable.class)
    public Message<String> throwableExceptionHandler(Throwable e,HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求地址'{}',捕获系统级别异常'{}'", requestURI,e.getMessage(),e);
        return new Message<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * IllegalArgumentException 捕获转换类型异常
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Message<String> illegalArgumentException(IllegalArgumentException e)
    {
        String message = e.getMessage();
        logger.error("IllegalArgumentException：{}",e.getMessage(),e);
        if (Objects.nonNull(message)) {
            //错误信息
            return new Message<>(HttpStatus.BAD_REQUEST.value(),message);
        }
        return  new Message<>(HttpStatus.BAD_REQUEST.value(),"error");
    }
    /**
     * InvalidFormatException 捕获转换类型异常
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidFormatException.class)
    public Message<String> invalidFormatException(InvalidFormatException e)
    {
        String message = e.getMessage();
        logger.error("InvalidFormatException：{}",e.getMessage(),e);
        if (Objects.nonNull(message)) {
            //错误信息
            return new Message<>(HttpStatus.BAD_REQUEST.value(),message);
        }
        return new Message<>(HttpStatus.BAD_REQUEST.value(),"error");
    }



    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Message<Void> handleBindException(BindException e) {
        BindingResult bindingResult =  e.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        logger.error("参数验证异常：{}",e.getMessage(), e);
        if (!errors.isEmpty()) {
            // 只显示第一个错误信息
            return new Message<>(HttpStatus.BAD_REQUEST.value(), errors.get(0).getDefaultMessage());
        }
        return new Message<>(HttpStatus.BAD_REQUEST.value(),"MethodArgumentNotValid");
    }

    /**
     * 业务异常处理
     * 业务自定义code 与 message
     *
     */
    @ExceptionHandler(BusinessException.class)
    public Message<String> handleBusinessException(BusinessException e) {
        logger.error("业务自定义异常:{},{}",e.getCode(),e.getMessage(),e);
        return new Message<>(e.getCode(),e.getMessage());
    }
}
