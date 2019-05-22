package com.imooc.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseEdgeServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(CourseEdgeServiceApplication.class);
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(CourseFilter courseFilter){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(courseFilter);
//        List<String> userPatterns = new ArrayList<>();
//        userPatterns.add("/*");
//        filterRegistrationBean.setUrlPatterns(userPatterns);
//        return filterRegistrationBean;
//    }
}
