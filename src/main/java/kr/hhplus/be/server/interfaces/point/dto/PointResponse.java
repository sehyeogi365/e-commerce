package kr.hhplus.be.server.interfaces.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointResponse {
    private int userId;
    private int point;

}
