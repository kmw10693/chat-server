package chat.domain.users.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationResponse(
        @NotBlank String accessToken
) {
    public static AuthenticationResponse of(@NotBlank String accessToken) {
        return new AuthenticationResponse(accessToken);
    }
}
