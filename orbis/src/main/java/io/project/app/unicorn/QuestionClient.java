package io.project.app.unicorn;

import io.project.app.domain.Answer;
import io.project.app.domain.Question;
import io.project.app.dto.QuestionDTO;
import io.project.app.util.GsonConverter;
import java.io.IOException;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@SessionScoped
public class QuestionClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(QuestionClient.class);
    private final String BASE_URL = getBundle().getString("gateway");

    public QuestionClient() {

    }

    @PostConstruct
    public void init() {
        LOG.info("QuestionClient called");
    }

    public Question findById(String id) {
        LOG.info("Find Question By id " + id);
        Question model = new Question();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/marshal/api/v2/questions/find/id?id=" + id);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Question.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Find Question By id: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return model;
    }

    public QuestionDTO findAllQuestions() {
        LOG.info("Find All Questions ");
        QuestionDTO model = new QuestionDTO();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/marshal/api/v2/questions/find/all");
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), QuestionDTO.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Find All Questions: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return model;
    }

    public QuestionDTO findAllUserQuestions(String id) {
        LOG.info("Find Questions for user ");
        QuestionDTO model = new QuestionDTO();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/marshal/api/v2/questions/find/user/questions?id=" + id);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), QuestionDTO.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Find user Questions: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return model;
    }

    public Question addQuestion(Question question) {
        Question returnedQuestion = new Question();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Add Question");
            HttpPost request = new HttpPost(BASE_URL + "/marshal/api/v2/questions/question");

            String toJson = GsonConverter.toJson(question);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("Add Question: started  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedQuestion = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Question.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Add Question:  finished: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return returnedQuestion;
    }

    public Answer addAnswer(Answer answer) {
        Answer returnedQuestion = new Answer();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Add Answer");
            HttpPost request = new HttpPost(BASE_URL + "/marshal/api/v2/questions/answer");

            String toJson = GsonConverter.toJson(answer);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("Answer: started  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedQuestion = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Answer.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Add Answer:  finished: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return returnedQuestion;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }
}
