package kr.hhplus.be.server.interfaces;



import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("Unhandled exception occurred: ", e);
        return ResponseEntity.status(500).body(new ErrorResponse("500", "에러가 발생했습니다."));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        logger.error("Unhandled exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("400", e.getMessage()));
    }

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
//        logger.error("Unhandled exception occurred: ", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("500", "서버 내부 오류가 발생했습니다."));
//    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        // CustomException에서 ErrorCode를 가져와 상태 코드와 메시지를 처리
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus().toString(), errorCode.getMessage());
        logger.error("Unhandled exception occurred: ", e);
        // 상태 코드와 메시지에 맞는 응답 반환
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }
}
