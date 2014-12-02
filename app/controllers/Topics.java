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
import views.html.addquestions;
import java.util.*;
import java.util.concurrent.SynchronousQueue;
import models.core.Topic;
import dao.impl.TopicDAOImpl;
import views.html.addtopics;
import java.util.*;


/**
 * Created by Alex Kryukov on 02.12.14.
 */

public class Topics extends Controller {

	private static Form<Topic> topicForm = Form.form(Topic.class);


	public static Result delete(Long id) {
		return TODO;
	}

	public static Result addTopic(){
		List<Topic> topics = new ArrayList<Topic>();
		try {
			topics = Factory.getInstance().getTopicDAO().getAllTopics();
		} catch (Exception e) {
			System.err.println("Smth wrong");
		}

		return ok(addtopics.render(topics,topicForm));
	}

	public static Result save(){
		Form<Topic> boundTopicForm = topicForm.bindFromRequest();
		Topic topic = boundTopicForm.get();
		topic.save();
		List<Topic> topics = new ArrayList<Topic>();
		try {
			topics = Factory.getInstance().getTopicDAO().getAllTopics();
		} catch (Exception e) {
			System.err.println("Smth wrong");
		}

		return ok(addtopics.render(topics,topicForm));
	}

	

}