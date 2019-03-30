package io.project.app.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        LOGGER.info("*************** RESPONSE STARTED **************** ");
        RequestContext ctx = RequestContext.getCurrentContext();
        LOGGER.info("Adding the correlation id to the outbound headers. {}", filterUtils.getCorrelationId());
        LOGGER.info("HEADER NAMES " + ctx.getResponse().getHeaderNames().toString());
        // ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());
        ctx.getResponse().addHeader("Access-Control-Expose-Headers", "AUTH-TOKEN");
        //ctx.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        return ctx.getResponse();
    }
}
