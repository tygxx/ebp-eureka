package com.yy.ebp.eureka;

import com.yy.ebp.eureka.filter.DashboardFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${dashboard.enable}")
    private Boolean dashboardEnable;

    @Bean
    public FilterRegistrationBean userSessionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("dashboardFilter");
        DashboardFilter dashboardFilter = new DashboardFilter(dashboardEnable);
        registration.setFilter(dashboardFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER);

        return registration;
    }
}
