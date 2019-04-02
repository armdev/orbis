package io.project.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "questions")
public class Questions implements Serializable {

    @Id
    private String id;
    private String userId;
    private String username;
    private String question;    
    private Date publishDate;
    private Integer status;
    private List<Answers> answers;

    
}
