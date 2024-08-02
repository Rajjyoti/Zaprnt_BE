package com.zaprnt.beans.common.es.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ZESSortOption {
    private String field; // Field to sort by
    private ZESSortOrder order; // Sort order: "asc" or "desc"

}
