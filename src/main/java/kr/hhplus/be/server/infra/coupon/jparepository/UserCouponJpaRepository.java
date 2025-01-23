package kr.hhplus.be.server.infra.coupon.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

    //쿠폰 발급
    UserCoupon save(UserCoupon userCoupon);
}
