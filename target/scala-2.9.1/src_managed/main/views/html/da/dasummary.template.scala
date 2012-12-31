
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
	
	<script type="text/javascript" src=""""),_display_(Seq[Any](/*14.39*/routes/*14.45*/.Assets.at("external/sgatools-analysis/color_picker/jquery.miniColors.js"))),format.raw/*14.119*/(""""></script>
	<link type="text/css" rel="stylesheet" href=""""),_display_(Seq[Any](/*15.48*/routes/*15.54*/.Assets.at("external/sgatools-analysis/color_picker/jquery.miniColors.css"))),format.raw/*15.129*/("""" />
	
	<h2>Data analysis</h2>
	<hr>
	
	<div class="well">
		<select id="dataCombobox" class="input-xlarge">
		"""),_display_(Seq[Any](/*22.4*/for((key,value) <- job.outputFilesMap) yield /*22.42*/{_display_(Seq[Any](format.raw/*22.43*/("""
			<option image-path="/assets/jobs/"""),_display_(Seq[Any](/*23.38*/job/*23.41*/.jobid)),format.raw/*23.47*/("""/ia/input_images/"""),_display_(Seq[Any](/*23.65*/key/*23.68*/.substring(0,key.length-4))),format.raw/*23.94*/("""" 
					gridded-image-path="/assets/jobs/"""),_display_(Seq[Any](/*24.40*/job/*24.43*/.jobid)),format.raw/*24.49*/("""/ia/output_images/masked_"""),_display_(Seq[Any](/*24.75*/key/*24.78*/.substring(0,key.length-4))),format.raw/*24.104*/("""" 
					data-path=""""),_display_(Seq[Any](/*25.18*/routes/*25.24*/.Assets.at(value.replace(Constants.BASE_PUBLIC_DIR+"/", "")))),format.raw/*25.84*/("""">"""),_display_(Seq[Any](/*25.87*/key)),format.raw/*25.90*/("""</option>
		""")))})),format.raw/*26.4*/("""
		</select>
		<select id="viewType" class="input-medium">
			<option name="colonysize" domainHigh=3000 domainMed=1500 domainLow=0>Raw colony sizes</option>
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
	
			
			<div class="conatiner">
			
			<!--Heatmap & Image-->
			<h3 style="margin-bottom:5px">Heatmap</h3>
			<center>
			<div class="row">
				<div id="plateImageContents" class="span6" ><img id="plateImage"></div>
				<div id="heatmapContents" class="span6" ></div>
			</div>
			
			</center>
			<hr><!--Separator-->
			
			
			<style>
				#data-table tbody"""),format.raw("""{"""),format.raw/*126.23*/("""
					height:150px;
  					overflow:auto;
				"""),format.raw("""}"""),format.raw/*129.6*/("""
			</style>
			<!--Histogram-->
			
			<div class="row span12">
				<div id="bar-chart">
					
					<h3>Histogram</h3>
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
			
	<script type="text/javascript">
			var maxHeatmapWidth = 460;
			
			$('.domainInput').change(function()"""),format.raw("""{"""),format.raw/*156.40*/("""
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*158.5*/(""");
			
			$('#viewType').change(function()"""),format.raw("""{"""),format.raw/*160.37*/("""
				var viewtype = $('#viewType option:selected');
				
				$('#domainLowInput').val(viewtype.attr('domainLow'));
				$('#domainMedInput').val(viewtype.attr('domainMed'));
				$('#domainHighInput').val(viewtype.attr('domainHigh'));
				
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*168.5*/(""");
			
			$(".color-picker").miniColors("""),format.raw("""{"""),format.raw/*170.35*/("""
			    change: function(hex, rgb) """),format.raw("""{"""),format.raw/*171.36*/(""" 
					$('#dataCombobox').trigger('change');
			    """),format.raw("""}"""),format.raw/*173.9*/("""
			"""),format.raw("""}"""),format.raw/*174.5*/(""");
			
			$(document).ready(function() """),format.raw("""{"""),format.raw/*176.34*/("""
				$('#viewType').trigger('change');
				$('#dataCombobox').trigger('change');
			"""),format.raw("""}"""),format.raw/*179.5*/(""");
			
		    $('#dataCombobox').change(function() """),format.raw("""{"""),format.raw/*181.45*/("""
		    	var viewtype = $('#viewType option:selected');
		    	var plate = $('#dataCombobox option:selected');
		    	
				drawHeatmap("""),format.raw("""{"""),format.raw/*185.18*/("""
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
				"""),format.raw("""}"""),format.raw/*197.6*/(""");
				
				var barChart = drawBarChart("""),format.raw("""{"""),format.raw/*199.34*/("""
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
				"""),format.raw("""}"""),format.raw/*210.6*/(""");		
				
				$('#plateImage')
					.attr('src', plate.attr('image-path'))
					.css("""),format.raw("""{"""),format.raw/*214.12*/(""" height: $('.heatmapSVG').height() - 20,
							width: $('.heatmapSVG').width() - 20"""),format.raw("""}"""),format.raw/*215.45*/(""");
				
				$('#griddedImage').trigger('change');
				
				$('#plateImage').error(function() """),format.raw("""{"""),format.raw/*219.40*/("""
				  $('#plateImage').attr('src', """"),_display_(Seq[Any](/*220.38*/routes/*220.44*/.Assets.at("images/noimage_plate_small.jpeg"))),format.raw/*220.89*/("""");
				"""),format.raw("""}"""),format.raw/*221.6*/(""");
			"""),format.raw("""}"""),format.raw/*222.5*/(""");
			
	</script>
	
	
""")))})))}
    }
    
    def render(job:NSjob) = apply(job)
    
    def f:((NSjob) => play.api.templates.Html) = (job) => apply(job)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Dec 31 14:00:36 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/da/dasummary.scala.html
                    HASH: 437e812522dd3bed6d4b5174ae88e376a599b5ec
                    MATRIX: 761->1|896->13|933->63|978->100|1017->102|1093->143|1107->149|1177->198|1262->248|1276->254|1358->314|1443->364|1457->370|1541->432|1627->482|1642->488|1720->543|1806->593|1821->599|1892->648|1987->707|2002->713|2075->763|2156->808|2171->814|2268->888|2363->947|2378->953|2476->1028|2623->1140|2677->1178|2716->1179|2790->1217|2802->1220|2830->1226|2884->1244|2896->1247|2944->1273|3022->1315|3034->1318|3062->1324|3124->1350|3136->1353|3185->1379|3241->1399|3256->1405|3338->1465|3377->1468|3402->1471|3446->1484|3748->1751|3774->1768|3814->1770|3937->1860|4036->1912|4110->1940|4169->1952|4237->1974|5915->3605|6059->3702|6182->3779|6234->3784|6349->3853|6407->3865|6500->3911|6588->3952|6701->4019|6753->4024|6866->4091|6962->4140|7442->4572|7536->4619|8380->5415|8474->5462|8565->5505|8896->5789|8985->5830|9069->5866|9169->5919|9221->5924|9309->5964|9441->6049|9540->6100|9723->6235|10205->6670|10294->6711|10717->7087|10851->7173|10984->7258|11126->7352|11201->7390|11217->7396|11285->7441|11341->7450|11395->7457
                    LINES: 27->1|33->1|35->5|35->5|35->5|37->7|37->7|37->7|38->8|38->8|38->8|39->9|39->9|39->9|40->10|40->10|40->10|41->11|41->11|41->11|42->12|42->12|42->12|44->14|44->14|44->14|45->15|45->15|45->15|52->22|52->22|52->22|53->23|53->23|53->23|53->23|53->23|53->23|54->24|54->24|54->24|54->24|54->24|54->24|55->25|55->25|55->25|55->25|55->25|56->26|61->31|61->31|61->31|61->31|65->35|67->37|68->38|70->40|112->82|114->84|116->86|116->86|118->88|120->90|122->92|123->93|126->96|126->96|129->99|131->101|156->126|159->129|186->156|188->158|190->160|198->168|200->170|201->171|203->173|204->174|206->176|209->179|211->181|215->185|227->197|229->199|240->210|244->214|245->215|249->219|250->220|250->220|250->220|251->221|252->222
                    -- GENERATED --
                */
            