package controllers;


import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import play.api.mvc.*;
import models.core.Rule;
import controllers.Factory;
import dao.impl.*;
import dao.impl.QuestionDAOImpl;
import play.data.Form;
import views.html.addrules;
import java.util.*;


/**
 * Created by Alex Kryukov on 02.12.14.
 */

public class Rules extends Controller {


	private static Form<Rule> ruleForm = Form.form(Rule.class);


	public static Result delete(Long id) {
		return TODO;
	}

	public static Result addRule(){
		List<Rule> rules = new ArrayList<Rule>();
		try {
			rules = Factory.getInstance().getRuleDAO().getAllRules();
		} catch (Exception e) {
			System.err.println("Smth wrong");
		}

		return ok(addrules.render(rules,ruleForm));
	}

	public static Result save(){
		Form<Rule> boundRuleForm = ruleForm.bindFromRequest();
		Rule rule = boundRuleForm.get();
		rule.save();
		List<Rule> rules = new ArrayList<Rule>();
		try {
			rules = Factory.getInstance().getRuleDAO().getAllRules();
		} catch (Exception e) {
			System.err.println("Smth wrong");
		}

		return ok(addrules.render(rules,ruleForm));
	}

	

}