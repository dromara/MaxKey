package org.maxkey.domain.apps;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "APPS_CAS_DETAILS")
public class AppsCasDetails extends Apps {

    /**
     * 
     */
    private static final long serialVersionUID = -4272290765948322084L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    private String id;
    @Column
    private String service;
    @Column
    private String callbackUrl;

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }




}
