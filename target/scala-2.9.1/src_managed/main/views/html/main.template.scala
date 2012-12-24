
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
	
		<script type="text/javascript" src=""""),_display_(Seq[Any](/*23.40*/routes/*23.46*/.Assets.at("javascripts/sgatools.js"))),format.raw/*23.83*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*24.17*/routes/*24.23*/.Assets.at("javascripts/jquery-1.7.1.min.js"))),format.raw/*24.68*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*25.17*/routes/*25.23*/.Assets.at("javascripts/jquery.cookie.js"))),format.raw/*25.65*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*26.17*/routes/*26.23*/.Assets.at("external/bootstrap-toggle/js/jquery.toggle.buttons.js"))),format.raw/*26.90*/(""""></script>
		<link href=""""),_display_(Seq[Any](/*27.16*/routes/*27.22*/.Assets.at("external/bootstrap-toggle/stylesheets/bootstrap-toggle-buttons.css"))),format.raw/*27.102*/("""" rel="stylesheet">
	
		
		<script>
		$(document).ready(function()"""),format.raw("""{"""),format.raw/*31.32*/("""
			$('.toggle-button').toggleButtons();
		"""),format.raw("""}"""),format.raw/*33.4*/(""");
		</script>
	</head>
	
<body>
	
    <!--Stickyfooter: Wrap all page content here -->
    <div id="wrap">
		<!-- Navigation -->
		<div class="navbar navbar-fixed-top"> 
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href=""""),_display_(Seq[Any](/*45.30*/routes/*45.36*/.Application.index())),format.raw/*45.56*/("""">
						<img src=""""),_display_(Seq[Any](/*46.18*/routes/*46.24*/.Assets.at("images/sgaicon.png"))),format.raw/*46.56*/("""" style="height:32px;margin-top:-7px;">
						<b class="heavy">SGA</b>tools
					</a>
					<div class="nav-collapse">
						<ul class="nav">
							<li class=""""),_display_(Seq[Any](/*51.20*/("active".when(nav == "ia")))),format.raw/*51.48*/(""""><a href=""""),_display_(Seq[Any](/*51.60*/routes/*51.66*/.IAcontroller.initIAForm())),format.raw/*51.92*/("""">Image analysis</a></li>
							<li class=""""),_display_(Seq[Any](/*52.20*/("active".when(nav == "ns")))),format.raw/*52.48*/(""""><a href=""""),_display_(Seq[Any](/*52.60*/routes/*52.66*/.NScontroller.initNSform())),format.raw/*52.92*/("""">Normalization & Scoring</a></li>
							<li class=""""),_display_(Seq[Any](/*53.20*/("active".when(nav == "da")))),format.raw/*53.48*/(""""><a href="#">Data analysis</a></li>
							
						</ul>
						<ul class="nav pull-right">
							<li class=""""),_display_(Seq[Any](/*57.20*/("active".when(nav == "hp")))),format.raw/*57.48*/(""""><a href=""""),_display_(Seq[Any](/*57.60*/routes/*57.66*/.Application.renderHelpPage())),format.raw/*57.95*/("""">Help</a></li>
							<li class=""""),_display_(Seq[Any](/*58.20*/("active".when(nav == "cn")))),format.raw/*58.48*/(""""><a href=""""),_display_(Seq[Any](/*58.60*/routes/*58.66*/.Application.renderContactPage())),format.raw/*58.98*/("""">Contact</a></li>
							<li class=""""),_display_(Seq[Any](/*59.20*/("active".when(nav == "ab")))),format.raw/*59.48*/(""""><a href=""""),_display_(Seq[Any](/*59.60*/routes/*59.66*/.Application.renderAboutPage())),format.raw/*59.96*/("""">About</a></li>
						</ul>
					</div><!--/.nav-collapse -->
				</div><!-- end .container -->
			</div><!-- end .navbar-inner -->
		</div><!-- end .navbar -->

		<!-- Content -->
		<div class="container" style="padding-top:90px">
			"""),_display_(Seq[Any](/*68.5*/content)),format.raw/*68.12*/("""
			
			<div id="push"></div>
	    </div>
	</div> <!--end wrap-->    
    
    
	<!-- Footer -->
	<div id="footer">
      <div class="container">
        <center><p class="muted credit">© 2012 Boone lab ⋅ <a target="_blank" href="https://github.com/boonelab/sgatools/issues">Issue tracker</a> ⋅ <a href="https://github.com/boonelab/sgatools/tree/master/public/SGAtools" target="_blank">Download</a></p></centre>
      </div>
    </div>
    
	<!-- Javascript -->
	"""),_display_(Seq[Any](/*83.3*/modal("helpModal", "helpModalHeading", "helpModalBody"))),format.raw/*83.58*/("""
	"""),_display_(Seq[Any](/*84.3*/modal("arrayDefModal", "arrayDefModalHeading", "arrayDefModalBody"))),format.raw/*84.70*/("""
	
	<!-- Placed at the end of the document so the pages load faster -->
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
                    DATE: Mon Dec 24 02:28:27 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/main.scala.html
                    HASH: 1915c0fb7d145b62b3a178da48c2fe556464cffe
                    MATRIX: 766->1|886->44|1000->123|1026->128|1504->570|1519->576|1576->611|1647->646|1662->652|1751->719|1822->754|1837->760|1915->816|2187->1052|2202->1058|2261->1095|2325->1123|2340->1129|2407->1174|2471->1202|2486->1208|2550->1250|2614->1278|2629->1284|2718->1351|2781->1378|2796->1384|2899->1464|3013->1531|3103->1575|3397->1833|3412->1839|3454->1859|3510->1879|3525->1885|3579->1917|3775->2077|3825->2105|3873->2117|3888->2123|3936->2149|4017->2194|4067->2222|4115->2234|4130->2240|4178->2266|4268->2320|4318->2348|4464->2458|4514->2486|4562->2498|4577->2504|4628->2533|4699->2568|4749->2596|4797->2608|4812->2614|4866->2646|4940->2684|4990->2712|5038->2724|5053->2730|5105->2760|5377->2997|5406->3004|5905->3468|5982->3523|6020->3526|6109->3593|6255->3703|6270->3709|6334->3751|6420->3801|6435->3807|6524->3873|6642->3944|6700->3955
                    LINES: 27->1|30->1|36->7|36->7|43->14|43->14|43->14|44->15|44->15|44->15|45->16|45->16|45->16|52->23|52->23|52->23|53->24|53->24|53->24|54->25|54->25|54->25|55->26|55->26|55->26|56->27|56->27|56->27|60->31|62->33|74->45|74->45|74->45|75->46|75->46|75->46|80->51|80->51|80->51|80->51|80->51|81->52|81->52|81->52|81->52|81->52|82->53|82->53|86->57|86->57|86->57|86->57|86->57|87->58|87->58|87->58|87->58|87->58|88->59|88->59|88->59|88->59|88->59|97->68|97->68|112->83|112->83|113->84|113->84|116->87|116->87|116->87|117->88|117->88|117->88|119->90|119->90
                    -- GENERATED --
                */
            