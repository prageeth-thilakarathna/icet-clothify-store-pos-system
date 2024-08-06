package edu.icet.pos.model.place_order;

import edu.icet.pos.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartDetail {
    private Product product;
    private Integer quantity;
    private Double price;
    private Double total;
}
