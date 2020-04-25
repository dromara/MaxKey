package org.maxkey.domain.apps;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Crystal.Sea
 *
 */
@Table(name = "APPS_SAML_V20_DETAILS")
public class AppsSAML20Details extends Apps {

    /**
     * 
     */
    private static final long serialVersionUID = -291159876339333345L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    protected String id;
    @Column
    private String certIssuer;
    @Column
    private String certSubject;
    @Column
    private String certExpiration;
    @Column
    private byte[] keyStore;
    @Column
    private String entityId;
    @Column
    private String spAcsUrl;
    @Column
    private String issuer;
    @Column
    private String audience;
    @Column
    private String nameidFormat;
    @Column
    private String validityInterval;
    /**
     * Redirect-Post Post-Post IdpInit-Post Redirect-PostSimpleSign
     * Post-PostSimpleSign IdpInit-PostSimpleSign
     */
    @Column
    private String binding;

    /**
     * 0 false 1 true
     */
    @Column
    private int encrypted;

    /**
     * for upload
     */
    private MultipartFile certMetaFile;
    /**
     * metadata or certificate
     */
    private String fileType;

    /**
     * 0 original 1 uppercase 2 lowercase
     */
    @Column
    private int nameIdConvert;

    public static class BINDINGTYPE {
        public String Redirect_Post = "Redirect-Post";
        public String Post_Post = "Post-Post";
        public String IdpInit_Post = "IdpInit-Post";
        public String Redirect_PostSimpleSign = "Redirect-PostSimpleSign";
        public String Post_PostSimpleSign = "Post-PostSimpleSign";
        public String IdpInit_PostSimpleSign = "IdpInit-PostSimpleSign";
    }

    /**
     * 
     */
    public AppsSAML20Details() {
        super();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the certIssuer
     */
    public String getCertIssuer() {
        return certIssuer;
    }

    /**
     * @param certIssuer the certIssuer to set
     */
    public void setCertIssuer(String certIssuer) {
        this.certIssuer = certIssuer;
    }

    /**
     * @return the certSubject
     */
    public String getCertSubject() {
        return certSubject;
    }

    /**
     * @param certSubject the certSubject to set
     */
    public void setCertSubject(String certSubject) {
        this.certSubject = certSubject;
    }

    /**
     * @return the certExpiration
     */
    public String getCertExpiration() {
        return certExpiration;
    }

    /**
     * @param certExpiration the certExpiration to set
     */
    public void setCertExpiration(String certExpiration) {
        this.certExpiration = certExpiration;
    }

    /**
     * @return the keyStore
     */
    public byte[] getKeyStore() {
        return keyStore;
    }

    /**
     * @param keyStore the keyStore to set
     */
    public void setKeyStore(byte[] keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * @return the entityId
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     * @return the spAcsUrl
     */
    public String getSpAcsUrl() {
        return spAcsUrl;
    }

    /**
     * @param spAcsUrl the spAcsUrl to set
     */
    public void setSpAcsUrl(String spAcsUrl) {
        this.spAcsUrl = spAcsUrl;
    }

    /**
     * @return the issuer
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @param issuer the issuer to set
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * @return the audience
     */
    public String getAudience() {
        return audience;
    }

    /**
     * @param audience the audience to set
     */
    public void setAudience(String audience) {
        this.audience = audience;
    }

    /**
     * @return the nameidFormat
     */
    public String getNameidFormat() {
        return nameidFormat;
    }

    /**
     * @param nameidFormat the nameidFormat to set
     */
    public void setNameidFormat(String nameidFormat) {
        this.nameidFormat = nameidFormat;
    }

    /**
     * @return the validityInterval
     */
    public String getValidityInterval() {
        return validityInterval;
    }

    /**
     * @param validityInterval the validityInterval to set
     */
    public void setValidityInterval(String validityInterval) {
        this.validityInterval = validityInterval;
    }

    /**
     * @return the certMetaFile
     */
    public MultipartFile getCertMetaFile() {
        return certMetaFile;
    }

    /**
     * @param certMetaFile the certMetaFile to set
     */
    public void setCertMetaFile(MultipartFile certMetaFile) {
        this.certMetaFile = certMetaFile;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public int getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(int encrypted) {
        this.encrypted = encrypted;
    }

    public int getNameIdConvert() {
        return nameIdConvert;
    }

    public void setNameIdConvert(int nameIdConvert) {
        this.nameIdConvert = nameIdConvert;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SAMLBaseDetails [certIssuer=" + certIssuer + ", certSubject=" + certSubject + ", certExpiration="
                + certExpiration + ", keyStore=" + Arrays.toString(keyStore) + ", entityId=" + entityId + ", spAcsUrl="
                + spAcsUrl + ", issuer=" + issuer + ", audience=" + audience + ", nameidFormat=" + nameidFormat
                + ", validityInterval=" + validityInterval + ", binding=" + binding + ", encrypted=" + encrypted
                + ", certMetaFile=" + certMetaFile + ", fileType=" + fileType + ", nameIdConvert=" + nameIdConvert
                + "]";
    }

}
