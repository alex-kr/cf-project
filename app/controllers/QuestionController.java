package controllers;

import algorithm.QuestionSelector;
import models.core.AnswerRecord;
import models.core.Choice;
import models.core.Question;
import models.core.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import to.ChoiceTO;
import to.QuestionTO;
import to.UserTO;

import java.sql.SQLException;
import java.util.Map;

import static to.Transformer.convert;

/**
 * Created by Victor Dichko on 08.10.14.
 */
public class QuestionController extends Controller {

    private static final Logger.ALogger logger = Logger.of(QuestionController.class);

    public static Result showQuestion() {
        User user = new User();
        user.id = 0L;
        user.fullname = session("fullname");

        Question question = QuestionSelector.getRandom();
        QuestionTO qto = convert(question);
        UserTO uto = convert(user);
        return ok(views.html.core.one.render(qto, uto));
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
        User user = new User();
        Question question = new Question();
        Choice choice = new Choice();
        try {
            user = Factory.getInstance().getUserDAO().getUserByFullname(fullname);
            question = Factory.getInstance().getQuesionDAO().getQuestionById(qid);
            choice = Factory.getInstance().getChoiceDAO().getChoiceById(choiceId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

            // Creating user if doesn't exist
        if (user == null) {
            user = new User();
            user.fullname = fullname;
            user.isAdmin = false;
            try {
                Factory.getInstance().getUserDAO().addUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            // Saving data to DB
        answerRecord.user = user;
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
        return ok(views.html.core.oneresult.render(qto, cto));
    }
}
