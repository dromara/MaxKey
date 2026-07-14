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


package org.dromara.maxkey.web.config.controller;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.*;
import org.dromara.maxkey.persistence.service.SynchroAssociationService;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = {"/config/synchronizers"})
public class SynchroAssociationController {
    static final Logger logger = LoggerFactory.getLogger(SynchroAssociationController.class);

    @Autowired
    SynchroAssociationService synchroAssociationService;

    @RequestMapping(value = {"/mapping-list/{syncId}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> mapping(@PathVariable Long syncId,@CurrentUser UserInfo currentUser) {
        logger.debug("mapping {}", syncId);
        List<SynchroAssociation> synchroAssociation = synchroAssociationService.findBySyncId(syncId,currentUser.getInstId());
        return new Message<>(synchroAssociation).buildResponse();
    }

    @RequestMapping(value = {"/mapping-get/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> mappingGet(@PathVariable Long id, @CurrentUser UserInfo currentUser) {
        logger.debug("mapping get {}", id);
        SynchroAssociation synchroFieldMap = synchroAssociationService.get(String.valueOf(id),currentUser.getInstId());
        return new Message<>(synchroFieldMap).buildResponse();
    }

    @RequestMapping(value = {"/mapping-delete/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> mappingDelete(@PathVariable Long id, @CurrentUser UserInfo currentUser) {
        logger.debug("mappingDelete {}", id);
        synchroAssociationService.delete(String.valueOf(id),currentUser.getInstId());
        return new Message<SynchroAssociation>(Message.SUCCESS).buildResponse();
    }

    @ResponseBody
    @PostMapping(value = {"/mapping-add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> mappingadd(@RequestBody SynchroAssociation synchroAssociation, @CurrentUser UserInfo currentUser) {
        logger.debug("-mapping add  : {}", synchroAssociation);
        synchroAssociation.setCreateTime(new Date());
        synchroAssociation.setInstId(currentUser.getInstId());
        if (synchroAssociationService.insert(synchroAssociation)) {
            return new Message<Synchronizers>(Message.SUCCESS).buildResponse();
        } else {
            return new Message<Synchronizers>(Message.FAIL).buildResponse();
        }
    }

    @ResponseBody
    @PutMapping(value = {"/mapping-update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> mappingupdate(@RequestBody SynchroAssociation synchroAssociation, @CurrentUser UserInfo currentUser) {
        logger.debug("-mapping update  : {}", synchroAssociation);
        synchroAssociation.setUpdateTime(new Date());
        synchroAssociation.setInstId(currentUser.getInstId());
        if (synchroAssociationService.update(synchroAssociation)) {
            return new Message<Synchronizers>(Message.SUCCESS).buildResponse();
        } else {
            return new Message<Synchronizers>(Message.FAIL).buildResponse();
        }
    }

}
