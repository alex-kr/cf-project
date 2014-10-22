package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import play.libs.Json;
import play.api.mvc.*;
import models.core.Question;
import models.core.ChoiceText;
import controllers.Factory;
import dao.impl.*;
import dao.impl.QuestionDAOImpl;
import play.data.Form;
import views.html.questions;
import views.html.addform;
import java.util.*;

/**
 * Created by Alex Kryukov on 09.10.14.
 */

public class Questions extends Controller {

	private static Form<Question> questionForm = Form.form(Question.class);

	public static Result index(){
		return TODO;
	}
/*
	public static Result addQuestion() {
		Question question = Form.form(Question.class).bindFromRequest().get();
		try {
			Factory.getInstance().getQuestionDAO().addQuestion(question);
		} catch (Exception e) {
			System.err.println("Smth wrong");
		}
		return TODO;
	}
*/
	public static Result delete(Long id){
		/*ObjectNode jsonResult = Json.newObject();
		try {
			if (QuestionDAOImpl.getQuestionById(id) != null){
    			QuestionDAOImpl.deleteQuestion(QuestionDAOImpl.getQuestionById(id));
				jsonResult.put("error", 0);
      			jsonResult.put("error_message", "");	
			} else {
				jsonResult.put("error", 1);
      			jsonResult.put("error_message", "Question does not exist");
			}
		} catch (Exception e) {
			System.err.println("Smth wrong in Question controller: delete()");
		}

		return ok(jsonResult);*/
		return TODO;
	}

	public static Result addQuestion(){
		List<Question> quest = new ArrayList<Question>();
		try {
			quest = Factory.getInstance().getQuestionDAO().getAllQuestions();
		} catch (Exception e) {
			System.err.println("Smth wrong");
		}
		Question question = Form.form(Question.class).bindFromRequest().get();
		return ok(addform.render(quest,Form.form(Question.class)));
	}

	public static Result save(){
		Form<Question> boundForm = questionForm.bindFromRequest();
		Question question = boundForm.get();
		question.save();
		return ok("azazaza");
	}

}