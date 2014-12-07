package models.core;

import controllers.Factory;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    public String fullname;

    public boolean isAdmin;

    public Long getAccountLevel() {
        Long accountLevel = null;
        try {
            accountLevel = Factory.getInstance().getUserDAO().getAccountLevelById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountLevel;
    }

    /**
     * Returns percentage of correctly answered questions from user (aka account) level,
     * where user level - level of last answered question.
     */
    public Double getLevelProgress() {
        Long accountLevel = this.getAccountLevel();
        List<Question> levelQuestions = null;
        List<AnswerRecord> levelQuestionsAnsweredCorrectly = null;
        try {
            levelQuestions = Factory.getInstance().getQuestionDAO().getQuestionsByLevel(accountLevel);
            levelQuestionsAnsweredCorrectly = Factory.getInstance().getAnswerRecordDAO().getDistinctCorrectAnswersByAccountIdAndLevel(id, accountLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (levelQuestions != null && !levelQuestions.isEmpty()
                && levelQuestionsAnsweredCorrectly != null && !levelQuestionsAnsweredCorrectly.isEmpty()) {
            return ((double) levelQuestionsAnsweredCorrectly.size()) / levelQuestions.size();
        }
        return 0D;
    }
}
