package io.project.app.resources;

import io.project.app.domain.Question;
import io.project.app.dto.SearchResultDTO;
import io.project.app.services.SearchService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/search")
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(path = "/full", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> search(@RequestParam(name = "tag", required = true) String searchQuery) {
        log.info("REST request to start search " + searchQuery);
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        final List<Question> searchResult = searchService.matchWithQuery(searchQuery);
        searchResultDTO.getQuestionList().addAll(searchResult);
        return ResponseEntity.status(HttpStatus.OK).body(searchResultDTO);
    }

}
