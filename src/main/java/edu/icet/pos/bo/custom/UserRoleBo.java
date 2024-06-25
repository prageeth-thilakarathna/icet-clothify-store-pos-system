package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.user.UserRole;

import java.util.List;

public interface UserRoleBo extends SuperBo {
    void userRoleRegister(UserRole userRole);

    UserRole getUserRole(Integer id);

    UserRole getUserRoleByName(String name);

    List<UserRole> getAllUserRole();
}
