package edu.miu.cs545.waa.domain;

import edu.miu.cs545.waa.domain.dto.Base;
import edu.miu.cs545.waa.enums.Payment;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "buyer_order")
public class Order extends Base {
    private Instant orderDate;
    private int quantity;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address shippingAddress;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address billingAddress;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Product product;

}
