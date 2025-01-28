package kr.hhplus.be.server.domain.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SUCCESS(HttpStatus.OK, "OK"),

    //== 400 ==//
    NOT_SUPPORTED_HTTP_METHOD(HttpStatus.BAD_REQUEST,"지원하지 않는 Http Method 방식입니다."),
    NOT_VALID_METHOD_ARGUMENT(HttpStatus.BAD_REQUEST,"유효하지 않은 Request Body 혹은 Argument입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 사용자를 찾을 수 없습니다."),
    POINT_NOT_FOUND(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),
    ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품을 찾을 수 없습니다."),
    COUPON_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 쿠폰을 찾을 수 없습니다."),
    COUPON_QUANTITY_ZERO(HttpStatus.BAD_REQUEST, "해당 쿠폰을 수량이 부족합니다."),
    COUPON_OVER_DATE(HttpStatus.BAD_REQUEST, "해당 쿠폰을 만료기간일이 지났습니다."),
    ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 주문내역을 찾을 수 없습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 결제내역을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
