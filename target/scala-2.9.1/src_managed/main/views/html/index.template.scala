
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
			<div class="item">
				<img src="http://placehold.it/1000x350">
			</div>
			<div class="item active">
				<img src="http://placehold.it/1000x350/222222/fff">
			</div>
			<div class="item">
				<img src="http://placehold.it/1000x350/0081CC/fff">
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
	
    <h2 style="color:red">UNDER DEVELOPMENT: DO NOT USE</h2>
    
""")))})))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/index.scala.html
                    HASH: d54603a0ea18c7c4814d71df44b78924d90c2fba
                    MATRIX: 828->2|858->24|897->26
                    LINES: 30->2|30->2|30->2
                    -- GENERATED --
                */
            