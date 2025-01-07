package kr.hhplus.be.server.infra.point.repository;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepositoryImpl implements PointRepository {


    @Override
    public Point getUserPoint(int userId) {
        return getUserPoint(userId);
    }

    @Override
    public Point chargePoint(int userId) {
        return chargePoint(userId);
    }

}
