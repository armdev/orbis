package io.project.app.profile;

import io.project.app.unicorn.AuthClient;
import io.project.app.security.SessionContext;
import io.project.app.domain.User;
import io.project.app.unicorn.ProfileClient;

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
@Data
public class ProfileBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ProfileBean.class);

    @Inject
    private AuthClient userAuthClient;

    @Inject
    private ProfileClient profileClient;

    @Inject
    private SessionContext sessionContext = null;

    private User userModel = null;

    private String userPassword;

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

    public String updatePassword() {

        int changePassword = profileClient.changePassword(userModel.getId(), userPassword);

        if (changePassword != 200) {

            FacesMessage msg = new FacesMessage("Notificaation", "Password update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            FacesMessage msg = new FacesMessage("Notificaation", "Password update success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    public String updateProfile() {

        User updateProfile = profileClient.updateProfile(userModel);

        if (updateProfile.getEmail() != null) {
            sessionContext.setUser(updateProfile);
            FacesMessage msg = new FacesMessage("System message", "Profile updated");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error", "Profile update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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
