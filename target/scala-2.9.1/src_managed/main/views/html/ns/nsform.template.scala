
package views.html.ns

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
object nsform extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[Form[NSjob],String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(nsForm: Form[NSjob], jobid: String, selectedFromIA: String):play.api.templates.Html = {
        _display_ {import helper._

import helper.twitterBootstrap._

implicit def /*5.2*/implicitFieldConstructor/*5.26*/ = {{ FieldConstructor(extra.twitterBootstrapInput.f) }};
Seq[Any](format.raw/*1.62*/("""

"""),format.raw/*5.81*/("""
"""),_display_(Seq[Any](/*6.2*/main("Normalization & Scoring", nav="ns")/*6.43*/ {_display_(Seq[Any](format.raw/*6.45*/("""
	
	
	"""),_display_(Seq[Any](/*9.3*/helper/*9.9*/.form(action = routes.NScontroller.submit, 'enctype -> "multipart/form-data", 'id -> "normalizationScoringForm", 'class->"form-horizontal")/*9.148*/ {_display_(Seq[Any](format.raw/*9.150*/("""
		<h2>Normalization & Scoring</h2>  
		<hr>
		
		<div id="errorBox"></div>
		
		<div id="loadedFilesBox" class="hide">
		<h3 id="loadedFilesHeader" style="margin-bottom:10px">Loaded files</h3>
		<table id="loadedFilesTable" class="table table-condensed" style="width:100%">
		</table>
		<hr>
		</div>
			
		<!--Data upload-->
			<h3 style="margin-bottom:10px">Data input</h3>
			<fieldset>
			
            <!--File format selection-->
            <input type="hidden" id="jobid" name="jobid" value=""""),_display_(Seq[Any](/*27.66*/jobid)),format.raw/*27.71*/(""""/>
            <div id="preloadedPlateFiles" class="hide">
            	
            	"""),_display_(Seq[Any](/*30.15*/if("" != selectedFromIA)/*30.39*/{_display_(Seq[Any](format.raw/*30.40*/("""
			    	<script>
		            	$(document).ready(function()"""),format.raw("""{"""),format.raw/*32.45*/("""
		            		$('#cg-plateFiles').hide(); 
		            		$('#loadedFilesHeader').text('Preloaded files');
		            		var preloaded = JSON.parse('"""),_display_(Seq[Any](/*35.46*/Html(selectedFromIA))),format.raw/*35.66*/("""');
		            		$('#loadedFilesTable').html(preloaded.fileTableSGA());
		            		$('#loadedFilesBox').show();
		            		$('#plateFiles').attr('valid', 'true');
		            		$('#nssubmit').removeAttr('disabled');
		            	"""),format.raw("""}"""),format.raw/*40.17*/(""");
		            	
	            	</script>
            	""")))})),format.raw/*43.15*/("""
            </div>
            
            
            
            """),_display_(Seq[Any](/*48.14*/inputFile(
	            	nsForm("plateFiles"),
	            	'_label -> "Plate file(s)",
	            	'multiple -> "",
	            	'uploadwidget -> "Select file(s)"
	        ))),format.raw/*53.11*/("""
          	
          	
            
			<!--Include array definition checkbox-->
			"""),_display_(Seq[Any](/*58.5*/helper/*58.11*/.input(
				nsForm("doArrayDef"),
				'_label -> "Plate layout",
				'_showConstraints -> false,
				'helpModal -> "array-definition"
           	)/*63.14*/{ (id, name, value, args) =>_display_(Seq[Any](format.raw/*63.42*/("""
			<label class="checkbox">
				<input id=""""),_display_(Seq[Any](/*65.17*/id)),format.raw/*65.19*/("""" name=""""),_display_(Seq[Any](/*65.28*/name)),format.raw/*65.32*/("""" """),_display_(Seq[Any](/*65.35*/toHtmlArgs(args))),format.raw/*65.51*/(""" type="checkbox" value="true">
					<span>&nbsp;Define plate layout</span>
					</label>
			""")))})),format.raw/*68.5*/("""
			
			<!-- Enclose in div to be shown when checkbox clicked-->
			<div id="arrDefOpts">
			
           	<!--Array definition files - predefined-->
			"""),_display_(Seq[Any](/*74.5*/helper/*74.11*/.input(nsForm("arrayDefPredefined"),'_label -> "")/*74.61*/ { (id, name, value, args) =>_display_(Seq[Any](format.raw/*74.90*/("""
				<!--Populate predefined array definitions-->
			    <select id=""""),_display_(Seq[Any](/*76.21*/id)),format.raw/*76.23*/("""" name=""""),_display_(Seq[Any](/*76.32*/name)),format.raw/*76.36*/("""" """),_display_(Seq[Any](/*76.39*/toHtmlArgs(args))),format.raw/*76.55*/(""">
			    	"""),_display_(Seq[Any](/*77.10*/for(value <- ComboboxOpts.arrayDef) yield /*77.45*/{_display_(Seq[Any](format.raw/*77.46*/("""
			    		<option>"""),_display_(Seq[Any](/*78.19*/value)),format.raw/*78.24*/("""</option>
			    	""")))})),format.raw/*79.10*/("""
			    </select>
				<!--Populate their plates-->
				"""),_display_(Seq[Any](/*82.6*/for(i <- 0 to ComboboxOpts.arrayDef.size -1) yield /*82.50*/{_display_(Seq[Any](format.raw/*82.51*/("""
					"""),_display_(Seq[Any](/*83.7*/if(i != 0 & i != ComboboxOpts.arrayDef.size-1)/*83.53*/{_display_(Seq[Any](format.raw/*83.54*/("""
						<select class="plateCombo input-small" id=""""),_display_(Seq[Any](/*84.51*/ComboboxOpts/*84.63*/.arrayDef.get(i))),format.raw/*84.79*/("""-plates" style="display:none">
							"""),_display_(Seq[Any](/*85.9*/for(plateName <- ComboboxOpts.arrayDefPlates(ComboboxOpts.arrayDef.get(i))) yield /*85.84*/{_display_(Seq[Any](format.raw/*85.85*/("""
								<option>"""),_display_(Seq[Any](/*86.18*/plateName)),format.raw/*86.27*/("""</option>
							""")))})),format.raw/*87.9*/("""
						</select>
					""")))})),format.raw/*89.7*/("""
				""")))})),format.raw/*90.6*/("""
				<!--Button which triggers modal viewer-->
				<button id="viewPlate" class="btn" type="button" data-toggle="modal" data-target="#helpModal" style="display:none">
					<i class="icon-share"></i>
				</button>
			 """)))})),format.raw/*95.6*/("""
			 
			<!-- Array definition custom file input-->
			"""),_display_(Seq[Any](/*98.5*/inputFile(
            	nsForm("arrayDefCustomFile"),
            	'_label -> "",
            	'uploadwidget -> "Select plate layout file"
            ))),format.raw/*102.14*/("""
            
            </div>
			</fieldset>
			<hr>
			
			
			
			
			<h3 style="margin-bottom:10px">Options</h3>

			<fieldset>
			
			
            """),_display_(Seq[Any](/*116.14*/select(
                nsForm("replicates"), 
                options = options(ComboboxOpts.replicates),
                '_label -> "Replicates",
                '_showConstraints -> false,
				'helpModal -> "array-replicates"
            ))),format.raw/*122.14*/("""
            
          	"""),_display_(Seq[Any](/*124.13*/helper/*124.19*/.input(
           		nsForm("doLinkage"),
           		'_label -> "Linkage correction",
           		'_showConstraints -> false,
           		'_help -> "Note—chromosome number/position data for genes must be supplied",
				'helpModal -> "linkage-correction"
           	)/*130.14*/{ (id, name, value, args) =>_display_(Seq[Any](format.raw/*130.42*/("""
           		<label class="checkbox">
					<input id=""""),_display_(Seq[Any](/*132.18*/id)),format.raw/*132.20*/("""" name=""""),_display_(Seq[Any](/*132.29*/name)),format.raw/*132.33*/(""""  """),_display_(Seq[Any](/*132.37*/toHtmlArgs(args))),format.raw/*132.53*/(""" type="checkbox" value="true">
					<span>&nbsp;Remove genes with a specific proximity to one another from the analysis</span>
				</label>
			""")))})),format.raw/*135.5*/("""
			
           	"""),_display_(Seq[Any](/*137.14*/helper/*137.20*/.input(
           		nsForm("linkageCutoff"),
           		'_label -> "Linkage cutoff",
           		'_showConstraints -> false,
           		'pattern -> "\\d+"
           	)/*142.14*/{ (id, name, value, args) =>_display_(Seq[Any](format.raw/*142.42*/("""
           		<div class="input-append">
      				<input class="span2" id=""""),_display_(Seq[Any](/*144.37*/id)),format.raw/*144.39*/("""" name=""""),_display_(Seq[Any](/*144.48*/name)),format.raw/*144.52*/("""" """),_display_(Seq[Any](/*144.55*/toHtmlArgs(args))),format.raw/*144.71*/(""" size="16" type="text" style="width:175px" 
      				value=""""),_display_(Seq[Any](/*145.19*/nsForm/*145.25*/.get.linkageCutoff)),format.raw/*145.43*/(""""><span class="add-on">KB</span>
      			</div>
			""")))})),format.raw/*147.5*/("""
			
				
			"""),_display_(Seq[Any](/*150.5*/helper/*150.11*/.input(
           		nsForm("doScoring"),
           		'_label -> "Score results",
           		'_showConstraints -> false,
           		'_help -> "Note—control screens and/or single mutant fitness data must be supplied",
			'helpModal -> "do-scoring"
           	)/*156.14*/{ (id, name, value, args) =>_display_(Seq[Any](format.raw/*156.42*/("""
           		<label class="checkbox">
					<input id=""""),_display_(Seq[Any](/*158.18*/id)),format.raw/*158.20*/("""" name=""""),_display_(Seq[Any](/*158.29*/name)),format.raw/*158.33*/(""""  """),_display_(Seq[Any](/*158.37*/toHtmlArgs(args))),format.raw/*158.53*/(""" type="checkbox" value="true">
					 <span>&nbsp;Scores the normalized output</span>
				</label>
			""")))})),format.raw/*161.5*/("""
			"""),_display_(Seq[Any](/*162.5*/helper/*162.11*/.input(
           		nsForm("scoringFunction"),
           		'_label -> "Scoring function",
           		'_showConstraints -> false
           	)/*166.14*/{ (id, name, value, args) =>_display_(Seq[Any](format.raw/*166.42*/("""
           		<select id=""""),_display_(Seq[Any](/*167.27*/id)),format.raw/*167.29*/("""" name=""""),_display_(Seq[Any](/*167.38*/name)),format.raw/*167.42*/("""">
           			"""),_display_(Seq[Any](/*168.16*/for(scoringFunction <- ComboboxOpts.scoringFunctions) yield /*168.69*/{_display_(Seq[Any](format.raw/*168.70*/("""
           				<option>"""),_display_(Seq[Any](/*169.25*/Html(scoringFunction))),format.raw/*169.46*/("""</option>
           			""")))})),format.raw/*170.16*/("""
				</select>
           		
			""")))})),format.raw/*173.5*/("""
				
			</fieldset>
			
			<!--
			<hr>
			<h3 id="advancedOptsBtn" style="margin-bottom:10px;cursor: hand; cursor: pointer;">
			Advanced options <i id="advancedOptsIcon" class="icon-chevron-right" style="font-size:10px; color:#0081cc"></i></h3>
			<fieldset id="advancedOpts" class="hide">
			<div class="alert">
			  <button type="button" class="close" data-dismiss="alert">×</button>
			  <strong>Warning!</strong> Changing these options could drastically affect your results. Do not modify unless necessary.
			</div>
			
			<div class="row">
				<div class="span2 offset2">
				<strong><p>Normalizations</p></strong>
		        <label id="advPlate"><input id="advPlate" type="checkbox" checked style="vertical-align:1px;"/> Plate-specific effect</label>
		        <label><input type="checkbox" checked style="vertical-align:1px;"/> Spatial effect</label>
		        <label><input type="checkbox" checked style="vertical-align:1px;"/> Row/column effect</label>
		        <label><input type="checkbox" checked style="vertical-align:1px;"/> Competition effect</label>
		        <label><input type="checkbox" checked style="vertical-align:1px;"/> Plate-specific effect 2</label>
		        </div>
		        <div class="span2">
		        <strong><p>Filters</p></strong>
		        <label><input type="checkbox" checked style="vertical-align:1px;"/> Large replicates</label>
		        <label><input type="checkbox" checked style="vertical-align:1px;"/> Jackknife filter</label>
		        <label><input type="checkbox" checked style="vertical-align:1px;"/> Cap filter</label>
		        </div>
		        <div class="span2" id="advancedDesc">
		        </div>
		    </div>
				
			</fieldset>
			-->
			
			
			
			<!--Submit/clear all box-->
        	<div class="form-actions">
		  	<button id="nssubmit" class="btn btn-primary" disabled>Normalize</button>
		  	<a href=""""),_display_(Seq[Any](/*214.16*/routes/*214.22*/.NScontroller.initNSform())),format.raw/*214.48*/(""""><button id="clearall" type="button" class="btn">Clear all</button></a>
			</div>
			
			<!-- Scripts for the page -->
    		<script src=""""),_display_(Seq[Any](/*218.21*/routes/*218.27*/.Assets.at("javascripts/ns.js"))),format.raw/*218.58*/(""""></script>
			
			
	""")))})),format.raw/*221.3*/(""" 
	<div id="loadingDiv" class="hide">
		"""),_display_(Seq[Any](/*223.4*/loading("Normalizing / Scoring", "Expected time 2-5 seconds"))),format.raw/*223.65*/("""
	</div>
	<script>
	$("#nssubmit").click(function() """),format.raw("""{"""),format.raw/*226.35*/("""
		$('#normalizationScoringForm').addClass('hide');
		$('#loadingDiv').removeClass('hide');
		$('#normalizationScoringForm').submit();
	"""),format.raw("""}"""),format.raw/*230.3*/(""");
	
	
	</script>
""")))})))}
    }
    
    def render(nsForm:Form[NSjob],jobid:String,selectedFromIA:String) = apply(nsForm,jobid,selectedFromIA)
    
    def f:((Form[NSjob],String,String) => play.api.templates.Html) = (nsForm,jobid,selectedFromIA) => apply(nsForm,jobid,selectedFromIA)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/ns/nsform.scala.html
                    HASH: c2ac703a905e388b06a2e4ce57b5116584232299
                    MATRIX: 778->1|957->115|989->139|1074->61|1103->194|1139->196|1188->237|1227->239|1268->246|1281->252|1429->391|1469->393|2006->894|2033->899|2157->987|2190->1011|2229->1012|2338->1074|2530->1230|2572->1250|2866->1497|2955->1554|3063->1626|3263->1804|3384->1890|3399->1896|3555->2043|3621->2071|3702->2116|3726->2118|3771->2127|3797->2131|3836->2134|3874->2150|3998->2243|4186->2396|4201->2402|4260->2452|4327->2481|4433->2551|4457->2553|4502->2562|4528->2566|4567->2569|4605->2585|4652->2596|4703->2631|4742->2632|4797->2651|4824->2656|4875->2675|4966->2731|5026->2775|5065->2776|5107->2783|5162->2829|5201->2830|5288->2881|5309->2893|5347->2909|5421->2948|5512->3023|5551->3024|5605->3042|5636->3051|5685->3069|5739->3092|5776->3098|6025->3316|6116->3372|6291->3524|6483->3679|6748->3921|6811->3947|6827->3953|7108->4224|7175->4252|7268->4308|7293->4310|7339->4319|7366->4323|7407->4327|7446->4343|7622->4487|7677->4505|7693->4511|7877->4685|7944->4713|8058->4790|8083->4792|8129->4801|8156->4805|8196->4808|8235->4824|8334->4886|8350->4892|8391->4910|8476->4963|8526->4977|8542->4983|8817->5248|8884->5276|8977->5332|9002->5334|9048->5343|9075->5347|9116->5351|9155->5367|9289->5469|9330->5474|9346->5480|9501->5625|9568->5653|9632->5680|9657->5682|9703->5691|9730->5695|9785->5713|9855->5766|9895->5767|9957->5792|10001->5813|10059->5838|10124->5871|12028->7738|12044->7744|12093->7770|12270->7910|12286->7916|12340->7947|12394->7969|12471->8010|12555->8071|12656->8124|12840->8261
                    LINES: 27->1|32->5|32->5|33->1|35->5|36->6|36->6|36->6|39->9|39->9|39->9|39->9|57->27|57->27|60->30|60->30|60->30|62->32|65->35|65->35|70->40|73->43|78->48|83->53|88->58|88->58|93->63|93->63|95->65|95->65|95->65|95->65|95->65|95->65|98->68|104->74|104->74|104->74|104->74|106->76|106->76|106->76|106->76|106->76|106->76|107->77|107->77|107->77|108->78|108->78|109->79|112->82|112->82|112->82|113->83|113->83|113->83|114->84|114->84|114->84|115->85|115->85|115->85|116->86|116->86|117->87|119->89|120->90|125->95|128->98|132->102|146->116|152->122|154->124|154->124|160->130|160->130|162->132|162->132|162->132|162->132|162->132|162->132|165->135|167->137|167->137|172->142|172->142|174->144|174->144|174->144|174->144|174->144|174->144|175->145|175->145|175->145|177->147|180->150|180->150|186->156|186->156|188->158|188->158|188->158|188->158|188->158|188->158|191->161|192->162|192->162|196->166|196->166|197->167|197->167|197->167|197->167|198->168|198->168|198->168|199->169|199->169|200->170|203->173|244->214|244->214|244->214|248->218|248->218|248->218|251->221|253->223|253->223|256->226|260->230
                    -- GENERATED --
                */
            