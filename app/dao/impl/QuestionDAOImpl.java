package dao.impl;

import dao.QuestionDAO;
import dao.QuestionDAO;
import models.core.Question;
import org.hibernate.Session;
import play.Logger;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Created by ilia on 24.09.14.
 */
public class QuestionDAOImpl implements QuestionDAO{
    private static final Logger.ALogger logger = Logger.of(QuestionDAOImpl.class);

    public void addQuestion(Question question) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(question);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creation error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateQuestion(Long id, Question question) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(question);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Insertion error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Question getQuestionById(Long id) throws SQLException {
        Session session = null;
        Question question = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            question = (Question) session.get(Question.class, id);
        } catch (Exception e) {
            logger.error("'findById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return question;
    }

    public List<Question> getAllQuestions() throws SQLException {
        Session session = null;
        List<Question> questions = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            questions = session.createCriteria(Question.class).list();
        } catch (Exception e) {
            logger.error("'getAllQuestions' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return questions;
    }

    public void deleteQuestion(Question question) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(question);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Deleting error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
