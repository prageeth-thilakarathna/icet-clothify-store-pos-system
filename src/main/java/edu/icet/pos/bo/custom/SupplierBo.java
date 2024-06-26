package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.supplier.Supplier;

public interface SupplierBo extends SuperBo {
    void supplierRegister(Supplier supplier);
}
