package dao;

import models.core.Rule;

import java.sql.SQLException;
import java.util.List;



public interface RuleDAO {
    public void addRule(Rule rule) throws SQLException;
    public void updateRule (Long id, Rule rule) throws SQLException;
    public Rule getRuleById(Long id) throws SQLException;
    public List<Rule> getAllRules() throws SQLException;
    public void deleteRule(Rule rule) throws SQLException;
}