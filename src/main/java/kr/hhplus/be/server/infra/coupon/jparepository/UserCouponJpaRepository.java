package kr.hhplus.be.server.infra.coupon.jparepository;


import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

    // 사용자 쿠폰 목록 조회
    @Query("SELECT c FROM UserCoupon c WHERE c.userId = :userId")
    List<UserCoupon> getUserCoupon(@Param("userId")int userId);

    // 사용자 쿠폰 한행
    @Query("SELECT uc FROM UserCoupon uc WHERE uc.userId = :userId AND uc.couponId = :couponId")
    UserCoupon getByUserId(@Param("userId")int userId, @Param("couponId") int couponId);

    @Query("SELECT uc FROM UserCoupon uc WHERE uc.userId = :userId AND uc.couponId = :couponId")
    Optional<UserCoupon> findByUserIdAndCouponId(@Param("userId")int userId, @Param("couponId") int couponId);

    // 사용자 쿠폰 사용
    //@Lock(LockModeType.PESSIMISTIC_WRITE)

    @Modifying
    @Query(value = "UPDATE user_coupon SET statement = 'USED' WHERE id = :id", nativeQuery = true)
    void useCoupon(@Param("id") long id);
}
