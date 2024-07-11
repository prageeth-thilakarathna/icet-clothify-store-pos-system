package edu.icet.pos.controller.place_order.custom;

import edu.icet.pos.model.product.Product;
import javafx.fxml.Initializable;

import java.sql.SQLException;

public interface PlaceOrderCard extends Initializable {
    void setDetail(Product product) throws SQLException;

    Product getProduct();
}
