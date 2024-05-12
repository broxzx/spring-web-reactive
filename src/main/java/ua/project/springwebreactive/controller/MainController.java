package ua.project.springwebreactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ua.project.springwebreactive.domain.Message;

@RestController
@RequestMapping("controller")
public class MainController {

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
}
