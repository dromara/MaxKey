package org.maxkey.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yapeng.li
 * @since 2020/9/21 21:12
 */
public class ImportResultVO {

    /**
     * 成功结果
     */
    List<ImportResultBaseVO> success;
    /**
     * 失败结果
     */
    List<ImportResultBaseVO> error;
    /**
     * 忽略结果
     */
    List<ImportResultBaseVO> ignore;
    /**
     * 成功次数
     */
    Integer successCount;
    /**
     * 失败次数
     */
    Integer errorCount;
    /**
     * 忽略次数
     */
    Integer ignoreCount;

    public List<ImportResultBaseVO> getSuccess() {
        if(success==null){
            success = new ArrayList<>();
        }
        return success;
    }

    public void setSuccess(List<ImportResultBaseVO> success) {
        this.success = success;
    }

    public List<ImportResultBaseVO> getError() {
        if(error==null){
            error = new ArrayList<>();
        }
        return error;
    }

    public void setError(List<ImportResultBaseVO> error) {
        this.error = error;
    }

    public List<ImportResultBaseVO> getIgnore() {
        if(ignore==null){
            ignore = new ArrayList<>();
        }
        return ignore;
    }

    public void setIgnore(List<ImportResultBaseVO> ignore) {
        this.ignore = ignore;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getIgnoreCount() {
        return ignoreCount;
    }

    public void setIgnoreCount(Integer ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    @Override
    public String toString() {
        return "ImportResultVO{" +
                "success=" + success +
                ", error=" + error +
                ", ignore=" + ignore +
                ", successCount=" + successCount +
                ", errorCount=" + errorCount +
                ", ignoreCount=" + ignoreCount +
                '}';
    }
}
