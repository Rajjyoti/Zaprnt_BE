package com.zaprnt.beans.common.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseMongoBean extends BaseAuditableBean {
    @Id
    private String id;
}
