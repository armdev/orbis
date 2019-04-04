package io.project.app.unicorn;

import io.project.app.dto.SearchResultDTO;
import io.project.app.dto.TagsDTO;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@SessionScoped
public class TagClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(TagClient.class);
    private final String BASE_URL = getBundle().getString("gateway");

    public TagClient() {

    }

    @PostConstruct
    public void init() {
        LOG.info("TagClient called");
    }

    public TagsDTO findAllTags() {
        LOG.info("Find All tags ");
        TagsDTO model = new TagsDTO();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/marshal/api/v2/tags/all");
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), TagsDTO.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Find All Questions: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return model;
    }

    public SearchResultDTO searchByTag(String tag) {
        LOG.info("searchByTag By tag " + tag);
        SearchResultDTO model = new SearchResultDTO();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/marshal/api/v2/search/full?tag=" + tag);
            request.addHeader("charset", "UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    model = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), SearchResultDTO.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("searchByTag: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOG.error("Exception caught.", e);
        }
        return model;
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }
}
