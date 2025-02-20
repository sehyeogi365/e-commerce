package kr.hhplus.be.server.domain.order.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import kr.hhplus.be.server.domain.order.enums.OutboxStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_outbox")
public class OrderOutbox {// Outbox 엔티티
    @Id // PK
    private String message_id; // Message_ID 면 좋음.
    // 그 메세지를 식별할 수 있는 식별자

    private String kafka_message_id; // 실제 카프카에 태울 메세지 아이디

    // @Column(nullable = false)
    // private EventType eventType; // Optional ) 한 테이블로 관리할 때만..

    @Column(nullable = false)
    private OutboxStatus status;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public OrderOutbox(long orderId, String message) {
        this.message_id = "msg-" + orderId; // orderId를 사용하여 메시지 ID 설정
        this.status = OutboxStatus.PENDING; // 초기 상태를 PENDING으로 설정
        this.payload = message; // 전달받은 메시지 페이로드 설정
        this.createdAt = LocalDateTime.now(); // 생성 시각 설정
    }
}
