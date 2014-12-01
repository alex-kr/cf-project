package dao.impl;

import dao.TopicDAO;
import models.core.Topic;
import org.hibernate.Session;
import play.Logger;
import util.HibernateUtil;

import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class TopicDAOImpl implements TopicDAO {
    private static final Logger.ALogger logger = Logger.of(TopicDAOImpl.class);

    public void addTopic(Topic topic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(topic);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creation error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateTopic(Long id, Topic topic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(topic);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Insertion error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Topic getTopicById(Long id) throws SQLException {
        Session session = null;
        Topic topic = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            topic = (Topic) session.get(Topic.class, id);
        } catch (Exception e) {
            logger.error("'findById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return topic;
    }

    public Collection getAllTopics() throws SQLException {
        Session session = null;
        List<Topic> topics = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            topics = session.createCriteria(Topic.class).list();
        } catch (Exception e) {
            logger.error("'getAllTopics' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return topics;
    }


    public void deleteTopic(Topic topic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(topic);
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
