package chat.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}