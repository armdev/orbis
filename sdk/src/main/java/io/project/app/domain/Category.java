package io.project.app.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "category")
public class Category implements Serializable {

    @Id
    private String id;
    @Indexed
    private String name;    
    private Date publishDate;
    private Date updateDate;
    private Integer status;

    public Category(String name, Date publishDate, Date updateDate, Integer status) {
        this.name = name;
        this.publishDate = publishDate;
        this.updateDate = updateDate;
        this.status = status;
    }

}
