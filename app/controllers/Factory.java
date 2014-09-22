package controllers;

import dao.impl.UserDAOImpl;
import dao.UserDAO;

/**
 * Created by Victor Dichko on 22.09.14.
 */
public class Factory {

    private static UserDAO userDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }
}
