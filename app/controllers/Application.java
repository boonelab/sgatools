package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

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
  
  

}