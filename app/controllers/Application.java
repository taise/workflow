package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;
import views.html.login.*;

import models.User;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

  public static class Login {
    public String email;
    public String password;

    public String validate() {
      if (User.login(email, password) == null) {
        return "Invalid user or password";
      }
      return null;
    }
  }
  
  public static Result login() {
    return ok(form.render(form(Login.class)));
  }

  public static Result authenticate() {
    Form<Login> loginForm  = form(Login.class).bindFromRequest();
    // hasErrors check to form validate result
    if(loginForm.hasErrors()) {
      return badRequest(form.render(loginForm));
    } else {
      session("email", loginForm.get().email);
      return redirect(routes.Requests.index());
    }
  }
}
