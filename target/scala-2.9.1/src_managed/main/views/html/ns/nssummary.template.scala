
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
object nssummary extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[NSjob,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(job: NSjob):play.api.templates.Html = {
        _display_ {import play.libs.Scala._

import play.Logger;


Seq[Any](format.raw/*1.14*/("""

"""),_display_(Seq[Any](/*5.2*/main(title="Normalization complete", nav="ns")/*5.48*/ {_display_(Seq[Any](format.raw/*5.50*/("""
	<h2>Normalization/Scoring complete!</h2><br>
	<!--Buttons to download/repeat analysis or proceed to normalization-->
	<a class="btn" href=""""),_display_(Seq[Any](/*8.24*/routes/*8.30*/.Assets.at(job.downloadZipPath))),format.raw/*8.61*/(""""><i class="icon-download"></i> Download results</a>
	<a class="btn" href=""""),_display_(Seq[Any](/*9.24*/routes/*9.30*/.NScontroller.initNSform)),format.raw/*9.54*/(""""><i class="icon-repeat"></i> Start over</a>
	<a class="btn" href="/preloaded-dataanalysis/"""),_display_(Seq[Any](/*10.48*/job/*10.51*/.jobid)),format.raw/*10.57*/(""""><i class="icon-tasks"></i> Data analysis »</a>
	
	<br>
	<br>
	<h3 style="margin:bottom:10px">Plate files</h3>
	
	<script>
		$(document).ready(function()"""),format.raw("""{"""),format.raw/*17.32*/("""
			var f = [ """),_display_(Seq[Any](/*18.15*/for(fileName <- job.outputFilesMap.keySet) yield /*18.57*/{_display_(Seq[Any](format.raw/*18.58*/(""" """"),_display_(Seq[Any](/*18.61*/fileName)),format.raw/*18.69*/("""" , """)))})),format.raw/*18.74*/(""" ]
			$('#plateFilesTable').html(f.fileTableSGA());
		"""),format.raw("""}"""),format.raw/*20.4*/(""");
		
	</script>
	<table class="table table-condensed" id="plateFilesTable">
		
	</table>
	
	<h3 style="margin:bottom:10px">Job summary</h3>
	
	<table class="table">
		<tr>
			<td style="width:200px">Elapsed time</td>
			<td>"""),_display_(Seq[Any](/*32.9*/job/*32.12*/.timeElapsed)),format.raw/*32.24*/("""</td>
		</tr>
		<tr>
			<td>Array layout file</td>
			<td>"""),_display_(Seq[Any](/*36.9*/job/*36.12*/.summaryAD)),format.raw/*36.22*/("""</td>
		</tr>
		<tr>
			<td>Replicates</td>
			<td>"""),_display_(Seq[Any](/*40.9*/job/*40.12*/.replicates)),format.raw/*40.23*/("""</td>
		</tr>
		
		"""),_display_(Seq[Any](/*43.4*/if(job.doLinkage == true)/*43.29*/{_display_(Seq[Any](format.raw/*43.30*/("""
			<tr>
				<td>Linkage correction cutoff</td>
				<td>"""),_display_(Seq[Any](/*46.10*/job/*46.13*/.linkageCutoff)),format.raw/*46.27*/("""</td>
			</tr>
		""")))})),format.raw/*48.4*/("""
		<tr>
			<td>Results scored</td>
			<td><i 
			"""),_display_(Seq[Any](/*52.5*/if(job.doScoring == true)/*52.30*/{_display_(Seq[Any](format.raw/*52.31*/(""" class="icon-ok"""")))}/*52.48*/else/*52.52*/{_display_(Seq[Any](format.raw/*52.53*/("""class="icon-remove"""")))})),format.raw/*52.73*/(""" 
			></i></td>
		</tr>
		
	</table>
	
	
""")))})))}
    }
    
    def render(job:NSjob) = apply(job)
    
    def f:((NSjob) => play.api.templates.Html) = (job) => apply(job)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/ns/nssummary.scala.html
                    HASH: 9448ec628c629299240157f7da834aec3c4b8053
                    MATRIX: 761->1|896->13|933->63|987->109|1026->111|1203->253|1217->259|1269->290|1380->366|1394->372|1439->396|1567->488|1579->491|1607->497|1809->652|1860->667|1918->709|1957->710|1996->713|2026->721|2063->726|2164->781|2425->1007|2437->1010|2471->1022|2565->1081|2577->1084|2609->1094|2696->1146|2708->1149|2741->1160|2796->1180|2830->1205|2869->1206|2962->1263|2974->1266|3010->1280|3059->1298|3144->1348|3178->1373|3217->1374|3253->1391|3266->1395|3305->1396|3357->1416
                    LINES: 27->1|33->1|35->5|35->5|35->5|38->8|38->8|38->8|39->9|39->9|39->9|40->10|40->10|40->10|47->17|48->18|48->18|48->18|48->18|48->18|48->18|50->20|62->32|62->32|62->32|66->36|66->36|66->36|70->40|70->40|70->40|73->43|73->43|73->43|76->46|76->46|76->46|78->48|82->52|82->52|82->52|82->52|82->52|82->52|82->52
                    -- GENERATED --
                */
            