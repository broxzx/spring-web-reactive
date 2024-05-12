package ua.project.springwebreactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ua.project.springwebreactive.domain.UserEntity;
import ua.project.springwebreactive.domain.UserRole;
import ua.project.springwebreactive.dto.UserRequestDto;
import ua.project.springwebreactive.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<UserEntity> obtainedUserEntity = userRepository.findByUsername(username);

        return obtainedUserEntity.map(user -> new User(user.getUsername(), user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))))
                .cast(UserDetails.class);
    }

    public void saveUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.username())
                .password(userRequestDto.password())
                .role(UserRole.ROLE_USER)
                .build();

        userRepository.save(userEntity);
    }

    public Mono<UserEntity> saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
