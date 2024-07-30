package com.zaprnt.beans.bootstrap;

import com.zaprnt.beans.enums.Category;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CategoryMetadata {
    private Category category;
    private List<CategoryMetadata> subCategories;
}
