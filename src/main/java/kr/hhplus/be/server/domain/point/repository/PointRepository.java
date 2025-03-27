package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.Point;

public interface PointRepository {

    // 포인트 조회
    Point getByUserId(long userId);

    // 잔액 충전
    Integer chargePoint(long userId, int point);

    // 포인트 차감
    Integer usePoint(long userId, int point);
}
