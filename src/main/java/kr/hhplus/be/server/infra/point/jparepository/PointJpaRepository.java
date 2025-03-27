package kr.hhplus.be.server.infra.point.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    // 포인트 조회
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM Point p WHERE p.userId = :userId")
    Point getByUserId(long userId);

    // 잔액 충전
    @Modifying
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query(value = "Update user_point SET point = point + :point WHERE userId = :userId", nativeQuery = true)
    Integer chargePoint(@Param("userId") long userId, @Param("point") int point);

    // 포인트 차감
    @Modifying
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "Update user_point p SET point = point - :point WHERE userId = :userId", nativeQuery = true)
    Integer usePoint(@Param("userId") long userId, @Param("point") int point);

}
