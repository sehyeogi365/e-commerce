package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    //포인트 조회
    public Point getUserPoint(int userId){
        return pointRepository.getUserPoint(userId);
    }


    //잔액 충전
    public Integer chargePoint(int userId, int point){
        return pointRepository.chargePoint(userId, point);
    }

}
