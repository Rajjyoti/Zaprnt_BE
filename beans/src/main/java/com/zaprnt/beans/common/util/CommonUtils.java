package com.zaprnt.beans.common.util;

import java.util.Collection;
import java.util.Collections;

public class CommonUtils {
    public static <T> Collection<T> nullSafeCollection(Collection<T> collection) {
        return collection != null ? collection : Collections.emptyList();
    }
}
