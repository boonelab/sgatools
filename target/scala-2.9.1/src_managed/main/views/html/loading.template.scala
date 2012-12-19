
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
object loading extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(message: String, note: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.33*/("""

<link href=""""),_display_(Seq[Any](/*3.14*/routes/*3.20*/.Assets.at("stylesheets/loading.css"))),format.raw/*3.57*/("""" rel="stylesheet">
<script>
 window.onload=function()"""),format.raw("""{"""),format.raw/*5.27*/("""
         var dotspan = document.getElementById('dots');
         window.setInterval(function()"""),format.raw("""{"""),format.raw/*7.40*/("""
            if(dotspan.textContent == '.....')"""),format.raw("""{"""),format.raw/*8.48*/("""
               dotspan.textContent = '.';
            """),format.raw("""}"""),format.raw/*10.14*/("""
            else"""),format.raw("""{"""),format.raw/*11.18*/("""
               dotspan.textContent += '.';
            """),format.raw("""}"""),format.raw/*13.14*/("""
         """),format.raw("""}"""),format.raw/*14.11*/(""", 1000);
      """),format.raw("""}"""),format.raw/*15.8*/("""
</script>
<center>
	
	
	<div class="loader-container" style="margin-left:180px; margin-top:90px">
	<ul id="transit" >
		<!-- sphere effect -->
	    <li><div id="layer1" class="sphere"></div> </li>
	    <li><div id="layer2" class="sphere"></div></li>
	    <li><div id="layer3" class="sphere"></div></li>
	    <li><div id="layer4" class="sphere"></div></li>
	    <li><div id="layer5" class="sphere"></div></li>
		
	</ul>​
	</div>
	
	<h3>"""),_display_(Seq[Any](/*32.7*/message)),format.raw/*32.14*/(""" <span id="dots">.</span> </h3><h4>"""),_display_(Seq[Any](/*32.50*/note)),format.raw/*32.54*/("""</h4>
</center>
"""))}
    }
    
    def render(message:String,note:String) = apply(message,note)
    
    def f:((String,String) => play.api.templates.Html) = (message,note) => apply(message,note)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/loading.scala.html
                    HASH: 83b46642dddd92d59fb4ab9af965dbbd4ca1bb2a
                    MATRIX: 764->1|872->32|922->47|936->53|994->90|1095->145|1237->241|1331->289|1434->345|1499->363|1603->420|1661->431|1723->447|2195->884|2224->891|2296->927|2322->931
                    LINES: 27->1|30->1|32->3|32->3|32->3|34->5|36->7|37->8|39->10|40->11|42->13|43->14|44->15|61->32|61->32|61->32|61->32
                    -- GENERATED --
                */
            