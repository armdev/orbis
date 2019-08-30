package io.project.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
@Document(collection = "shipperaccount")
public class ShipperAccount implements Serializable {

    @Id
    private String id;
    @Indexed
    private String userId;
    private Date createdAt;
    private String description;
    private String slug;
    private String timezone;
    private Date updateAt;
    private Address address = new Address();
    private Map<String, String> credentials = new HashMap<>();

}
