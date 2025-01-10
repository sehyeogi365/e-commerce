package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {

    private final PointRepository pointRepository;

    //포인트 조회
    public Point getUserPoint(int userId){
        return pointRepository.getUserPoint(userId);
    }

    //잔액 충전
    public Integer chargePoint(int userId, int point){

        //0포인트 충전시, 10000단위가 아닐시, 백만포인트 이상 보유시, 충전이후 백만포인트 초과시 등등,
        if(pointRepository.chargePoint(userId, point) == 0) {
            return null;
        }

        if(pointRepository.chargePoint(userId, point) % 10000 != 0){
            return null;
        }

        if(pointRepository.getUserPoint(userId).getPoint() >= 1000000){
            return  null;
        }

        if(pointRepository.getUserPoint(userId).getPoint() + pointRepository.chargePoint(userId, point)>= 1000000){
            return null;
        }

        return pointRepository.chargePoint(userId, point);
    }

}
