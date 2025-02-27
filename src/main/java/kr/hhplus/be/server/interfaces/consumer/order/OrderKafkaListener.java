package kr.hhplus.be.server.interfaces.consumer.order;

import com.fasterxml.jackson.databind.JsonNode;
import kr.hhplus.be.server.infra.kafka.order.OrderKafkaContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.rmi.server.LogStream.log;

@Slf4j
@Component
public class OrderKafkaListener {// @KafkaListner  바깥(카프카) 에서 메세지를 컨슈밍하고 처리하는 친구 OrderKafkaListner 라고 하던 OrderKafkaConsumer라 해도 됨
    // 인터페이스에 넣는것 취향차이?? order 패키지 분리하여 컨슈머 넣기 컨트롤러와 같은레벨이기에
    // 컨슘 때마다 아웃박스에서 해당 메세지 삭제
    @KafkaListener(topics = OrderKafkaContants.ORDER_COMPLETED, groupId = "order-group")
    public void consumeOrderMessage(JsonNode orderJson) {
        log("주문 ID: " + orderJson.get("orderId").asText());
        log("상품명: " + orderJson.get("productName").asText());
    }
}
