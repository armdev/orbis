package io.project.app.shipment;

import io.project.app.domain.Shipment;
import io.project.app.dto.EquipmentType;
import io.project.app.security.SessionContext;
import io.project.app.unicorn.ShipmentClient;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author root
 */
@Named(value = "editShipment")
@ViewScoped
@Data
@NoArgsConstructor
public class EditShipment implements Serializable {

    @Inject
    private ShipmentClient shipmentClient;

    @Inject
    private SessionContext sessionContext;
   
    private Shipment shipment = new Shipment();
   
    private boolean skip;
  
    private String shipmentId;

    @PostConstruct
    public void init() {
        shipmentId = ((this.getRequestParameter("shipmentId")));
        System.out.println("Shipment id " + shipmentId);
        
        if (shipmentId != null) {
            shipment = shipmentClient.getShipmentById(shipmentId);
        }

    }

    private String getRequestParameter(String paramName) {
        String returnValue = null;
        if (getExternalContext().getRequestParameterMap().containsKey(paramName)) {
            returnValue = (getExternalContext().getRequestParameterMap().get(paramName));
        }
        return returnValue;
    }

    public String save() {
        shipment.setUserId(sessionContext.getUser().getId());
        shipmentClient.updateShipment(shipment);
        FacesMessage msg = new FacesMessage("Successful", "Shipment Saved :" + shipment.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "shipmentlist";
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
    
    

    @Produces
    @ViewScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @ViewScoped
    public ExternalContext getExternalContext() {
        return this.getFacesContext().getExternalContext();
    }

    public EquipmentType[] getEquipmentList() {
        return EquipmentType.values();
    }

}
