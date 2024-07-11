package edu.icet.pos.model.place_order;

import edu.icet.pos.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    private Integer id;
    private Integer orderId;
    private Product product;
    private Integer quantity;
    private Double total;
}
