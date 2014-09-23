package controllers;

import dao.ChoiceTextDAO;
import dao.UserDAO;
import dao.impl.ChoiceTextDAOImpl;
import dao.impl.UserDAOImpl;

/**
 * Created by Victor Dichko on 22.09.14.
 */
public class Factory {

    private static UserDAO userDAO = null;
    private static ChoiceTextDAO choiceTextDAO = null;
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

    public ChoiceTextDAO getChoiceTextDAO() {
        if (choiceTextDAO == null) {
            choiceTextDAO = new ChoiceTextDAOImpl();
        }
        return choiceTextDAO;
    }
}
