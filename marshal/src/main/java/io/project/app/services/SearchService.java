package io.project.app.services;

import io.project.app.domain.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

/**
 *
 * @author armdev
 */
@Service
@Component
@Slf4j
public class SearchService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Question> matchWithQuery(String queryText) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(queryText);
        TextQuery query = TextQuery.queryText(criteria).sortByScore();
        List<Question> foundList = mongoTemplate.find(query, Question.class);
        return foundList;
    }

}
