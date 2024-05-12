package ua.project.springwebreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.project.springwebreactive.domain.MessageEntity;

public interface MessageRepository extends ReactiveCrudRepository<MessageEntity, Long> {

}
