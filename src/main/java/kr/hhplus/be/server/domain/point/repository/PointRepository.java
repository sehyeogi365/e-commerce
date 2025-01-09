package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.Point;

public interface PointRepository {

    //포인트 조회
    Point getUserPoint(int userId);

    //잔액 충전
    Integer chargePoint(int userId, int point);
}
