package com.zaprnt.beans.common.es.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ZESFilter {
    private ZESLogicalOperator logicalOperator; // Logical operator: "AND" or "OR"
    private List<ZESFilterCondition> conditions; // List of filter conditions
}
