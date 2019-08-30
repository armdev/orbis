package io.project.app.resources;

import io.project.app.api.responses.TagApiResponse;
import io.project.app.domain.Tag;
import io.project.app.repositories.TagRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/tags")
@Slf4j
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping(path = "/all", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> get() {        
        TagApiResponse searchResultDTO = new TagApiResponse();
        final List<Tag> searchResult = tagRepository.findAll();
        searchResultDTO.getTagList().addAll(searchResult);
        return ResponseEntity.status(HttpStatus.OK).body(searchResultDTO);
    }

}
