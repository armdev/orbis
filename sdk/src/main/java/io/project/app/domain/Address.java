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
@Document(collection = "address")
public class Address implements Serializable {

    @Id
    private String id;

    @Indexed
    private String userId;

    private String country;

    private String contactName;

    private String phone;

    private String fax;

    private String skype;

    private String email;

    private String companyName;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String type;

    private Float latitude;
    private Float longitude;
    private float baseRateDollars;
    private float rateMiles;

}
