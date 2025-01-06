package kr.hhplus.be.server.interfaces.balance.controller;


import kr.hhplus.be.server.domain.balance.service.BalanceService;
import kr.hhplus.be.server.interfaces.balance.dto.PointResponse;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/point")
public class BalanceController {

    BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    //포인트 충전
    @PostMapping("/charge")
    public ApiResponse<PointResponse> pointCharge(@PathVariable(name = "userId") int userId){


    }

    //잔액 조회
    @GetMapping("/{userId}")
    public ResponseEntity<Integer> getBalance(@PathVariable Long userId) {
        int balance = balanceService.pointSelect();
        return ResponseEntity.ok(balance);
    }


    //

}
