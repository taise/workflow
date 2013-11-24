package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import java.util.List;

import models.Request;

import views.html.request.*;

public class Requests extends Controller {

    @Security.Authenticated(Auth.class)
    public static Result index() {
        List<Request> requests = Request.all();
        return ok(index.render(requests, requests.size()));
    }

    @Security.Authenticated(Auth.class)
    public static Result newForm() {
        return ok(form.render(form(Request.class)));
    }

    @Security.Authenticated(Auth.class)
    public static Result create() {
        Form<Request> requestForm = form(Request.class).bindFromRequest();
        if(requestForm.hasErrors()) {
          return badRequest(form.render(requestForm));
        }
        Request request = requestForm.get();
        request.save();
        return redirect(routes.Requests.index());
    }

    @Security.Authenticated(Auth.class)
    public static Result show(Long id) {
      Request request = Request.find.byId(id);
      return ok(show.render(request));
    }
}
