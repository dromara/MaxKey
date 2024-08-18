package org.dromara.maxkey.authn.provider.scancode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.exception.BusinessException;
import org.dromara.maxkey.persistence.cache.MomentaryService;
import org.dromara.maxkey.util.IdGenerator;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.util.TimeJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;

/**
 * @description:
 * @author: orangeBabu
 * @time: 15/8/2024 AM9:49
 */

@Repository
public class ScanCodeService {
    private static final Logger _logger = LoggerFactory.getLogger(ScanCodeService.class);

    static final String SCANCODE_TICKET = "login:scancode:%s";

    static final String SCANCODE_CONFIRM = "login:scancode:confirm:%s";

    public static class STATE {
        public static final String SCANED = "scaned";
        public static final String CONFIRMED = "confirmed";
        public static final String CANCELED = "canceled";

        public static final String CANCEL = "cancel";
        public static final String CONFIRM = "confirm";
    }

    int validitySeconds = 60 * 3; //default 3 minutes.

    int cancelValiditySeconds = 60 * 1; //default 1 minutes.


    @Autowired
    IdGenerator idGenerator;

    @Autowired
    MomentaryService momentaryService;

    private String getKey(String ticket) {
        return SCANCODE_TICKET.formatted(ticket);
    }

    private String getConfirmKey(Long sessionId) {
        return SCANCODE_CONFIRM.formatted(sessionId);
    }

    public String createTicket() {
        String ticket = idGenerator.generate();
        ScanCodeState scanCodeState = new ScanCodeState();
        scanCodeState.setState("unscanned");

        // 将对象序列化为 JSON 字符串
        String jsonString = TimeJsonUtils.gsonToString(scanCodeState);
        momentaryService.put(getKey(ticket), "", jsonString);
        _logger.info("Ticket {} , Duration {}", ticket, jsonString);

        return ticket;
    }

    public boolean validateTicket(String ticket, Session session) {
        String key = getKey(ticket);
        Object value = momentaryService.get(key, "");
        if (Objects.isNull(value)) {
            return false;
        }

        ScanCodeState scanCodeState = new ScanCodeState();
        scanCodeState.setState("scanned");
        scanCodeState.setTicket(ticket);
        scanCodeState.setSessionId(session.getId());
        momentaryService.put(key, "", TimeJsonUtils.gsonToString(scanCodeState));

        return true;
    }

    public ScanCodeState consume(String ticket){
        String key = getKey(ticket);
        Object o = momentaryService.get(key, "");
        if (Objects.nonNull(o)) {
            String redisObject = o.toString();
            ScanCodeState scanCodeState = TimeJsonUtils.gsonStringToObject(redisObject, ScanCodeState.class);
            if ("scanned".equals(scanCodeState.getState())) {
                momentaryService.remove(key, "");
                return scanCodeState;
            } else {
                return null;
            }
        } else {
            throw new BusinessException(20004, "该二维码失效");
        }
    }
}
