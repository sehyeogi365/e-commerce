package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository {

    //포인트 조회
    Point getUserPoint(int userId);

    //잔액 충전
    Point chargePoint(int userId);
}
