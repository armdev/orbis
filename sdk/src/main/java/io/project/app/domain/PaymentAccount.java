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
@Document(collection = "paymentsaccount")
public class PaymentAccount implements Serializable {

    @Id
    private String id;
    @Indexed
    private String userId;
    private String type;

    private String accountNumber;

    private String postalCode;

    private String country;

}
