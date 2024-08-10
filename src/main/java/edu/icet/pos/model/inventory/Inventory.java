package edu.icet.pos.model.inventory;

import edu.icet.pos.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Integer id;
    private ProductEntity product;
    private Integer stock;
    private Date registerAt;
}
