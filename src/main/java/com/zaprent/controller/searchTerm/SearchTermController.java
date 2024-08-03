package com.zaprent.controller.searchTerm;

import com.zaprent.service.searchTerm.SearchTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search-terms")
public class SearchTermController {
    private final SearchTermService searchTermService;

    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, Object>> getRecommendedSearchTerms(@RequestParam(name = "query", required = false) String id) {
        return okResponseEntity(searchTermService.getRecommendedSearchTerms(id));
    }

    @PostMapping("/{searchTerm}")
    public ResponseEntity<Map<String, Object>> saveSearchTerm(@PathVariable String searchTerm) {
        searchTermService.saveSearchTerm(searchTerm);
        return okResponseEntity("Saved");
    }

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> saveSearchTerms(@RequestBody List<String> searchTerms) {
        searchTermService.saveSearchTerms(searchTerms);
        return okResponseEntity("Saved");
    }
}
