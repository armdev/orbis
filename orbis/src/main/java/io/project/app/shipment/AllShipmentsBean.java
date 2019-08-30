package io.project.app.shipment;


import io.project.app.domain.Shipment;
import io.project.app.security.SessionContext;
import io.project.app.unicorn.ShipmentClient;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Produces;
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
@Named(value = "allShipmentsBean")
@ViewScoped
@Data
@NoArgsConstructor
public class AllShipmentsBean implements Serializable {

    @Inject
    private ShipmentClient shipmentClient;

    @Inject
    private SessionContext sessionContext;

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


    public List<Shipment> getShipmentList() {       
        return shipmentClient.getShipmentList(sessionContext.getUser().getId());
    }

   

}
