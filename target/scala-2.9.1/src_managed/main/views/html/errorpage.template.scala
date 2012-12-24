
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
object errorpage extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(requested: String, errorcode: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.40*/("""


"""),_display_(Seq[Any](/*4.2*/main(title="Error", nav="errorpage")/*4.38*/ {_display_(Seq[Any](format.raw/*4.40*/("""
	<center style="padding-top:70px">
		
		<h1 style="font-size:150px; line-height:200px">
		<i class="icon-exclamation-sign" style="color:#B94A48; font-size:150px"></i>"""),_display_(Seq[Any](/*8.80*/if(requested == "")/*8.99*/{_display_(Seq[Any](format.raw/*8.100*/("""404""")))}/*8.104*/else/*8.108*/{_display_(Seq[Any](_display_(Seq[Any](/*8.110*/errorcode))))})),format.raw/*8.120*/("""
		
		<h2 style="margin-top:-30px">"""),_display_(Seq[Any](/*10.33*/if(requested == "")/*10.52*/{_display_(Seq[Any](format.raw/*10.53*/("""Not found""")))}/*10.63*/else/*10.67*/{_display_(Seq[Any](_display_(Seq[Any](/*10.69*/requested))))})),format.raw/*10.79*/("""</h2>
		</h1>
		
		<hr> 
		
		<a onclick="history.go(-1)" class="btn btn-large">
			<i class="icon-arrow-left"></i> Return to previous page
		</a>
		<a href="/" onclick="#" class="btn btn-large">
			<i class="icon-home"></i> Go home
		</a>
	</center>
""")))})))}
    }
    
    def render(requested:String,errorcode:String) = apply(requested,errorcode)
    
    def f:((String,String) => play.api.templates.Html) = (requested,errorcode) => apply(requested,errorcode)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Dec 20 17:12:33 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/errorpage.scala.html
                    HASH: 5e4f3442331dd4e65b9b884ebd8edeccab29c2ac
                    MATRIX: 766->1|881->39|919->43|963->79|1002->81|1205->249|1232->268|1271->269|1294->273|1307->277|1355->279|1391->289|1463->325|1491->344|1530->345|1559->355|1572->359|1620->361|1656->371
                    LINES: 27->1|30->1|33->4|33->4|33->4|37->8|37->8|37->8|37->8|37->8|37->8|37->8|39->10|39->10|39->10|39->10|39->10|39->10|39->10
                    -- GENERATED --
                */
            