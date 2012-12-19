
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
	
	<h2>Data analysis</h2><br>
	
	<div class="well">
		<select id="dataCombobox" class="input-xlarge">
		"""),_display_(Seq[Any](/*22.4*/for((key,value) <- job.outputFilesMap) yield /*22.42*/{_display_(Seq[Any](format.raw/*22.43*/("""
			<option image-path="/assets/jobs/"""),_display_(Seq[Any](/*23.38*/job/*23.41*/.jobid)),format.raw/*23.47*/("""/ia/input_images/"""),_display_(Seq[Any](/*23.65*/key/*23.68*/.substring(0,key.length-4))),format.raw/*23.94*/("""" 
					gridded-image-path="/assets/jobs/"""),_display_(Seq[Any](/*24.40*/job/*24.43*/.jobid)),format.raw/*24.49*/("""/ia/output_images/masked_"""),_display_(Seq[Any](/*24.75*/key/*24.78*/.substring(0,key.length-4))),format.raw/*24.104*/("""" 
					data-path=""""),_display_(Seq[Any](/*25.18*/routes/*25.24*/.Assets.at(value.replace(Constants.BASE_PUBLIC_DIR+"/", "")))),format.raw/*25.84*/("""">"""),_display_(Seq[Any](/*25.87*/key)),format.raw/*25.90*/("""</option>
		""")))})),format.raw/*26.4*/("""
		</select>
		<select id="viewType" class="input-medium">
			<option name="colonysize" domainHigh=300 domainMed=150 domainLow=0>Raw colony sizes</option>
			<option name="ncolonysize" domainHigh=900 domainMed=510 domainLow=100>Normalized colony sizes</option>
			"""),_display_(Seq[Any](/*31.5*/if(job.doScoring)/*31.22*/ {_display_(Seq[Any](format.raw/*31.24*/("""<option name="score" domainHigh=1 domainMed=0 domainLow=-1 selected>Scored data</option> """)))})),format.raw/*31.114*/("""
		</select>
		
		<style>
			.input-prepend .add-on"""),format.raw("""{"""),format.raw/*35.27*/("""
				margin-right:-4px;
			"""),format.raw("""}"""),format.raw/*37.5*/("""
			.span3c"""),format.raw("""{"""),format.raw/*38.12*/("""
				width:195px;
			"""),format.raw("""}"""),format.raw/*40.5*/("""
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
				$('#griddedImage').change(function()"""),format.raw("""{"""),format.raw/*82.42*/("""
					var plate = $('#dataCombobox option:selected');
					if($('#griddedImage').is(':checked'))"""),format.raw("""{"""),format.raw/*84.44*/("""
						$('#plateImage').attr('src', plate.attr('gridded-image-path'));
					"""),format.raw("""}"""),format.raw/*86.7*/("""else"""),format.raw("""{"""),format.raw/*86.12*/("""
						$('#plateImage').attr('src', plate.attr('image-path'));
					"""),format.raw("""}"""),format.raw/*88.7*/("""
					
				"""),format.raw("""}"""),format.raw/*90.6*/(""");
				
				$('#hideImage').change(function()"""),format.raw("""{"""),format.raw/*92.39*/("""
					if($('#hideImage').is(':checked'))"""),format.raw("""{"""),format.raw/*93.41*/("""
						$('#plateImage').hide();
						maxHeatmapWidth = 920;
					"""),format.raw("""}"""),format.raw/*96.7*/("""else"""),format.raw("""{"""),format.raw/*96.12*/("""
						$('#plateImage').show();
						maxHeatmapWidth = 460;
					"""),format.raw("""}"""),format.raw/*99.7*/("""
					$('#dataCombobox').trigger('change');
				"""),format.raw("""}"""),format.raw/*101.6*/(""");
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
				#data-table tbody"""),format.raw("""{"""),format.raw/*124.23*/("""
					height:150px;
  					overflow:auto;
				"""),format.raw("""}"""),format.raw/*127.6*/("""
			</style>
			<!--Histogram-->
			<div class="row span12">
				<div id="bar-chart">
			    	<div id="data-count">
			    		<span class="filter-count"></span> selected out of <span class="total-count"></span>
			    		<a class="reset" href="javascript:barChart.filterAll();dc.redrawAll();" style="display:none;">reset</a>
			    	</div>
			    	<div id="bar-chart-contents">
			    	</div>
			    </div>
				<table id="data-table" class="table table-hover table-condensed" style="margin-bottom:100px">
				    <thead><tr class="row"><th>Row</th><th>Col</th><th>Query</th><th>Array</th><th>Score</th></tr>
				    </thead>
			    </table>    
			</div>
			
			
			</div>
			</center>
			
	<script type="text/javascript">
			var maxHeatmapWidth = 460;
			
			$('.domainInput').change(function()"""),format.raw("""{"""),format.raw/*152.40*/("""
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*154.5*/(""");
			
			$('#viewType').change(function()"""),format.raw("""{"""),format.raw/*156.37*/("""
				var viewtype = $('#viewType option:selected');
				
				$('#domainLowInput').val(viewtype.attr('domainLow'));
				$('#domainMedInput').val(viewtype.attr('domainMed'));
				$('#domainHighInput').val(viewtype.attr('domainHigh'));
				
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*164.5*/(""");
			
			$(".color-picker").miniColors("""),format.raw("""{"""),format.raw/*166.35*/("""
			    change: function(hex, rgb) """),format.raw("""{"""),format.raw/*167.36*/(""" 
					$('#dataCombobox').trigger('change');
			    """),format.raw("""}"""),format.raw/*169.9*/("""
			"""),format.raw("""}"""),format.raw/*170.5*/(""");
			
			$(document).ready(function() """),format.raw("""{"""),format.raw/*172.34*/("""
				$('#viewType').trigger('change');
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*175.5*/(""");
			
		    $('#dataCombobox').change(function() """),format.raw("""{"""),format.raw/*177.45*/("""
		    	var viewtype = $('#viewType option:selected');
		    	var plate = $('#dataCombobox option:selected');
		    	
				drawHeatmap("""),format.raw("""{"""),format.raw/*181.18*/("""
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
				"""),format.raw("""}"""),format.raw/*193.6*/(""");
				
				var barChart = drawBarChart("""),format.raw("""{"""),format.raw/*195.34*/("""
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
				"""),format.raw("""}"""),format.raw/*206.6*/(""");		
				
				$('#plateImage')
					.attr('src', plate.attr('image-path'))
					.css("""),format.raw("""{"""),format.raw/*210.12*/(""" height: $('.heatmapSVG').height() - 20,
							width: $('.heatmapSVG').width() - 20"""),format.raw("""}"""),format.raw/*211.45*/(""");
				
				$('#griddedImage').trigger('change');
				
				$('#plateImage').error(function() """),format.raw("""{"""),format.raw/*215.40*/("""
				  $('#plateImage').attr('src', """"),_display_(Seq[Any](/*216.38*/routes/*216.44*/.Assets.at("images/noimage_plate_small.jpeg"))),format.raw/*216.89*/("""");
				"""),format.raw("""}"""),format.raw/*217.6*/(""");
			"""),format.raw("""}"""),format.raw/*218.5*/(""");
			
	</script>
	
	
""")))})))}
    }
    
    def render(job:NSjob) = apply(job)
    
    def f:((NSjob) => play.api.templates.Html) = (job) => apply(job)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:50:38 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/da/dasummary.scala.html
                    HASH: 57d5f57f09238689de9ad765fac1c424a7fb3ba2
                    MATRIX: 761->1|896->13|933->63|978->100|1017->102|1093->143|1107->149|1177->198|1262->248|1276->254|1358->314|1443->364|1457->370|1541->432|1627->482|1642->488|1720->543|1806->593|1821->599|1892->648|1987->707|2002->713|2075->763|2158->810|2173->816|2270->890|2365->949|2380->955|2478->1030|2623->1140|2677->1178|2716->1179|2790->1217|2802->1220|2830->1226|2884->1244|2896->1247|2944->1273|3022->1315|3034->1318|3062->1324|3124->1350|3136->1353|3185->1379|3241->1399|3256->1405|3338->1465|3377->1468|3402->1471|3446->1484|3746->1749|3772->1766|3812->1768|3935->1858|4034->1910|4108->1938|4167->1950|4235->1972|5913->3603|6057->3700|6180->3777|6232->3782|6347->3851|6405->3863|6498->3909|6586->3950|6699->4017|6751->4022|6864->4089|6960->4138|7381->4511|7475->4558|8317->5352|8411->5399|8502->5442|8833->5726|8922->5767|9006->5803|9106->5856|9158->5861|9246->5901|9378->5986|9477->6037|9660->6172|10142->6607|10231->6648|10654->7024|10788->7110|10921->7195|11063->7289|11138->7327|11154->7333|11222->7378|11278->7387|11332->7394
                    LINES: 27->1|33->1|35->5|35->5|35->5|37->7|37->7|37->7|38->8|38->8|38->8|39->9|39->9|39->9|40->10|40->10|40->10|41->11|41->11|41->11|42->12|42->12|42->12|45->15|45->15|45->15|46->16|46->16|46->16|52->22|52->22|52->22|53->23|53->23|53->23|53->23|53->23|53->23|54->24|54->24|54->24|54->24|54->24|54->24|55->25|55->25|55->25|55->25|55->25|56->26|61->31|61->31|61->31|61->31|65->35|67->37|68->38|70->40|112->82|114->84|116->86|116->86|118->88|120->90|122->92|123->93|126->96|126->96|129->99|131->101|154->124|157->127|182->152|184->154|186->156|194->164|196->166|197->167|199->169|200->170|202->172|205->175|207->177|211->181|223->193|225->195|236->206|240->210|241->211|245->215|246->216|246->216|246->216|247->217|248->218
                    -- GENERATED --
                */
            