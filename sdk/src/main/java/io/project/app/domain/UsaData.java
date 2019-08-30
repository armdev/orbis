package io.project.app.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author armena
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString
@Document(collection = "usa_data")
public class UsaData implements Serializable {

    private static final long serialVersionUID = 5803757207294029907L;

    private String city;
    @Field("city_ascii")
    private String cityAscii;
    @TextIndexed
    @Field("state_id")
    private String stateId;
    @Field("state_name")
    private String stateName;
    @Field("county_fips")
    private String countyFips;
    @Field("county_name")
    private String countyName;
    private String lat;
    private String lng;
    private String population;
    @Field("population_proper")
    private String populationProper;
    private String density;
    private String source;
    private String incorporated;
    private String timezone;
    private String zips;
    @Id
    private String id;
// https://lishman.io/object-mapping-with-spring-data-mongodb
}
