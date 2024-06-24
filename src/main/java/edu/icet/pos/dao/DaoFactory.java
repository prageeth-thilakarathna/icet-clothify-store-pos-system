package edu.icet.pos.dao;

import edu.icet.pos.dao.custom.impl.CategoryDaoImpl;
import edu.icet.pos.dao.custom.impl.UserDaoImpl;
import edu.icet.pos.dao.custom.impl.UserRoleDaoImpl;
import edu.icet.pos.util.DaoType;

public class DaoFactory {
    private DaoFactory(){}

    public static <T extends SuperDao> T getDao(DaoType daoType){
        switch (daoType){
            case USER_ROLE: return (T) new UserRoleDaoImpl();
            case USER: return (T) new UserDaoImpl();
            case CATEGORY: return (T) new CategoryDaoImpl();
        }
        return null;
    }
}
