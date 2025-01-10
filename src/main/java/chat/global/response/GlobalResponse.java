package chat.global.response;

import chat.global.response.status.ResultCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static chat.global.response.status.ResultCodeEnum.*;

@Getter
public class GlobalResponse<T> {
    private final ResultCodeEnum resultCode;
    private final HttpStatus headers;
    private final T resultData;

    public GlobalResponse(T resultData, ResultCodeEnum resultCode, HttpStatus status) {
        this.resultData = resultData;
        this.resultCode = resultCode;
        this.headers = status;
    }

    public static <T> GlobalResponse<T> of(T resultData, ResultCodeEnum resultCode, HttpStatus status) {
        return new GlobalResponse<>(resultData, resultCode, status);
    }

    public static <T> GlobalResponse<T> of(HttpStatus headers, T data) {
        return of(data, SUCCESS, headers);
    }

    public static <T> GlobalResponse<T> of(T data) {
        return of(HttpStatus.OK, data);
    }
}
