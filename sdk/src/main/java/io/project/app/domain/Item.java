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
@Document(collection = "item")
public class Item implements Serializable {

    @Id
    private String id;
    @Indexed
    private String parcelId;
    private String description;
    private int quantity;

    private Money price;

    private Weight weight;

    private String itemId;

    private String originCountry;

    private String sku;

    private String hsCode;

}
