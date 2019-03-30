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
    private ProfileClient profileClient;

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
        
        User updateProfile = profileClient.updateProfile(userModel);
        
        
        if (updateProfile.getEmail() != null) {
            FacesMessage msg = new FacesMessage("Profile updated", "Profile updated");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    
//
//       
//        FacesMessage msg = new FacesMessage(getBundle().getString("updatefail"), getBundle().getString("updatefail"));
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }


    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
