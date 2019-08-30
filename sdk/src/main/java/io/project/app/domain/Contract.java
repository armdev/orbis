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
@Document(collection = "contract")
public class Contract implements Serializable {

    @Id
    private String id;
    private String lotId;
    private String bidId;
    @Indexed
    private String bidderId;
    @Indexed
    private String shipperId;
    private double contractPrice;
    private Date contractStart;
    private Date contractEnd;

}
