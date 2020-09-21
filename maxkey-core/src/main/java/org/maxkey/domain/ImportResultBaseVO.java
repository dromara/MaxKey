package org.maxkey.domain;

import org.apache.commons.lang.StringUtils;

/**
 * @author yapeng.li
 * @date 2019.3.6
 */
public class ImportResultBaseVO {
    /**
     * 资源名
     */
    private String name;
    /**
     * 状态
     */
    private String status;
    /**
     * 描述
     */
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        if(status==0){
            this.status = "SUCCESS";
        }else if(status==1){
            this.status = "FAILURE";
        }else{
            this.status = "IGNORE";
        }
    }

    public String getDesc() {
        if(StringUtils.isEmpty(desc)){
            return "";
        }
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ImportResultBaseVO{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
