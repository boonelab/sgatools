
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.api.templates.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import com.avaje.ebean._
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object errorpage extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(requested: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.21*/("""


"""),_display_(Seq[Any](/*4.2*/main(title="Error", nav="errorpage")/*4.38*/ {_display_(Seq[Any](format.raw/*4.40*/("""
	<center>
		<h1 class="text-error">404</h1>
		<h4>"""),_display_(Seq[Any](/*7.8*/if(requested == "")/*7.27*/{_display_(Seq[Any](format.raw/*7.28*/("""Not found""")))}/*7.38*/else/*7.42*/{_display_(Seq[Any](_display_(Seq[Any](/*7.44*/requested))))})),format.raw/*7.54*/("""</h4>
	</center>
""")))})))}
    }
    
    def render(requested:String) = apply(requested)
    
    def f:((String) => play.api.templates.Html) = (requested) => apply(requested)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/errorpage.scala.html
                    HASH: 217bee900871676bb51b8644d336b99d64694c81
                    MATRIX: 759->1|855->20|893->24|937->60|976->62|1062->114|1089->133|1127->134|1155->144|1167->148|1214->150|1249->160
                    LINES: 27->1|30->1|33->4|33->4|33->4|36->7|36->7|36->7|36->7|36->7|36->7|36->7
                    -- GENERATED --
                */
            