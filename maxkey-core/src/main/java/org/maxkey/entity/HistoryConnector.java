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
 

package org.maxkey.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseEntity;

@Entity
@Table(name = "MXK_HISTORY_CONNECTOR")  
public class HistoryConnector  extends JpaBaseEntity  implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 3465459057253994386L;
    
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO,generator="snowflakeid")
    String id;
    
    @Column
    String conName;
    
    @Column
    String conType;
    
    @Column
    String conAction;
    
    @Column
    String sourceId;
    
    @Column
    String sourceName;
    
    @Column
    String objectId;
    
    @Column
    String objectName;
    
    @Column
    String description;
    
    
    String syncTime;
    
    @Column
    String result;
    
    String startDate;
    
    String endDate;
    
	@Column
	private String instId;

	private String instName;
   
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getConName() {
        return conName;
    }


    public void setConName(String conName) {
        this.conName = conName;
    }


    public String getConType() {
        return conType;
    }


    public void setConType(String conType) {
        this.conType = conType;
    }


    public String getSourceId() {
        return sourceId;
    }


    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }


    public String getSourceName() {
        return sourceName;
    }


    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }


    public String getObjectId() {
        return objectId;
    }


    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    public String getObjectName() {
        return objectName;
    }


    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getSyncTime() {
        return syncTime;
    }


    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }


    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public String getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getConAction() {
		return conAction;
	}


	public void setConAction(String conAction) {
		this.conAction = conAction;
	}


	public String getInstId() {
		return instId;
	}


	public void setInstId(String instId) {
		this.instId = instId;
	}


	public String getInstName() {
		return instName;
	}


	public void setInstName(String instName) {
		this.instName = instName;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HistoryConnector [id=");
		builder.append(id);
		builder.append(", conName=");
		builder.append(conName);
		builder.append(", conType=");
		builder.append(conType);
		builder.append(", conAction=");
		builder.append(conAction);
		builder.append(", sourceId=");
		builder.append(sourceId);
		builder.append(", sourceName=");
		builder.append(sourceName);
		builder.append(", objectId=");
		builder.append(objectId);
		builder.append(", objectName=");
		builder.append(objectName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", syncTime=");
		builder.append(syncTime);
		builder.append(", result=");
		builder.append(result);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}
    
    
}
