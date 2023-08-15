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
 

package org.dromara.maxkey.provision;

public class ProvisionMessage {

	String 	id;
    String 	topic;
    String 	actionType;
    String 	sendTime;
    String  content;
    int     connected;
    int     instId;
    
    Object  sourceObject;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getConnected() {
		return connected;
	}

	public void setConnected(int connected) {
		this.connected = connected;
	}

	public Object getSourceObject() {
		return sourceObject;
	}

	public void setSourceObject(Object sourceObject) {
		this.sourceObject = sourceObject;
	}

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}

	public ProvisionMessage() {
    }

	public ProvisionMessage(String id,String topic, String actionType, String sendTime,  String content,Object sourceObject) {
		super();
		this.id = id;
		this.topic = topic;
		this.actionType = actionType;
		this.sendTime = sendTime;
		this.content = content;
		this.sourceObject = sourceObject;
	}
    
    
}
