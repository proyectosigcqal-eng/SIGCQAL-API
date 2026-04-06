package com.sigcqal.api.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class Config {

 @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("SICAT")
                .version("1.0.0")
                .description("DocumentaciÃ³n de mi API REST")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact()
                    .name("Secretaria de Finanzas")
                    .email("tu@email.com")
                    .url("https://sefin.zacatecas.gob.mx"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://springdoc.org")));
    }


}