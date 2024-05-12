package ua.project.springwebreactive.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.*;
import ua.project.springwebreactive.handlers.GreetingHandler;

import java.util.Map;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class BeanConfiguration {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

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

    @Bean
    @SneakyThrows
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(authorizeExchange -> authorizeExchange
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/controller/**").hasRole("ADMIN")
                        .anyExchange().authenticated())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManager authenticationManager) {
        return authenticationManager;
    }
}

