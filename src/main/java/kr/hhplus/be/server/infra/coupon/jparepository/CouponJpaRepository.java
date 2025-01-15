package kr.hhplus.be.server.infra.coupon.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;

import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;



public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    //쿠폰 목록 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.quantity > 0")
    List<Coupon> findAll();

    //쿠폰 한행 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :id")
    Optional<Coupon> findById(@Param("id") long id);

    //쿠폰 사용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query("UPDATE Coupon c SET c.quantity = c.quantity - 1 WHERE c.id = :id AND c.quantity > 0")
    void useCoupon(@Param("id") long id);

    //쿠폰 발급
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    UserCoupon save(@Param("id") long id, @Param("userId") int userId);

    //사용자 쿠폰 목록 조회
    @Query("SELECT c FROM UserCoupon c WHERE c.userId = :userId")
    List<UserCoupon> findByUserId(@Param("userId")int userId);

}
