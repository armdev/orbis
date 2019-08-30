package io.project.app.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PreFilter2 extends ZuulFilter {

    private static final int FILTER_ORDER = 2;
    private static final boolean SHOULD_FILTER = true;

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

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("PreFilter 2: "
                + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        
        log.info("Second level validation of request");

        return null;
    }
}
