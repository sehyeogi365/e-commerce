package kr.hhplus.be.server.interfaces.point.dto;

public record UserPoint(
        long id,
        int point,
        long updateMillis
) {
    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }
}
