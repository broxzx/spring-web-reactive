package ua.project.springwebreactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.project.springwebreactive.config.JwtUtil;
import ua.project.springwebreactive.domain.UserEntity;
import ua.project.springwebreactive.dto.UserRequestDto;
import ua.project.springwebreactive.service.UserService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@RequestBody UserRequestDto userRequestDto) {
        return userService.findByUsername(userRequestDto.username())
                .cast(UserEntity.class)
                .map(user -> Objects.equals(user.getPassword(), userRequestDto.password()) ? ResponseEntity.ok().body(jwtUtil.generateToken(user)) :
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
