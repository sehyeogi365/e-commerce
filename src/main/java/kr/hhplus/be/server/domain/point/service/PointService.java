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
    public PointResponse getUserPoint(long userId){
        Point point = pointRepository.getUserPoint(userId);
        return new PointResponse(userId, point.getPoint());
    }

    //잔액 충전
    @Transactional//보통이렇게 트랜잭션 붙여줌 예외 사항시 롤백
    public Integer chargePoint(long userId, int point){

        //0포인트 충전시, 10000단위가 아닐시, 백만포인트 이상 보유시, 충전이후 백만포인트 초과시 등등,
        //이미 업데이트 했기에 -1이 들어감 이경우 트랜잭션으로 롤백처리 해야 한다함


//        1. chargePoint 메서드에서 Repository를 대상으로 포인트 충전 시도를 두 번 시도하고 있습니다. (메서드의 첫 부분과 반환 부분이요. 이 경우에는 트랜잭션의 ACID 자체가 무너집니다.)
//        이 부분 수정이 필요합니다.

//        2. 요청에 대한 검증은 항상 도입부에서 체크될 수 있어야 합니다. (반환값에 대한 검증은 반환값 이후)

//        3. 이미 chargePoint의 결과를 반환하고 있으므로 getUserPoint로 재조회하는 일은 없어야 합니다.
        //이런식으로 포인트값에대한 예외처리 먼저 설정

        if(point < 0) {
            throw new CustomException(ErrorCode.POINT_NOT_FOUND);
        }// <0 으로도 충분하다고 피드백

        if(point % 10000 != 0){
            throw new IllegalArgumentException("10000포인트 단위어야 합니다!");
        }

        if(point >= 1000000){
            throw new IllegalArgumentException("백만포인트 이상 충전하면 안됩니다!");
        }

        Point userPoint = pointRepository.getUserPoint(userId);

        if(userPoint == null) {
            throw new CustomException(ErrorCode.POINT_NOT_FOUND);
        }

        if(userPoint.getPoint() + point> 1000000){
            throw new IllegalArgumentException("충전시 백만포인트를 초과하면 안됩니다!");
        }

        return pointRepository.chargePoint(userId, point);
    }

}
