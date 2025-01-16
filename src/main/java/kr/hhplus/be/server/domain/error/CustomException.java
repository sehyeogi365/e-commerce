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
    public CustomException(ErrorCode errorCode) {//에러코드 필수로 받아오기
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode;
    }
}
