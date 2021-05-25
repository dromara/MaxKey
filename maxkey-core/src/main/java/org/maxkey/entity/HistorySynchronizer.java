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
@Table(name = "MXK_HISTORY_SYNCHRONIZER")  
public class HistorySynchronizer  extends JpaBaseEntity  implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1184644499009162756L;
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO,generator="snowflakeid")
    String id;
    @Column
    String syncId;
    @Column
    String syncName;
    @Column
    String objectId;
    @Column
    String objectType;
    @Column
    String objectName;
    String syncTime;
    @Column
    String result;
    
    String startDate;
    String endDate;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSyncId() {
        return syncId;
    }
    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }
    public String getSyncName() {
        return syncName;
    }
    public void setSyncName(String syncName) {
        this.syncName = syncName;
    }
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getObjectType() {
        return objectType;
    }
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    public String getObjectName() {
        return objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
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
    public HistorySynchronizer() {
        super();
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
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HistorySynchronizer [id=");
        builder.append(id);
        builder.append(", syncId=");
        builder.append(syncId);
        builder.append(", syncName=");
        builder.append(syncName);
        builder.append(", objectId=");
        builder.append(objectId);
        builder.append(", objectType=");
        builder.append(objectType);
        builder.append(", objectName=");
        builder.append(objectName);
        builder.append(", syncTime=");
        builder.append(syncTime);
        builder.append(", result=");
        builder.append(result);
        builder.append("]");
        return builder.toString();
    }
    
    
}
