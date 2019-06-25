/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleRenderingMode;

/**
 *
 * @author armena
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverTimeline implements ScheduleEvent, Serializable {

    private String id;
    private String driverId;
    private String firstname;
    private String lastname;
    private Date dateTime;
    private String from;
    private Double fromLatitude;
    private Double fromLongitude;
    private String to;
    private Double toLatitude;
    private Double toLongitude;
    private Integer payout;
    private String shipperId;
    private String shipper;
    private Integer duration;
    private String shipmentId;
    private String loadType = "full";//loadboard, regular-recurring

    @Override
    public Object getData() {
        return "Plan";
    }

    @Override
    public String getTitle() {
        return loadType;
    }

    @Override
    public Date getStartDate() {
        return dateTime;
    }

    @Override
    public Date getEndDate() {
        return dateTime;
    }

    @Override
    public boolean isAllDay() {
        return true;
    }

    @Override
    public String getStyleClass() {
        return null;
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "My plan";
    }

    @Override
    public String getUrl() {
        return "";
    }

    @Override
    public ScheduleRenderingMode getRenderingMode() {
        return ScheduleRenderingMode.BACKGROUND;
    }

    @Override
    public Map<String, Object> getDynamicProperties() {
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
