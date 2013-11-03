package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.Request;

import views.html.request.*;

//TODO: RequestController => Requests
public class RequestController extends Controller {

    final static Form<Request> requestForm = form(Request.class);

    public static Result index() {
        return ok(index.render("Request.index"));
    }

    public static Result newForm() {
        return ok(form.render(requestForm));
    }

    public static Result save() {
        return redirect(routes.RequestController.index());
    }
}
