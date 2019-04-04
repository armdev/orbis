package io.project.app.filters;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PostFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

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
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        RequestContext ctx = RequestContext.getCurrentContext();
        
       // log.info("Response filter");

        log.info("PostFilter Response : " + String.format("response's content type is %s", ctx.getResponse().getStatus()));
        //log.info("*************** RESPONSE STARTED **************** ");

        //log.info("HEADER NAMES " + ctx.getResponse().getHeaderNames().toString());
        ctx.getResponse().addHeader("Access-Control-Expose-Headers", "AUTH-TOKEN");

        return null;
    }
}
