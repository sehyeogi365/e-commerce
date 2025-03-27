package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.interfaces.api.point.dto.PointResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService pointService;
    @Test
    @DisplayName("사용자의 포인트 조회")
    void 포인트_조회(){
        // given
        int userId = 1;
        int amount = 1000;

        Point point = Point.builder()
                            .userId(userId)
                            .point(amount)
                            .build();

        when(pointRepository.getByUserId(userId)).thenReturn(point);

        // when
        PointResponse result = pointService.getUserPoint(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(amount);
    }

    @Test
    @DisplayName("사용자의 포인트 충전")
    void 포인트_충전(){
        // given
        long userId = 1L;
        int amount = 10000;

        Point point = Point.builder()
                            .userId(userId)
                            .point(amount)
                            .build();
        // 겟유저포인트는 모킹안해서 아무것도 안들어옴

        when(pointRepository.chargePoint(userId, amount)).thenReturn(point.getPoint());
        when(pointRepository.getByUserId(userId)).thenReturn(point);

        // when
        Integer result = pointService.chargePoint(userId, amount);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(point.getPoint());
    }

    @Test
    @DisplayName("사용자의 포인트 충전 실패 케이스: 10000단위가 아닐시")
    void 포인트_충전2(){
        // given
        long userId = 1L;
        int amount = 10001;

        Point point = Point.builder()
                            .userId(userId)
                            .point(amount)
                            .build();

        when(pointRepository.chargePoint(userId, amount)).thenReturn(point.getPoint());
        when(pointRepository.getByUserId(userId)).thenReturn(point);

        // when
        Integer result = pointService.chargePoint(userId, amount);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(point.getPoint());
    }

    @Test
    @DisplayName("사용자의 포인트 충전 실패 케이스: 백만 포인트 이상 충전시")
    void 포인트_충전3(){
        // given
        long userId = 1L;
        int amount = 1000000;

        Point point = Point.builder()
                .userId(userId)
                .point(amount)
                .build();

        when(pointRepository.chargePoint(userId, amount)).thenReturn(point.getPoint());
        when(pointRepository.getByUserId(userId)).thenReturn(point);

        // when
        Integer result = pointService.chargePoint(userId, amount);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(point.getPoint());
    }

    @Test
    @DisplayName("사용자의 포인트 충전 실패 케이스: 0포인트이하 충전시")
    void 포인트_충전4(){
        // given
        long userId = 1L;
        int amount = 0;

        Point point = Point.builder()
                .userId(userId)
                .point(amount)
                .build();


        when(pointRepository.chargePoint(userId, amount)).thenReturn(point.getPoint());
        when(pointRepository.getByUserId(userId)).thenReturn(point);

        // when
        Integer result = pointService.chargePoint(userId, amount);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(point.getPoint());
    }

    @Test
    @DisplayName("사용자의 포인트 충전 실패 케이스: 충전시 누적 포인트 백만 포인트 초과하면 안됨")
    void 포인트_충전5(){
        // given
        long userId = 1L;
        int amount = 600000;// 충전할양

        Point point = Point.builder()
                .userId(userId)
                .point(500000)// 기존 포인트
                .build();

        when(pointRepository.chargePoint(userId, amount)).thenReturn(amount);
        when(pointRepository.getByUserId(userId)).thenReturn(point);

        // when
        Integer result = pointService.chargePoint(userId, amount);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(point.getPoint());
    }

}