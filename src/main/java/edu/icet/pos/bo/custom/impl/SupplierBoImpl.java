package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.SupplierDao;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.model.supplier.Supplier;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class SupplierBoImpl implements SupplierBo {
    private final SupplierDao supplierDao = DaoFactory.getDao(DaoType.SUPPLIER);

    @Override
    public void supplierRegister(Supplier supplier) {
        assert supplierDao != null;
        supplierDao.save(new ModelMapper().map(supplier, SupplierEntity.class));
    }
}
