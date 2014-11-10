package dao;

import models.core.ChoiceText;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Victor Dichko on 23.09.14.
 */
public interface ChoiceTextDAO {
    public void addChoiceText(ChoiceText choiceText) throws SQLException;
    public void updateChoiceText (Long id, ChoiceText choiceText) throws SQLException;
    public ChoiceText getChoiceTextById(Long id) throws SQLException;
    public Collection getAllChoiceTexts() throws SQLException;
    public void deleteChoiceText(ChoiceText choiceText) throws SQLException;
}
