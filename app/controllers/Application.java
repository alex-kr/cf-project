package controllers;

import algorithm.QuestionSelector;
import models.core.Account;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.about;

import java.sql.SQLException;

public class Application extends Controller {

  public static Result index()  {

//      // Examples of using Hibernate in our application
//
//      // Saving in DB
//      UserDAO userDAO = Factory.getInstance().getUserDAO();
//      User user = new User();
//      user.fullname = "Some User";
//      user.isAdmin = false;
//      try {
//          userDAO.addAccount(user);
//      }
//      catch (SQLException ex) {
//          return ok("Exception occurred");
//      }
//
//      // Getting from DB
//      try {
//          String firstUserName = Factory.getInstance().getUserDAO().getAccountById(1L).fullname;
//        return ok(firstUserName);
//      }
//      catch (SQLException ex) {
//          return ok("Exception occurred");
//      }
//********************** This content has been added recently *************************
      if (session("fullname") != null) {
          Account account = null;
          String output = null;
          try {
              account = Factory.getInstance().getUserDAO().getAccountByFullname(session("fullname"));
              Long level = account.getAccountLevel();
              Double progress = account.getLevelProgress();
              output = "Account: " + account.fullname + "\n"
                      + "Account level: " + level.toString() + "\n"
                      + "Account progress: " + progress.toString();
              return ok(output);
          } catch (SQLException ex) {
              ex.printStackTrace();
          }
      }
//*************************** The end for added code **********************************
      return ok(QuestionSelector.getRandom().questionText);
//      return TODO;
  }


  public static Result about() {
      return ok(about.render());
  }



}
