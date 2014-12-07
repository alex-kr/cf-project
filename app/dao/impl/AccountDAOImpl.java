package dao.impl;

import dao.AccountDAO;
import models.core.Account;
import models.core.AnswerRecord;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import play.Logger;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Victor Dichko on 22.09.14.
 */
public class AccountDAOImpl implements AccountDAO {

    private static final Logger.ALogger logger = Logger.of(AccountDAOImpl.class);

    public void addAccount(Account account) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(account);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creation error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateAccount(Long id, Account account) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(account);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Insertion error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Account getAccountById(Long id) throws SQLException {
        Session session = null;
        Account account = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            account = (Account) session.get(Account.class, id);
        } catch (Exception e) {
            logger.error("'findById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return account;
    }

    public Account getAccountByFullname(String fullname) throws SQLException {
        Session session = null;
        Account account = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            //
            Criteria cr = session.createCriteria(Account.class);
            cr.add(Restrictions.eq("fullname", fullname));
            if (cr.list().isEmpty()) {
                return null;
                //throw new SQLException("Wrong number of user with fullname: " + fullname);
            }
            account = (Account) cr.list().get(0);
            //
        } catch (Exception e) {
            logger.error("'findByFullname' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return account;
    }

    public Long getAccountLevelById(Long id) throws SQLException {
        Session session = null;
        Long accountLevel = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria cr = session.createCriteria(AnswerRecord.class);
            cr.add(Restrictions.eq("account_id", id))
              .addOrder(Order.desc("id"));
            if (cr.list().isEmpty()) {
                return null;
                //throw new SQLException("Wrong number of user with fullname: " + fullname);
            }
            accountLevel = (Long) cr.list().get(0);
        } catch (Exception e) {
            logger.error("'getAccountLevelById' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accountLevel;
    }

    public Collection getAllAccounts() throws SQLException {
        Session session = null;
        List<Account> accounts = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            accounts = session.createCriteria(Account.class).list();
        } catch (Exception e) {
            logger.error("'getAllAccounts' error. " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accounts;
    }

    public void deleteAccount(Account account) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(account);
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
