package cyou.noteit.image_server.global.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_API_KEY(HttpStatus.UNAUTHORIZED, "유효하지 않은 키입니다."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
