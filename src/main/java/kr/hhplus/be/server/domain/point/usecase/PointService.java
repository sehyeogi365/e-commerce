package kr.hhplus.be.server.domain.point.usecase;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    //포인트 조회
    public Point getUserPoint(int userId){

        return pointRepository.getUserPoint(userId);
    }


    //잔액 충전
    public Point chargePoint(int userId){

        return pointRepository.chargePoint(userId);
    }


}
