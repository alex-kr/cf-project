package controllers;

import dao.*;
import dao.impl.*;

/**
 * Created by Victor Dichko on 22.09.14.
 */
public class Factory {

    private static UserDAO userDAO = null;
    private static ChoiceTextDAO choiceTextDAO = null;
    private static QuestionDAO quesionDAO = null;
    private static ChoiceDAO choiceDAO = null;
    private static AnswerRecordDAO answerRecordDAO = null;
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

    public QuestionDAO getQuesionDAO() {
        if (quesionDAO == null) {
            quesionDAO = new QuestionDAOImpl();
        }
        return quesionDAO;
    }

    public ChoiceDAO getChoiceDAO() {
        if (choiceDAO == null) {
            choiceDAO = new ChoiceDAOImpl();
        }
        return choiceDAO;
    }

    public AnswerRecordDAO getAnswerRecordDAO() {
        if (answerRecordDAO == null) {
            answerRecordDAO = new AnswerRecordDAOImpl();
        }
        return answerRecordDAO;
    }
}
