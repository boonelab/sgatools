
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
object iasummary extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[IAjob,Form[NSjob],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(job: IAjob, nsForm: Form[NSjob]):play.api.templates.Html = {
        _display_ {import helper._

import helper.twitterBootstrap._

import play.libs.Scala._

import play.Logger;

implicit def /*5.2*/implicitFieldConstructor/*5.26*/ = {{ FieldConstructor(extra.twitterBootstrapInput.f) }};
Seq[Any](format.raw/*1.35*/("""

"""),format.raw/*5.81*/("""
"""),_display_(Seq[Any](/*8.2*/main(title="Images processed!", nav="ia")/*8.43*/ {_display_(Seq[Any](format.raw/*8.45*/("""
	
	<div id="errorDiv"></div>
	
	<!--Buttons to download/repeat analysis or proceed to normalization-->
	<a class="btn" href=""""),_display_(Seq[Any](/*13.24*/routes/*13.30*/.Assets.at(job.downloadZipPath))),format.raw/*13.61*/(""""><i class="icon-download"></i> Download results</a>
	<a class="btn" href=""""),_display_(Seq[Any](/*14.24*/routes/*14.30*/.IAcontroller.initIAForm)),format.raw/*14.54*/(""""><i class="icon-repeat"></i> Start over</a>
	
	"""),_display_(Seq[Any](/*16.3*/helper/*16.9*/.form(action = routes.NScontroller.initPreloadedNSform(), 'id -> "toNSform", 'style->"display:inline")/*16.111*/ {_display_(Seq[Any](format.raw/*16.113*/("""
		"""),_display_(Seq[Any](/*17.4*/inputText(nsForm("jobid")))),format.raw/*17.30*/("""
	 	"""),_display_(Seq[Any](/*18.5*/inputText(nsForm("jsonSelectedForNS")))),format.raw/*18.43*/("""
		<a id="toNSsubmit" class="btn"><i class="icon-bar-chart"></i> Normalize and score »</a>
	""")))})),format.raw/*20.3*/("""
	
	<script>
		$(document).ready(function() """),format.raw("""{"""),format.raw/*23.33*/("""
			$('#jobid').attr('value','"""),_display_(Seq[Any](/*24.31*/job/*24.34*/.jobid)),format.raw/*24.40*/("""');
			$('#cg-jobid').hide();
			$('#cg-jsonSelectedForNS').hide();
		"""),format.raw("""}"""),format.raw/*27.4*/(""");
		$('#toNSsubmit').click(function()"""),format.raw("""{"""),format.raw/*28.37*/("""
			if(selectedPlates.length > 0)"""),format.raw("""{"""),format.raw/*29.34*/("""
				$('#jsonSelectedForNS').attr('value', JSON.stringify(selectedPlates));
				$('#toNSform').submit();
			"""),format.raw("""}"""),format.raw/*32.5*/("""else"""),format.raw("""{"""),format.raw/*32.10*/("""
				//Throw error: 0 plates selected
				$('#errorDiv').html('<div class="alert alert-error" id="errorChild"><button type="button" class="close" data-dismiss="alert">×</button><strong>Error</strong> You must select at least one plate for normalization/scoring</div>');
				$('#errorDiv').children().delay(5000).fadeOut('slow', function()"""),format.raw("""{"""),format.raw/*35.70*/(""" $(this).remove(); """),format.raw("""}"""),format.raw/*35.90*/(""");
			"""),format.raw("""}"""),format.raw/*36.5*/("""
			
		"""),format.raw("""}"""),format.raw/*38.4*/(""");
	</script>
	<br><br>
	
	<!--Job summary shown as a table-->
	
	<h3 style="margin-bottom:10px">Job summary</h3>
	<table class="table">
		<tr>
			<td style="width:200px">Elapsed time</td>
			<td>"""),_display_(Seq[Any](/*48.9*/job/*48.12*/.timeElapsed)),format.raw/*48.24*/("""</td>
		</tr>
		<tr>
			<td style="width:200px">Plate format</td>
			<td>"""),_display_(Seq[Any](/*52.9*/job/*52.12*/.plateFormat)),format.raw/*52.24*/("""</td>
		</tr>
		<tr>
			<td colspan="2">
			<table class="table-condensed" style="width:100%">
			<thead>
				<tr>
					<th>File name</th>
					<th>Status</th>
					<th>Screen type</th>
					<th>Query name</th>
					<th>Array plate id</th>
					<th>Normalize/Score</th>
				</tr>
			</thead>
			"""),_display_(Seq[Any](/*67.5*/for(passedFile <- job.passedFiles) yield /*67.39*/{_display_(Seq[Any](format.raw/*67.40*/("""
				<tr class="">
					<td style="width:300px"><i class="icon-picture stamp"></i> """),_display_(Seq[Any](/*69.66*/passedFile/*69.76*/.plateImageName)),format.raw/*69.91*/("""</td>
					<td>Passed</td>
					<td>"""),_display_(Seq[Any](/*71.11*/Help/*71.15*/.getPlateContents(passedFile.plateImageName).get(1))),format.raw/*71.66*/("""</td>
					<td>"""),_display_(Seq[Any](/*72.11*/Help/*72.15*/.getPlateContents(passedFile.plateImageName).get(2))),format.raw/*72.66*/("""</td>
					<td>"""),_display_(Seq[Any](/*73.11*/Help/*73.15*/.getPlateContents(passedFile.plateImageName).get(3))),format.raw/*73.66*/("""</td>
					<td>
						<div class="plateData" id=""""),_display_(Seq[Any](/*75.35*/passedFile/*75.45*/.plateImageName)),format.raw/*75.60*/("""" data-path=""""),_display_(Seq[Any](/*75.74*/passedFile/*75.84*/.outputDatName)),format.raw/*75.98*/("""">
						<div class="btn-group" data-toggle="buttons-radio" style="display:inline; margin-right:5px">
						<button onclick="selectPlateClicked(this)" id=""""),_display_(Seq[Any](/*77.55*/passedFile/*77.65*/.plateImageName)),format.raw/*77.80*/("""" name="includeicon" 
							type="button" class="btn btn-small btn-success active" value="1"><i class="icon-ok icon-white"></i></button>
						<button onclick="selectPlateClicked(this)" id=""""),_display_(Seq[Any](/*79.55*/passedFile/*79.65*/.plateImageName)),format.raw/*79.80*/("""" name="includeicon" 
							type="button" class="btn btn-small" value="0"><i class="icon-remove"></i></button>
						</div> 
					</td>
				</tr>
			""")))})),format.raw/*84.5*/("""
			
			</table>
			
			</td>
		</tr>
	</table>
	
	
	<!--Masked images generated - displayed as thumbnails-->
	<h3 style="margin-bottom:10px">Output images</h3>
	
	<ul class="thumbnails">
	"""),_display_(Seq[Any](/*97.3*/for(passedFile <- job.passedFiles) yield /*97.37*/{_display_(Seq[Any](format.raw/*97.38*/("""
	 	
	 	<li class="span6">
	 	<div class="thumbnail">	
	 		
			<div class="plateImage">
				<img src=""""),_display_(Seq[Any](/*103.16*/routes/*103.22*/.Assets.at(passedFile.outputMaskedPath))),format.raw/*103.61*/("""" style="height:300px">
			</div>
			<div class="caption">
				 <h4>"""),_display_(Seq[Any](/*106.11*/passedFile/*106.21*/.plateImageName)),format.raw/*106.36*/("""</h4>
          	
			</div>
		</div>
        </li>
	""")))})),format.raw/*111.3*/("""
	</ul>
	
	<!-- Scripts for the page -->
	<!--Import slider css/js-->
	<script src=""""),_display_(Seq[Any](/*116.16*/routes/*116.22*/.Assets.at("external/imageLens/jquery.zoom.js"))),format.raw/*116.69*/(""""></script>
	<script src=""""),_display_(Seq[Any](/*117.16*/routes/*117.22*/.Assets.at("javascripts/ia.js"))),format.raw/*117.53*/(""""></script>
	<script>
		var selectedPlates;
		$(document).ready(function()"""),format.raw("""{"""),format.raw/*120.32*/("""  
			// Hover zoom for images
			$('div.plateImage').zoom(); 
			selectedPlates = populateSelectedPlates();
		"""),format.raw("""}"""),format.raw/*124.4*/(""");
		
	
    </script>
""")))})))}
    }
    
    def render(job:IAjob,nsForm:Form[NSjob]) = apply(job,nsForm)
    
    def f:((IAjob,Form[NSjob]) => play.api.templates.Html) = (job,nsForm) => apply(job,nsForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/ia/iasummary.scala.html
                    HASH: 4583827849519ada27962686a6f26cda47853288
                    MATRIX: 773->1|972->88|1004->112|1089->34|1118->167|1154->216|1203->257|1242->259|1405->386|1420->392|1473->423|1585->499|1600->505|1646->529|1730->578|1744->584|1856->686|1897->688|1936->692|1984->718|2024->723|2084->761|2208->854|2300->899|2367->930|2379->933|2407->939|2524->1010|2610->1049|2691->1083|2846->1192|2898->1197|3284->1536|3351->1556|3404->1563|3458->1571|3690->1768|3702->1771|3736->1783|3845->1857|3857->1860|3891->1872|4222->2168|4272->2202|4311->2203|4431->2287|4450->2297|4487->2312|4560->2349|4573->2353|4646->2404|4698->2420|4711->2424|4784->2475|4836->2491|4849->2495|4922->2546|5008->2596|5027->2606|5064->2621|5114->2635|5133->2645|5169->2659|5361->2815|5380->2825|5417->2840|5645->3032|5664->3042|5701->3057|5883->3208|6108->3398|6158->3432|6197->3433|6337->3536|6353->3542|6415->3581|6521->3650|6541->3660|6579->3675|6664->3728|6786->3813|6802->3819|6872->3866|6936->3893|6952->3899|7006->3930|7129->4005|7288->4117
                    LINES: 27->1|36->5|36->5|37->1|39->5|40->8|40->8|40->8|45->13|45->13|45->13|46->14|46->14|46->14|48->16|48->16|48->16|48->16|49->17|49->17|50->18|50->18|52->20|55->23|56->24|56->24|56->24|59->27|60->28|61->29|64->32|64->32|67->35|67->35|68->36|70->38|80->48|80->48|80->48|84->52|84->52|84->52|99->67|99->67|99->67|101->69|101->69|101->69|103->71|103->71|103->71|104->72|104->72|104->72|105->73|105->73|105->73|107->75|107->75|107->75|107->75|107->75|107->75|109->77|109->77|109->77|111->79|111->79|111->79|116->84|129->97|129->97|129->97|135->103|135->103|135->103|138->106|138->106|138->106|143->111|148->116|148->116|148->116|149->117|149->117|149->117|152->120|156->124
                    -- GENERATED --
                */
            