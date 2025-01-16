package kr.hhplus.be.server.interfaces.point.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.Interceptor;
import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.service.PointService;
import kr.hhplus.be.server.interfaces.point.dto.PointRequest;
import kr.hhplus.be.server.interfaces.point.dto.PointResponse;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/point")
public class PointController {

    private final PointService pointService;

    //잔액 조회
    @GetMapping("/{userId}")
    @Tag(name = "포인트 잔액조회")
    @Operation(summary = "포인트  조회", description = "유저 ID로 포인트 정보를 조회합니다.")
    public ApiResponse<PointResponse> getBalance(@Parameter(description = "조회할 유저의 ID", required = true)
                                                 @PathVariable("userId") int userId) {
        PointResponse response = pointService.getUserPoint(userId);

        log.info("response " + response);
        return ApiResponse.ok(new PointResponse(userId, response.getPoint()));
    }

    //포인트 충전
    @PostMapping("/charge")
    @Tag(name = "포인트 충전")
    @Operation(summary = "포인트  충전", description = "유저 ID로 포인트를 충전합니다.")
    public ApiResponse<PointResponse> pointCharge(@RequestBody PointRequest pointRequest){

        Integer point = pointService.chargePoint(pointRequest.getUserId(), pointRequest.getPoint());
        PointResponse response = new PointResponse(pointRequest.getUserId(), point);

        log.info("response " +response);
        return ApiResponse.ok(new PointResponse(pointRequest.getUserId(), point));
    }

}
