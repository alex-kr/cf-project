package controllers;

import algorithm.CollaborativeFiltering;
import algorithm.QuestionSelector;
import models.core.*;
import play.Logger;
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

    private static long seed;

    public static Result showQuestion() {
        Account account = new Account();
        try {
            account = Factory.getInstance().getUserDAO().getAccountByFullname(session("fullname"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Question question;
        if (account == null) {
            account = new Account();
            account.id = 0L;
            account.fullname = session("fullname");
            question = QuestionSelector.getRandom();
        } else {
            question = CollaborativeFiltering.getNextQuestion(account);
            //question = QuestionSelector.getRandom();
        }
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
        String fullname = body.get("fullname")[0];
        Long qid = Long.parseLong(body.get("questionId")[0]);
        Long choiceId = Long.parseLong(body.get("answer")[0]);

        // Persisting data
        session("fullname", fullname);
        AnswerRecord answerRecord = new AnswerRecord();
        Account account = new Account();
        Question question = new Question();
        Choice choice = new Choice();
        try {
            account = Factory.getInstance().getUserDAO().getAccountByFullname(fullname);
            question = Factory.getInstance().getQuestionDAO().getQuestionById(qid);
            choice = Factory.getInstance().getChoiceDAO().getChoiceById(choiceId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

            // Creating user if doesn't exist
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
}
