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
    @Transactional//보통이렇게 트랜잭션 붙여줌 예외 사항시 롤백
    public Integer chargePoint(int userId, int point){

        //0포인트 충전시, 10000단위가 아닐시, 백만포인트 이상 보유시, 충전이후 백만포인트 초과시 등등,
        //이미 업데이트 했기에 -1이 들어감 이경우 트랜잭션으로 롤백처리 해야 한다함

        //이런식으로 포인트값에대한 예외처리 먼저 설정
        Integer result = pointRepository.chargePoint(userId, point);

        Point userPoint = pointRepository.getUserPoint(userId);


        if(result <= 0) {
            throw new CustomException(ErrorCode.POINT_NOT_FOUND);
        }// <0 으로도 충분하다고 피드백

        if(result % 10000 != 0){
            throw new IllegalArgumentException("10000포인트 단위어야 합니다!");
        }

        if(result >= 1000000){
            throw new IllegalArgumentException("백만포인트 이상 충전하면 안됩니다!");
        }

        if(userPoint.getPoint() + result> 1000000){
            throw new IllegalArgumentException("충전시 백만포인트를 초과하면 안됩니다!");
        }

        return pointRepository.chargePoint(userId, point);
    }

}
