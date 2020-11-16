package com.spk.web.myfirstws;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
//Swagger ui: http://localhost:8080/my-first-ws/swagger-ui/index.html

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Contact contact = new Contact(
            "Sahar Pk",
            "http://www.test.com",
            "sahar.pk88@gmail.com"
    );
    //VendorExtensions are custom properties to describe some extra functionality
    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "Restful Web Service Documentation",
            "These pages document the My-First-WS Restful Web Service Endpoints",
            "1.0",
            "http://www.test.com",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions);


    @Bean
    public Docket apiDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPs")))
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spk.web.myfirstws"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
//   When using SpringFox version 2.9.2
//    @Bean
//    public LinkDiscoverers discovers(){
//        List<LinkDiscoverers> plugins=new ArrayList<>();
//        plugins.add(new CollectionJsonLinkDiscoverer());
//        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
//    }

}
