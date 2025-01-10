package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {


    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService pointService;
    @Test
    @DisplayName("사용자의 포인트 조회")
    void 포인트_조회(){
        //Given
        int userId = 1;
        int amount = 1000;

        Point point = Point.builder()
                .userId(userId)
                .point(amount)
                .build();

        //When
        when(pointRepository.getUserPoint(userId)).thenReturn(point);
        Point result = pointService.getUserPoint(userId);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(amount);

    }

    @Test
    @DisplayName("사용자의 포인트 충전")
    void 포인트_충전(){
        //Given
        int userId = 1;
        int amount = 10000;

        Point point = Point.builder()
                .userId(userId)
                .point(amount)
                .build();

        //When
        when(pointRepository.chargePoint(1, 10000)).thenReturn(point.getPoint());

        Point result = pointService.getUserPoint(userId);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getPoint()).isEqualTo(point.getPoint());
    }

    @Test
    @DisplayName("사용자의 포인트 충전 실패 케이스: 10000단위가 아닐시")
    void 포인트_충전2(){
        //Given
        int userId = 1;
        int amount = 10001;

        Point point = Point.builder()
                            .userId(userId)
                            .point(amount)
                            .build();
        //When
        when(pointRepository.chargePoint(userId, amount)).thenReturn(point.getPoint());

        Integer result = pointService.chargePoint(userId, amount);

        //Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(point.getPoint());
    }

    @Test
    @DisplayName("사용자의 포인트 충전 실패 케이스: 백만 포인트 이상 충전시")
    void 포인트_충전3(){
        int userId = 1;
        int amount = 10000001;

        Point point = Point.builder()
                .userId(userId)
                .point(amount)
                .build();

        //When
        when(pointRepository.chargePoint(userId, amount)).thenReturn(point.getPoint());

        Integer result = pointService.chargePoint(userId, amount);

        //Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(point.getPoint());
    }

}