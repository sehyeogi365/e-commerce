package kr.hhplus.be.server.domain.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
    private ErrorCode errorCode;
}
