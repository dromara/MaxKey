/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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


package org.dromara.maxkey.synchronizer.reorg;

import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.synchronizer.workweixin.service.ReorgDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReorgDeptSynchronizerService implements ISynchronizerService {
    final static Logger _logger = LoggerFactory.getLogger(ReorgDeptSynchronizerService.class);
    Synchronizers synchronizer;

    @Autowired
    ReorgDeptService reorgDeptService;


    public ReorgDeptSynchronizerService() {
        super();
    }

    public void sync() throws Exception {
        _logger.info("Sync ...");
        reorgDeptService.setSynchronizer(synchronizer);
        reorgDeptService.sync();

    }

 

    public void setReorgDeptService(ReorgDeptService reorgDeptService) {
		this.reorgDeptService = reorgDeptService;
	}

	@Override
    public void setSynchronizer(Synchronizers synchronizer) {
        this.synchronizer = synchronizer;

    }

}
