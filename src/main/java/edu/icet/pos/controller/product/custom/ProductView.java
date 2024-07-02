package edu.icet.pos.controller.product.custom;

import javafx.fxml.Initializable;

public interface ProductView extends Initializable {
    void loadCard();

    void updatePanel(String name);
}
