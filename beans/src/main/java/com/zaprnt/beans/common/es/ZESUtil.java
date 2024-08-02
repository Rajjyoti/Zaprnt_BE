package com.zaprnt.beans.common.es;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.json.JsonData;
import com.zaprnt.beans.common.es.request.ZESFilter;
import com.zaprnt.beans.common.es.request.ZESFilterCondition;
import com.zaprnt.beans.common.es.request.ZESLogicalOperator;
import com.zaprnt.beans.common.es.request.ZESRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.zaprnt.beans.common.util.CommonUtils.nullSafeCollection;
import static java.util.Objects.nonNull;

public class ZESUtil {

    public static SearchRequest buildSearchRequest(ZESRequest esRequest, String esIndex) {
        // Build the query
        Query query = buildFilterQuery(esRequest);

        // Build the search request
        return SearchRequest.of(s -> s
                .index(esIndex)
                .query(query)
                .from(esRequest.getPage() * esRequest.getSize())
                .size(esRequest.getSize())
                .sort(esRequest.getSortOptions().stream()
                        .map(sortOption -> SortOptions.of(so -> so
                                .field(FieldSort.of(fs -> fs
                                        .field(sortOption.getField())
                                        .order(SortOrder.valueOf(sortOption.getOrder().getValue()))))))
                        .collect(Collectors.toList()))
        );
    }

//    private static Query buildQuery(ZESRequest esRequest) {
//        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
//
//        if (esRequest.getFilter() != null) {
//            if (esRequest.getFilter().getConditions().size() <= 1) {
//                return buildFilterQuery(esRequest.getFilter());
//            }
//            if (esRequest.getFilter().getLogicalOperator() == null || esRequest.getFilter().getLogicalOperator() == ZESLogicalOperator.AND) {
//                boolQueryBuilder.must(buildFilterQuery(esRequest.getFilter()));
//            } else {
//                boolQueryBuilder.should(buildFilterQuery(esRequest.getFilter()));
//            }
//        }
//
//        return boolQueryBuilder.build()._toQuery();
//    }

    private static Query buildFilterQuery(ZESRequest esRequest) {
        ZESFilter filter = esRequest.getFilter();
        // Create a builder for the top-level BoolQuery
        BoolQuery.Builder topLevelBoolQueryBuilder = new BoolQuery.Builder();

        for (ZESFilterCondition condition : nullSafeCollection(filter.getConditions())) {
            if (condition == null) {
                continue;
            }

            // Build the query for the current condition
            Query parentQuery = buildConditionQuery(condition);

            //TODO: Handle nested conditions
//            BoolQuery.Builder nestedBoolQueryBuilder = new BoolQuery.Builder();
//            if (condition.getConditions() != null && !condition.getConditions().isEmpty()) {
//                // Build the nested query
//                Query nestedQuery = buildFilterQuery(new ZESFilter()
//                        .setLogicalOperator(condition.getLogicalOperator())
//                        .setConditions(condition.getConditions()));
//
//                // Create a BoolQuery to combine the parent query with the nested query
//                if (condition.getLogicalOperator() == ZESLogicalOperator.OR) {
//                    nestedBoolQueryBuilder.should(parentQuery);
//                    nestedBoolQueryBuilder.should(nestedQuery);
//                } else {
//                    nestedBoolQueryBuilder.must(parentQuery);
//                    nestedBoolQueryBuilder.must(nestedQuery);
//                }
//                // Add the combined query to the top-level BoolQuery
//                topLevelBoolQueryBuilder.must(nestedBoolQueryBuilder.build()._toQuery());
//            } else {
            // No nested conditions, just add the current condition
            if (filter.getLogicalOperator() == ZESLogicalOperator.OR) {
                topLevelBoolQueryBuilder.should(parentQuery);
            } else {
                topLevelBoolQueryBuilder.must(parentQuery);
            }
        }

        BoolQuery.Builder searchTextBoolQuery = null;
        if (esRequest.getSearchText() != null && !esRequest.getSearchText().isEmpty()) {
            searchTextBoolQuery = new BoolQuery.Builder();

            searchTextBoolQuery.should(MatchQuery.of(mq -> mq
                    .field("searchText")
                    .query(esRequest.getSearchText().toLowerCase())
            )._toQuery());

            searchTextBoolQuery.should(MatchPhraseQuery.of(mpq -> mpq
                    .field("searchText")
                    .query(esRequest.getSearchText().toLowerCase())
            )._toQuery());

            searchTextBoolQuery.should(WildcardQuery.of(wq -> wq
                    .field("searchText")
                    .value("*" + esRequest.getSearchText().toLowerCase() + "*")
            )._toQuery());
        }

        BoolQuery.Builder combinedQueryBuilder = new BoolQuery.Builder();
        if (nonNull(searchTextBoolQuery)) {
            combinedQueryBuilder.must(topLevelBoolQueryBuilder.build()._toQuery());
            combinedQueryBuilder.must(searchTextBoolQuery.build()._toQuery());
            return combinedQueryBuilder.build()._toQuery();
        }

        return topLevelBoolQueryBuilder.build()._toQuery();
    }

    private static Query buildConditionQuery(ZESFilterCondition condition) {
        switch (condition.getOperation()) {
            case EQUALS:
                return TermQuery.of(tq -> tq
                        .field(condition.getField())
                        .value(FieldValue.of(condition.getValue()))
                )._toQuery();
            case GREATER_THAN:
                return RangeQuery.of(rq -> rq
                        .field(condition.getField())
                        .gt(JsonData.of(condition.getValue()))
                )._toQuery();
            case LESS_THAN:
                return RangeQuery.of(rq -> rq
                        .field(condition.getField())
                        .lt(JsonData.of(condition.getValue()))
                )._toQuery();
            case GREATER_THAN_OR_EQUAL:
                return RangeQuery.of(rq -> rq
                        .field(condition.getField())
                        .gte(JsonData.of(condition.getValue()))
                )._toQuery();
            case LESS_THAN_OR_EQUAL:
                return RangeQuery.of(rq -> rq
                        .field(condition.getField())
                        .lte(JsonData.of(condition.getValue()))
                )._toQuery();
            case RANGE:
                List<Object> range = (List<Object>) condition.getValue();
                if (range.size() != 2) {
                    throw new IllegalArgumentException("RANGE operation requires a list with exactly two values: [start, end]");
                }
                return RangeQuery.of(rq -> rq
                        .field(condition.getField())
                        .gte(JsonData.of(range.get(0)))
                        .lte(JsonData.of(range.get(1)))
                )._toQuery();
            case EXISTS:
                return ExistsQuery.of(eq -> eq
                        .field(condition.getField())
                )._toQuery();
            case NOT_EXISTS:
                return BoolQuery.of(bq -> bq
                        .mustNot(ExistsQuery.of(eq -> eq
                                .field(condition.getField())
                        )._toQuery())
                )._toQuery();
            case MATCH:
                return MatchQuery.of(mq -> mq
                        .field(condition.getField())
                        .query(condition.getValue().toString())
                )._toQuery();
            case IN:
                List<Object> values = (List<Object>) condition.getValue();
                return TermsQuery.of(tq -> tq
                        .field(condition.getField())
                        .terms(TermsQueryField.of(tqf -> tqf.value(values.stream()
                                .map(FieldValue::of)
                                .collect(Collectors.toList()))))
                )._toQuery();
            default:
                throw new IllegalArgumentException("Unknown operation: " + condition.getOperation());
        }
    }
}
