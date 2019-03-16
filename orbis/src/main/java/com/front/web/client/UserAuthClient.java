package com.front.web.client;



import io.project.app.domain.User;
import io.project.app.dto.Login;
import io.project.app.util.GsonConverter;
import java.io.IOException;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@ApplicationScoped
public class UserAuthClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(UserAuthClient.class);
    private String BASE_URL = getBundle().getString("gateway");

    public UserAuthClient() {

    }

    @PostConstruct
    public void init() {
        log.debug("UserAuthClient called");
    }

    public User getUserByEmail(String email) {
        log.info("UserAuthClient: getUserByEmail called@@@@ " + email);
        User model = new User();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/register/api/v2/users/find/user/email?email=" + email);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), User.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("getUserById request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            log.error("Exception caught.", e);
        }
        return model;
    }

    public User getUserById(String userId) {
        log.info("UserAuthClient: getUserById called@@@@ " + userId);
        User model = new User();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/register/api/v2/users/find/user/id?userId=" + userId);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), User.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("getUserById request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            log.error("Exception caught.", e);
        }
        return model;
    }

    @SuppressWarnings("unchecked")
    public User userLogin(Login model) {
        log.info("UserAuthClient: userLogin called ");
        User returnedModel = new User();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/auth/api/v2/users/auth");
            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("userLogin httpResponse.getStatusLine().getStatusCode() " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), User.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("userLogin request/response time in milliseconds: " + elapsedTime);

        } catch (IOException e) {
            log.error("Exception caught.", e);
        }
        return returnedModel;
    }

    @SuppressWarnings("unchecked")
    public User userRegistration(User model) {
        User returnedModel = new User();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            log.info("UserAuthClient: userRegistration ");
            HttpPost request = new HttpPost(BASE_URL + "/register/api/v2/users/register");

            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("userRegistration status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), User.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("userRegistration request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            log.error("Exception caught.", e);
        }
        return returnedModel;
    }

//    @SuppressWarnings("unchecked")
//    public UserModel updateUser(UserModel model) {
//        UserModel userModel = new UserModel();
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            log.debug("UserAuthClient: updateUser called ");
//            HttpPut request = new HttpPut(AUTH_SERVICE_PATH + "update");
//            JSONObject json = new JSONObject();
//            json.put("id", model.getId());
//            json.put("firstname", model.getFirstname());
//            json.put("lastname", model.getLastname());
//            json.put("email", model.getEmail());
//            json.put("mobileno", model.getMobileno());
//            log.info("Token passed " + model.getToken());
//            json.put("token", model.getToken());
//            StringEntity params = new StringEntity(json.toString(), "UTF-8");
//            request.addHeader("content-type", "application/json;charset=UTF-8");
//            request.addHeader("charset", "UTF-8");
//            request.setEntity(params);
//            long startTime = System.currentTimeMillis();
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    userModel = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), UserModel.class);
//                    userModel.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                } else {
//                    userModel.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                }
//            }
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            log.info("updateUser request/response time in milliseconds: " + elapsedTime);
//        } catch (IOException e) {
//            log.error("Exception caught.", e);
//        }
//        return userModel;
//    }
//
//    public UserModel getUserById(Long userId) {
//        log.info("UserAuthClient: getUserById called@@@@ " + userId);
//        UserModel model = new UserModel();
//        long startTime = System.currentTimeMillis();
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(AUTH_SERVICE_PATH + "find/" + userId);
//            request.addHeader("charset", "UTF-8");
//            CloseableHttpResponse response = httpClient.execute(request);
//            response.addHeader("content-type", "application/json;charset=UTF-8");
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    model = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), UserModel.class);
//                    model.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                } else {
//                    model.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                }
//
//            }
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            log.info("getUserById######## request/response time in milliseconds: " + elapsedTime);
//        } catch (IOException e) {
//            log.error("Exception caught.", e);
//        }
//        return model;
//    }
//
//    public UserModel getUserByEmail(String email) {
//        log.info("UserAuthClient: getUserByEmail called@@@@@ ");
//        long startTime = System.currentTimeMillis();
//        UserModel model = new UserModel();
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(AUTH_SERVICE_PATH + "findemail/" + email);
//            request.addHeader("charset", "UTF-8");
//            CloseableHttpResponse response = httpClient.execute(request);
//            response.addHeader("content-type", "application/json;charset=UTF-8");
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    model = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), UserModel.class);
//                    model.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                } else {
//                    model.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                }
//            }
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            log.info("getUserByEmail#$#$## request/response time in milliseconds: " + elapsedTime);
//        } catch (IOException e) {
//            log.error("Exception caught.", e);
//        }
//        return model;
//    }
//
//    @SuppressWarnings("unchecked")
//    public UserModel changeOldPassword(UserModel model, String password) {
//        UserModel returnedModel = new UserModel();
//        long startTime = System.currentTimeMillis();
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            log.debug("changePassword called ");
//            HttpPut request = new HttpPut(AUTH_SERVICE_PATH + "old/update/password");
//            JSONObject json = new JSONObject();
//            json.put("token", model.getToken());
//            json.put("email", model.getEmail());
//            json.put("password_hash", (password));
//            json.put("id", model.getId());
//            StringEntity params = new StringEntity(json.toString(), "UTF-8");
//            request.addHeader("content-type", "application/json;charset=UTF-8");
//            request.addHeader("charset", "UTF-8");
//            request.setEntity(params);
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    returnedModel = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), UserModel.class);
//                    returnedModel.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                } else {
//                    returnedModel.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                }
//
//            }
//
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            log.info("changePassword request/response time in milliseconds: " + elapsedTime);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("Exception caught.", e);
//        }
//        return returnedModel;
//    }
//
//    @SuppressWarnings("unchecked")
//    public UserModel changePassword(Long userId, String password) {
//        UserModel returnedModel = new UserModel();
//        long startTime = System.currentTimeMillis();
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            log.debug("changePassword called ");
//            HttpPut request = new HttpPut(AUTH_SERVICE_PATH + "update/forgot/password");
//            JSONObject json = new JSONObject();
//            json.put("password_hash", (password));
//            json.put("id", userId);
//            StringEntity params = new StringEntity(json.toString(), "UTF-8");
//            request.addHeader("content-type", "application/json;charset=UTF-8");
//            request.addHeader("charset", "UTF-8");
//            request.setEntity(params);
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    returnedModel = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), UserModel.class);
//                    returnedModel.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                } else {
//                    returnedModel.setResponsestatus(httpResponse.getStatusLine().getStatusCode());
//                }
//
//            }
//
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            log.info("changePassword request/response time in milliseconds: " + elapsedTime);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("Exception caught.", e);
//        }
//        return returnedModel;
//    }
//
//    public boolean checkUserEmailForUpdate(Long userId, String email) {
//        log.info("UserAuthClient: checkUserEmailForUpdate called ");
//        boolean check = true;
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(AUTH_SERVICE_PATH + "checkemail/" + userId + "/" + email);
//            request.addHeader("charset", "UTF-8");
//            CloseableHttpResponse response = httpClient.execute(request);
//            response.addHeader("content-type", "application/json;charset=UTF-8");
//            long startTime = System.currentTimeMillis();
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    check = false;
//                }
//            }
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            log.info("checkUserEmailForUpdate request/response time in milliseconds: " + elapsedTime);
//        } catch (IOException e) {
//            log.error("Exception caught.", e);
//        }
//        return check;
//    }
//
//    public boolean sendForgotPasswordLink(String email) {
//        log.debug("sendForgotPasswordLink:  called ");
//        boolean check = false;
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPost request = new HttpPost(AUTH_SERVICE_PATH + "forgotpassword/send");
//            JSONObject json = new JSONObject();
//
//            json.put("email", email);
//            StringEntity params = new StringEntity(json.toString(), "UTF-8");
//            request.addHeader("content-type", "application/json");
//            request.addHeader("charset", "UTF-8");
//            request.setEntity(params);
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    check = true;
//                }
//            }
//
//        } catch (IOException e) {
//            log.error("Exception caught.", e);
//        }
//        return check;
//    }
//
//    public boolean sendActivationLink(Long id, String email) {
//        log.debug("sendActivationLink ");
//        boolean check = false;
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPost request = new HttpPost(AUTH_SERVICE_PATH + "activation/send/activationlink");
//            JSONObject json = new JSONObject();
//            json.put("id", id);
//            json.put("email", email);
//            StringEntity params = new StringEntity(json.toString(), "UTF-8");
//            request.addHeader("content-type", "application/json");
//            request.addHeader("charset", "UTF-8");
//            request.setEntity(params);
//            CloseableHttpResponse response = httpClient.execute(request);
//            HttpEntity entity = response.getEntity();
//            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    check = true;
//                }
//            }
//        } catch (IOException e) {
//            log.error("Exception caught.", e);
//        }
//        return check;
//    }
    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }
}

// http://aspects.jcabi.com/annotation-cacheable.html
// http://aspects.jcabi.com/example-weaving.html
