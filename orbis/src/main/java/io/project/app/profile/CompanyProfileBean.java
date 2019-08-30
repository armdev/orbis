package io.project.app.profile;

import io.project.app.domain.CareerAccount;
import io.project.app.domain.ShipperAccount;
import io.project.app.unicorn.AuthClient;
import io.project.app.security.SessionContext;
import io.project.app.domain.User;
import io.project.app.unicorn.AccountProfileClient;

import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.apache.log4j.Logger;

@Named
@ViewScoped
public class CompanyProfileBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(CompanyProfileBean.class);

    @Inject
    private AuthClient userAuthClient;

    @Inject
    private AccountProfileClient accountProfileClient;

    @Inject
    private SessionContext sessionContext = null;

    private User userModel = new User();

    private ShipperAccount shipperAccount = new ShipperAccount();

    private CareerAccount careerAccount = new CareerAccount();

    public CompanyProfileBean() {
    }

    @PostConstruct
    public void init() {
        if (sessionContext.getUser().getId() != null) {

            userModel = userAuthClient.getUserById(sessionContext.getUser().getId());

            if (userModel.getAccountType().equalsIgnoreCase("Shipper")) {
                shipperAccount = accountProfileClient.getShipperProfile(userModel.getId());
            }

            if (userModel.getAccountType().equalsIgnoreCase("Career")) {
                careerAccount = accountProfileClient.getCareerProfile(userModel.getId());
            }

        }

        LOGGER.info("Profile Bean called");

    }

    public String updateShipperProfile() {

        shipperAccount.setUserId(sessionContext.getUser().getId());
        ShipperAccount updateShipperProfile = accountProfileClient.updateShipperProfile(shipperAccount);

        if (updateShipperProfile.getId() != null) {
            FacesMessage msg = new FacesMessage("System message", "Profile updated");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error", "Profile update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null;
    }

    public String updateCarrierProfile() {

        careerAccount.setUserId(sessionContext.getUser().getId());
        CareerAccount careerAccountUpdated = accountProfileClient.updateCareerProfile(careerAccount);

        if (careerAccountUpdated.getId() != null) {
            FacesMessage msg = new FacesMessage("System message", "Profile updated");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error", "Profile update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null;
    }

    public AuthClient getUserAuthClient() {
        return userAuthClient;
    }

    public void setUserAuthClient(AuthClient userAuthClient) {
        this.userAuthClient = userAuthClient;
    }

    public AccountProfileClient getAccountProfileClient() {
        return accountProfileClient;
    }

    public void setAccountProfileClient(AccountProfileClient accountProfileClient) {
        this.accountProfileClient = accountProfileClient;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public ShipperAccount getShipperAccount() {
        return shipperAccount;
    }

    public void setShipperAccount(ShipperAccount shipperAccount) {
        this.shipperAccount = shipperAccount;
    }

    public CareerAccount getCareerAccount() {
        return careerAccount;
    }

    public void setCareerAccount(CareerAccount careerAccount) {
        this.careerAccount = careerAccount;
    }

    public User getUserModel() {
        return userModel;
    }

    public void setUserModel(User userModel) {
        this.userModel = userModel;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
