package controllers;

import play.*;
import play.mvc.*;

import views.html.request.*;

public class RequestController extends Controller {

    public static Result index() {
        return ok(index.render("Request.index"));
    }
}
