
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import static play.mvc.Results.*;
import views.html.*;


import static play.mvc.Results.*;
public class Global extends GlobalSettings {

	 @Override
	 public void onStart(Application app) {
		 Logger.info("Application has started");
  	 }  
  
	 @Override
	 public void onStop(Application app) {
		 Logger.info("Application shutdown...");
	 }   
	 
	 
	 @Override
	 public Result onError(RequestHeader request, Throwable t) {
		 return ok(errorpage.render("A fatal error has occured, please contact the developers", "500"));
	 }
	 
	 @Override
	 public Result onHandlerNotFound(RequestHeader request) {
		 //return ok("NOT FOUND" + request.toString());
		 return ok(errorpage.render("The page you are looking for cannot be found", "404"));
	 } 
	 
	 @Override
	 public Result onBadRequest(RequestHeader request, String error) {
		 return ok(errorpage.render("Bad request. Don't try to hack the URI!", "400"));
	}  
	   
}