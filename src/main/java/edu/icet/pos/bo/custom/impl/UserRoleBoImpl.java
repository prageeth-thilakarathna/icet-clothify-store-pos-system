package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.dao.custom.UserRoleDao;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class UserRoleBoImpl implements UserRoleBo {
    private UserRoleDao userRoleDao = DaoFactory.getDao(DaoType.USER_ROLE);

    @Override
    public void registerUserRole(UserRole userRole) {
        userRoleDao.save(new ModelMapper().map(userRole, UserRoleEntity.class));
    }

    @Override
    public UserRole getUserRole(Integer id) {
        return new ModelMapper().map(userRoleDao.get(id), UserRole.class);
    }
}
