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
@Document(collection = "bid")
public class Bid implements Serializable {

    @Id
    private String id;
    private String lotId;
    @Indexed
    private String bidder;
    private String bidderName;   
    private double bid;  
    

}
