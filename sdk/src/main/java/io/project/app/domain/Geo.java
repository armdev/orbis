package io.project.app.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author armena
 */
@ToString
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Geo implements Serializable {
    
    private float locationLatitude;
    private float locationLongitude;  

    public Geo(float locationLatitude, float locationLongitude) {
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

}
