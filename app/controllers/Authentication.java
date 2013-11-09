package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.authentication.*;
import views.html.request.*;

import models.User;

public class Authentication extends Controller {

  public static class Login {
    public String email;
    public String password;
  }
  
  public static Result loginForm() {
    return ok(loginForm.render(form(Login.class)));
  }

  public static Result login() {
    Login login = form(Login.class).bindFromRequest().get();
    if(login.email == null || login.password == null) {
      return badRequest(loginForm.render(form(Login.class)));
    }

    User user = User.findByEmail(login.email);
    if(user != null) {
      if (user.password.equals(login.password)) {
        session().clear();
        session("email", login.email);
        return redirect(routes.Requests.index());
      } 
    }
    return badRequest(loginForm.render(form(Login.class)));
  }
}
