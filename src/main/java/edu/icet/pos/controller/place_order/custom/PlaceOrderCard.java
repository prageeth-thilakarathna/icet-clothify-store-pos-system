package edu.icet.pos.controller.place_order.custom;

import edu.icet.pos.model.product.Product;

import java.sql.SQLException;

public interface PlaceOrderCard {
    void setDetail(Product product) throws SQLException;

    Product getProduct();

    void setBtnAddToCartDisable();

    void setBtnAddToCartActive();
}
