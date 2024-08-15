package org.dromara.maxkey.persistence.service;

import org.dromara.maxkey.persistence.cache.MomentaryService;
import org.dromara.maxkey.util.IdGenerator;
import org.dromara.maxkey.util.ObjectTransformer;
import org.dromara.mybatis.jpa.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;

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

    public static class STATE{
        public static final String SCANED 		= "scaned";
        public static final String CONFIRMED 	= "confirmed";
        public static final String CANCELED 	= "canceled";

        public static final String CANCEL 		= "cancel";
        public static final String CONFIRM 		= "confirm";
    }

    int validitySeconds 	= 60 * 3; //default 3 minutes.

    int cancelValiditySeconds 	= 60 * 1; //default 1 minutes.


    @Autowired
    IdGenerator idGenerator;

    @Autowired
    MomentaryService momentaryService;

    private String getKey(Long ticket) {
        return SCANCODE_TICKET.formatted(ticket);
    }

    private String getConfirmKey(Long sessionId) {
        return SCANCODE_CONFIRM.formatted(sessionId);
    }

    public Long createTicket() {
        Long ticket = 0L;
        ticket = Long.parseLong(idGenerator.generate());
        momentaryService.put(getKey(ticket), "ORCode", Duration.ofSeconds(validitySeconds));
        _logger.info("Ticket {} , Duration {}", ticket , Duration.ofSeconds(validitySeconds));
        return ticket;
    }
}
