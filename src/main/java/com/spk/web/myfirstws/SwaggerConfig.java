package com.spk.web.myfirstws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//Swagger ui: http://localhost:8080/my-first-ws/swagger-ui/index.html

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
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
