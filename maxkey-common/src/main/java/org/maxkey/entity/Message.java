package org.maxkey.entity;

import org.springframework.http.ResponseEntity;

public class Message<T> {
	
	public final static int SUCCESS	= 0;	//成功
	public final static int ERROR	= 1;	//错误
	public final static int FAIL	= 2;	//失败
	public final static int INFO	= 101;	//信息
	public final static int PROMPT	= 102;	//提示
	public final static int WARNING	= 103;	//警告
	
	int code;
	
	String message;
	
	T data;

	public Message() {
		this.code = SUCCESS;
	}

	public Message(int code) {
		this.code = code;
	}
	public Message(T data) {
		this.data = data;
	}
	
	public Message(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Message(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public Message(int code, T data) {
		this.code = code;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResponseEntity<?>  buildResponse() {
		return ResponseEntity.ok(this);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}

}
