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
@Document(collection = "lot")
public class Lot implements Serializable {

    @Id
    private String id;
    private String lotName;
    private String description;
    private String addressFrom;
    private String addressTo;
    private String truck;//van
    private double price;
    private Date endDate;
    @Indexed
    private String publisherId;
    private String publisherName;
    

}
