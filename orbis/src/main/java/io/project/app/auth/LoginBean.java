package io.project.app.auth;

import io.project.app.unicorn.AuthClient;
import io.project.app.security.SessionContext;
import io.project.app.util.CommonConstants;
import io.project.app.domain.User;
import io.project.app.dto.Login;

import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

/**
 *
 * @author armdev
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private AuthClient userAuthClient;

    @Inject
    private SessionContext sessionContext = null;

    @Setter
    @Getter
    private Login loginModel;
    private String ipAddress;

    @Setter
    @Getter
    private User user;

    public LoginBean() {
        LOGGER.info("##LoginBean called");
    }

    @PostConstruct
    public void init() {
        loginModel = new Login();
        user = new User();
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();

    }

    public String loginUser() {
        User user = userAuthClient.userLogin(loginModel);
        if (user == null) {
            return "nouser";
        }

        if (user.getStatus().equals(CommonConstants.ACTIVATED)) {
            sessionContext.setUser(user);

            sessionContext.init();
            return "dashboard";
//            if (user.getStatus().equals(CommonConstants.NOT_ACTIVATED)) {
//                LOGGER.info("Not active user");
//                return "activationlink";
//            }
//            if (user.getStatus().equals(CommonConstants.BLOCKED)) {
//                LOGGER.info("Blocked user");
//                return "nouser";
//            }
//            if (user.getStatus().equals(CommonConstants.ACTIVATED)) {
//                LOGGER.info("Active user");
//               
//            }
        }
        FacesMessage msg = new FacesMessage(getBundle().getString("nouser"), getBundle().getString("nouser"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;

    }

    public String registerUser() {
        LOGGER.info("Register new user, started");
        User returnedUser = userAuthClient.getUserByEmail(user.getEmail());
        if (returnedUser.getId() != null) {
            LOGGER.info("User tried to register with busy email " + returnedUser.getEmail());
            FacesMessage msg = new FacesMessage(getBundle().getString("emailbusy"), getBundle().getString("emailbusy"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }

        LOGGER.info("Register new user");

        User returnedModel = userAuthClient.userRegistration(user);
        if (returnedModel.getId() != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    public void validatePassword(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String passwd = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        // Let required="true" do its job.
        if (passwd.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }

        if (!passwd.equals(confirmPassword)) {

            FacesMessage msg = new FacesMessage("Password must match confirm password");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();

        }

    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

    public void publishMessage() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getBundle().getString("registersuccess"), getBundle().getString("registersuccess"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
