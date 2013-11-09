package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import java.util.List;

import models.Request;

import views.html.request.*;

public class Requests extends Controller {

    final static Form<Request> requestForm = form(Request.class);

    @Security.Authenticated(Auth.class)
    public static Result index() {
        List<Request> requests = Request.all();
        return ok(index.render(requests, requests.size()));
    }

    public static Result newForm() {
        return ok(form.render(requestForm));
    }

    public static Result create() {
        Form<Request> requestForm = form(Request.class).bindFromRequest();
        Request request = requestForm.get();
        request.save();
        return redirect(routes.Requests.index());
    }

    public static Result show(Long id) {
      Request request = Request.find.byId(id);
      return ok(show.render(request));
    }
}
