package io.mitrofanovbp.autoservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoService Lite CRM API")
                        .description("The minimum CRM for a car service. Author: Bogdan Mitrofanov (mitrofanovbp)")
                        .version("0.1.0")
                        .license(new License().name("All rights reserved.")));
    }
}
