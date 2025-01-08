package kr.hhplus.be.server.infra.point.jparepository;

import kr.hhplus.be.server.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<Point, Integer> {

    //포인트 조회
    Point findByUserId(int userId);

    //잔액 충전
    Point chargePoint(int userId);
}
