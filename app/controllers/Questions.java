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
import models.core.Choice;
import controllers.Factory;
import dao.impl.*;
import dao.impl.QuestionDAOImpl;
import play.data.Form;
import views.html.questions;
import views.html.addform;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

import controllers.helpers.ChoiceTextHolder;

/**
 * Created by Alex Kryukov on 09.10.14.
 */

public class Questions extends Controller {

	private static Form<Question> questionForm = Form.form(Question.class);
	private static Form<ChoiceTextHolder> choiceTextHolderForm = Form.form(ChoiceTextHolder.class);


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
				return ok(addform.render(quest,questionForm,choiceTextHolderForm));
	}

	public static Result save(){
		Form<Question> boundQuestionForm = questionForm.bindFromRequest();
		Question question = boundQuestionForm.get();
		question.save();
		String rightChoice = choiceTextHolderForm.bindFromRequest().get().rightChoice;
		List<String> wrongChoices = choiceTextHolderForm.bindFromRequest().get().wrongChoice;
        List<ChoiceText> choiceTexts = new ArrayList<>();
        try{
            choiceTexts = (ArrayList)Factory.getInstance().getChoiceTextDAO().getAllChoiceTexts();
        }catch (Exception e) {
            System.err.println("Smth wrong");
        }

        List<ChoiceText> choiceTextRW = new ArrayList<>();
        choiceTextRW.add(new ChoiceText(rightChoice));
        for(String i : wrongChoices)
            choiceTextRW.add(new ChoiceText(i));

        for(ChoiceText i : choiceTextRW){

            for(ChoiceText j : choiceTexts)//check if we already have such choicetexts
                if(j.text.equals(i.text))
                    i.id = j.id;

            if(i.id == null)
                i.save();

            if(choiceTextRW.indexOf(i) == 0)
                new Choice(question,i,true).save();
            else
                new Choice(question,i,false).save();
        }

		List<Question> quest = new ArrayList<Question>();
		try {
			quest = Factory.getInstance().getQuestionDAO().getAllQuestions();
		} catch (Exception e) {
			System.err.println("Smth wrong");
		}
		return ok(addform.render(quest,questionForm,choiceTextHolderForm));
	}

}