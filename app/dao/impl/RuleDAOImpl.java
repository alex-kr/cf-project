package dao.impl;

import dao.RuleDAO;
import models.core.Rule;
import org.hibernate.Session;
import play.Logger;
import util.HibernateUtil;

import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class RuleDAOImpl implements RuleDAO{
    private static final Logger.ALogger logger = Logger.of(RuleDAOImpl.class);

    public void addRule(Rule rule) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(rule);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creation error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateRule(Long id, Rule rule) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(rule);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Insertion error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Rule getRuleById(Long id) throws SQLException {
        Session session = null;
        Rule rule = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            rule = (Rule) session.get(Rule.class, id);
        } catch (Exception e) {
            logger.error("'findById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return rule;
    }

    public Collection getAllRules() throws SQLException {
        Session session = null;
        List<Rule> rules = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            rules = session.createCriteria(Rule.class).list();
        } catch (Exception e) {
            logger.error("'getAllRules' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return rules;
    }


    public void deleteRule(Rule rule) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(rule);
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
