package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.UserEntity;

public interface UserDao extends SuperDao {
    void save(UserEntity userEntity);

    UserEntity getByEmail(String email);

    UserEntity get(Integer id);

    void update(UserEntity userEntity);

    void delete(UserEntity userEntity);
}
