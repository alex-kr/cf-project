package dao;

import models.core.Choice;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by ilia on 24.09.14.
 */
public interface ChoiceDAO {
    public void addChoice(Choice choice) throws SQLException;
    public void updateChoice (Long id, Choice choice) throws SQLException;
    public Choice getChoiceById(Long id) throws SQLException;
    public Collection getAllChoices() throws SQLException;
    public void deleteChoice(Choice choice) throws SQLException;
    public List<Choice> getChoicesByQuestionId(Long questionId) throws SQLException;
}
