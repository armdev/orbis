package io.project.app.question;

import io.project.app.domain.Question;
import io.project.app.security.SessionContext;
import io.project.app.unicorn.QuestionClient;

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
public class AddQuestionBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AddQuestionBean.class);

    @Inject
    private QuestionClient questionClient;

    @Inject
    private SessionContext sessionContext = null;

    private Question question = new Question();

    public AddQuestionBean() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("AddQuestionBean Bean called");

    }

    public String addQuestion() {
        question.setUserId(sessionContext.getUser().getId());
        question.setUsername(sessionContext.getUser().getFirstname() + " " + sessionContext.getUser().getLastname());
        Question addQuestion = questionClient.addQuestion(question);
        if (addQuestion.getId() != null) {
            FacesMessage msg = new FacesMessage("You are added new question", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "qlist";
        } else {
            FacesMessage msg = new FacesMessage("Could not add new question", "Could not add new question");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
