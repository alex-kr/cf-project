package dao;

import models.core.User;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Victor Dichko on 22.09.14.
 */
public interface UserDAO {
    public void addUser(User user) throws SQLException;
    public void updateUser (Long id, User user) throws SQLException;
    public User getUserById(Long id) throws SQLException;
    public User getUserByFullname(String fullname) throws SQLException;
    public Collection getAllUsers() throws SQLException;
    public void deleteUser(User user) throws SQLException;
}
