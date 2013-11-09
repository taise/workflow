package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.authentication.*;

import models.User;

public class Authentication extends Controller {

  public static class Login {
    public String email;
    public String password;
  }
  
  public static Result loginForm() {
    return ok(form.render(form(Login.class)));
  }

  public static Result login() {
    Form<Login> loginForm  = form(Login.class).bindFromRequest();
    Login login = loginForm.get();
    //TODO: change to Login validation
    if(login.email == null || login.password == null) {
      return badRequest(form.render(loginForm));
    }

    User user = User.login(login.email, login.password);
    if(user != null) {
      session().clear();
      session("email", login.email);
      return redirect(routes.Requests.index());
    }
    return badRequest(form.render(loginForm));
  }
}
