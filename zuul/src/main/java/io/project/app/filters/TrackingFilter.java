package io.project.app.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.project.app.model.Claim;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingFilter.class);

//    @Autowired
//    @Lazy
//    private IUserService userService;

    @Autowired
    private FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent() {
        return filterUtils.getCorrelationId() != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

//    private WebClient reactiveClient(String token) {
//        return WebClient.create("http://auth:5001/api/v2/users/verify/" + token);
//    }

    @Autowired
    private RestTemplate restTemplate;

    public String traceClaim(Claim claim) {
        return claim.toString();
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        //  String mpFront = request.getHeader("MP-FRONT");
        String userAgent = request.getHeader("User-Agent");

        String authToken = request.getHeader("AUTH-TOKEN");

        LOGGER.info("ZUUL report: User-Agent is " + userAgent);

        LOGGER.info("ZUUL report: authToken is " + authToken);

        try {
            if (ctx.getRequest().getRequestURI().contains("actuator")) {
                ctx.setResponseStatusCode(401);
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody("Dear user your requestes url does not exist any more.");
                return null;
            }

//            if (!ctx.getRequest().getRequestURI().contains("/gateway/web/auth")
//                    && !ctx.getRequest().getRequestURI().contains("/gateway/web/register")
//                   
//                    && !ctx.getRequest().getRequestURI().contains("/gateway/web/stripe")) {
//
//                //String authToken = request.getHeader("AUTH-TOKEN");
//                if (authToken == null) {
//                    LOGGER.info("Aut token does not exist, bye ");
//                    ctx.setResponseStatusCode(401);
//                    ctx.setSendZuulResponse(false);
//                    ctx.setResponseBody("MP-AUTH-TOKEN token does not exist, or invalid");
//                    return null;
//                }
//
//                Claim check = restTemplate.getForObject("http://auth:5001/api/v2/users/verify/user/" + authToken, Claim.class);
//
//                if (check != null) {
//                    LOGGER.info("ZUUL: User tokes is valid: claim is valid " + check);
//                } else {
//                    LOGGER.info("CLAIM IS INVALID??? ############### " + check);
//                    ctx.setResponseStatusCode(401);
//                    ctx.setSendZuulResponse(false);
//                    ctx.setResponseBody("MP-AUTH-TOKEN token does not exist, or invalid");
//                    return null;
//                }
//            }

        } catch (RestClientException e) {
            LOGGER.info("ERROR Claim is null " + e.getLocalizedMessage());
            //ctx.setResponseStatusCode(401);
            //ctx.setSendZuulResponse(false);
           // ctx.setResponseBody("Exception in ZULL, some header are missing");
            return null;
        }

       
     
        return null;
    }
}
