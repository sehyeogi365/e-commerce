package kr.hhplus.be.server.domain.order.usecase;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //주문하기
    @Transactional
    public Order orderProduct(Order order){

        return orderRepository.save(order);
    }

    //주문 목록 조회
    public Optional<Order> getOrderList(int userId){

        return orderRepository.getOrderList(userId);
    }

}
