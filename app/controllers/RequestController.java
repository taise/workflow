package controllers;

import play.*;
import play.mvc.*;

import views.html.request.*;

public class RequestController extends Controller {

    public static Result index() {
        return ok(index.render("Request.index"));
    }

    public static Result newPage() {
        return ok(newPage.render("Request.new"));
    }
}
