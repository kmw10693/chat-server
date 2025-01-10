package chat.domain.users.dto.response;

import chat.domain.users.domain.User;
import jakarta.validation.constraints.NotBlank;

public record UserCreateResponse(
        @NotBlank Long id,
        @NotBlank String username
) {
    public static UserCreateResponse from(User user) {
        return new UserCreateResponse(user.getId(), user.getName());
    }
}
