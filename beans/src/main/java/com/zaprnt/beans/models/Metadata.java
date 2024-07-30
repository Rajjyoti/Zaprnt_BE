package com.zaprnt.beans.models;

import com.zaprnt.beans.bootstrap.AttributeMetadata;
import com.zaprnt.beans.bootstrap.CategoryMetadata;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@Document
public class Metadata {
    @Id
    private String id;
    @Indexed(unique = true)
    private String type;
    private List<AttributeMetadata> attributeMetadata;
    private List<CategoryMetadata> categoryMetadata;
}
