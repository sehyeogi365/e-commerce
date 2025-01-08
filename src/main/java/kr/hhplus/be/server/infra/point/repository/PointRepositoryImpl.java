package kr.hhplus.be.server.infra.point.repository;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.infra.point.jparepository.PointJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    public PointRepositoryImpl(PointJpaRepository pointJpaRepository) {
        this.pointJpaRepository = pointJpaRepository;
    }

    @Override
    public Point getUserPoint(int userId) {
        return pointJpaRepository.findByUserId(userId);
    }

    @Override
    public Point chargePoint(int userId) {
        return pointJpaRepository.chargePoint(userId);
    }

}
