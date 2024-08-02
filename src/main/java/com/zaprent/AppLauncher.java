package com.zaprent;

import com.zaprent.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication(exclude = {
        DispatcherServletAutoConfiguration.class,
        ElasticsearchRepositoriesAutoConfiguration.class,
        ElasticsearchRestClientAutoConfiguration.class,
})
//@ComponentScan(basePackages = {"com.zaprent"},
//        excludeFilters = {
//            @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.zaprent\\..*launcher\\..*")
//        })
//@EnableMongoRepositories(basePackages = "com.zaprent.repo")
//@ComponentScan(basePackages = {"com.zaprent"})
//@EnableAutoConfiguration
//@Configuration
public class AppLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AppLauncher.class, args);
    }

    @Bean
    public DispatcherServletRegistrationBean zaprentAPI() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfig.class);
        dispatcherServlet.setApplicationContext(applicationContext);
        DispatcherServletRegistrationBean servletRegistrationBean =
                new DispatcherServletRegistrationBean(dispatcherServlet, "/api/*");
        servletRegistrationBean.setName("zaprentAPI");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
}
