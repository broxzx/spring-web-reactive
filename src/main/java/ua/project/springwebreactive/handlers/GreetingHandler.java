package ua.project.springwebreactive.handlers;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.project.springwebreactive.domain.MessageEntity;

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        Long page = request.queryParam("page")
                .map(Long::valueOf)
                .orElse(0L);
        Long size = request.queryParam("size")
                .map(Long::valueOf)
                .orElse(10L);

        Flux<MessageEntity> data = Flux
                .just("Hello request!",
                        "more message",
                        "even more")
                .skip(page)
                .take(size)
                .map(MessageEntity::new);

        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(data, MessageEntity.class);
    }
}
