package io.project.app.unicorn;

import io.project.app.domain.CareerAccount;
import io.project.app.domain.ShipperAccount;
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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


@Named
@SessionScoped
public class AccountProfileClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(AccountProfileClient.class);
    private final String BASE_URL = getBundle().getString("gateway");

    public AccountProfileClient() {

    }

    @PostConstruct
    public void init() {
        LOG.info("AccountProfileClient called");
    }
    
    
    public CareerAccount getCareerProfile(String id) {
        CareerAccount returnedModel = new CareerAccount();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Update update shipper profile ");
            HttpGet request = new HttpGet(BASE_URL + "/profile/api/v2/organization/career/id?id="+id);            
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");            
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), CareerAccount.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return returnedModel;
    }
    
    
     public ShipperAccount getShipperProfile(String id) {
        ShipperAccount returnedModel = new ShipperAccount();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Update update shipper profile ");
            HttpGet request = new HttpGet(BASE_URL + "/profile/api/v2/organization/shipper/id?id="+id);
            
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");            
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), ShipperAccount.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return returnedModel;
    }

    public ShipperAccount updateShipperProfile(ShipperAccount model) {
        ShipperAccount returnedModel = new ShipperAccount();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Update update shipper profile ");
            HttpPut request = new HttpPut(BASE_URL + "/profile/api/v2/organization/shipper");
            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), ShipperAccount.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return returnedModel;
    }

    public CareerAccount updateCareerProfile(CareerAccount model) {
        CareerAccount returnedModel = new CareerAccount();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOG.info("Update updateCareerProfile ");
            HttpPut request = new HttpPut(BASE_URL + "/profile/api/v2/organization/career");
            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOG.info("status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), CareerAccount.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return returnedModel;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }
}
