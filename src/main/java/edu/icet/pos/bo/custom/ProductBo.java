package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.product.Product;

public interface ProductBo extends SuperBo {
    void productRegister(Product product);
}
