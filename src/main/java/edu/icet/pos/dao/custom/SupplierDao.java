package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.SupplierEntity;

import java.util.List;

public interface SupplierDao extends SuperDao {
    void save(SupplierEntity supplierEntity);

    SupplierEntity get(Integer id);

    void update(SupplierEntity supplierEntity);

    void delete(SupplierEntity supplierEntity);

    int count();

    List<SupplierEntity> getPerPage(int offset);

    List<SupplierEntity> getAll();
}
