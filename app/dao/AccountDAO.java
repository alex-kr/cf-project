package dao;

import models.core.Account;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Victor Dichko on 22.09.14.
 */
public interface AccountDAO {
    public void addAccount(Account account) throws SQLException;
    public void updateAccount(Long id, Account account) throws SQLException;
    public Account getAccountById(Long id) throws SQLException;
    public Account getAccountByFullname(String fullname) throws SQLException;
    public Long getAccountLevelById(Long id) throws SQLException;
    public Collection getAllAccounts() throws SQLException;
    public void deleteAccount(Account account) throws SQLException;
}
