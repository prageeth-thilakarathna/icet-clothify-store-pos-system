package edu.icet.pos.controller.inventory.custom;

import edu.icet.pos.model.inventory.Inventory;
import javafx.fxml.Initializable;

public interface InventoryForm extends Initializable {
    void clearInventory();

    void loadInventoryToForm(Inventory inventory);

    void refreshForm();
}
