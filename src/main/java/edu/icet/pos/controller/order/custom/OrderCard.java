package edu.icet.pos.controller.order.custom;

import edu.icet.pos.model.product.Product;
import javafx.fxml.Initializable;

import java.sql.SQLException;

public interface OrderCard extends Initializable {
    void setDetail(Product product) throws SQLException;
}
