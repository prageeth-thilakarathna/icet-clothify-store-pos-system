package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.User;

public interface UserBo extends SuperBo {
    void userRegister(User user);

    User getUserByEmail(String email);

    User getUser(Integer id);
}
