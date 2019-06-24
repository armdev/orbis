package io.project.app.unicorn;

import io.project.app.domain.Shipment;
import io.project.app.util.GsonConverter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@ApplicationScoped
public class ShipmentClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(ShipmentClient.class);
    private String BASE_URL = getBundle().getString("gateway");

    public ShipmentClient() {

    }

    @PostConstruct
    public void init() {
        log.debug("ShipmentClient called");
    }

    @SuppressWarnings("unchecked")
    public Shipment addNewShipment(Shipment model) {
        Shipment returnedModel = new Shipment();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            log.info("ShipmentClient: addNewShipment ");
            HttpPost request = new HttpPost(BASE_URL + "/shipment/api/v2/shipments/shipment");

            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("ShipmentClient addNewShipment status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Shipment.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("addNewShipment request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            log.error("addNewShipment Exception caught.", e);
        }
        return returnedModel;
    }

    @SuppressWarnings("unchecked")
    public Shipment updateShipment(Shipment model) {
        Shipment returnedModel = new Shipment();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            log.info("ShipmentClient: updateShipment ");
            HttpPut request = new HttpPut(BASE_URL + "/shipment/api/v2/shipments/shipment/update/id");

            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("ShipmentClient updateShipment status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Shipment.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("updateShipment request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            log.error("updateShipment Exception caught.", e);
        }
        return returnedModel;
    }

    public Shipment getShipmentById(String id) {
        log.info("ShipmentClient: getShipmentById with id: " + id);
        Shipment model = new Shipment();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/shipment/api/v2/shipments/shipment/id?id=" + id);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Shipment.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("ShipmentClient  getShipmentById request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            log.error("ShipmentClient Exception caught.", e);
        }
        return model;
    }

    public List<Shipment> getShipmentList(String userId) {
        log.info("getShipmentList: called@@@@ " + userId);
        List<Shipment> model = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/shipment/api/v2/shipments/shipment/find/list/user/id?userId=" + userId);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), List.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("getShipmentList request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            log.error("Exception caught.", e);
        }
        return model;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }
}
