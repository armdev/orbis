package io.project.app.profile;

import io.project.app.unicorn.AuthClient;
import io.project.app.security.SessionContext;
import io.project.app.domain.User;


import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

@Named
@ViewScoped
public class ProfileBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ProfileBean.class);

    @Inject
    private AuthClient userAuthClient;

    @Inject
    private SessionContext sessionContext = null;

    @Setter
    @Getter
    private User userModel = null;

    public ProfileBean() {
    }

    @PostConstruct
    public void init() {

        if (sessionContext.getUser().getId() != null) {
            LOGGER.info("Calling getUserById");
            userModel = userAuthClient.getUserById(sessionContext.getUser().getId());
        }
        LOGGER.info("Profile Bean called");

    }

    public String updateProfile() {
        if (userModel.getEmail() == null) {
            FacesMessage msg = new FacesMessage("Fill email please", "Fill email please");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     //   boolean check = userAuthClient.checkUserEmailForUpdate(sessionContext.getUser().getId(), userModel.getEmail());

//        if (check) {
//            FacesMessage msg = new FacesMessage(getBundle().getString("emailbusy"), getBundle().getString("emailbusy"));
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            return null;
//        }
//        LOGGER.info("Token SET " + sessionContext.getUser().getToken());
//        userModel.setToken(sessionContext.getUser().getToken());
//        userModel.setId(sessionContext.getUser().getId());

        // returnedModel = userAuthClient.updateUser(userModel);
//        sessionContext.setUser(returnedModel);
//
//        LOGGER.debug("UpdateProfile");
//        if (returnedModel.getToken() != null) {
//            LOGGER.info("Calling getUserById!!!!!");
//            userModel = userAuthClient.getUserById(sessionContext.getUser().getId());
//        }
//
//        if (userModel != null && userModel.getId() != null) {
//            LOGGER.debug("UpdateProfile Success");
//            FacesMessage msg = new FacesMessage(getBundle().getString("updatesuccess"), getBundle().getString("updatesuccess"));
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            return null;
//        }

        LOGGER.debug("UpdateProfile fail");
        FacesMessage msg = new FacesMessage(getBundle().getString("updatefail"), getBundle().getString("updatefail"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

//    public String changeOldPassword() {
//        userModel.setToken(sessionContext.getUser().getToken());
//        userModel.setId(sessionContext.getUser().getId());
//        UserModel model = userAuthClient.changeOldPassword(sessionContext.getUser(), userModel.getPassword_hash());
//        LOGGER.debug("Update password");
//        if (model != null && model.getId() != null) {
//            LOGGER.debug("Update password Success");
//            FacesMessage msg = new FacesMessage(getBundle().getString("updatesuccess"), getBundle().getString("updatesuccess"));
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            return null;
//        }
//        LOGGER.debug("Update password fail");
//        FacesMessage msg = new FacesMessage(getBundle().getString("updatefail"), getBundle().getString("updatefail"));
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        return null;
//
//    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
