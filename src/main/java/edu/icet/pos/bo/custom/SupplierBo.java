package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.supplier.Supplier;

import java.util.List;

public interface SupplierBo extends SuperBo {
    void supplierRegister(Supplier supplier);

    Supplier getSupplier(Integer id);

    void supplierUpdate(Supplier supplier);

    void supplierDelete(Supplier supplier);

    int getSupplierCount();

    List<Supplier> getSupplierPerPage(int offset);

    List<Supplier> getAllSupplier();

    List<Supplier> getFirstSupplier();

    List<Supplier> getSupplierByYear(String year);
}
