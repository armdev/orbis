package io.project.app.question;

import io.project.app.domain.Question;
import io.project.app.security.SessionContext;
import io.project.app.unicorn.QuestionClient;

import java.io.Serializable;
import java.util.List;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.apache.log4j.Logger;

@Named
@ViewScoped
@Data
public class QuestionListBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(QuestionListBean.class);

    @Inject
    private QuestionClient questionClient;

    @Inject
    private SessionContext sessionContext = null;

    private Question question = new Question();

    public QuestionListBean() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("AddQuestionBean Bean called");

    }

    public  List<Question> getQuestionList() {
       return questionClient.findAllQuestions().getQuestionList();        
    }
    
     public  List<Question> getUserQuestionList() {
       return questionClient.findAllUserQuestions(sessionContext.getUser().getId()).getQuestionList();        
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
