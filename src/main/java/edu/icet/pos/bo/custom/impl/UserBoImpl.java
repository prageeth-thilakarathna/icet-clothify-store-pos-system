package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.user.User;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class UserBoImpl implements UserBo {
    private final UserDao userDao = DaoFactory.getDao(DaoType.USER);

    @Override
    public void userRegister(User user) {
        assert userDao != null;
        userDao.save(new ModelMapper().map(user, UserEntity.class));
    }

    @Override
    public User getUserByEmail(String email) {
        assert userDao != null;
        return new ModelMapper().map(userDao.getByEmail(email), User.class);
    }

    @Override
    public User getUser(Integer id) {
        assert userDao != null;
        return new ModelMapper().map(userDao.get(id), User.class);
    }

    @Override
    public void userUpdate(User user) {
        assert userDao != null;
        userDao.update(new ModelMapper().map(user, UserEntity.class));
    }

    @Override
    public void userDelete(User user) {
        assert userDao != null;
        userDao.delete(new ModelMapper().map(user, UserEntity.class));
    }

    @Override
    public List<User> getAllUser() {
        assert userDao != null;
        return new ModelMapper().map(userDao.getAll(), new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public int getUserCount() {
        assert userDao != null;
        return userDao.count();
    }

    @Override
    public List<User> getUserPerPage(int offset) {
        assert userDao != null;
        return new ModelMapper().map(userDao.getPerPage(offset), new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public List<User> getUsersNotExistInEmployee() {
        assert userDao != null;
        return new ModelMapper().map(userDao.getNotIsExist(), new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public void employeeRegister(Employee employee) {
        assert userDao != null;
        userDao.employeeSave(new ModelMapper().map(employee, EmployeeEntity.class));
    }
}
