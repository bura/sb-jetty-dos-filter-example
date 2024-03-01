package dev.bura.jetty.df.configuration;

import dev.bura.jetty.df.web.filter.CustomDoSFilter;
import org.eclipse.jetty.ee10.servlets.DoSFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author a.bloshchetsov
 */
@Configuration
class JettyConfiguration {

    @Bean
    FilterRegistrationBean<DoSFilter> dosFilter(@Value("${jetty.dosFilter.maxRequestMs}") Long maxRequestMs) {
        final var registrationBean = new FilterRegistrationBean<DoSFilter>();
        registrationBean.setFilter(new CustomDoSFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.addInitParameter("maxRequestMs", maxRequestMs.toString());

        return registrationBean;
    }

}
