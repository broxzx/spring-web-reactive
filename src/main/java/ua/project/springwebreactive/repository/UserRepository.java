package ua.project.springwebreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ua.project.springwebreactive.domain.UserEntity;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {

    Mono<UserEntity> findByUsername(String username);
}
