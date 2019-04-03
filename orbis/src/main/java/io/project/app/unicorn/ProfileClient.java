package io.project.app.unicorn;

import io.project.app.domain.User;
import io.project.app.dto.FileDTO;
import io.project.app.dto.PasswordUpdate;
import io.project.app.util.GsonConverter;
import java.io.IOException;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@SessionScoped
public class ProfileClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProfileClient.class);
    private final String BASE_URL = getBundle().getString("gateway");

    public ProfileClient() {

    }

    @PostConstruct
    public void init() {
        LOG.info("ProfileClient called");
    }

    public FileDTO findUserAvatar(String id) {
        FileDTO fileDTO = new FileDTO();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("#####FindUserAvatar file started ");
            HttpGet request = new HttpGet(BASE_URL + "/box/api/v2/documents/user/avatar?id=" + id);

            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");

            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("FindUserAvatar file  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileDTO = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), FileDTO.class);
                    LOG.info("#######File name is " + fileDTO.getFileName());
                    LOG.info("#########File id is " + fileDTO.getId());
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("@@@@@@@@@@@@@@@@findUserAvatar File started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("$$$$$Exception caught.", e);
        }
        return fileDTO;
    }

    public FileDTO getFileById(String id) {
        FileDTO fileDTO = new FileDTO();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("getFileById file started ");
            HttpGet request = new HttpGet(BASE_URL + "/box/api/v2/documents?id=" + id);

            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");

            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("Get file  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileDTO = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), FileDTO.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Get File started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return fileDTO;
    }

    public FileDTO deleteFile(String id) {
        FileDTO fileDTO = new FileDTO();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Upload file started ");
            HttpDelete request = new HttpDelete(BASE_URL + "/box/api/v2/documents?id=" + id);

            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");

            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("delete file  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileDTO = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), FileDTO.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("delete file started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return fileDTO;
    }

    public String saveFile(FileDTO fileDTO) {
        String fileId = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Upload file started ");
            HttpPut request = new HttpPut(BASE_URL + "/box/api/v2/documents");

            String toJson = GsonConverter.toJson(fileDTO);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("Upload file started  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileId = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), String.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Upload file started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return fileId;
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
            LOG.info("Change Password called ");
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
