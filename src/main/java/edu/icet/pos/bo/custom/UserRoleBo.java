package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.UserRole;

public interface UserRoleBo extends SuperBo {
    void registerUserRole(UserRole userRole);

    UserRole getUserRole(Integer id);
}
