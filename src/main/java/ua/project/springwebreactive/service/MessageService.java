package ua.project.springwebreactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.project.springwebreactive.domain.Message;
import ua.project.springwebreactive.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Flux<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Mono<Message> saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
