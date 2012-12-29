
package views.html.da

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
object dasummary extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[NSjob,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(job: NSjob):play.api.templates.Html = {
        _display_ {import play.libs.Scala._

import play.Logger;


Seq[Any](format.raw/*1.14*/("""

"""),_display_(Seq[Any](/*5.2*/main(title="Data analysis", nav="da")/*5.39*/ {_display_(Seq[Any](format.raw/*5.41*/("""
	
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*7.39*/routes/*7.45*/.Assets.at("external/sgatools-analysis/d3.v2.js"))),format.raw/*7.94*/(""""></script>
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*8.39*/routes/*8.45*/.Assets.at("external/sgatools-analysis/sgatools.heatmap.js"))),format.raw/*8.105*/(""""></script>
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*9.39*/routes/*9.45*/.Assets.at("external/sgatools-analysis/sgatools.histogram.js"))),format.raw/*9.107*/(""""></script>
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*10.39*/routes/*10.45*/.Assets.at("external/sgatools-analysis/crossfilter.js"))),format.raw/*10.100*/(""""></script>
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*11.39*/routes/*11.45*/.Assets.at("external/sgatools-analysis/dc/dc.js"))),format.raw/*11.94*/(""""></script>
	<link type="text/css" rel="stylesheet" href=""""),_display_(Seq[Any](/*12.48*/routes/*12.54*/.Assets.at("external/sgatools-analysis/dc/dc.css"))),format.raw/*12.104*/("""" />
	
	
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*15.39*/routes/*15.45*/.Assets.at("external/sgatools-analysis/color_picker/jquery.miniColors.js"))),format.raw/*15.119*/(""""></script>
	<link type="text/css" rel="stylesheet" href=""""),_display_(Seq[Any](/*16.48*/routes/*16.54*/.Assets.at("external/sgatools-analysis/color_picker/jquery.miniColors.css"))),format.raw/*16.129*/("""" />
	
	<h2>Data analysis</h2>
	<hr>
	
	<div class="well">
		<select id="dataCombobox" class="input-xlarge">
		"""),_display_(Seq[Any](/*23.4*/for((key,value) <- job.outputFilesMap) yield /*23.42*/{_display_(Seq[Any](format.raw/*23.43*/("""
			<option image-path="/assets/jobs/"""),_display_(Seq[Any](/*24.38*/job/*24.41*/.jobid)),format.raw/*24.47*/("""/ia/input_images/"""),_display_(Seq[Any](/*24.65*/key/*24.68*/.substring(0,key.length-4))),format.raw/*24.94*/("""" 
					gridded-image-path="/assets/jobs/"""),_display_(Seq[Any](/*25.40*/job/*25.43*/.jobid)),format.raw/*25.49*/("""/ia/output_images/masked_"""),_display_(Seq[Any](/*25.75*/key/*25.78*/.substring(0,key.length-4))),format.raw/*25.104*/("""" 
					data-path=""""),_display_(Seq[Any](/*26.18*/routes/*26.24*/.Assets.at(value.replace(Constants.BASE_PUBLIC_DIR+"/", "")))),format.raw/*26.84*/("""">"""),_display_(Seq[Any](/*26.87*/key)),format.raw/*26.90*/("""</option>
		""")))})),format.raw/*27.4*/("""
		</select>
		<select id="viewType" class="input-medium">
			<option name="colonysize" domainHigh=300 domainMed=150 domainLow=0>Raw colony sizes</option>
			<option name="ncolonysize" domainHigh=900 domainMed=510 domainLow=100>Normalized colony sizes</option>
			"""),_display_(Seq[Any](/*32.5*/if(job.doScoring)/*32.22*/ {_display_(Seq[Any](format.raw/*32.24*/("""<option name="score" domainHigh=1 domainMed=0 domainLow=-1 selected>Scored data</option> """)))})),format.raw/*32.114*/("""
		</select>
		
		<style>
			.input-prepend .add-on"""),format.raw("""{"""),format.raw/*36.27*/("""
				margin-right:-4px;
			"""),format.raw("""}"""),format.raw/*38.5*/("""
			.span3c"""),format.raw("""{"""),format.raw/*39.12*/("""
				width:195px;
			"""),format.raw("""}"""),format.raw/*41.5*/("""
		</style>
			
		<div class="container">
		<div class="row" style="margin-left:-18px">
			<div class="input-prepend span3c">
			  <span class="add-on">Low</span>
			  <input class="span1 domainInput" id="domainLowInput" type="text" style="text-align:center;" placeholder="">
			  <input type="text" id="colorLow" class="input-mini color-picker miniColors" size="7" autocomplete="off" value="#FF0000" maxlength="7">
			</div>
            
             <div class="input-prepend span3c">
			  <span class="add-on">Med</span>
			  <input class="span1 domainInput" id="domainMedInput" type="text" style="text-align:center;" placeholder="">
			  <input type="text" id="colorMed" class="input-mini color-picker miniColors" size="7" autocomplete="off" value="#000000" maxlength="7">
			</div>
			
			<div class="input-prepend span3c">
			  <span class="add-on">High</span>
			  <input class="span1 domainInput" id="domainHighInput" type="text" style="text-align:center;" placeholder="">
			  <input type="text" id="colorHigh" class="input-mini color-picker miniColors" size="7" autocomplete="off" value="#008000" maxlength="7">
			</div>
		</div>	
		
		<div class="row">
			<div class="span2">
				<label for="griddedImage" class="">Gridded image</label>
				<div class="toggle-button">
					 <input id="griddedImage" type="checkbox" value="value1">
				</div>
			</div>
			
			
			<div class="span2">
				<label for="hideImage" class="">Hide plate image</label>
				<div class="toggle-button">
					 <input id="hideImage" type="checkbox" value="value1">
				</div>
			</div>
			
			
			<script>
				$('#griddedImage').change(function()"""),format.raw("""{"""),format.raw/*83.42*/("""
					var plate = $('#dataCombobox option:selected');
					if($('#griddedImage').is(':checked'))"""),format.raw("""{"""),format.raw/*85.44*/("""
						$('#plateImage').attr('src', plate.attr('gridded-image-path'));
					"""),format.raw("""}"""),format.raw/*87.7*/("""else"""),format.raw("""{"""),format.raw/*87.12*/("""
						$('#plateImage').attr('src', plate.attr('image-path'));
					"""),format.raw("""}"""),format.raw/*89.7*/("""
					
				"""),format.raw("""}"""),format.raw/*91.6*/(""");
				
				$('#hideImage').change(function()"""),format.raw("""{"""),format.raw/*93.39*/("""
					if($('#hideImage').is(':checked'))"""),format.raw("""{"""),format.raw/*94.41*/("""
						$('#plateImage').hide();
						maxHeatmapWidth = 920;
					"""),format.raw("""}"""),format.raw/*97.7*/("""else"""),format.raw("""{"""),format.raw/*97.12*/("""
						$('#plateImage').show();
						maxHeatmapWidth = 460;
					"""),format.raw("""}"""),format.raw/*100.7*/("""
					$('#dataCombobox').trigger('change');
				"""),format.raw("""}"""),format.raw/*102.6*/(""");
			</script>
			
		</div>
		</div>
			
		
	</div><!-- End well -->
	
			<center>
			
			<div class="conatiner">
			
			<!--Heatmap & Image-->
			<div class="row">
				<div id="plateImageContents" class="span6" ><img id="plateImage"></div>
				<div id="heatmapContents" class="span6" ></div>
			</div>
			
			<hr><!--Separator-->
			
			
			<style>
				#data-table tbody"""),format.raw("""{"""),format.raw/*125.23*/("""
					height:150px;
  					overflow:auto;
				"""),format.raw("""}"""),format.raw/*128.6*/("""
			</style>
			<!--Histogram-->
			<div class="row span12">
				<div id="bar-chart">
			    	<div id="data-count">
			    		<span class="filter-count"></span> selected out of <span class="total-count"></span>
			    		<a class="reset" href="javascript:barChart.filterAll();dc.redrawAll();">reset</a>
			    	</div>
			    	<div id="bar-chart-contents">
			    	</div>
			    </div>
				<table id="data-table" class="table table-hover table-condensed" style="margin-bottom:100px">
				    <thead><tr class="row"><th>Row</th><th>Column</th><th>Query</th><th>Array</th><th>Score</th></tr>
				    </thead>
			    </table>    
			</div>
			
			
			</div>
			</center>
			
	<script type="text/javascript">
			var maxHeatmapWidth = 460;
			
			$('.domainInput').change(function()"""),format.raw("""{"""),format.raw/*153.40*/("""
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*155.5*/(""");
			
			$('#viewType').change(function()"""),format.raw("""{"""),format.raw/*157.37*/("""
				var viewtype = $('#viewType option:selected');
				
				$('#domainLowInput').val(viewtype.attr('domainLow'));
				$('#domainMedInput').val(viewtype.attr('domainMed'));
				$('#domainHighInput').val(viewtype.attr('domainHigh'));
				
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*165.5*/(""");
			
			$(".color-picker").miniColors("""),format.raw("""{"""),format.raw/*167.35*/("""
			    change: function(hex, rgb) """),format.raw("""{"""),format.raw/*168.36*/(""" 
					$('#dataCombobox').trigger('change');
			    """),format.raw("""}"""),format.raw/*170.9*/("""
			"""),format.raw("""}"""),format.raw/*171.5*/(""");
			
			$(document).ready(function() """),format.raw("""{"""),format.raw/*173.34*/("""
				$('#viewType').trigger('change');
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*176.5*/(""");
			
		    $('#dataCombobox').change(function() """),format.raw("""{"""),format.raw/*178.45*/("""
		    	var viewtype = $('#viewType option:selected');
		    	var plate = $('#dataCombobox option:selected');
		    	
				drawHeatmap("""),format.raw("""{"""),format.raw/*182.18*/("""
					heatmapContainer : '#heatmapContents',
					dataPath : plate.attr('data-path'),
					gridSize : 10,
					maxwidth: maxHeatmapWidth,
					domainLow: +$('#domainLowInput').val(),
					domainMed: +$('#domainMedInput').val(), 
					domainHigh: +$('#domainHighInput').val(),
					columnToUse: viewtype.attr('name'),
					colorLow: $('#colorLow').val(),
					colorMed: $('#colorMed').val(), 
					colorHigh: $('#colorHigh').val()
				"""),format.raw("""}"""),format.raw/*194.6*/(""");
				
				var barChart = drawBarChart("""),format.raw("""{"""),format.raw/*196.34*/("""
					'dataName' : plate.attr('data-path'),
					'divChart' : "#bar-chart-contents", 
					'divDataTable' : "#data-table", 
					'divDataCount' : "#data-count",
					'maxDataRows' : 10, 
					'chartWidth' : 946, 
					'chartHeight' : 200,
					columnToUse: viewtype.attr('name'),
					domainLow: +$('#domainLowInput').val(),
					domainHigh: +$('#domainHighInput').val()
				"""),format.raw("""}"""),format.raw/*207.6*/(""");		
				
				$('#plateImage')
					.attr('src', plate.attr('image-path'))
					.css("""),format.raw("""{"""),format.raw/*211.12*/(""" height: $('.heatmapSVG').height() - 20,
							width: $('.heatmapSVG').width() - 20"""),format.raw("""}"""),format.raw/*212.45*/(""");
				
				$('#griddedImage').trigger('change');
				
				$('#plateImage').error(function() """),format.raw("""{"""),format.raw/*216.40*/("""
				  $('#plateImage').attr('src', """"),_display_(Seq[Any](/*217.38*/routes/*217.44*/.Assets.at("images/noimage_plate_small.jpeg"))),format.raw/*217.89*/("""");
				"""),format.raw("""}"""),format.raw/*218.6*/(""");
			"""),format.raw("""}"""),format.raw/*219.5*/(""");
			
	</script>
	
	
""")))})))}
    }
    
    def render(job:NSjob) = apply(job)
    
    def f:((NSjob) => play.api.templates.Html) = (job) => apply(job)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Dec 28 21:27:41 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/da/dasummary.scala.html
                    HASH: 5a31b6b51fddc972f0db1a6c92e4f2cbce20b76f
                    MATRIX: 761->1|896->13|933->63|978->100|1017->102|1093->143|1107->149|1177->198|1262->248|1276->254|1358->314|1443->364|1457->370|1541->432|1627->482|1642->488|1720->543|1806->593|1821->599|1892->648|1987->707|2002->713|2075->763|2158->810|2173->816|2270->890|2365->949|2380->955|2478->1030|2625->1142|2679->1180|2718->1181|2792->1219|2804->1222|2832->1228|2886->1246|2898->1249|2946->1275|3024->1317|3036->1320|3064->1326|3126->1352|3138->1355|3187->1381|3243->1401|3258->1407|3340->1467|3379->1470|3404->1473|3448->1486|3748->1751|3774->1768|3814->1770|3937->1860|4036->1912|4110->1940|4169->1952|4237->1974|5915->3605|6059->3702|6182->3779|6234->3784|6349->3853|6407->3865|6500->3911|6588->3952|6701->4019|6753->4024|6867->4091|6963->4140|7384->4513|7478->4560|8301->5335|8395->5382|8486->5425|8817->5709|8906->5750|8990->5786|9090->5839|9142->5844|9230->5884|9362->5969|9461->6020|9644->6155|10126->6590|10215->6631|10638->7007|10772->7093|10905->7178|11047->7272|11122->7310|11138->7316|11206->7361|11262->7370|11316->7377
                    LINES: 27->1|33->1|35->5|35->5|35->5|37->7|37->7|37->7|38->8|38->8|38->8|39->9|39->9|39->9|40->10|40->10|40->10|41->11|41->11|41->11|42->12|42->12|42->12|45->15|45->15|45->15|46->16|46->16|46->16|53->23|53->23|53->23|54->24|54->24|54->24|54->24|54->24|54->24|55->25|55->25|55->25|55->25|55->25|55->25|56->26|56->26|56->26|56->26|56->26|57->27|62->32|62->32|62->32|62->32|66->36|68->38|69->39|71->41|113->83|115->85|117->87|117->87|119->89|121->91|123->93|124->94|127->97|127->97|130->100|132->102|155->125|158->128|183->153|185->155|187->157|195->165|197->167|198->168|200->170|201->171|203->173|206->176|208->178|212->182|224->194|226->196|237->207|241->211|242->212|246->216|247->217|247->217|247->217|248->218|249->219
                    -- GENERATED --
                */
            