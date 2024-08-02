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

public class ZESUtil {

    public static SearchRequest buildSearchRequest(ZESRequest esRequest, String esIndex) {
        // Build the query
        Query query = buildQuery(esRequest);

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
                                        .order(SortOrder.valueOf(sortOption.getOrder().getValue().toUpperCase()))))))
                        .collect(Collectors.toList()))
        );
    }

    private static Query buildQuery(ZESRequest esRequest) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        if (esRequest.getFilter() != null) {
            boolQueryBuilder.filter(buildFilterQuery(esRequest.getFilter()));
        }

        return boolQueryBuilder.build()._toQuery();
    }

    private static Query buildFilterQuery(ZESFilter filter) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        for (ZESFilterCondition condition : filter.getConditions()) {
            if (condition == null) {
                continue;
            }
            Query query = condition.getConditions() != null && !condition.getConditions().isEmpty()
                    ? buildFilterQuery(new ZESFilter()
                    .setLogicalOperator(condition.getLogicalOperator())
                    .setConditions(condition.getConditions()))
                    : buildConditionQuery(condition);

            if (filter.getLogicalOperator() == ZESLogicalOperator.OR) {
                boolQueryBuilder.should(query);
            } else {
                boolQueryBuilder.must(query);
            }
        }

        return boolQueryBuilder.build()._toQuery();
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
            default:
                throw new IllegalArgumentException("Unknown operation: " + condition.getOperation());
        }
    }
}
