package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleDao extends SuperDao {
    void save(UserRoleEntity userRoleEntity);

    UserRoleEntity get(Integer id);

    UserRoleEntity getByName(String name);

    List<UserRoleEntity> getAll();
}
