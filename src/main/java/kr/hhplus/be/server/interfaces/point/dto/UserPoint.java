package kr.hhplus.be.server.interfaces.point.dto;

public record UserPoint(
        int id,
        int point,
        long updateMillis
) {
    public static UserPoint empty(int id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }
}
