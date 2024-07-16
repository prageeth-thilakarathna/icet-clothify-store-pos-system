package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.SupplierDao;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.model.supplier.Supplier;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class SupplierBoImpl implements SupplierBo {
    private final SupplierDao supplierDao = DaoFactory.getDao(DaoType.SUPPLIER);

    @Override
    public void supplierRegister(Supplier supplier) {
        assert supplierDao != null;
        supplierDao.save(new ModelMapper().map(supplier, SupplierEntity.class));
    }

    @Override
    public Supplier getSupplier(Integer id) {
        assert supplierDao != null;
        return new ModelMapper().map(supplierDao.get(id), Supplier.class);
    }

    @Override
    public void supplierUpdate(Supplier supplier) {
        assert supplierDao != null;
        supplierDao.update(new ModelMapper().map(supplier, SupplierEntity.class));
    }

    @Override
    public void supplierDelete(Supplier supplier) {
        assert supplierDao != null;
        supplierDao.delete(new ModelMapper().map(supplier, SupplierEntity.class));
    }

    @Override
    public int getSupplierCount() {
        assert supplierDao != null;
        return supplierDao.count();
    }

    @Override
    public List<Supplier> getSupplierPerPage(int offset) {
        assert supplierDao != null;
        return new ModelMapper().map(supplierDao.getPerPage(offset), new TypeToken<List<Supplier>>() {
        }.getType());
    }

    @Override
    public List<Supplier> getAllSupplier() {
        assert supplierDao != null;
        return new ModelMapper().map(supplierDao.getAll(), new TypeToken<List<Supplier>>() {
        }.getType());
    }
}
