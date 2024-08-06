package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.product.Product;

import java.util.List;

public interface ProductBo extends SuperBo {
    Product productRegister(Product product);

    Product getProduct(Integer id);

    void productUpdate(Product product);

    void productDelete(Product product);

    int getProductCount(boolean filter);

    List<Product> getProductPerPage(boolean filter, int limit, int offset);

    List<Product> getProductPerPage(String ids, int limit, int offset);

    int getProductCountByFilter(String ids);

    void productAvaQtyUpdate(Product product);
}
