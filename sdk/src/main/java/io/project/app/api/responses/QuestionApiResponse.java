package io.project.app.api.responses;

import io.project.app.domain.Question;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author armen
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class QuestionApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<Question> questionList = new ArrayList<>();
  
}
