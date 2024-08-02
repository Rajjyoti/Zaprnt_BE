package com.zaprnt.beans.common.mongo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseAuditableBean {
    private String createdBy;
    private String lastModifiedBy;
    private boolean deleted;
    private Long createdTime;
    private Long modifiedTime;

    public BaseAuditableBean(){
        this.initializeAuditFields();
    }

    public void initializeAuditFields() {
        if (this.getCreatedTime() == null) {
            this.setCreatedTime(System.currentTimeMillis());
        }
        if (this.getModifiedTime() == null) {
            this.setModifiedTime(this.getCreatedTime());
        } else {
            this.setModifiedTime(System.currentTimeMillis());
        }
    }
}
