package com.zaprnt.beans.bootstrap;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Attribute {
    private String key;
    private String type;
}
