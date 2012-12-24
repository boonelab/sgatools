// @SOURCE:/Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/conf/routes
// @HASH:89648151b1611ee28f9346fd9f61faf0e001306b
// @DATE:Thu Dec 20 17:12:31 EST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:31
// @LINE:28
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers {

// @LINE:31
class ReverseAssets {
    


 
// @LINE:31
def at(file:String) = {
   Call("GET", "/assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                        

                      
    
}
                            

// @LINE:28
class ReverseDAcontroller {
    


 
// @LINE:28
def initPreloadedDA(jobid:String) = {
   Call("GET", "/preloaded-dataanalysis/" + implicitly[PathBindable[String]].unbind("jobid", jobid))
}
                                                        

                      
    
}
                            

// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:20
class ReverseNScontroller {
    


 
// @LINE:25
def redirectPreloadedNSform(jobid:String) = {
   Call("GET", "/preloaded-normalizationscoring/" + implicitly[PathBindable[String]].unbind("jobid", jobid))
}
                                                        
 
// @LINE:24
def initPreloadedNSform() = {
   Call("POST", "/preloaded-normalizationscoring")
}
                                                        
 
// @LINE:22
def showJob(jobid:String) = {
   Call("GET", "/normalizationscoring/" + implicitly[PathBindable[String]].unbind("jobid", jobid))
}
                                                        
 
// @LINE:20
def submit() = {
   Call("POST", "/normalizationscoring")
}
                                                        
 
// @LINE:21
def initNSform() = {
   Call("GET", "/normalizationscoring")
}
                                                        

                      
    
}
                            

// @LINE:16
// @LINE:15
// @LINE:14
class ReverseIAcontroller {
    


 
// @LINE:16
def submit() = {
   Call("POST", "/imageanalysis")
}
                                                        
 
// @LINE:14
def initIAForm() = {
   Call("GET", "/imageanalysis")
}
                                                        
 
// @LINE:15
def showJob(jobid:String) = {
   Call("GET", "/imageanalysis/" + implicitly[PathBindable[String]].unbind("jobid", jobid))
}
                                                        

                      
    
}
                            

// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    


 
// @LINE:11
def renderContactPage() = {
   Call("GET", "/contact")
}
                                                        
 
// @LINE:9
def renderHelpPage() = {
   Call("GET", "/help")
}
                                                        
 
// @LINE:10
def renderAboutPage() = {
   Call("GET", "/about")
}
                                                        
 
// @LINE:6
def index() = {
   Call("GET", "/")
}
                                                        

                      
    
}
                            
}
                    


// @LINE:31
// @LINE:28
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:31
class ReverseAssets {
    


 
// @LINE:31
def at = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:28
class ReverseDAcontroller {
    


 
// @LINE:28
def initPreloadedDA = JavascriptReverseRoute(
   "controllers.DAcontroller.initPreloadedDA",
   """
      function(jobid) {
      return _wA({method:"GET", url:"/preloaded-dataanalysis/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("jobid", jobid)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:20
class ReverseNScontroller {
    


 
// @LINE:25
def redirectPreloadedNSform = JavascriptReverseRoute(
   "controllers.NScontroller.redirectPreloadedNSform",
   """
      function(jobid) {
      return _wA({method:"GET", url:"/preloaded-normalizationscoring/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("jobid", jobid)})
      }
   """
)
                                                        
 
// @LINE:24
def initPreloadedNSform = JavascriptReverseRoute(
   "controllers.NScontroller.initPreloadedNSform",
   """
      function() {
      return _wA({method:"POST", url:"/preloaded-normalizationscoring"})
      }
   """
)
                                                        
 
// @LINE:22
def showJob = JavascriptReverseRoute(
   "controllers.NScontroller.showJob",
   """
      function(jobid) {
      return _wA({method:"GET", url:"/normalizationscoring/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("jobid", jobid)})
      }
   """
)
                                                        
 
// @LINE:20
def submit = JavascriptReverseRoute(
   "controllers.NScontroller.submit",
   """
      function() {
      return _wA({method:"POST", url:"/normalizationscoring"})
      }
   """
)
                                                        
 
// @LINE:21
def initNSform = JavascriptReverseRoute(
   "controllers.NScontroller.initNSform",
   """
      function() {
      return _wA({method:"GET", url:"/normalizationscoring"})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:16
// @LINE:15
// @LINE:14
class ReverseIAcontroller {
    


 
// @LINE:16
def submit = JavascriptReverseRoute(
   "controllers.IAcontroller.submit",
   """
      function() {
      return _wA({method:"POST", url:"/imageanalysis"})
      }
   """
)
                                                        
 
// @LINE:14
def initIAForm = JavascriptReverseRoute(
   "controllers.IAcontroller.initIAForm",
   """
      function() {
      return _wA({method:"GET", url:"/imageanalysis"})
      }
   """
)
                                                        
 
// @LINE:15
def showJob = JavascriptReverseRoute(
   "controllers.IAcontroller.showJob",
   """
      function(jobid) {
      return _wA({method:"GET", url:"/imageanalysis/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("jobid", jobid)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    


 
// @LINE:11
def renderContactPage = JavascriptReverseRoute(
   "controllers.Application.renderContactPage",
   """
      function() {
      return _wA({method:"GET", url:"/contact"})
      }
   """
)
                                                        
 
// @LINE:9
def renderHelpPage = JavascriptReverseRoute(
   "controllers.Application.renderHelpPage",
   """
      function() {
      return _wA({method:"GET", url:"/help"})
      }
   """
)
                                                        
 
// @LINE:10
def renderAboutPage = JavascriptReverseRoute(
   "controllers.Application.renderAboutPage",
   """
      function() {
      return _wA({method:"GET", url:"/about"})
      }
   """
)
                                                        
 
// @LINE:6
def index = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"/"})
      }
   """
)
                                                        

                      
    
}
                            
}
                    


// @LINE:31
// @LINE:28
// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.ref {

// @LINE:31
class ReverseAssets {
    


 
// @LINE:31
def at(path:String, file:String) = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]))
)
                              

                      
    
}
                            

// @LINE:28
class ReverseDAcontroller {
    


 
// @LINE:28
def initPreloadedDA(jobid:String) = new play.api.mvc.HandlerRef(
   controllers.DAcontroller.initPreloadedDA(jobid), HandlerDef(this, "controllers.DAcontroller", "initPreloadedDA", Seq(classOf[String]))
)
                              

                      
    
}
                            

// @LINE:25
// @LINE:24
// @LINE:22
// @LINE:21
// @LINE:20
class ReverseNScontroller {
    


 
// @LINE:25
def redirectPreloadedNSform(jobid:String) = new play.api.mvc.HandlerRef(
   controllers.NScontroller.redirectPreloadedNSform(jobid), HandlerDef(this, "controllers.NScontroller", "redirectPreloadedNSform", Seq(classOf[String]))
)
                              
 
// @LINE:24
def initPreloadedNSform() = new play.api.mvc.HandlerRef(
   controllers.NScontroller.initPreloadedNSform(), HandlerDef(this, "controllers.NScontroller", "initPreloadedNSform", Seq())
)
                              
 
// @LINE:22
def showJob(jobid:String) = new play.api.mvc.HandlerRef(
   controllers.NScontroller.showJob(jobid), HandlerDef(this, "controllers.NScontroller", "showJob", Seq(classOf[String]))
)
                              
 
// @LINE:20
def submit() = new play.api.mvc.HandlerRef(
   controllers.NScontroller.submit(), HandlerDef(this, "controllers.NScontroller", "submit", Seq())
)
                              
 
// @LINE:21
def initNSform() = new play.api.mvc.HandlerRef(
   controllers.NScontroller.initNSform(), HandlerDef(this, "controllers.NScontroller", "initNSform", Seq())
)
                              

                      
    
}
                            

// @LINE:16
// @LINE:15
// @LINE:14
class ReverseIAcontroller {
    


 
// @LINE:16
def submit() = new play.api.mvc.HandlerRef(
   controllers.IAcontroller.submit(), HandlerDef(this, "controllers.IAcontroller", "submit", Seq())
)
                              
 
// @LINE:14
def initIAForm() = new play.api.mvc.HandlerRef(
   controllers.IAcontroller.initIAForm(), HandlerDef(this, "controllers.IAcontroller", "initIAForm", Seq())
)
                              
 
// @LINE:15
def showJob(jobid:String) = new play.api.mvc.HandlerRef(
   controllers.IAcontroller.showJob(jobid), HandlerDef(this, "controllers.IAcontroller", "showJob", Seq(classOf[String]))
)
                              

                      
    
}
                            

// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    


 
// @LINE:11
def renderContactPage() = new play.api.mvc.HandlerRef(
   controllers.Application.renderContactPage(), HandlerDef(this, "controllers.Application", "renderContactPage", Seq())
)
                              
 
// @LINE:9
def renderHelpPage() = new play.api.mvc.HandlerRef(
   controllers.Application.renderHelpPage(), HandlerDef(this, "controllers.Application", "renderHelpPage", Seq())
)
                              
 
// @LINE:10
def renderAboutPage() = new play.api.mvc.HandlerRef(
   controllers.Application.renderAboutPage(), HandlerDef(this, "controllers.Application", "renderAboutPage", Seq())
)
                              
 
// @LINE:6
def index() = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq())
)
                              

                      
    
}
                            
}
                    
                