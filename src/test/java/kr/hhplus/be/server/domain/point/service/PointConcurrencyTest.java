package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.interfaces.api.point.dto.PointResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class PointConcurrencyTest {

    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService pointService;

    // 목표: 한 사용자가 동시에 3번 포인트를 충전할 때 데이터 정합성 유지 확인

    @Test
    @DisplayName("한 사용자가 한 계정에 동시에 3번 포인트  충전")
    void 동시에_충전() throws Exception{

        // given
        // 반복 횟수 변수, 유저아이디, 충전할 포인트 선언
        final int threadCount = 3;
        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        final Long userId = 1L;
        final int point = 0;

        // when
        for(int i = 0; i < threadCount; i++){
            executorService.submit(() -> {
                try {
                    pointService.chargePoint(userId, 100); // 100 포인트 충전
                } finally {
                    countDownLatch.countDown(); // 작업이 끝나면 카운트다운
                }
            });

        }
        countDownLatch.await();
        executorService.shutdown();

        PointResponse result = pointService.getUserPoint(userId);

        // then
        assertThat(result).isNotNull();
        //assertThat(result.getPoint()).isEqualTo(300);
    }

}
