package dao.impl;

import dao.ChoiceDAO;
import models.core.Choice;
import org.hibernate.Session;
import play.Logger;
import util.HibernateUtil;

import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Created by ilia on 24.09.14.
 */
public class ChoiceDAOImpl implements ChoiceDAO{
    private static final Logger.ALogger logger = Logger.of(ChoiceDAOImpl.class);

    public void addChoice(Choice choice) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(choice);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creation error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateChoice(Long id, Choice choice) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(choice);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Insertion error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Choice getChoiceById(Long id) throws SQLException {
        Session session = null;
        Choice choice = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            choice = (Choice) session.get(Choice.class, id);
        } catch (Exception e) {
            logger.error("'findById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return choice;
    }

    public Collection getAllChoices() throws SQLException {
        Session session = null;
        List<Choice> choices = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            choices = session.createCriteria(Choice.class).list();
        } catch (Exception e) {
            logger.error("'getAllChoices' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return choices;
    }

    public List<Choice> getChoicesByQuestionId(Long questionId) {
        Session session = null;
        List<Choice> choices = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            choices = session.createCriteria(Choice.class)
                             .add(Restrictions.eq("question.id", questionId)).list();
        } catch (Exception e) {
            logger.error("'getAllChoices' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return choices;
    }

    public void deleteChoice(Choice choice) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(choice);
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
