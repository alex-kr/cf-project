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
import java.util.*;
import java.util.concurrent.SynchronousQueue;


/**
 * Created by Alex Kryukov on 02.12.14.
 */

public class Rules extends Controller {


	public static Result delete(Long id) {
		return TODO;
	}

	public static Result addRule() {
		return TODO;
	}

	public static Result save() {
		return TODO;
	}

	

}