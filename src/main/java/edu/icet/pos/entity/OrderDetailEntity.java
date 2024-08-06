package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orderId", foreignKey = @ForeignKey(name = "fk_orders_order_detail"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderEntity orders;

    @ManyToOne
    @JoinColumn(name = "productId", foreignKey = @ForeignKey(name = "fk_product_order_detail"))
    private ProductEntity product;

    private Integer quantity;
    private Double price;
    private Double total;

}
