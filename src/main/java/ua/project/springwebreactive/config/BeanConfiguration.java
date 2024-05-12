package ua.project.springwebreactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import ua.project.springwebreactive.handlers.GreetingHandler;

import java.util.Map;

@Configuration
public class BeanConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(GreetingHandler greetingHandler) {
        RequestPredicate route = RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN));

        return RouterFunctions.route(route, greetingHandler::hello)
                .andRoute(
                        RequestPredicates.GET("/"),
                        serverRequest -> {
                            String username = serverRequest.queryParam("username")
                                    .orElse("anonymous");
                            return ServerResponse.ok()
                                    .render("index", Map.of("username", username));
                        }
                );
    }
}

