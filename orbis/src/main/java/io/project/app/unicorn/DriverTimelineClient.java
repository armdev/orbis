package io.project.app.unicorn;

import com.google.gson.Gson;
import io.project.app.web.dto.DriverTimeline;

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
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

@Named
@ApplicationScoped
public class DriverTimelineClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(DriverTimelineClient.class);

   private final String BASE_URL = getBundle().getString("gateway");

    private Gson gson;

    public DriverTimelineClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("PreferencesClient called");
    }

    @SuppressWarnings("unchecked")
    public DriverTimeline updateTimeline(DriverTimeline model) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("saveNewPreferences: saveNewPreferences ");
            HttpPut request = new HttpPut(BASE_URL + "/marshal/api/v2/drivers/timeline/record/update");
            gson = new Gson();
            String jsonInString = gson.toJson(model);
            StringEntity params = new StringEntity(jsonInString, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("userRegistration status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    model = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), DriverTimeline.class);

                }

            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("userRegistration request/response time in milliseconds: " + elapsedTime);

        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }

        return model;
    }

    @SuppressWarnings("unchecked")
    public DriverTimeline addNewTimeline(DriverTimeline model) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("addNewTimline: started ");
            HttpPost request = new HttpPost(BASE_URL + "timeline");

            gson = new Gson();
            String jsonInString = gson.toJson(model);

            StringEntity params = new StringEntity(jsonInString, "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("addNewTimline status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    model = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), DriverTimeline.class);

                }

            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("addNewTimline request/response time in milliseconds: " + elapsedTime);

        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return model;
    }

    public DriverTimeline findById(String id) {
        LOGGER.info("DriverTimeline: findById called@@@@ " + id);
        DriverTimeline model = new DriverTimeline();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "timeline/primary/" + id);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    model = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), DriverTimeline.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("DriverTimeline request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return model;
    }

    public List<DriverTimeline> findTimelineByDriverId(String driverId) {
        LOGGER.info("findTimelineByDriverId: findById called@@@@ " + driverId);
        List<DriverTimeline> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "timeline/" + driverId);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    //list = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), List.class);
                    list = mapper.readValue((EntityUtils.toString(httpResponse.getEntity())), new TypeReference<List<DriverTimeline>>() {
                    });
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("findById######## request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return list;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }
}
