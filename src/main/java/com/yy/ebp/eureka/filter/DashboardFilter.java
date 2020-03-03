package com.yy.ebp.eureka.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: ebp-eureka
 * @description: 过滤器
 * @date: 2020/02/29
 * @author: tengyong
 **/
public class DashboardFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DashboardFilter.class);

    private Boolean dashboardEnable = true;

    public DashboardFilter(boolean dashboardEnable) {
        this.dashboardEnable = dashboardEnable;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String servletPath = request.getServletPath();
        if(dashboardEnable && "/".equals(servletPath)) {
            logger.debug("重定向到dashboard.html");
            response.sendRedirect("./dashboard.html");
        }else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
