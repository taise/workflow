package controllers;

import play.*;
import play.mvc.*;
import static play.data.Form.*;

import views.html.authentication.*;
import views.html.request.*;

public class Authentication extends Controller {

  public static class Login {
    public String email;
    public String password;
  }
  
  public static Result loginForm() {
    return ok(loginForm.render(form(Login.class)));
  }

  public static Result login() {
    return redirect(routes.Requests.index());
  }
}
