package ua.project.springwebreactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.project.springwebreactive.domain.MessageEntity;
import ua.project.springwebreactive.service.MessageService;

@RestController
@RequestMapping("controller")
@RequiredArgsConstructor
public class MainController {

    private final MessageService messageService;

    @GetMapping
    public Flux<MessageEntity> getMessages(@RequestParam(required = false, defaultValue = "0") Long page,
                                           @RequestParam(required = false, defaultValue = "10") Long size) {
        return Flux
                .just("Hello request!",
                        "more message",
                        "even more")
                .skip(page)
                .take(size)
                .map(MessageEntity::new);
    }

    @GetMapping("/messages")
    public Flux<MessageEntity> getListMessages(@RequestParam(required = false, defaultValue = "0") Long page,
                                               @RequestParam(required = false, defaultValue = "10") Long size) {
        return messageService.getAllMessages();
    }

    @PostMapping
    public Mono<MessageEntity> saveMessage(@RequestBody MessageEntity messageEntity) {
        return messageService.saveMessage(messageEntity);
    }
}
