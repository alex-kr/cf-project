package algorithm;

import controllers.Factory;
import models.core.Question;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import play.Logger;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
* Created by Victor Dichko on 24.09.14.
*/

// This class includes methods to select question using collaborative filtering
public class QuestionSelector {
    private static final Logger.ALogger logger = Logger.of(QuestionSelector.class);

    public static Question getRandom() {
        Random random = new Random();
        try {
            Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(Question.class);
            List<Long> questionIds = criteria.setProjection(Projections.property("id")).list();
            int listIdx = random.nextInt(questionIds.size());
            Long id = questionIds.get(listIdx);
            logger.debug("Get random question with id = " + id);
            return Factory.getInstance().getQuestionDAO().getQuestionById(id);
        }
        catch (SQLException ex) {
            logger.error("Error occurred when getting question: " + ex.getMessage());
            return null;
        }
    }
}
