package io.project.app.question;

import io.project.app.domain.Answer;
import io.project.app.domain.Question;
import io.project.app.security.SessionContext;
import io.project.app.unicorn.QuestionClient;
import java.io.IOException;

import java.io.Serializable;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.apache.log4j.Logger;

@Named
@ViewScoped
@Data
public class OpenQuestionBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(OpenQuestionBean.class);

    @Inject
    private QuestionClient questionClient;

    @Inject
    private SessionContext sessionContext;

    private FacesContext context;

    private ExternalContext externalContext;

    private String questionId;

    private Question question = new Question();

    private Answer answer = new Answer();

    public OpenQuestionBean() {
    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        questionId = ((this.getRequestParameter("id")));
        LOGGER.info("OpenQuestionBean Bean called");
        if (questionId != null) {
            question = questionClient.findById(questionId);
        }

    }

    private String getRequestParameter(String paramName) {
        String returnValue = null;
        if (externalContext.getRequestParameterMap().containsKey(paramName)) {
            returnValue = (externalContext.getRequestParameterMap().get(paramName));
        }
        return returnValue;
    }

    public void addAnswer() {
        try {
            answer.setQuestionId(questionId);
            answer.setUserId(sessionContext.getUser().getId());
            answer.setUsername(sessionContext.getUser().getFirstname() + " " + sessionContext.getUser().getLastname());

            questionClient.addAnswer(answer);
            FacesContext.getCurrentInstance().getExternalContext().redirect("open.jsf?id=" + questionId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
