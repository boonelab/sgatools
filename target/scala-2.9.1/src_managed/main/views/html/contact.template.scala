
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
object contact extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply():play.api.templates.Html = {
        _display_ {

Seq[Any](_display_(Seq[Any](/*2.2*/main(title="Contact", nav="cn")/*2.33*/ {_display_(Seq[Any](format.raw/*2.35*/("""

<style>
	.contact-thumbnail """),format.raw("""{"""),format.raw/*5.22*/("""
		float: left;
		border: 1px solid #666;
		border-radius: 4px;
		max-width: 80px;
		float: left;
		margin: 0px 8px 0px 0px;
		box-shadow: 0px 1px 3px #999;
	"""),format.raw("""}"""),format.raw/*13.3*/("""
	blockquote """),format.raw("""{"""),format.raw/*14.14*/("""
		border-left: transparent;
	"""),format.raw("""}"""),format.raw/*16.3*/("""
	
	p.position"""),format.raw("""{"""),format.raw/*18.13*/("""
		color: #333;
		font-size: 12px;
		line-height: 20px;
	"""),format.raw("""}"""),format.raw/*22.3*/("""
	
	p.quote """),format.raw("""{"""),format.raw/*24.11*/("""
		font-weight:350;
	"""),format.raw("""}"""),format.raw/*26.3*/("""
</style>
	
	
Feedback is greatly appreciated. If you have any questions, comments or are experiencing issues with processing your data please contact:
<br><br>

	<table class="span12">
		<tr>
			<td>
				<div>
					<img class="thumbnail contact-thumbnail" src=""""),_display_(Seq[Any](/*37.53*/routes/*37.59*/.Assets.at("images/people/wagih.jpeg"))),format.raw/*37.97*/("""" alt="Omar Wagih">
		
					<blockquote>
						<p class="quote">Omar Wagih</p>
						<p class="position">Software Developer</p>
						<small><a href="mailto:omar.wagih"""),format.raw("""{"""),format.raw/*42.41*/("""AT"""),format.raw("""}"""),format.raw/*42.44*/("""utoronto.ca">omar.wagih@utoronto.ca</a></small>
					</blockquote>
				</div>
			</td>
			<td>
				<div>
					<img class="thumbnail contact-thumbnail" src=""""),_display_(Seq[Any](/*48.53*/routes/*48.59*/.Assets.at("images/people/parts.jpeg"))),format.raw/*48.97*/("""" alt="Leopold Parts" style=" ">
					<blockquote>
						<p class="quote">Leopold Parts</p>
						<p class="position">Research Associate</p>
						<small><a href="mailto:leopold.parts"""),format.raw("""{"""),format.raw/*52.44*/("""AT"""),format.raw("""}"""),format.raw/*52.47*/("""gmail.com">leopold.parts@gmail.com</a></small>
					</blockquote>
				</div>
			</td>
			<td>
				<div>
					<img class="thumbnail contact-thumbnail" src=""""),_display_(Seq[Any](/*58.53*/routes/*58.59*/.Assets.at("images/people/costanzo.jpeg"))),format.raw/*58.100*/("""" alt="Michael Costanzo" style=" ">
					<blockquote>
						<p class="quote">Michael Costanzo</p>
						<p class="position">Research Associate</p>
						<small><a href="mailto:michael.costanzo@utoronto.ca">michael.costanzo@utoronto.ca</a></small>
					</blockquote>
				</div>
			</td>
		</tr>
		<tr><td style="padding-top:20px"></td></tr>
		
		<tr>
			<td>
				<div>
					<img class="thumbnail contact-thumbnail" src=""""),_display_(Seq[Any](/*72.53*/routes/*72.59*/.Assets.at("images/people/baryshnikova.jpeg"))),format.raw/*72.104*/("""" alt="Anastasia Baryshnikova" style=" ">
					<blockquote>
						<p class="quote">Anastasia Baryshnikova</p>
						<p class="position">Graduate Student</p>
						<small><a href="mailto:a.baryshnikova@utoronto.ca">a.baryshnikova@utoronto.ca</a></small>
					</blockquote>
				</div>
			</td>
			<td>
				<div>
					<img class="thumbnail contact-thumbnail" src=""""),_display_(Seq[Any](/*82.53*/routes/*82.59*/.Assets.at("images/people/usaj.jpeg"))),format.raw/*82.96*/("""" alt="Matej Usaj" style=" ">
					<blockquote>
						<p class="quote">Matej Usaj</p>
						<p class="position">Genetic Network Team Technician</p>
						<small><a href="mailto:m.usaj@utoronto.ca">m.usaj@utoronto.ca</a></small>
					</blockquote>
				</div>
			</td>
			<td>
				<div>
					<img class="thumbnail contact-thumbnail" src=""""),_display_(Seq[Any](/*92.53*/routes/*92.59*/.Assets.at("images/people/boone.jpeg"))),format.raw/*92.97*/("""" alt="Charlie Boone" style=" ">
					<blockquote>
						<p class="quote">Charlie Boone</p>
						<p class="position">Principal Investigator</p>
						<small><a href="mailto:charlie.boone@utoronto.ca">charlie.boone@utoronto.ca</a></small>
						
					</blockquote>
				</div>
			</td>
		</tr>
	</table>
	

	
	
""")))})))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/contact.scala.html
                    HASH: cb38880ac2f62feb7f0acb273ab7ef3a1455c7ce
                    MATRIX: 830->2|869->33|908->35|985->66|1190->225|1251->239|1328->270|1390->285|1494->343|1554->356|1622->378|1921->641|1936->647|1996->685|2211->853|2261->856|2454->1014|2469->1020|2529->1058|2760->1242|2810->1245|3002->1402|3017->1408|3081->1449|3537->1871|3552->1877|3620->1922|4018->2286|4033->2292|4092->2329|4465->2668|4480->2674|4540->2712
                    LINES: 30->2|30->2|30->2|33->5|41->13|42->14|44->16|46->18|50->22|52->24|54->26|65->37|65->37|65->37|70->42|70->42|76->48|76->48|76->48|80->52|80->52|86->58|86->58|86->58|100->72|100->72|100->72|110->82|110->82|110->82|120->92|120->92|120->92
                    -- GENERATED --
                */
            