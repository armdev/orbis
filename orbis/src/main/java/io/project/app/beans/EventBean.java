/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.beans;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;


/**
 *
 * @author armena
 */
@Named(value = "eventBean")
@ViewScoped
public class EventBean implements Serializable {

    private String eventName;

    /**
     * Creates a new instance of EventBean
     */
    public EventBean() {
    }

  

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
