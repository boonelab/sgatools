
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
object about extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply():play.api.templates.Html = {
        _display_ {

Seq[Any](_display_(Seq[Any](/*2.2*/main("About", nav="ab")/*2.25*/ {_display_(Seq[Any](format.raw/*2.27*/("""
   <h2>About</h2>
   <hr>
    <p>
    Screening for combinations of mutations that have an unexpected effect on phenotype is 
    a powerful approach that has revealed much about the genetic landscape of cells. 
    These high throughput experiments quantify colony size as a measure of fitness, and produce raw data in form of plate images,
    which require processing and statistical analyses to obtain biological insights. 
    While large international consortia have dedicated teams for this purpose, single wetlabs currently do not have 
    a user-friendly solution for dealing with such data.
   </p>
   <p>
   SGAtools offers a single stop solution for analysing data from genetic screens. 
   There are three steps to the analysis pipeline, each of which can also be run separately from the rest. 
   First, images of plates with colonies are processed to give quantified colony sizes for the screen. 
   Next, the colony sizes are normalised and filtered within plates, taking into account position effects and other confounding factors. 
   In a case-control scenario, scores are calculated to assess the deviation of the observed data from expected. 
   Finally, the data can be visualised online to give intuition about the summary statistics, genes responsible for the strongest signal. 
   Further analysis functions, such as GO term enrichment and network visualisation, are available via external links.</p>

<p>SGAtools was developed at the Charlie Boone laboratory - Donnelly Centre, University of Toronto</p>
<hr>
<img src="http://placehold.it/200x70&text=UofTLogo">
<img src="http://placehold.it/200x70&text=BoonelabLogo">
<img src="http://placehold.it/200x70&text=DonnellyLogo">
<!--
<strong>Please cite SGAtools as: </strong><br>
<p>Wagih, O., Parts, L., ... , Boone, C. (2012) SGAtools: Tools for image processing, normalizing and scoring Synthetic Genetic Array screens</p>
  -->  
""")))})))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Dec 24 02:37:05 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/about.scala.html
                    HASH: 4228ae00b2a3446ef1c01fe31378c5b6df34cfde
                    MATRIX: 828->2|859->25|898->27
                    LINES: 30->2|30->2|30->2
                    -- GENERATED --
                */
            