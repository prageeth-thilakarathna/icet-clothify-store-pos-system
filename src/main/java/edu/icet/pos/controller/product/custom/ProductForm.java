package edu.icet.pos.controller.product.custom;

import edu.icet.pos.controller.custom.SuperController;
import edu.icet.pos.model.product.Product;

public interface ProductForm extends SuperController {
    void loadProductToForm(Product product);

    void clearProduct();

    void refreshForm();
}
