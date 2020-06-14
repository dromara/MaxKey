package org.maxkey.identity.kafka;

public class KafkaMessage {
    String topic;
    String actionType;
    String sendTime;
    String msgId;
    String content;
    
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
    public String getMsgId() {
        return msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    public Object getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public KafkaMessage() {
    }
}
