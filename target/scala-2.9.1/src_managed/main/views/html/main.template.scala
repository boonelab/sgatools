
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
object main extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[String,String,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String, nav: String)(content: Html):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.45*/("""

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>SGAtools | """),_display_(Seq[Any](/*7.22*/title)),format.raw/*7.27*/("""</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Tools for image processing, normalizing and scoring Synthetic Genetic Array screens">
		<meta name="author" content="Omar Wagih">
		
		<link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700,400italic,700italic|Open+Sans:600,700,800,300,400' rel='stylesheet' type='text/css'>
		<!-- Custom css -->
		<link href=""""),_display_(Seq[Any](/*14.16*/routes/*14.22*/.Assets.at("stylesheets/style.css"))),format.raw/*14.57*/("""" rel="stylesheet">
		<link href=""""),_display_(Seq[Any](/*15.16*/routes/*15.22*/.Assets.at("external/jasny-extensions/css/jasny-bootstrap.min.css"))),format.raw/*15.89*/("""" rel="stylesheet">
		<link href=""""),_display_(Seq[Any](/*16.16*/routes/*16.22*/.Assets.at("external/font-awesome/css/font-awesome.css"))),format.raw/*16.78*/("""" rel="stylesheet">
		
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	
		<script src=""""),_display_(Seq[Any](/*23.17*/routes/*23.23*/.Assets.at("javascripts/jquery-1.7.1.min.js"))),format.raw/*23.68*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*24.17*/routes/*24.23*/.Assets.at("javascripts/jquery.cookie.js"))),format.raw/*24.65*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*25.17*/routes/*25.23*/.Assets.at("external/bootstrap-toggle/js/jquery.toggle.buttons.js"))),format.raw/*25.90*/(""""></script>
		<link href=""""),_display_(Seq[Any](/*26.16*/routes/*26.22*/.Assets.at("external/bootstrap-toggle/stylesheets/bootstrap-toggle-buttons.css"))),format.raw/*26.102*/("""" rel="stylesheet">
	
		
		<script>
		$(document).ready(function()"""),format.raw("""{"""),format.raw/*30.32*/("""
			$('.toggle-button').toggleButtons();
		"""),format.raw("""}"""),format.raw/*32.4*/(""");
		</script>
	</head>
	
<body>
	
    <!--Stickyfooter: Wrap all page content here -->
    <div id="wrap">
		<!-- Navigation -->
		<div class="navbar navbar-fixed-top"> 
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href=""""),_display_(Seq[Any](/*44.30*/routes/*44.36*/.Application.index())),format.raw/*44.56*/("""">
						<img src=""""),_display_(Seq[Any](/*45.18*/routes/*45.24*/.Assets.at("images/sgaicon.png"))),format.raw/*45.56*/("""" style="height:32px;margin-top:-7px;">
						<b class="heavy">SGA</b>tools
					</a>
					<div class="nav-collapse">
						<ul class="nav">
							<li class=""""),_display_(Seq[Any](/*50.20*/("active".when(nav == "ia")))),format.raw/*50.48*/(""""><a href=""""),_display_(Seq[Any](/*50.60*/routes/*50.66*/.IAcontroller.initIAForm())),format.raw/*50.92*/("""">Image analysis</a></li>
							<li class=""""),_display_(Seq[Any](/*51.20*/("active".when(nav == "ns")))),format.raw/*51.48*/(""""><a href=""""),_display_(Seq[Any](/*51.60*/routes/*51.66*/.NScontroller.initNSform())),format.raw/*51.92*/("""">Normalization & Scoring</a></li>
							<li class=""""),_display_(Seq[Any](/*52.20*/("active".when(nav == "da")))),format.raw/*52.48*/(""""><a href="#">Data analysis</a></li>
							
						</ul>
						<ul class="nav pull-right">
							<li class=""""),_display_(Seq[Any](/*56.20*/("active".when(nav == "hp")))),format.raw/*56.48*/(""""><a href=""""),_display_(Seq[Any](/*56.60*/routes/*56.66*/.Application.renderHelpPage())),format.raw/*56.95*/("""">Help</a></li>
							<li class=""""),_display_(Seq[Any](/*57.20*/("active".when(nav == "cn")))),format.raw/*57.48*/(""""><a href=""""),_display_(Seq[Any](/*57.60*/routes/*57.66*/.Application.renderContactPage())),format.raw/*57.98*/("""">Contact</a></li>
							<li class=""""),_display_(Seq[Any](/*58.20*/("active".when(nav == "ab")))),format.raw/*58.48*/(""""><a href=""""),_display_(Seq[Any](/*58.60*/routes/*58.66*/.Application.renderAboutPage())),format.raw/*58.96*/("""">About</a></li>
						</ul>
					</div><!--/.nav-collapse -->
				</div><!-- end .container -->
			</div><!-- end .navbar-inner -->
		</div><!-- end .navbar -->

		<!-- Content -->
		<div class="container" style="padding-top:90px">
			"""),_display_(Seq[Any](/*67.5*/content)),format.raw/*67.12*/("""
			
			<div id="push"></div>
	    </div>
	</div> <!--end wrap-->    
    
    
	<!-- Footer -->
	<div id="footer">
      <div class="container">
        <center><p class="muted credit">© 2012 Boone lab ⋅ <a href="#">Issue tracker</a> ⋅ <a href="#">Download</a></p></centre>
      </div>
    </div>
    
	<!-- Javascript -->
	"""),_display_(Seq[Any](/*82.3*/modal("helpModal", "helpModalHeading", "helpModalBody"))),format.raw/*82.58*/("""
	"""),_display_(Seq[Any](/*83.3*/modal("arrayDefModal", "arrayDefModalHeading", "arrayDefModalBody"))),format.raw/*83.70*/("""
	
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*86.39*/routes/*86.45*/.Assets.at("javascripts/sgatools.js"))),format.raw/*86.82*/(""""></script>
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*87.39*/routes/*87.45*/.Assets.at("javascripts/bootstrap.min.js"))),format.raw/*87.87*/(""""></script>
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*88.39*/routes/*88.45*/.Assets.at("external/jasny-extensions/js/bootstrap-fileupload.js"))),format.raw/*88.111*/(""""></script>
	<script type="text/javascript">
			$('#helpModal').modal("""),format.raw("""{"""),format.raw/*90.27*/("""show:false"""),format.raw("""}"""),format.raw/*90.38*/(""");
	</script>
	
</body>
</html>
"""))}
    }
    
    def render(title:String,nav:String,content:Html) = apply(title,nav)(content)
    
    def f:((String,String) => (Html) => play.api.templates.Html) = (title,nav) => (content) => apply(title,nav)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/main.scala.html
                    HASH: 1e4f61f339719d47ae9eed92832e89c070e356eb
                    MATRIX: 766->1|886->44|1000->123|1026->128|1504->570|1519->576|1576->611|1647->646|1662->652|1751->719|1822->754|1837->760|1915->816|2164->1029|2179->1035|2246->1080|2310->1108|2325->1114|2389->1156|2453->1184|2468->1190|2557->1257|2620->1284|2635->1290|2738->1370|2852->1437|2942->1481|3236->1739|3251->1745|3293->1765|3349->1785|3364->1791|3418->1823|3614->1983|3664->2011|3712->2023|3727->2029|3775->2055|3856->2100|3906->2128|3954->2140|3969->2146|4017->2172|4107->2226|4157->2254|4303->2364|4353->2392|4401->2404|4416->2410|4467->2439|4538->2474|4588->2502|4636->2514|4651->2520|4705->2552|4779->2590|4829->2618|4877->2630|4892->2636|4944->2666|5216->2903|5245->2910|5607->3237|5684->3292|5722->3295|5811->3362|5957->3472|5972->3478|6031->3515|6117->3565|6132->3571|6196->3613|6282->3663|6297->3669|6386->3735|6504->3806|6562->3817
                    LINES: 27->1|30->1|36->7|36->7|43->14|43->14|43->14|44->15|44->15|44->15|45->16|45->16|45->16|52->23|52->23|52->23|53->24|53->24|53->24|54->25|54->25|54->25|55->26|55->26|55->26|59->30|61->32|73->44|73->44|73->44|74->45|74->45|74->45|79->50|79->50|79->50|79->50|79->50|80->51|80->51|80->51|80->51|80->51|81->52|81->52|85->56|85->56|85->56|85->56|85->56|86->57|86->57|86->57|86->57|86->57|87->58|87->58|87->58|87->58|87->58|96->67|96->67|111->82|111->82|112->83|112->83|115->86|115->86|115->86|116->87|116->87|116->87|117->88|117->88|117->88|119->90|119->90
                    -- GENERATED --
                */
            