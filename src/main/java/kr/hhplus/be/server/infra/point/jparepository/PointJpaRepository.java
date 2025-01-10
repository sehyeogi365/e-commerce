package kr.hhplus.be.server.infra.point.jparepository;

import kr.hhplus.be.server.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    //포인트 조회
    Point findByUserId(int userId);

    //잔액 충전
    @Query(value = "Update Point p SET p.point = p.point + :point WHERE p.userId = :userId", nativeQuery = true)
    Integer chargePoint(@Param("userId") int userId, @Param("point") int point);
}
