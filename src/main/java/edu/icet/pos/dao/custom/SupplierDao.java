package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.SupplierEntity;

public interface SupplierDao extends SuperDao {
    void save(SupplierEntity supplierEntity);
}
