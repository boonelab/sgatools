
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
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply():play.api.templates.Html = {
        _display_ {

Seq[Any](_display_(Seq[Any](/*2.2*/main("Home", nav="hm")/*2.24*/ {_display_(Seq[Any](format.raw/*2.26*/("""
   	
	<!-- Carousel -->	
	<div id="carousel" class="carousel slide" rel="carousel">
		<!-- Carousel items -->				
		<div class="carousel-inner">
			<!--<div class="item active">
				<center>
				<div class="brand" style="width:1000px; height:200px; font-size:80px; padding-top:65px; color: #222">
					<img src=""""),_display_(Seq[Any](/*11.17*/routes/*11.23*/.Assets.at("images/sgaicon.png"))),format.raw/*11.55*/("""" style="height:75px;margin-top:-15px;">
					<b class="heavy">SGA</b>tools<br>
					<div style="font-size:30px">Tools for the analysis and visualization of genetic screens</div>
					
				</div>
				</center>

			</div>-->
			<div class="item active">
				<img src=""""),_display_(Seq[Any](/*20.16*/routes/*20.22*/.Assets.at("images/slider/ia_cover.jpg"))),format.raw/*20.62*/("""">
				<div class="carousel-caption">
                      			<h4>Image analysis</h4>
                      			<p>Easily and rapidly quantify colony sizes through grid fitting.</p>
                    		</div>	
			</div>
			<div class="item">
				<img src=""""),_display_(Seq[Any](/*27.16*/routes/*27.22*/.Assets.at("images/slider/ns_cover.jpg"))),format.raw/*27.62*/("""">
				<div class="carousel-caption">
                      			<h4>Normalization</h4>
                      			<p>Remove unwanted nutrition and image-based effects and apply filters to enhance the quality of your data.</p>
                    		</div>	
			</div>
			<div class="item">
				<img src=""""),_display_(Seq[Any](/*34.16*/routes/*34.22*/.Assets.at("images/slider/da_cover.png"))),format.raw/*34.62*/("""">
				<div class="carousel-caption">
                      			<h4>Data analysis</h4>
                      			<p>Instant and interactive feedback of your analysis, provided straight through your browser.</p>
                    		</div>	
			</div>
			
		</div>
		<!-- Carousel navigation -->
		<a class="carousel-control left" href="#carousel" data-slide="prev">‹</a>
		<a class="carousel-control right" href="#carousel" data-slide="next">›</a>
	</div>
	
	<hr>

	<div class="row">
		<div class="span4 home-columns" style="margin-bottom: 20px;">
			<i class="icon-camera pull-left"></i>
			<div class="content">
				<h2>Image analysis</h2>
				<p>Utilizes the image analysis software, <a href="http://sourceforge.net/projects/ht-col-measurer/">HT colony grid analyzer</a> to determine and provide accurate pixel colony sizes. See details of the software here</p>
			</div>
		</div>
		<div class="span4 home-columns" style="margin-bottom: 20px;">
			<i class="icon-bar-chart pull-left"></i>
			<div class="content">
				<h2>Normalization</h2>
				<p>Comprehensive filtering and reduction of several plate/experimental artifacts to drastically enhance the quality of your data and allow for further processing</p>
			</div>
		</div>
		<div class="span4 home-columns" style="margin-bottom: 20px;">
			<i class="icon-tasks pull-left"></i>
			<div class="content">
				<h2>Scoring</h2>
				<p>Use control screens or single mutant fitness data to score normalized colony sizes allowing you to identify biologically meaningful genetic interactions</p>
			</div>
		</div>
		
	</div>
	
    
""")))})))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Dec 24 12:47:57 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/index.scala.html
                    HASH: 1c183b85f5b293bfcc2fd1fc9a73ad099f6cc647
                    MATRIX: 828->2|858->24|897->26|1247->340|1262->346|1316->378|1620->646|1635->652|1697->692|1992->951|2007->957|2069->997|2405->1297|2420->1303|2482->1343
                    LINES: 30->2|30->2|30->2|39->11|39->11|39->11|48->20|48->20|48->20|55->27|55->27|55->27|62->34|62->34|62->34
                    -- GENERATED --
                */
            