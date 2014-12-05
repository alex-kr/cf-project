package algorithm;

import controllers.Factory;
import models.core.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import play.Logger;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Question.class);
            List<Long> questionIds = criteria.setProjection(Projections.property("id")).list();
            Long level = 1L;
            List<Long> questionIdsByLevel = new ArrayList<>();
            for(Long id : questionIds)
                if(Factory.getInstance().getQuestionDAO().getQuestionById(id).level.equals(level))
                    questionIdsByLevel.add(id);

            int listIdx = random.nextInt(questionIdsByLevel.size());
            Long id = questionIdsByLevel.get(listIdx);
            logger.debug("Get random question with id = " + id);
            session.close();
            return Factory.getInstance().getQuestionDAO().getQuestionById(id);
        }
        catch (SQLException ex) {
            session.close();
            logger.error("Error occurred when getting question: " + ex.getMessage());
            return null;
        }
    }
}
