package kr.hhplus.be.server.infra.coupon.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {



    //사용자 쿠폰 목록 조회
    @Query("SELECT c FROM UserCoupon c WHERE c.userId = :userId")
    List<UserCoupon> findByUserId(@Param("userId")int userId);

    // 사용자 쿠폰 사용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query("UPDATE UserCoupon c SET c.couponStatus = 'USED' WHERE c.id = :id")
    void useCoupon(@Param("id") long id);
}
