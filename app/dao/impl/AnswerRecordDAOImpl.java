package dao.impl;

import dao.AnswerRecordDAO;
import models.core.AnswerRecord;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import play.Logger;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Created by ilia on 24.09.14.
 */
public class AnswerRecordDAOImpl implements AnswerRecordDAO{
    private static final Logger.ALogger logger = Logger.of(AnswerRecordDAOImpl.class);

    public void addAnswerRecord(AnswerRecord answerRecord) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(answerRecord);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creation error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateAnswerRecord(Long id, AnswerRecord answerRecord) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(answerRecord);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Insertion error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public AnswerRecord getAnswerRecordById(Long id) throws SQLException {
        Session session = null;
        AnswerRecord answerRecord = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            answerRecord = (AnswerRecord) session.get(AnswerRecord.class, id);
        } catch (Exception e) {
            logger.error("'findById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return answerRecord;
    }

    @Override
    public List<AnswerRecord> getAnswerRecordsByAccountIdAndLevel(Long accountId, Long level) throws SQLException {
        Session session = null;
        List<AnswerRecord> answerRecords = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            answerRecords = session.createCriteria(AnswerRecord.class)
                    .add(Restrictions.eq("account_id", accountId))
                    .add(Restrictions.eq("question.level", level))
                    .list();
        } catch (Exception e) {
            logger.error("'getAnswerRecordsByAccountIdAndLevel' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return answerRecords;
    }

    @Override
    public List<AnswerRecord> getDistinctCorrectAnswersByAccountIdAndLevel(Long accountId, Long level) throws SQLException {
        Session session = null;
        List<AnswerRecord> answerRecords = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            answerRecords = session.createCriteria(AnswerRecord.class)
                    .add(Restrictions.eq("account_id", accountId))
                    .add(Restrictions.eq("question.level", level))
                    .add(Restrictions.eq("correct", true))
                    .setProjection(Projections.distinct(Projections.property("question_id")))
                    .list();
        } catch (Exception e) {
            logger.error("'getAnswerRecordsByAccountIdAndLevel' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return answerRecords;
    }

    public Collection getAllAnswerRecords() throws SQLException {
        Session session = null;
        List<AnswerRecord> answerRecords = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            answerRecords = session.createCriteria(AnswerRecord.class).list();
        } catch (Exception e) {
            logger.error("'getAllAnswerRecords' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return answerRecords;
    }

    public void deleteAnswerRecord(AnswerRecord answerRecord) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(answerRecord);
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
