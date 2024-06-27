package edu.icet.pos.controller.supplier.custom;

import edu.icet.pos.model.supplier.Supplier;
import javafx.fxml.Initializable;

public interface SupplierForm extends Initializable {
    void loadSupplierToForm(Supplier supplier);

    void clearSupplier();
}
