package org.dromara.maxkey.authn.provider.scancode;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.maxkey.authn.session.Session;

/**
 * @description:
 * @author: orangeBabu
 * @time: 16/8/2024 PM5:42
 */
public class ScanCodeState {

    String sessionId;

    String ticket;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long confirmKey;

    String state;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getConfirmKey() {
        return confirmKey;
    }

    public void setConfirmKey(Long confirmKey) {
        this.confirmKey = confirmKey;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
