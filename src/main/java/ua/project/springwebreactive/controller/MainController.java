package ua.project.springwebreactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.project.springwebreactive.domain.Message;
import ua.project.springwebreactive.service.MessageService;

@RestController
@RequestMapping("controller")
@RequiredArgsConstructor
public class MainController {

    private final MessageService messageService;

    @GetMapping
    public Flux<Message> getMessages(@RequestParam(required = false, defaultValue = "0") Long page,
                                     @RequestParam(required = false, defaultValue = "10") Long size) {
        return Flux
                .just("Hello request!",
                        "more message",
                        "even more")
                .skip(page)
                .take(size)
                .map(Message::new);
    }

    @GetMapping("/messages")
    public Flux<Message> getListMessages(@RequestParam(required = false, defaultValue = "0") Long page,
                                     @RequestParam(required = false, defaultValue = "10") Long size) {
        return messageService.getAllMessages();
    }

    @PostMapping
    public Mono<Message> saveMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }
}
