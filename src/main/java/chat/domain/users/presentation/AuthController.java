package chat.domain.users.presentation;

import chat.domain.users.dto.request.AuthenticationRequest;
import chat.domain.users.dto.response.AuthenticationResponse;
import chat.global.security.jwt.JwtTokenProvider;
import chat.global.response.GlobalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Mono<GlobalResponse<AuthenticationResponse>> login(
            @Valid @RequestBody Mono<AuthenticationRequest> authRequest) {
        Hooks.onOperatorDebug();

        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                login.username(), login.password()))
                        .map(this.tokenProvider::createToken))
                .map(jwt -> {
                    AuthenticationResponse authenticationResponse = AuthenticationResponse.of(jwt);
                    return GlobalResponse.of(authenticationResponse);
                });
    }
}
