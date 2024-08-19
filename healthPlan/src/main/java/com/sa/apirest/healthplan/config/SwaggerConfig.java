package com.sa.apirest.healthplan.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;


@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getApiInfo() {

        ApiResponse okAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" : \"Traido con exito!\"}")))
        );

        ApiResponse updatedAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" : \"Se actualizo un registro!\"}")))
        );

        ApiResponse createdAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 201, \"Status\" : \"Created!\", \"Message\" : \"Se creo un registro con exito!\"}")))
        );

        ApiResponse noContentAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 204, \"Status\" : \"NoContent!\", \"Message\" : \"No hay contenido con que responder!\"}")))
        );
        ApiResponse badRequestAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 400, \"Status\" : \"BadRequest!\", \"Message\" : \"El request no es valido!\"}")))
        );

        ApiResponse unauthorizedAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 401, \"Status\" : \"Unauthorized!\", \"Message\" : \"Las credenciales no son validas!\"}")))
        );

        ApiResponse notFoundAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 404, \"Status\" : \"NotFound!\", \"Message\" : \"No se encontro ningun registro!\"}")))
        );

        ApiResponse internalServerErrorAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\"code\" : 500, \"Status\" : \"InternalServerError!\", \"Message\" : \"Un error inesperado ocurrio en el servidor!\"}")))
        );

        Components components = new Components();
        components.addResponses("okAPI", okAPI);
        components.addResponses("notFoundAPI", notFoundAPI);
        components.addResponses("createdAPI", createdAPI);
        components.addResponses("UnauthorizedAPI", unauthorizedAPI);
        components.addResponses("badRequestAPI", badRequestAPI);
        components.addResponses("updatedAPI", updatedAPI);
        components.addResponses("noContentAPI", noContentAPI);
        components.addResponses("internalServerErrorAPI", internalServerErrorAPI);

        return new OpenAPI()
                .openapi("3.0.1")
                .components(components)
                .info(new Info()
                        .title("MS healthplan")
                        .version("v1")
                        .description("Manejo de planes medicos")
                        .termsOfService("https://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Martin Estrada")
                                .email("martintheduck1@gmail.com")
                                .url("https://github.com/martinSaav"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
