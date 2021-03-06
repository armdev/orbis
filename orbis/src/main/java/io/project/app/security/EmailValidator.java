package io.project.app.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext,
            UIComponent uIComponent, Object value) throws ValidatorException {
        String enteredEmail = (String) value;
        Pattern p = Pattern.compile("^[a-zA-Z0-9._-]+@.+\\.[a-z]+");

        MessageFactory msg = new MessageFactory();
        FacesMessage errMsg = new FacesMessage(msg.getMessage("wrongFormat"));
        if (enteredEmail.trim().length() > 255) {
            throw new ValidatorException(errMsg);
        }
        Matcher m = p.matcher(enteredEmail.trim());
        boolean matchFound = m.matches();
        if (!matchFound) {
            throw new ValidatorException(errMsg);
        }

    }
}
