package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.api.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final PointRepository pointRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    // 주문하기
    @Transactional
    public OrderResponse orderProduct(Order order){
        // 상품 수량 확인후 주문 신청
        Product product = productRepository.findById(order.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        OrderResponse response = new OrderResponse(order.getId(), order.getUserId(), order.getCouponId(), order.getProductId());

        int originPrice = calculateOriginPrice(product.getQuantity(), product.getPrice());

        // 쿠폰 정보 확인 및 수량 차감
        int discountPrice = 0;

        if(product.getQuantity() <= 0){
            throw new CustomException(ErrorCode.ITEM_QUANTITY_ZERO);
        }

        // 주문에 쿠폰 적용
        if(order.getCouponId() > 0){//쿠폰 적용 + 가격 감소
            //UserCoupon userCoupon = couponRepository.findUserCouponInfo(payment.getUserId(),payment.getCouponId()).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
            discountPrice = calculateDiscountPrice(originPrice, order.getUserId(), order.getCouponId());
            couponRepository.useCoupon(order.getCouponId());
            //포인트 차감
            pointRepository.usePoint(order.getUserId(), discountPrice);
        } else {
            pointRepository.usePoint(order.getUserId(), originPrice);
        }
        return response;
    }

    // 원가 계산
    public Integer calculateOriginPrice(int quantity, int price) {
        return quantity * price;
    }

    // 할인 가격 계산
    public Integer calculateDiscountPrice(int originPrice, long userId, long couponId) {

        UserCoupon userCoupon = couponRepository.findUserCouponInfo(userId, couponId)
                .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        Coupon coupon = userCoupon.getCoupon();
        if (coupon == null) {
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND); // coupon이 null인 경우 처리
        }
        return (originPrice * (100- coupon.getPercent()) / 100);
    }

    // 주문 목록 조회
    public List <OrderResponse> getOrderList(long userId){
        List<Order> orderList = orderRepository.getOrders(userId);

        if(orderList.isEmpty()){
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }
        List<OrderResponse> response = new ArrayList<>();

        for(Order order: orderList){
            response.add(new OrderResponse(order.getId(), order.getUserId(),order.getCouponId(), order.getProductId()));
        }

        return response;
    }
}
