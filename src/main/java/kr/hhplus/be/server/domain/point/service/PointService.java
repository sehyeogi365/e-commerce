package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.interfaces.point.dto.PointResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PointService {

    private final PointRepository pointRepository;

    //포인트 조회
    public PointResponse getUserPoint(int userId){
        Point point = pointRepository.getUserPoint(userId);
        return new PointResponse(userId, point.getPoint());
    }

    //잔액 충전
    public Integer chargePoint(int userId, int point){

        //0포인트 충전시, 10000단위가 아닐시, 백만포인트 이상 보유시, 충전이후 백만포인트 초과시 등등,
        if(pointRepository.chargePoint(userId, point) <= 0) {
            throw new CustomException(ErrorCode.POINT_NOT_FOUND);
        }

        if(pointRepository.chargePoint(userId, point) % 10000 != 0){
            throw new IllegalArgumentException("10000포인트 단위어야 합니다!");
        }

        if(pointRepository.getUserPoint(userId).getPoint() >= 1000000){
            throw new IllegalArgumentException("백만포인트를 초과하면 안됩니다!");
        }

        if(pointRepository.getUserPoint(userId).getPoint() + pointRepository.chargePoint(userId, point)>= 1000000){
            throw new IllegalArgumentException("충전시 백만포인트를 초과하면 안됩니다!");
        }

        return pointRepository.chargePoint(userId, point);
    }

}
