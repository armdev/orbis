package io.project.app.shipment;

import io.project.app.domain.Address;
import io.project.app.domain.Parcel;
import io.project.app.domain.Shipment;
import io.project.app.dto.EquipmentType;
import io.project.app.security.SessionContext;
import io.project.app.unicorn.ShipmentClient;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
@Named(value = "newShipment")
@SessionScoped
@Data
@NoArgsConstructor
public class NewShipment implements Serializable {

    @Inject
    private ShipmentClient shipmentClient;

    @Inject
    private SessionContext sessionContext;

    private Shipment shipment;
    private Address addressFrom;
    private Address addressTo;
    private Parcel parcel;
    private boolean skip;

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

    @PostConstruct
    public void init() {
        shipment = new Shipment();
        addressFrom = new Address();
        addressTo = new Address();
        parcel = new Parcel();

    }

    public String save() {
        shipment.setShipFrom(addressFrom);
        shipment.setShipTo(addressTo);
        shipment.setUserId(sessionContext.getUser().getId());
        shipmentClient.addNewShipment(shipment);
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

}
