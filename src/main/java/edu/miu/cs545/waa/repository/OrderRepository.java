package edu.miu.cs545.waa.repository;

import edu.miu.cs545.waa.domain.Order;
import edu.miu.cs545.waa.enums.Payment;
import edu.miu.cs545.waa.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByIdAndUserId(Long id, String userId);

    List<Order> findAllByUserId(String userId);

    List<Order> findAllByUserIdAndStatus(String userId, Status status);

    List<Order> findAllByIdInAndUserIdAndPayment(List<Long> ids, String userId, Payment payment);
}
