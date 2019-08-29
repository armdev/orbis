package io.project.app.domain;

import java.io.Serializable;
import java.util.Date;
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
@Document(collection = "notification")
public class Notification implements Serializable {

    @Id
    private String id;
    private String fromId;
    private String fromName;
    private String toId;
    private String toName;
    private String message;
    private Date messageSentDate;

}
