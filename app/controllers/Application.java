package controllers;

import dao.UserDAO;
import models.core.User;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

public class Application extends Controller {

  public static Result index()  {

      // Examples of using Hibernate in our application

      // Saving in DB
      UserDAO userDAO = Factory.getInstance().getUserDAO();
      User user = new User();
      user.fullname = "Some User";
      user.isAdmin = false;
      try {
          userDAO.addUser(user);
      }
      catch (SQLException ex) {
          return ok("Exception occurred");
      }

      // Getting from DB
      try {
          String firstUserName = Factory.getInstance().getUserDAO().getUserById(1L).fullname;
        return ok(firstUserName);
      }
      catch (SQLException ex) {
          return ok("Exception occurred");
      }
  }
}
