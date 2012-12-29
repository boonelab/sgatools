
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
		<link href=""""),_display_(Seq[Any](/*17.16*/routes/*17.22*/.Assets.at("stylesheets/docs.css"))),format.raw/*17.56*/("""" rel="stylesheet">
    
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	
		<script type="text/javascript" src=""""),_display_(Seq[Any](/*24.40*/routes/*24.46*/.Assets.at("javascripts/sgatools.js"))),format.raw/*24.83*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*25.17*/routes/*25.23*/.Assets.at("javascripts/jquery-1.7.1.min.js"))),format.raw/*25.68*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*26.17*/routes/*26.23*/.Assets.at("javascripts/jquery.cookie.js"))),format.raw/*26.65*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*27.17*/routes/*27.23*/.Assets.at("external/bootstrap-toggle/js/jquery.toggle.buttons.js"))),format.raw/*27.90*/(""""></script>
		<link href=""""),_display_(Seq[Any](/*28.16*/routes/*28.22*/.Assets.at("external/bootstrap-toggle/stylesheets/bootstrap-toggle-buttons.css"))),format.raw/*28.102*/("""" rel="stylesheet">
	
		
		<script>
		$(document).ready(function()"""),format.raw("""{"""),format.raw/*32.32*/("""
			$('.toggle-button').toggleButtons();
		"""),format.raw("""}"""),format.raw/*34.4*/(""");
		</script>
	</head>
	
<body>
	
    <!--Stickyfooter: Wrap all page content here -->
    <div id="wrap">
		<!-- Navigation -->
		<div class="navbar navbar-fixed-top"> 
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href=""""),_display_(Seq[Any](/*46.30*/routes/*46.36*/.Application.index())),format.raw/*46.56*/("""">
						<img src=""""),_display_(Seq[Any](/*47.18*/routes/*47.24*/.Assets.at("images/sgaicon.png"))),format.raw/*47.56*/("""" style="height:32px;margin-top:-7px;" alt="SGAtools icon">
						<b class="heavy">SGA</b>tools
					</a>
					<div class="nav-collapse">
						<ul class="nav">
							<li class=""""),_display_(Seq[Any](/*52.20*/("active".when(nav == "ia")))),format.raw/*52.48*/(""""><a href=""""),_display_(Seq[Any](/*52.60*/routes/*52.66*/.IAcontroller.initIAForm())),format.raw/*52.92*/("""">Image analysis</a></li>
							<li class=""""),_display_(Seq[Any](/*53.20*/("active".when(nav == "ns")))),format.raw/*53.48*/(""""><a href=""""),_display_(Seq[Any](/*53.60*/routes/*53.66*/.NScontroller.initNSform())),format.raw/*53.92*/("""">Normalization & Scoring</a></li>
							<li class=""""),_display_(Seq[Any](/*54.20*/("active".when(nav == "da")))),format.raw/*54.48*/(""""><a href="#">Data analysis</a></li>
							
						</ul>
						<ul class="nav pull-right">
							<li class=""""),_display_(Seq[Any](/*58.20*/("active".when(nav == "hp")))),format.raw/*58.48*/(""""><a href=""""),_display_(Seq[Any](/*58.60*/routes/*58.66*/.Application.renderHelpPage())),format.raw/*58.95*/("""">Help</a></li>
							<li class=""""),_display_(Seq[Any](/*59.20*/("active".when(nav == "cn")))),format.raw/*59.48*/(""""><a href=""""),_display_(Seq[Any](/*59.60*/routes/*59.66*/.Application.renderContactPage())),format.raw/*59.98*/("""">Contact</a></li>
							<li class=""""),_display_(Seq[Any](/*60.20*/("active".when(nav == "ab")))),format.raw/*60.48*/(""""><a href=""""),_display_(Seq[Any](/*60.60*/routes/*60.66*/.Application.renderAboutPage())),format.raw/*60.96*/("""">About</a></li>
						</ul>
					</div><!--/.nav-collapse -->
				</div><!-- end .container -->
			</div><!-- end .navbar-inner -->
		</div><!-- end .navbar -->

		<!-- Content -->
		<div class="container" style="padding-top:90px">
			"""),_display_(Seq[Any](/*69.5*/content)),format.raw/*69.12*/("""
			
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
	"""),_display_(Seq[Any](/*84.3*/modal("helpModal", "helpModalHeading", "helpModalBody"))),format.raw/*84.58*/("""
	"""),_display_(Seq[Any](/*85.3*/modal("arrayDefModal", "arrayDefModalHeading", "arrayDefModalBody"))),format.raw/*85.70*/("""
	
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*88.39*/routes/*88.45*/.Assets.at("javascripts/bootstrap.min.js"))),format.raw/*88.87*/(""""></script>
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*89.39*/routes/*89.45*/.Assets.at("external/jasny-extensions/js/bootstrap-fileupload.js"))),format.raw/*89.111*/(""""></script>
	<script type="text/javascript">
			$('#helpModal').modal("""),format.raw("""{"""),format.raw/*91.27*/("""show:false"""),format.raw("""}"""),format.raw/*91.38*/(""");
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
                    DATE: Sat Dec 29 05:09:41 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/main.scala.html
                    HASH: 4e953cd3f96bcb1787afc47a5870713f7da3fff3
                    MATRIX: 766->1|886->44|1000->123|1026->128|1504->570|1519->576|1576->611|1647->646|1662->652|1751->719|1822->754|1837->760|1915->816|1986->851|2001->857|2057->891|2331->1129|2346->1135|2405->1172|2469->1200|2484->1206|2551->1251|2615->1279|2630->1285|2694->1327|2758->1355|2773->1361|2862->1428|2925->1455|2940->1461|3043->1541|3157->1608|3247->1652|3541->1910|3556->1916|3598->1936|3654->1956|3669->1962|3723->1994|3939->2174|3989->2202|4037->2214|4052->2220|4100->2246|4181->2291|4231->2319|4279->2331|4294->2337|4342->2363|4432->2417|4482->2445|4628->2555|4678->2583|4726->2595|4741->2601|4792->2630|4863->2665|4913->2693|4961->2705|4976->2711|5030->2743|5104->2781|5154->2809|5202->2821|5217->2827|5269->2857|5541->3094|5570->3101|6069->3565|6146->3620|6184->3623|6273->3690|6419->3800|6434->3806|6498->3848|6584->3898|6599->3904|6688->3970|6806->4041|6864->4052
                    LINES: 27->1|30->1|36->7|36->7|43->14|43->14|43->14|44->15|44->15|44->15|45->16|45->16|45->16|46->17|46->17|46->17|53->24|53->24|53->24|54->25|54->25|54->25|55->26|55->26|55->26|56->27|56->27|56->27|57->28|57->28|57->28|61->32|63->34|75->46|75->46|75->46|76->47|76->47|76->47|81->52|81->52|81->52|81->52|81->52|82->53|82->53|82->53|82->53|82->53|83->54|83->54|87->58|87->58|87->58|87->58|87->58|88->59|88->59|88->59|88->59|88->59|89->60|89->60|89->60|89->60|89->60|98->69|98->69|113->84|113->84|114->85|114->85|117->88|117->88|117->88|118->89|118->89|118->89|120->91|120->91
                    -- GENERATED --
                */
            