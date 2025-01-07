package kr.hhplus.be.server.interfaces.point.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import kr.hhplus.be.server.interfaces.point.dto.PointRequest;
import kr.hhplus.be.server.interfaces.point.dto.PointResponse;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/point")
public class PointController {


    //포인트 충전
    @PostMapping("/charge")
    @Tag(name = "포인트 충전")
    @Operation(summary = "포인트  충전", description = "유저 ID로 포인트를 충전합니다.")
    public ApiResponse<PointResponse> pointCharge(@RequestParam PointRequest pointRequest){

        PointResponse response = new PointResponse(pointRequest.getUserId(), pointRequest.getPoint() );

        return ApiResponse.ok(response);
    }

    //잔액 조회
    @GetMapping("/{userId}")
    @Tag(name = "포인트 잔액조회")
    @Operation(summary = "포인트  조회", description = "유저 ID로 포인트 정보를 조회합니다.")
    public ApiResponse<PointResponse> getBalance(@PathVariable int userId) {

        int currentBalance = 10000;//실제로는 db에서 불러올 것
        PointResponse response = new PointResponse(userId, currentBalance);

        return ApiResponse.ok(response);
    }

}
