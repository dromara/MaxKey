package org.maxkey.crypto.password.opt;

public class OneTimePassword {
    private String id;
    private int type;
    private String token;
    private String username;
    private String receiver;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OneTimePassword [id=").append(id)
            .append(", type=").append(type).append(", token=").append(token)
            .append(", username=").append(username).append(", receiver=").append(receiver)
                .append(", createTime=").append(createTime).append("]");
        return builder.toString();
    }

}
