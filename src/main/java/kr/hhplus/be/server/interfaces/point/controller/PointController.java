package kr.hhplus.be.server.interfaces.point.controller;


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
    public ApiResponse<PointResponse> pointCharge(@RequestParam PointRequest pointRequest){

        PointResponse response = new PointResponse(pointRequest.getUserId(), pointRequest.getPoint() );

        return ApiResponse.ok(response);
    }

    //잔액 조회
    @GetMapping("/{userId}")
    public ApiResponse<PointResponse> getBalance(@PathVariable int userId) {

        int currentBalance = 10000;//실제로는 db에서 불러올 것
        PointResponse response = new PointResponse(userId, currentBalance);

        return ApiResponse.ok(response);
    }

}
