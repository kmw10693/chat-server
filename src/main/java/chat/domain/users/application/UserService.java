package chat.domain.users.application;

import chat.domain.users.domain.User;
import chat.domain.users.domain.repository.UserRepository;
import chat.domain.users.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userR2dbcRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> create(UserCreateRequest userCreateRequest) {
        return userR2dbcRepository.save(User.builder()
                        .name(userCreateRequest.username())
                        .password(passwordEncoder.encode(userCreateRequest.password())).build());
    }

    public Flux<User> findAll() {
        return userR2dbcRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return userR2dbcRepository.findById(id);
    }

    public Mono<User> update(Long id, String name){
        return userR2dbcRepository.findById(id)
                .flatMap(u -> {
                    u.setName(name);
                    return userR2dbcRepository.save(u);
                });
    }
}
