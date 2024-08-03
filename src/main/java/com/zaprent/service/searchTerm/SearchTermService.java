package com.zaprent.service.searchTerm;

import com.zaprent.config.ZElasticSearchClient;
import com.zaprnt.beans.common.es.request.*;
import com.zaprnt.beans.common.es.response.ZESResponse;
import com.zaprnt.beans.models.SearchTerm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zaprent.utils.SearchTermUtils.createESRequestForRecommendedSearchStrings;
import static com.zaprnt.beans.common.util.CommonUtils.nullSafeCollection;
import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchTermService {

    private final ZElasticSearchClient elasticSearchClient;
    private final static String SEARCH_TERM_INDEX = "search_term";

    public void saveSearchTerm(String searchString) {
        try {
            String id = searchString.toLowerCase();
            SearchTerm existingTerm = elasticSearchClient.get(SEARCH_TERM_INDEX, id, SearchTerm.class);
            int frequency = 1;
            if (nonNull(existingTerm)) {
                frequency += existingTerm.getFrequency();
            }
            SearchTerm searchTerm = new SearchTerm(id, frequency);
            elasticSearchClient.save(SEARCH_TERM_INDEX, id, searchTerm);
        } catch (Exception e) {
            log.error("[saveSearchTerm] failed to save search term");
        }
    }

    public void saveSearchTerms(List<String> searchTerms) {
        try {
            List<SearchTerm> searchTermList = new ArrayList<>();
            for (String term: searchTerms) {
                searchTermList.add(new SearchTerm(term, 1));
            }
            elasticSearchClient.bulkSave(SEARCH_TERM_INDEX, searchTermList);
        } catch (Exception e) {
            log.error("[saveSearchTerms] failed to save search terms");
        }
    }

    public List<String> getRecommendedSearchTerms(String searchString) {
        if (isBlank(searchString)) {
            return null;
        }
        try {
            ZESRequest esRequest = createESRequestForRecommendedSearchStrings(searchString.toLowerCase());
            ZESResponse<SearchTerm> response = elasticSearchClient.search(esRequest, SEARCH_TERM_INDEX, SearchTerm.class);
            if (nonNull(response) && nonNull(response.getHits()) && !response.getHits().isEmpty()) {
                return nullSafeCollection(response.getHits()).stream().filter(Objects::nonNull).map(SearchTerm::getTerm).toList();
            }
            return null;
        } catch (Exception e) {
            log.error("[getRecommendedSearchTerms] failed to fetch recommended search terms");
            return null;
        }
    }
}
