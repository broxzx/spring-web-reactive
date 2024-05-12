package ua.project.springwebreactive.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;
        try {
            username = jwtUtil.getUsername(authToken);
        } catch (Exception e) {
            username = null;
            System.out.println(e.getMessage());
        }

        if (username != null && jwtUtil.validateToken(authToken)) {
            List<String> roles = jwtUtil.getRoles(authToken);

            List<SimpleGrantedAuthority> authorities = roles
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

            return Mono.just(authenticationToken);
        } else {
            return Mono.empty();
        }
    }
}
