package algorithm;

import controllers.Factory;
import models.core.AnswerRecord;
import models.core.Question;
import models.core.User;
import play.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by ilia on 31.10.14.
 */
public class CollaborativeFiltering {
    private static final Logger.ALogger logger = Logger.of(CollaborativeFiltering.class);

    public static Question getNextQuestion(User user){
        List<AnswerRecord> answerRecords;
        try{
            answerRecords = (ArrayList) Factory.getInstance().getAnswerRecordDAO().getAllAnswerRecords();
        }catch (SQLException ex) {
            logger.error("Error occurred when getting all answerRecords: " + ex.getMessage());
            return null;
        }
        //userId->(questionId->rating))
        Map<Long,Map<Long,Double>> userChoices = getChoicesRating(answerRecords);

        //cos measure
        Map<Long,Double> measure = new HashMap<>();//Collections.emptyMap();
        double measureSum = 0.0;

        double userNorm = 0.0;
        for(Long questionId : userChoices.get(user.id).keySet())
            userNorm += userChoices.get(user.id).get(questionId) * userChoices.get(user.id).get(questionId);
        userNorm = Math.sqrt(userNorm);

        for(Long userId : userChoices.keySet()){
            if(!userId.equals(user.id)){
                double temp = 0.0;  //scalar mult
                double norm = 0.0;
                for(Long questionId : userChoices.get(userId).keySet()){
                    temp += userChoices.get(userId).get(questionId) * userChoices.get(user.id).get(questionId);
                    norm += userChoices.get(userId).get(questionId) * userChoices.get(userId).get(questionId);
                }
                norm = Math.sqrt(norm);
                if(norm != 0.0){
                    measure.put(userId,temp / (norm * userNorm));
                    measureSum += temp / (norm * userNorm);
                }
                else measure.put(userId,0.0);
            }
        }

        if(measureSum == 0.0){
            logger.error("MeasureSum is ZERO");
            return null;
        }

        //TODO get N users with max measure and work only with them

        for(Long userId : userChoices.keySet())
            if(!userId.equals(user.id))
                for(Long questionId : userChoices.get(userId).keySet())
                    userChoices.get(userId).put(questionId, userChoices.get(userId).get(questionId) * measure.get(userId));

        //questionId -> global measure
        Map<Long,Double> result = new HashMap<>();//Collections.emptyMap();
        for(Long userId : userChoices.keySet())
            if(!userId.equals(user.id))
                for(Long questionId : userChoices.get(userId).keySet()){
                    if(result.containsKey(questionId))
                        result.put(questionId, result.get(questionId)
                                + userChoices.get(userId).get(questionId) / measureSum);
                    else
                        result.put(questionId,userChoices.get(userId).get(questionId)  / measureSum);
                }

        //find max
        Long id = result.keySet().iterator().next();
        double max = 0.0;
        for(Long questionId : result.keySet())
            if((!userAlreadyAnswers(user,questionId,answerRecords)) && (result.get(questionId) > max)) {
                max = result.get(questionId);
                id = questionId;
            }
System.out.println(max);
        System.out.println(measureSum);
        try{
            return Factory.getInstance().getQuestionDAO().getQuestionById(id);
        }
        catch (SQLException ex) {
            logger.error("Error occurred when getting question: " + ex.getMessage());
            return null;
        }
    }

    private static  Map<Long,Map<Long,Double>> getChoicesRating(List<AnswerRecord> answerRecords){

        //get all question and user Ids
        List<Long> questionIds = HibernateUtil.getSessionFactory().openSession()
                .createCriteria(Question.class)
                .setProjection(Projections.property("id"))
                .list();
        List<Long> userIds = HibernateUtil.getSessionFactory().openSession()
                .createCriteria(User.class)
                .setProjection(Projections.property("id"))
                .list();

        //userId->(questionId ->rating)
        //rating:
        //1 if incorrect answer
        //0 if no answer
        //0.5 if correct
        Map<Long,Map<Long,Double>> userChoices = new HashMap<>();//Collections.emptyMap();

        for(Long userId : userIds){
            List<AnswerRecord> userAnswers = new ArrayList<>();
            for( AnswerRecord a : answerRecords){
                if(a.user.id.equals(userId)) userAnswers.add(a);
            }
            Map<Long,Double> choices = new HashMap<>();//Collections.emptyMap();

            for(AnswerRecord a : userAnswers){
//                if(!choices.containsKey(a.question.id)){
                    if(a.correct) choices.put(a.question.id,0.5);
                    else choices.put(a.question.id, 1.0);
//                }else if(a.correct) choices.put(a.question.id,choices.get(a.question.id) + 0.5)
            }

            for(Long i : questionIds)
                if(!choices.containsKey(i))
                    choices.put(i,0.0);

            userChoices.put(userId,choices);
        }

        return userChoices;
    }

    private static boolean userAlreadyAnswers(User user,Long questionId,List<AnswerRecord> answerRecords){
        for(AnswerRecord i : answerRecords){
            if((i.getUser().id.equals(user.id)) && (i.question.id.equals(questionId)))
                return true;
        }
        return false;
    }
}
