package chat.domain.users.presentation;

import chat.domain.users.application.UserService;
import chat.domain.users.dto.request.UserCreateRequest;
import chat.domain.users.dto.response.UserCreateResponse;
import chat.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public Mono<GlobalResponse<UserCreateResponse>> createUser(@RequestBody UserCreateRequest userCreateRequest){
        return userService.create(userCreateRequest)
                .map(UserCreateResponse::from)
                .map(GlobalResponse::of);
    }
}
