package com.zaprnt.beans.bootstrap;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AttributeMetadata {
    private String category;
    private List<Attribute> attributes;
    private List<AttributeMetadata> subAttributeMetadata;
}
