package dao;

import models.core.AnswerRecord;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by ilia on 24.09.14.
 */
public interface AnswerRecordDAO {
    public void addAnswerRecord(AnswerRecord answerRecord) throws SQLException;
    public void updateAnswerRecord (Long id, AnswerRecord answerRecord) throws SQLException;
    public AnswerRecord getAnswerRecordById(Long id) throws SQLException;
    public List<AnswerRecord> getAnswerRecordsByAccountIdAndLevel(Long accountId, Long level) throws SQLException;
    public List<AnswerRecord> getDistinctCorrectAnswersByAccountIdAndLevel(Long accountId, Long level) throws SQLException;
    public Collection getAllAnswerRecords() throws SQLException;
    public void deleteAnswerRecord(AnswerRecord answerRecord) throws SQLException;
}
