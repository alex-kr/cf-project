package algorithm;

import controllers.Factory;
import models.core.*;
import org.hibernate.Session;
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

    private static Long level;
    private static Rule rule;
    private static double progress;
    private static double maxProgress = 0.8;
    private static boolean allAnswers = false;
    private static Session session;
    private static Map<Long,List<Integer>> progressByTopic;

    public static Question getNextQuestion(User user){
        session = HibernateUtil.getSessionFactory().openSession();
        List<AnswerRecord> answerRecords;
        try{
            answerRecords = (ArrayList) Factory.getInstance().getAnswerRecordDAO().getAllAnswerRecords();
        }catch (SQLException ex) {
            logger.error("Error occurred when getting all answerRecords: " + ex.getMessage());
            return null;
        }

        getCurrentProgress(user,answerRecords);
        //userId->(questionId->rating))
        Map<Long,Map<Long,Double>> userChoices = getChoicesRating(answerRecords);

        //cos measure
        Map<Long,Double> measure = new HashMap<>();
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
        Long id = 0L;// = result.keySet().iterator().next();
        double max = 0.0;
        Question question;
        allAnswers = false;
        for(Long questionId : result.keySet()){
            try {
                question = Factory.getInstance().getQuestionDAO().getQuestionById(questionId);
            }catch (SQLException ex) {
                logger.error("Error occurred when getting question: " + ex.getMessage());
                return null;
            }
            if((result.get(questionId) >= max) && (question.level.equals(level)) && (!userAlreadyAnswers(user,questionId,answerRecords)))  {
                max = result.get(questionId);
                id = questionId;
            }
        }
        if(id == 0L){
            allAnswers = true;
            for(Long questionId : result.keySet()){
                try {
                    question = Factory.getInstance().getQuestionDAO().getQuestionById(questionId);
                }catch (SQLException ex) {
                    logger.error("Error occurred when getting question: " + ex.getMessage());
                    return null;
                }
//                System.out.println(questionId + " " + result.get(questionId)+" "+(question.level.equals(level)) +" "+ (!userAlreadyAnswers(user,questionId,answerRecords)));
                if((result.get(questionId) >= max) && (question.level.equals(level)) && (!userAlreadyAnswers(user,questionId,answerRecords))) {
                    max = result.get(questionId);
                    id = questionId;
                }
            }
        }
        System.out.print("id " + id + allAnswers + " " + max);

        session.close();
        //TODO check if id is still 0L
//        System.out.println(measureSum);
        try {
            return Factory.getInstance().getQuestionDAO().getQuestionById(id);
        }catch (SQLException ex) {
            logger.error("Error occurred when getting question: " + ex.getMessage());
            return null;
        }
    }

    private static  Map<Long,Map<Long,Double>> getChoicesRating(List<AnswerRecord> answerRecords){

        //get all question and user Ids
        List<Long> questionIds = session
                .createCriteria(Question.class)
                .setProjection(Projections.property("id"))
                .list();
        List<Long> userIds = session
                .createCriteria(User.class)
                .setProjection(Projections.property("id"))
                .list();

        //userId->(questionId ->rating)
        //rating:
        //1 if incorrect answer
        //0 if no answer
        //0.5 if correct
        Map<Long,Map<Long,Double>> userChoices = new HashMap<>();

        for(Long userId : userIds){
            List<AnswerRecord> userAnswers = new ArrayList<>();
            for( AnswerRecord a : answerRecords){
                if(a.user.id.equals(userId)) userAnswers.add(a);
            }
            Map<Long,Double> choices = new HashMap<>();

            for(AnswerRecord a : userAnswers){
                if(!choices.containsKey(a.question.id)){
                    if(a.correct) choices.put(a.question.id,0.5);
                    else choices.put(a.question.id, 1.0);
                }else if(a.correct) choices.put(a.question.id,choices.get(a.question.id) * 0.5);
            }

            for(Long i : questionIds)
                if(!choices.containsKey(i))
                    choices.put(i,0.0);

            userChoices.put(userId,choices);
        }

        return userChoices;
    }

    private static boolean userAlreadyAnswers(User user,Long questionId,List<AnswerRecord> answerRecords){
        for(int i = answerRecords.size()-1; i >= 0; i--){//AnswerRecord i : answerRecords){
            if((answerRecords.get(i).getUser().id.equals(user.id)) && (answerRecords.get(i).question.id.equals(questionId))){
                if(answerRecords.get(i).correct) return true;
//                if(allAnswers) return false;  //user answered to all questions, but the progress is too low to change level
                return !allAnswers;//true;
            }
        }
        return false;
    }

    private static void getCurrentProgress(User user, List<AnswerRecord> answerRecords){
        int counter = 0;
        int counterCorrect = 0;
        Long currentLevel = 10L;      //max level
        int i = answerRecords.size()-1;
        progressByTopic = new HashMap<>();
        level = 1L;
        boolean ok = false;
        do{
            if(answerRecords.get(i).getUser().id.equals(user.id)){
                level = answerRecords.get(i).question.level;
                rule = answerRecords.get(i).question.rule;
                ok = true;
                System.out.println("id" + answerRecords.get(i).question.id);
            }
            i--;
        }while ((i >= 0) &&(!ok));

        getQuestionCountByTopic(level);

        i++;

        if(i > 0)
            do{
                if(answerRecords.get(i).getUser().id.equals(user.id)){
                    currentLevel = answerRecords.get(i).question.level;
//                    System.out.println("id" + answerRecords.get(i).question.id + currentLevel.equals(level) + (answerRecords.get(i).correct));
                    if(currentLevel.equals(level) && (answerRecords.get(i).correct)){
                        counterCorrect++;
                        List<Integer> values = progressByTopic.get(answerRecords.get(i).question.rule.topic.id);
                        values.set(0,values.get(0) + 1);
                        progressByTopic.put(answerRecords.get(i).question.rule.topic.id,values);
                    }
                }
                i--;
            }while ((i >= 0) && (currentLevel >= level));
//        System.out.println(counterCorrect);
        counter = getQuestionCount();//level);

        if(counter > 0)
            progress = (double)counterCorrect / (double)counter;
        else
            progress = 0.0; //there are no questions?

        if(progress >= maxProgress){
            level++;
            allAnswers = false;
            progress = 0.0;
            getQuestionCountByTopic(level);
        }
        System.out.println(progress);
        System.out.println(level);
    }

    private static int getQuestionCount(){//Long level){
//        List<Long> questionIds = session
//                .createCriteria(Question.class)
//                .setProjection(Projections.property("id"))
//                .list();

        int count = 0;
//        for(Long id : questionIds){
//            try {
//                if(Factory.getInstance().getQuestionDAO().getQuestionById(id).level.equals(level))
//                    count++;
//            }catch (SQLException ex) {
//                logger.error("Error occurred when getting question: " + ex.getMessage());
//                return 0;
//            }
//        }
        for(Map.Entry<Long,List<Integer>> entry: progressByTopic.entrySet()){
            count += entry.getValue().get(1);
        }
        System.out.println(count);
        return count;
    }

    private static void getQuestionCountByTopic(Long level){
        progressByTopic.clear();
        progressByTopic = new HashMap<>();
        List<Long> questionIds = session
                .createCriteria(Question.class)
                .setProjection(Projections.property("id"))
                .list();

        for(Long id: questionIds){
            try {
                Question question = Factory.getInstance().getQuestionDAO().getQuestionById(id);
                if(question.level.equals(level)){
                    if(!progressByTopic.containsKey(question.rule.topic.id)){
                        List<Integer> values = new ArrayList<>();
                        values.add(0,0);    //for correct answers
                        values.add(1,1);    //for question quantity
                        progressByTopic.put(question.rule.topic.id,values);
                    }else{
                        List<Integer> values = progressByTopic.get(question.rule.topic.id);
                        values.set(1,values.get(1) + 1);
                        progressByTopic.put(question.rule.topic.id,values);
                    }
                }
            }catch (SQLException ex) {
                logger.error("Error occurred when getting question: " + ex.getMessage());
                return;
            }
        }
    }
}
