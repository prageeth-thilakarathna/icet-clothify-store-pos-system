package edu.icet.pos.controller.product.custom;

import edu.icet.pos.model.product.Product;
import javafx.fxml.Initializable;

import java.sql.SQLException;

public interface ProductCard extends Initializable {
    void setDetail(Product product) throws SQLException;
}
