package br.com.foursales.ordermanagement.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@OpenAPIDefinition(servers = {@Server(url = "${server.servlet.context-path}", description = "Default Server URL")})
@Configuration
public class OpenApiConfiguration {

    private static final String API_NAME = "API GESTÃO PEDIDOS";
    private static final String API_DECRIPTION = "API com serviços de gestão de pedidos.";
    private static final String API_KEY = "apiKey";


    @Bean
    OpenAPI openApi() {
        OpenAPI openAPI =  new OpenAPI()
                .info(new Info()
                        .title(API_NAME)
                        .description(API_DECRIPTION)
                        .termsOfService("")
                        .version("3.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("apiteam@swagger.io")));

            openAPI.components(new Components()
                            .addSecuritySchemes(API_KEY,apiKeySecuritySchema()))
                    .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)));

        return openAPI;
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name("Authorization")
                .description("Informa o Token completo, junto com o Bearer")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
    }
}
