package com.zaprnt.beans.common.es.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ZESRequest {
    private ZESFilter filter;
    private List<ZESSortOption> sortOptions;
    private int page;
    private int size;
}
