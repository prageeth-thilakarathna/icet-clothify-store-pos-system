package edu.icet.pos.controller.supplier.custom;

import edu.icet.pos.controller.custom.SuperController;
import edu.icet.pos.model.supplier.Supplier;

public interface SupplierForm extends SuperController {
    void loadSupplierToForm(Supplier supplier);

    void clearSupplier();

    void refreshForm();
}
