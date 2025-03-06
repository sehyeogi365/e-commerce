package kr.hhplus.be.server.interfaces.api.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointRequest {
    private long userId;
    private int point;
}
