package edu.icet.pos.model.order;

import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private Integer id;
    private OrderEntity orders;
    private ProductEntity product;
    private Integer quantity;
    private Double price;
    private Double total;
}
