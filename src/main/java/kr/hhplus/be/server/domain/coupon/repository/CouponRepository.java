package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

//@Repository //디비 조회가 일어나는 구간이 아니기에 지우기
public interface CouponRepository {

    //쿠폰 목록 조회
    List<Coupon> getCoupons();

    //쿠폰 한행 조회
    Optional<Coupon> getCouponInfo(long id);

    //쿠폰 사용
    void useCoupon(long id);

    //쿠폰 발급
    UserCoupon getCoupon(long id, int userId);

    //사용자 쿠폰 목록 조회
    List<UserCoupon> getUserCoupon(int userId);

}
