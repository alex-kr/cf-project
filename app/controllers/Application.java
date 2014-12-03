package controllers;

import algorithm.QuestionSelector;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.about;

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
      return ok(QuestionSelector.getRandom().questionText);
//      return TODO;
  }


  public static Result about() {
      return ok(about.render());
  }



}
