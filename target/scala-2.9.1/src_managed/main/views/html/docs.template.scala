
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
object docs extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply():play.api.templates.Html = {
        _display_ {

Seq[Any](_display_(Seq[Any](/*2.2*/main("Help", nav="hp")/*2.24*/ {_display_(Seq[Any](format.raw/*2.26*/("""
    
    <!--<h1>Some help goes here</h1>-->
    <br>
    
	<div class="tabbable tabs-right">
      <ul class="nav nav-tabs">
        <li class="active"><a href="#gs" data-toggle="tab">Getting started</a></li>
        <li><a href="#fn" data-toggle="tab">File naming</a></li>
        <li><a href="#ia" data-toggle="tab">Image analysis</a></li>
        <li><a href="#ns" data-toggle="tab">Normalization & Scoring</a></li>
        <li><a href="#da" data-toggle="tab">Data analysis</a></li>
        <li><a href="#fa" data-toggle="tab">FAQs</a></li>
      </ul>
      <div class="tab-content">
        
        <div class="tab-pane active" id="gs">
	       	<div class="page-header">
	        	<h2>Getting started</h2>
	        </div>
        </div>
        
        <div class="tab-pane" id="fn">
	        <div class="page-header">
	        	<h2>File naming</h2>
	        </div>
	        
	        <div id="file-naming-help">
	        	<p>
	        	In order to properly score your data and utilize array layout files, additional information must be supplied via the file name of the image or dat file you are using.
	        	This information includes
	        	
	        	<ul>
	        		<li>Query name / ORF</li>
	        		<li>Screen type ― control vs non-control</li>
	        		<li>Array plate id </li>
	        	</ul>
	        	
	        	This information is supplemented through the file name in the following format:
	        	
	        	</p>
	        	
	        	<pre>"""),format.raw("""{"""),format.raw/*44.17*/("""username"""),format.raw("""}"""),format.raw/*44.26*/("""_"""),format.raw("""{"""),format.raw/*44.28*/("""query"""),format.raw("""}"""),format.raw/*44.34*/("""_"""),format.raw("""{"""),format.raw/*44.36*/("""arrayplateid"""),format.raw("""}"""),format.raw/*44.49*/(""" …</pre>
	        	
	        	<p>such that the query name is prefixed by <code>wt-</code> or <code>ctrl-</code> to indicate a control plate.
	        	
	        	</p>
	        	
	        	<p>For example:</p>
	        	
	        	<ul>
		        	<li>
			        	<pre>michael_YDL108W_1_boone_15-12-12.jpg</pre>
		        		<p>Indicates a <code>non-control</code> screen carried out by <code>michael</code> with the screen having the query <code>YDL108W</code> and an array plate id of <code>1</code>
		        	</li>
		        	
		        	<li>
			        	<pre>charlie_wt-YOR341W_9_sga_3-10-11.jpg</pre>
		        		<p>Indicates a <code>control</code> screen carried out by <code>charlie</code> with the screen having the query <code>YOR341W</code> and an array plate id of <code>9</code>
	        		</li>
	        	</ul>
	        	
	        	<hr>
	        	<strong>Note:</strong> It is not mandatory to name the files in the mentioned format. Files named with a format other than the one mentioned above will not me mapped a query/array ORF and will not be scored
	        	
	        </div>
        </div>
        
        <div class="tab-pane" id="ia">
        	<div class="page-header">
	        	<h2>Image analysis</h2>
	        </div>
	        
	        <p>
	        Image analysis is the first step of the SGAtools pipeline and involves fitting a grid onto your plate images, then using the bounds of the grid to quantify the colony size using pixel intensities
	        </p>
	        
	        <h3>Input:</h3>
	        <div id="ia-plate-files">
	        	<h4>Plate images</h4>
	        	<p>Images of your screen in <code>.jpg</code> format</p>
	        	<img src="http://placehold.it/350x200">
	        </div>
        </div>
        
        <div class="tab-pane" id="ns">
        	<div class="page-header">
	        	<h2>Normalization & Scoring</h2>
	        </div>
        </div>
        
        <div class="tab-pane" id="da">
          	<div class="page-header">
	        	<h2>Data analysis</h2>
	        </div>
        </div>
        
        <div class="tab-pane" id="fa">
          	<div class="page-header">
	        	<h2>FAQs</h2>
	        </div>
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
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/docs.scala.html
                    HASH: 4862a05e15695df6b57960045065ec5bb79cd680
                    MATRIX: 827->2|857->24|896->26|2419->1502|2475->1511|2524->1513|2577->1519|2626->1521|2686->1534
                    LINES: 30->2|30->2|30->2|72->44|72->44|72->44|72->44|72->44|72->44
                    -- GENERATED --
                */
            