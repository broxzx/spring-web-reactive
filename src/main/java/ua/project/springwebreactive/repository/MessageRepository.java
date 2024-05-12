package ua.project.springwebreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.project.springwebreactive.domain.Message;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {

}
