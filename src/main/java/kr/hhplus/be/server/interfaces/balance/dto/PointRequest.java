package kr.hhplus.be.server.interfaces.balance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointRequest  {
    private int userId;
    private int point;
}
