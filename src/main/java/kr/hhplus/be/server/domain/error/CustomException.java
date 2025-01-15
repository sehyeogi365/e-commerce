package kr.hhplus.be.server.domain.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
    private ErrorCode errorCode;
    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
