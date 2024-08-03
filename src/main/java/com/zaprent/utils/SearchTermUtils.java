package com.zaprent.utils;

import com.zaprnt.beans.common.es.request.*;

import java.util.Collections;
import java.util.List;

public class SearchTermUtils {
    public static ZESRequest createESRequestForRecommendedSearchStrings(String searchString) {
        return new ZESRequest().setFilter(getFilterForSearchTerm(searchString)).setSortOptions(setSortOptionsForSearchTerm()).setSize(10).setPage(0);
    }

    private static ZESFilter getFilterForSearchTerm(String searchString) {
        return new ZESFilter()
                .setLogicalOperator(ZESLogicalOperator.AND)
                .setConditions(getConditionForSearchTerm(searchString));
    }

    private static List<ZESFilterCondition> getConditionForSearchTerm(String searchString) {
        ZESFilterCondition condition = new ZESFilterCondition().setField("term").setValue(searchString).setOperation(ZESOperation.MATCH);
        return Collections.singletonList(condition);
    }

    private static List<ZESSortOption> setSortOptionsForSearchTerm() {
        ZESSortOption sortOption = new ZESSortOption().setField("frequency").setOrder(ZESSortOrder.DESC);
        return Collections.singletonList(sortOption);
    }
}
