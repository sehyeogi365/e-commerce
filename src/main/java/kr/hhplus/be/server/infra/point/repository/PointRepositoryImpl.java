package kr.hhplus.be.server.infra.point.repository;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.infra.point.jparepository.PointJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public Point getUserPoint(long userId) {
        return pointJpaRepository.findByUserId(userId);
    }

    @Override
    public Integer chargePoint(long userId, int point) {
        return pointJpaRepository.chargePoint(userId, point);
    }

    @Override
    public Integer usePoint(long userId, int point) {
        return pointJpaRepository.usePoint(userId, point);
    }

}
