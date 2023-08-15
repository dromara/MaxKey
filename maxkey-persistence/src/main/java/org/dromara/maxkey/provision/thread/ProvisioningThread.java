/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

package org.dromara.maxkey.provision.thread;

import java.io.Serializable;
import java.sql.Types;

import org.dromara.maxkey.pretty.impl.JsonPretty;
import org.dromara.maxkey.provision.ProvisionMessage;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.util.ObjectTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Provisioning Thread for send message
 *
 */
public class ProvisioningThread extends Thread{
	private static final Logger _logger = LoggerFactory.getLogger(ProvisioningThread.class);
    
	static final String PROVISION_INSERT_STATEMENT = "insert into mxk_history_provisions(id,topic,actiontype,content,sendtime,connected,instid) values (? , ? , ? , ? , ? , ?  , ? )";
	
	JdbcTemplate jdbcTemplate;
    
    ProvisionMessage msg;
    
    public ProvisioningThread(JdbcTemplate jdbcTemplate,
    		ProvisionMessage msg) {
    	this.jdbcTemplate = jdbcTemplate;
        this.msg = msg;
    }

    @Override
    public void run() {
    	_logger.debug("send message \n{}" ,new JsonPretty().jacksonFormat(msg.getSourceObject()));
    	msg.setContent(ObjectTransformer.serialize((Serializable)msg.getSourceObject()));
    	Inst inst = JsonUtils.gsonStringToObject(JsonUtils.gsonToString(msg.getSourceObject()), Inst.class);
    	jdbcTemplate.update(PROVISION_INSERT_STATEMENT,
                new Object[] { 
                		msg.getId(), msg.getTopic(), msg.getActionType(), msg.getContent(),
                		msg.getSendTime(),msg.getConnected(),inst.getInstId()
                        },
                new int[] { 
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
                        Types.TINYINT,Types.TINYINT
                        });
        _logger.debug("send to Message Queue finished .");
    }
    
    class Inst{
    	
    	int instId;

		public int getInstId() {
			return instId;
		}

		public void setInstId(int instId) {
			this.instId = instId;
		}

		public Inst() {}
    }
}
