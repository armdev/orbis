package io.project.app.shipment;


import io.project.app.domain.Parcel;
import io.project.app.domain.Shipment;
import io.project.app.constant.data.EquipmentType;
import io.project.app.constant.data.PackageType;
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

/**
 *
 * @author root
 */
@Named(value = "addParcelBean")
@ViewScoped
@Data
@NoArgsConstructor
public class AddParcelBean implements Serializable {

    @Inject
    private ShipmentClient shipmentClient;

    @Inject
    private SessionContext sessionContext;

    private Shipment shipment = new Shipment();

    private Parcel parcel = new Parcel();

    private String shipmentId;

    @PostConstruct
    public void init() {
        shipmentId = ((this.getRequestParameter("shipmentId")));
        //  System.out.println("Shipment id " + shipmentId);

        if (shipmentId != null) {
            shipment = shipmentClient.getShipmentById(shipmentId);
        }

    }

    public String updateParcel() {
        shipment.setUserId(sessionContext.getUser().getId());
        shipment.getParcels().add(parcel);
        shipmentClient.updateShipment(shipment);
        FacesMessage msg = new FacesMessage("Successful", "Shipment Saved :" + shipment.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "shipmentlist";
    }

    private String getRequestParameter(String paramName) {
        String returnValue = null;
        if (getExternalContext().getRequestParameterMap().containsKey(paramName)) {
            returnValue = (getExternalContext().getRequestParameterMap().get(paramName));
        }
        return returnValue;
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

    public PackageType[] getPackageList() {
        return PackageType.values();
    }

}
