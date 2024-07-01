package edu.icet.pos.controller.product.custom;

import edu.icet.pos.model.product.Product;
import javafx.fxml.Initializable;

public interface ProductForm extends Initializable {
    void loadProductToForm(Product product);

    void clearProduct();
}
