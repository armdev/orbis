package io.project.app.domain;

import java.io.Serializable;
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
@Document(collection = "parcel")
public class Parcel implements Serializable {

    @Id
    private String id;
    @Indexed
    private String shipmentId;
    private String boxType;
    private String quantity;
    private Integer width;
    private Integer height;
    private Integer depth;
    private String dimensionUnit;
    private Double weight;
    private String weightUnit;
 
    private String description;

}
