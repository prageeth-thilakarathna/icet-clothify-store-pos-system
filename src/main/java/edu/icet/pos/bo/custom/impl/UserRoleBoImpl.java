package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.UserRoleDao;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class UserRoleBoImpl implements UserRoleBo {
    private final UserRoleDao userRoleDao = DaoFactory.getDao(DaoType.USER_ROLE);

    @Override
    public void userRoleRegister(UserRole userRole) {
        assert userRoleDao != null;
        userRoleDao.save(new ModelMapper().map(userRole, UserRoleEntity.class));
    }

    @Override
    public UserRole getUserRole(Integer id) {
        assert userRoleDao != null;
        return new ModelMapper().map(userRoleDao.get(id), UserRole.class);
    }

    @Override
    public UserRole getUserRoleByName(String name) {
        assert userRoleDao != null;
        return new ModelMapper().map(userRoleDao.getByName(name), UserRole.class);
    }
}
