package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.product.Product;

import java.util.List;

public interface ProductBo extends SuperBo {
    Product productRegister(Product product);

    Product getProduct(Integer id);

    void productUpdate(Product product);

    void productDelete(Product product);

    int getProductCount();

    List<Product> getProductPerPage(int offset);
}
