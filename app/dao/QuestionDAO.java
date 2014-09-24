package dao;

import models.core.Question;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by ilia on 24.09.14.
 */
public interface QuestionDAO {
    public void addQuestion(Question question) throws SQLException;
    public void updateQuestion (Long id, Question question) throws SQLException;
    public Question getQuestionById(Long id) throws SQLException;
    public Collection getAllQuestions() throws SQLException;
    public void deleteQuestion(Question question) throws SQLException;
}
