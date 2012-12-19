
package views.html.ia

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
object iaform extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[IAjob],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(iaForm: Form[IAjob]):play.api.templates.Html = {
        _display_ {import helper._

import helper.twitterBootstrap._

implicit def /*5.2*/implicitFieldConstructor/*5.26*/ = {{ FieldConstructor(extra.twitterBootstrapInput.f) }};
Seq[Any](format.raw/*1.23*/("""

"""),format.raw/*5.81*/("""
"""),_display_(Seq[Any](/*6.2*/main("Image analysis", nav="ia")/*6.34*/ {_display_(Seq[Any](format.raw/*6.36*/("""
    
   """),_display_(Seq[Any](/*8.5*/helper/*8.11*/.form(action = routes.IAcontroller.submit(), 'enctype -> "multipart/form-data", 'id -> "imageprocessingForm", 'class->"form-horizontal")/*8.147*/ {_display_(Seq[Any](format.raw/*8.149*/("""
		<h2>Image analysis</h2>
		<hr>
		<p>File name format: <code>username_orf_arrayplateid_...</code> where ORF begins with <code>wt</code> for control plates
		<br>
		Example: <code>bob_wt-YDL108W_13_boonelab.jpg</code>
		</p>
		
		<h3 style="margin-bottom:10px">Data input</h3>
		
		<fieldset>
		
		<!--Images select button-->
		  """),_display_(Seq[Any](/*21.6*/inputFile(
	        iaForm("plateImages"),
	        '_label -> "Plate images(s)",
	        'multiple -> "",
	        'accept -> "image/gif, image/jpeg, image/png",
	    	'uploadwidget -> "Select image(s)"
	    ))),format.raw/*27.7*/("""
	    
	    
	    <!--Plate format of the screen images-->
	    """),_display_(Seq[Any](/*31.7*/select(
            iaForm("plateFormat"), 
            options = options(ComboboxOpts.plateFormat),
            '_label -> "Plate format",
            '_showConstraints -> false,
            'style -> "width:350px"
        ))),format.raw/*37.10*/("""
        
	    <!--Crop option - best left at index 0-->
         """),_display_(Seq[Any](/*40.11*/select(
            iaForm("cropOption"), 
            options = options(ComboboxOpts.cropOption),
            '_label -> "Crop option",
            '_showConstraints -> false,
            'style -> "width:350px"
        ))),format.raw/*46.10*/("""
        
		</fieldset>
		
	    <!--Loaded plates only visible when images selected (done via JS)-->
	    <div id="loaded-plates" style="display:none">
			<h3 id="loaded-label" style="margin-bottom:10px">Loaded plates</h3>
			<fieldset>
				<table id="loaded-table" class="table table-condensed">
				<thead><tr><th>Plate image</th><th>File name</th><th>Screen type</th><th>Query name</th><th>Array plate id</th></tr></thead>
				<tbody></tbody>
				</table>
			</fieldset>
		</div>
		
	    <!--Submit/clear all box-->
	    <div class="form-actions">
		  <button id="iasubmit" type="submit" class="btn btn-primary">Process images</button>
		  <a href=""""),_display_(Seq[Any](/*64.15*/routes/*64.21*/.IAcontroller.initIAForm())),format.raw/*64.47*/(""""><button id="clearall" type="button" class="btn">Clear all</button></a>
		</div>
	""")))})),format.raw/*66.3*/("""
	
	<div id="loadingDiv" class="hide">
		"""),_display_(Seq[Any](/*69.4*/loading("Processing images", "Expected time 25-60 seconds"))),format.raw/*69.63*/("""
	</div>

	<!-- Scripts for the page -->
    <script src=""""),_display_(Seq[Any](/*73.19*/routes/*73.25*/.Assets.at("javascripts/ia.js"))),format.raw/*73.56*/(""""></script>
    <script>
    	$(document).ready(function()"""),format.raw("""{"""),format.raw/*75.35*/("""
    		$('#iasubmit').attr('disabled', 'disabled');
    	"""),format.raw("""}"""),format.raw/*77.7*/(""");
    	// Submit click
    	$("#iasubmit").click(function() """),format.raw("""{"""),format.raw/*79.39*/("""
  			$('#imageprocessingForm').toggleClass("hide");
  			$('#loadingDiv').toggleClass("hide");
  			$('#imageprocessingForm').submit();
		"""),format.raw("""}"""),format.raw/*83.4*/(""");
    	
		$('#plateImages').change(handleFileSelect);
    </script>
""")))})))}
    }
    
    def render(iaForm:Form[IAjob]) = apply(iaForm)
    
    def f:((Form[IAjob]) => play.api.templates.Html) = (iaForm) => apply(iaForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/ia/iaform.scala.html
                    HASH: b15ced9473943472e30cc4235333e7caefa70d08
                    MATRIX: 764->1|904->76|936->100|1021->22|1050->155|1086->157|1126->189|1165->191|1209->201|1223->207|1368->343|1408->345|1775->677|2007->888|2107->953|2354->1178|2457->1245|2701->1467|3390->2120|3405->2126|3453->2152|3568->2236|3645->2278|3726->2337|3821->2396|3836->2402|3889->2433|3995->2492|4099->2550|4208->2612|4394->2752
                    LINES: 27->1|32->5|32->5|33->1|35->5|36->6|36->6|36->6|38->8|38->8|38->8|38->8|51->21|57->27|61->31|67->37|70->40|76->46|94->64|94->64|94->64|96->66|99->69|99->69|103->73|103->73|103->73|105->75|107->77|109->79|113->83
                    -- GENERATED --
                */
            