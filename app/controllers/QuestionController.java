package controllers;

import algorithm.CollaborativeFiltering;
import algorithm.QuestionSelector;
import models.core.*;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import to.*;

import java.sql.SQLException;
import java.util.*;

import static to.Transformer.convert;


/**
 * Created by Victor Dichko on 08.10.14.
 */
public class QuestionController extends Controller {

    private static final Logger.ALogger logger = Logger.of(QuestionController.class);

    private static Form<Topic> topicForm = Form.form(Topic.class);

    private static long seed;
    private static int counter = 0;

    public static Result showQuestion() {
        String fullname = session("fullname");
        if (fullname == null || fullname.isEmpty()) {
            return showLogin();
        }
        Account account = new Account();
        Long topicId = 0L;
        if (!session("topic_id").equals("")) topicId = Long.parseLong(session("topic_id"));
        try {
            account = Factory.getInstance().getUserDAO().getAccountByFullname(fullname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Question question;
        Long level = account.getAccountLevel();
        double progress = account.getLevelProgress();
        if(((counter > 0) && (counter < 5)) || (level == null) || ((level == 1L) && (progress < 0.0001))){
            counter++;
            System.out.println("Random");
            question = QuestionSelector.getRandom();
        }else{
            counter = 0;
            System.out.println("Collaborative");
            question = CollaborativeFiltering.getNextQuestion(account,level,progress,topicId);
        }
        if (question == null) {
            return finish();
        }
        //question = QuestionSelector.getRandom();
        QuestionTO qto = convert(question);
        AccountTO uto = convert(account);
        List<ChoiceTO> ctolist = new ArrayList<ChoiceTO>();
        List<Choice> clist = null;
        try {
            clist = Factory.getInstance().getChoiceDAO().getChoicesByQuestionId(question.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        seed = System.nanoTime();
        Collections.shuffle(clist, new Random(seed));
        for (Choice choice: clist) {
            ctolist.add(Transformer.convert(choice));
        }
        return ok(views.html.core.one.render(qto, uto, ctolist));
    }

    public static Result submitAnswer() {
        // Getting data from request
        Http.Request r = request();
        Map<String, String[]> body = r.body().asFormUrlEncoded();
        Long qid = Long.parseLong(body.get("questionId")[0]);
        Long choiceId = Long.parseLong(body.get("answer")[0]);

        // Persisting data
        AnswerRecord answerRecord = new AnswerRecord();
        Account account = new Account();
        Question question = new Question();
        Choice choice = new Choice();
        try {
            account = Factory.getInstance().getUserDAO().getAccountByFullname(session("fullname"));
            question = Factory.getInstance().getQuestionDAO().getQuestionById(qid);
            choice = Factory.getInstance().getChoiceDAO().getChoiceById(choiceId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Saving data to DB
        answerRecord.account = account;
        answerRecord.question = question;
        answerRecord.choice = choice;
        answerRecord.correct = choice.correct;
        try {
            Factory.getInstance().getAnswerRecordDAO().addAnswerRecord(answerRecord);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        QuestionTO qto = convert(question);
        ChoiceTO cto = convert(choice);

        List<ChoiceTO> ctolist = new ArrayList<ChoiceTO>();
        List<Choice> clist = null;
        try {
            clist = Factory.getInstance().getChoiceDAO().getChoicesByQuestionId(question.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Choice c: clist) {
            ctolist.add(Transformer.convert(c));
        }
        Collections.shuffle(ctolist, new Random(seed));

        Rule rule = null;
        try {
            rule = Factory.getInstance().getRuleDAO().getRuleById(question.rule.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RuleTO rto = convert(rule);
        return ok(views.html.core.oneresult.render(qto, cto, ctolist, rto));
    }

    public static Result showLogin() {
        AccountTO accountTO = new AccountTO();
        accountTO.fullname = session("fullname");
        accountTO.id = 0L;
        accountTO.isAdmin = false;

        String topicId = session("topic_id");
        if (topicId != null && !topicId.isEmpty()) {
            try {
                Topic topic = Factory.getInstance().getTopicDAO().getTopicById(Long.parseLong(topicId));
                topicForm = topicForm.fill(topic);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            topicForm = topicForm.fill(new Topic());
        }
        return ok(views.html.login.render(accountTO, topicForm));
    }

    public static Result submitlogin() {
        // Getting data from request
        Http.Request r = request();
        Map<String, String[]> body = r.body().asFormUrlEncoded();
        String fullname = body.get("fullname")[0];
        String topic_id = body.get("id")[0];

        session("topic_id", topic_id);
        session("fullname", fullname);

        logger.debug("topic_id: " + session("topic_id"));
        logger.debug("fullname: " + session("fullname"));

        // Retrieving User from DB
        Account account = new Account();
        try {
            account = Factory.getInstance().getUserDAO().getAccountByFullname(fullname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating User if doesn't exist
        if (account == null) {
            account = new Account();
            account.fullname = fullname;
            account.isAdmin = false;
            try {
                Factory.getInstance().getUserDAO().addAccount(account);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return showQuestion();
    }

    public static Result finish() {
        return ok(views.html.finish.render());
    }
}
