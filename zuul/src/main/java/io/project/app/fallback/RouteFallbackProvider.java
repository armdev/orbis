package io.project.app.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Slf4j
public class RouteFallbackProvider implements FallbackProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteFallbackProvider.class);

    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() {
                log.error("FallbackResponse " + HttpStatus.SERVICE_UNAVAILABLE);
                
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            @Override
            public int getRawStatusCode() {
                log.error("FallbackResponse 1 " + HttpStatus.SERVICE_UNAVAILABLE);
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            @Override
            public String getStatusText() {
                log.error("FallbackResponse 2 " + HttpStatus.SERVICE_UNAVAILABLE);
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() {
                if (cause != null && cause.getMessage() != null) {
                    LOGGER.error("Route:{} Message：{}", getRoute(), cause.getMessage());
                    return new ByteArrayInputStream(cause.getMessage().getBytes());
                } else {
                    LOGGER.error("Route:{} Message：{}", getRoute(), "Some error");
                    return new ByteArrayInputStream("Some bytes".getBytes());
                }
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String s, Throwable throwable) {
        
         log.error("fallbackResponse 3 " + throwable.getLocalizedMessage());
        return fallbackResponse(null);
    }
}
