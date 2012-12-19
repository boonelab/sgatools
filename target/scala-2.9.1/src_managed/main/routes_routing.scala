// @SOURCE:/Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/conf/routes
// @HASH:89648151b1611ee28f9346fd9f61faf0e001306b
// @DATE:Wed Dec 19 13:47:29 EST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:9
val controllers_Application_renderHelpPage1 = Route("GET", PathPattern(List(StaticPart("/help"))))
                    

// @LINE:10
val controllers_Application_renderAboutPage2 = Route("GET", PathPattern(List(StaticPart("/about"))))
                    

// @LINE:11
val controllers_Application_renderContactPage3 = Route("GET", PathPattern(List(StaticPart("/contact"))))
                    

// @LINE:14
val controllers_IAcontroller_initIAForm4 = Route("GET", PathPattern(List(StaticPart("/imageanalysis"))))
                    

// @LINE:15
val controllers_IAcontroller_showJob5 = Route("GET", PathPattern(List(StaticPart("/imageanalysis/"),DynamicPart("jobid", """[^/]+"""))))
                    

// @LINE:16
val controllers_IAcontroller_submit6 = Route("POST", PathPattern(List(StaticPart("/imageanalysis"))))
                    

// @LINE:20
val controllers_NScontroller_submit7 = Route("POST", PathPattern(List(StaticPart("/normalizationscoring"))))
                    

// @LINE:21
val controllers_NScontroller_initNSform8 = Route("GET", PathPattern(List(StaticPart("/normalizationscoring"))))
                    

// @LINE:22
val controllers_NScontroller_showJob9 = Route("GET", PathPattern(List(StaticPart("/normalizationscoring/"),DynamicPart("jobid", """[^/]+"""))))
                    

// @LINE:24
val controllers_NScontroller_initPreloadedNSform10 = Route("POST", PathPattern(List(StaticPart("/preloaded-normalizationscoring"))))
                    

// @LINE:25
val controllers_NScontroller_redirectPreloadedNSform11 = Route("GET", PathPattern(List(StaticPart("/preloaded-normalizationscoring/"),DynamicPart("jobid", """[^/]+"""))))
                    

// @LINE:28
val controllers_DAcontroller_initPreloadedDA12 = Route("GET", PathPattern(List(StaticPart("/preloaded-dataanalysis/"),DynamicPart("jobid", """[^/]+"""))))
                    

// @LINE:31
val controllers_Assets_at13 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    
def documentation = List(("""GET""","""/""","""controllers.Application.index()"""),("""GET""","""/help""","""controllers.Application.renderHelpPage()"""),("""GET""","""/about""","""controllers.Application.renderAboutPage()"""),("""GET""","""/contact""","""controllers.Application.renderContactPage()"""),("""GET""","""/imageanalysis""","""controllers.IAcontroller.initIAForm()"""),("""GET""","""/imageanalysis/$jobid<[^/]+>""","""controllers.IAcontroller.showJob(jobid:String)"""),("""POST""","""/imageanalysis""","""controllers.IAcontroller.submit()"""),("""POST""","""/normalizationscoring""","""controllers.NScontroller.submit()"""),("""GET""","""/normalizationscoring""","""controllers.NScontroller.initNSform()"""),("""GET""","""/normalizationscoring/$jobid<[^/]+>""","""controllers.NScontroller.showJob(jobid:String)"""),("""POST""","""/preloaded-normalizationscoring""","""controllers.NScontroller.initPreloadedNSform()"""),("""GET""","""/preloaded-normalizationscoring/$jobid<[^/]+>""","""controllers.NScontroller.redirectPreloadedNSform(jobid:String)"""),("""GET""","""/preloaded-dataanalysis/$jobid<[^/]+>""","""controllers.DAcontroller.initPreloadedDA(jobid:String)"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil))
   }
}
                    

// @LINE:9
case controllers_Application_renderHelpPage1(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.renderHelpPage(), HandlerDef(this, "controllers.Application", "renderHelpPage", Nil))
   }
}
                    

// @LINE:10
case controllers_Application_renderAboutPage2(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.renderAboutPage(), HandlerDef(this, "controllers.Application", "renderAboutPage", Nil))
   }
}
                    

// @LINE:11
case controllers_Application_renderContactPage3(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.renderContactPage(), HandlerDef(this, "controllers.Application", "renderContactPage", Nil))
   }
}
                    

// @LINE:14
case controllers_IAcontroller_initIAForm4(params) => {
   call { 
        invokeHandler(_root_.controllers.IAcontroller.initIAForm(), HandlerDef(this, "controllers.IAcontroller", "initIAForm", Nil))
   }
}
                    

// @LINE:15
case controllers_IAcontroller_showJob5(params) => {
   call(params.fromPath[String]("jobid", None)) { (jobid) =>
        invokeHandler(_root_.controllers.IAcontroller.showJob(jobid), HandlerDef(this, "controllers.IAcontroller", "showJob", Seq(classOf[String])))
   }
}
                    

// @LINE:16
case controllers_IAcontroller_submit6(params) => {
   call { 
        invokeHandler(_root_.controllers.IAcontroller.submit(), HandlerDef(this, "controllers.IAcontroller", "submit", Nil))
   }
}
                    

// @LINE:20
case controllers_NScontroller_submit7(params) => {
   call { 
        invokeHandler(_root_.controllers.NScontroller.submit(), HandlerDef(this, "controllers.NScontroller", "submit", Nil))
   }
}
                    

// @LINE:21
case controllers_NScontroller_initNSform8(params) => {
   call { 
        invokeHandler(_root_.controllers.NScontroller.initNSform(), HandlerDef(this, "controllers.NScontroller", "initNSform", Nil))
   }
}
                    

// @LINE:22
case controllers_NScontroller_showJob9(params) => {
   call(params.fromPath[String]("jobid", None)) { (jobid) =>
        invokeHandler(_root_.controllers.NScontroller.showJob(jobid), HandlerDef(this, "controllers.NScontroller", "showJob", Seq(classOf[String])))
   }
}
                    

// @LINE:24
case controllers_NScontroller_initPreloadedNSform10(params) => {
   call { 
        invokeHandler(_root_.controllers.NScontroller.initPreloadedNSform(), HandlerDef(this, "controllers.NScontroller", "initPreloadedNSform", Nil))
   }
}
                    

// @LINE:25
case controllers_NScontroller_redirectPreloadedNSform11(params) => {
   call(params.fromPath[String]("jobid", None)) { (jobid) =>
        invokeHandler(_root_.controllers.NScontroller.redirectPreloadedNSform(jobid), HandlerDef(this, "controllers.NScontroller", "redirectPreloadedNSform", Seq(classOf[String])))
   }
}
                    

// @LINE:28
case controllers_DAcontroller_initPreloadedDA12(params) => {
   call(params.fromPath[String]("jobid", None)) { (jobid) =>
        invokeHandler(_root_.controllers.DAcontroller.initPreloadedDA(jobid), HandlerDef(this, "controllers.DAcontroller", "initPreloadedDA", Seq(classOf[String])))
   }
}
                    

// @LINE:31
case controllers_Assets_at13(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    
}
    
}
                