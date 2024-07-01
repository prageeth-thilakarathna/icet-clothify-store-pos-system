package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.product.Product;

public interface ProductBo extends SuperBo {
    Product productRegister(Product product);

    Product getProduct(Integer id);

    void productUpdate(Product product);

    void productDelete(Product product);
}
