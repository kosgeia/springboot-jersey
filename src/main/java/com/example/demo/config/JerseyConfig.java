package com.example.demo.config;

import com.example.demo.jersey.AnotherJerseyResource;
import com.example.demo.jersey.HelloJerseyResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(HelloJerseyResource.class); // Scan Jersey resources
        register(AnotherJerseyResource.class); // Scan Jersey resources
        property(ServletProperties.FILTER_FORWARD_ON_404, true); // Forward 404 requests to Spring MVC
    }
}
