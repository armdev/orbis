/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services;

import io.project.app.dto.UserData;
import io.project.app.util.GsonConverter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class FriendService {

    public int sendUserData(UserData model) {
        log.info("sendUserData: friend called ");
        int status = 0;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://friend:6001/api/v2/users/user");
            String toJson = GsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("friend httpResponse.getStatusLine().getStatusCode() " + httpResponse.getStatusLine().getStatusCode());
                status = httpResponse.getStatusLine().getStatusCode();
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    //   returnedModel = GsonConverter.from(EntityUtils.toString(httpResponse.getEntity()), User.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("friend request/response time in milliseconds: " + elapsedTime);

        } catch (IOException e) {
            log.error("Exception caught.", e);
        }
        return status;
    }

}
