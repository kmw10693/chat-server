package chat.global.response.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(100);

    private final int code;
}
