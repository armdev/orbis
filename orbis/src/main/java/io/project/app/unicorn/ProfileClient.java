package io.project.app.unicorn;

import io.project.app.domain.User;
import io.project.app.dto.PasswordUpdate;
import io.project.app.util.GsonConverter;
import java.io.IOException;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@ApplicationScoped
public class ProfileClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProfileClient.class);
    private final String BASE_URL = getBundle().getString("gateway");

    public ProfileClient() {

    }

    @PostConstruct
    public void init() {
        LOG.debug("ProfileClient called");
    }

    public User updateProfile(User model) {
        User returnedModel = new User();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Update profile ");
            HttpPut request = new HttpPut(BASE_URL + "/profile/api/v2/profiles/user");

            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("Update profile status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), User.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Update profile request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return returnedModel;
    }

    @SuppressWarnings("unchecked")
    public int changePassword(String id, String password) {

        int status = 0;

        long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.debug("changePassword called ");
            HttpPut request = new HttpPut(BASE_URL + "/profile/api/v2/profiles/user/password");

            PasswordUpdate passwordUpdate = new PasswordUpdate(id, password);
            String toJson = GsonConverter.toJson(passwordUpdate);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    LOG.info("Password update status is " + status);
                    status = 200;
                } else {
                    status = 500;
                }
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("ChangePassword request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {         
            LOG.error("Exception caught.", e);
        }
        return status;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }
}
