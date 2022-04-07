package com.address.book.addressbookapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AddressBookApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApiApplication.class, args);

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/search/{isRemote}").allowedOrigins("http://localhost:8080");
            }
        };
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
