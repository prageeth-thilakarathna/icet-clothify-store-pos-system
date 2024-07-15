package edu.icet.pos.dao;

import edu.icet.pos.dao.custom.impl.*;
import edu.icet.pos.util.DaoType;

public class DaoFactory {
    private DaoFactory(){}

    public static <T extends SuperDao> T getDao(DaoType daoType){
        switch (daoType){
            case USER_ROLE: return (T) new UserRoleDaoImpl();
            case USER: return (T) new UserDaoImpl();
            case CATEGORY: return (T) new CategoryDaoImpl();
            case SUB_CATEGORY: return (T) new SubCategoryDaoImpl();
            case SUPPLIER: return (T) new SupplierDaoImpl();
            case PRODUCT: return (T) new ProductDaoImpl();
            case INVENTORY: return (T) new InventoryDaoImpl();
            case JOB_ROLE: return (T) new JobRoleDaoImpl();
        }
        return null;
    }
}
