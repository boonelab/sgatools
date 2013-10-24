package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.about;
import views.html.contact;
import views.html.dapage;
import views.html.docs;
import views.html.index;
import views.html.ia_email_summary;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result renderHelpPage() {
        return ok(docs.render());
    }

    public static Result renderAboutPage() {
        return ok(about.render());
    }

    public static Result renderContactPage() {
        return ok(contact.render());
    }

    public static Result renderDaPage() {
        return ok(dapage.render());
    }

    public static Result renderIAEmailPage() {
        return ok(ia_email_summary.render());
    }

}