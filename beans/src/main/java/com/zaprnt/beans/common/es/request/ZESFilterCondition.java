package com.zaprnt.beans.common.es.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ZESFilterCondition {
    private String field; // Field to filter on
    private Object value; // Value to match
    private ZESOperation operation; // Operation: "=", ">", "<", etc.
    private ZESLogicalOperator logicalOperator; // Logical operator for nested conditions
    private List<ZESFilterCondition> conditions; // Nested filter conditions
}
