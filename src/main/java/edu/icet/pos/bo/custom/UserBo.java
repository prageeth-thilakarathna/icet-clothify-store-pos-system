package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.User;

import java.util.List;

public interface UserBo extends SuperBo {
    void userRegister(User user);

    User getUserByEmail(String email);

    User getUser(Integer id);

    void userUpdate(User user);

    void userDelete(User user);

    List<User> getAllUser();

    int getUserCount();

    List<User> getUserPerPage(int offset);
}
