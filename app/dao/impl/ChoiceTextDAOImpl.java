package dao.impl;

import dao.ChoiceTextDAO;
import models.core.ChoiceText;
import org.hibernate.Session;
import play.Logger;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Victor Dichko on 23.09.14.
 */
public class ChoiceTextDAOImpl implements ChoiceTextDAO {

    private static final Logger.ALogger logger = Logger.of(ChoiceTextDAOImpl.class);

    public void addChoiceText(ChoiceText choiceText) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(choiceText);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creation error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateChoiceText(Long id, ChoiceText choiceText) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(choiceText);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Insertion error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public ChoiceText getChoiceTextById(Long id) throws SQLException {
        Session session = null;
        ChoiceText choiceText = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            choiceText = (ChoiceText) session.get(ChoiceText.class, id);
        } catch (Exception e) {
            logger.error("'findById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return choiceText;
    }

    public Collection getAllChoiceTexts() throws SQLException {
        Session session = null;
        List<ChoiceText> choiceTexts = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            choiceTexts = session.createCriteria(ChoiceText.class).list();
        } catch (Exception e) {
            logger.error("'getAllChoiceTexts' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return choiceTexts;
    }

    public void deleteChoiceText(ChoiceText choiceText) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(choiceText);
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
