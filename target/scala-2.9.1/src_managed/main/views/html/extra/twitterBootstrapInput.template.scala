
package views.html.extra

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
object twitterBootstrapInput extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[helper.FieldElements,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(elements: helper.FieldElements):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.34*/("""
<div class="control-group 
	"""),_display_(Seq[Any](/*3.3*/if(elements.hasErrors)/*3.25*/ {_display_(Seq[Any](format.raw/*3.27*/("""error""")))})),format.raw/*3.33*/("""" 
	"""),_display_(Seq[Any](/*4.3*/if(elements.args.contains('height))/*4.38*/{_display_(Seq[Any](format.raw/*4.39*/("""style="height:"""),_display_(Seq[Any](/*4.54*/elements/*4.62*/.args.get('height))),format.raw/*4.80*/(""""""")))})),format.raw/*4.82*/("""
	id="cg-"""),_display_(Seq[Any](/*5.10*/elements/*5.18*/.id)),format.raw/*5.21*/("""">
	
	<label """),_display_(Seq[Any](/*7.10*/if(elements.args.contains('helpModal))/*7.48*/{_display_(Seq[Any](format.raw/*7.49*/("""onClick="labelHelpClicked('"""),_display_(Seq[Any](/*7.77*/elements/*7.85*/.args.get('helpModal))),format.raw/*7.106*/("""')" data-toggle="modal" data-target="#helpModal"""")))})),format.raw/*7.155*/("""
		class="control-label" for=""""),_display_(Seq[Any](/*8.31*/elements/*8.39*/.id)),format.raw/*8.42*/("""">
		
		
		"""),_display_(Seq[Any](/*11.4*/if(elements.args.contains('helpModal))/*11.42*/{_display_(Seq[Any](format.raw/*11.43*/(""" <abbr title=""> """)))})),format.raw/*11.61*/("""
		"""),_display_(Seq[Any](/*12.4*/elements/*12.12*/.label)),format.raw/*12.18*/("""
		"""),_display_(Seq[Any](/*13.4*/if(elements.args.contains('helpModal))/*13.42*/{_display_(Seq[Any](format.raw/*13.43*/(""" </abbr> """)))})),format.raw/*13.53*/("""
	</label>
	
	<div class="controls">
	
		"""),_display_(Seq[Any](/*18.4*/if(elements.args.contains('uploadwidget))/*18.45*/{_display_(Seq[Any](format.raw/*18.46*/("""
				<div style="position:absolute; top:-100px;">"""),_display_(Seq[Any](/*19.50*/elements/*19.58*/.input)),format.raw/*19.64*/("""</div>
				<input type="button" class="btn" id=""""),_display_(Seq[Any](/*20.43*/elements/*20.51*/.id)),format.raw/*20.54*/("""-btn" value=""""),_display_(Seq[Any](/*20.68*/elements/*20.76*/.args.get('uploadwidget))),format.raw/*20.100*/(""""/>
				<span class="help-inline" id=""""),_display_(Seq[Any](/*21.36*/elements/*21.44*/.id)),format.raw/*21.47*/("""-span">"""),_display_(Seq[Any](/*21.55*/if(elements.args.contains('uploadhelp))/*21.94*/{_display_(Seq[Any](format.raw/*21.95*/(""" """),_display_(Seq[Any](/*21.97*/elements/*21.105*/.args.get('uploadhelp))),format.raw/*21.127*/(""" """)))})),format.raw/*21.129*/("""</span>
				<script>
					$('#"""),_display_(Seq[Any](/*23.11*/elements/*23.19*/.id)),format.raw/*23.22*/("""-btn').click(function()"""),format.raw("""{"""),format.raw/*23.46*/("""
						$('#"""),_display_(Seq[Any](/*24.12*/elements/*24.20*/.id)),format.raw/*24.23*/("""').trigger('click');
					"""),format.raw("""}"""),format.raw/*25.7*/(""");
					$('#"""),_display_(Seq[Any](/*26.11*/elements/*26.19*/.id)),format.raw/*26.22*/("""').change(function()"""),format.raw("""{"""),format.raw/*26.43*/("""
						var n = $('#"""),_display_(Seq[Any](/*27.20*/elements/*27.28*/.id)),format.raw/*27.31*/("""')[0].files.length;
						if(n == 1) """),format.raw("""{"""),format.raw/*28.19*/("""
							$('#"""),_display_(Seq[Any](/*29.13*/elements/*29.21*/.id)),format.raw/*29.24*/("""-span').html($('#"""),_display_(Seq[Any](/*29.42*/elements/*29.50*/.id)),format.raw/*29.53*/("""')[0].files[0].name);
						"""),format.raw("""}"""),format.raw/*30.8*/("""else if(n > 1)"""),format.raw("""{"""),format.raw/*30.23*/("""
							$('#"""),_display_(Seq[Any](/*31.13*/elements/*31.21*/.id)),format.raw/*31.24*/("""-span').html(n + " files selected");
						"""),format.raw("""}"""),format.raw/*32.8*/("""
						
						if(n > 0)"""),format.raw("""{"""),format.raw/*34.17*/("""
							$('#"""),_display_(Seq[Any](/*35.13*/elements/*35.21*/.id)),format.raw/*35.24*/("""-btn').val("Change"); 
						"""),format.raw("""}"""),format.raw/*36.8*/("""else"""),format.raw("""{"""),format.raw/*36.13*/("""
							$('#"""),_display_(Seq[Any](/*37.13*/elements/*37.21*/.id)),format.raw/*37.24*/("""-btn').val(""""),_display_(Seq[Any](/*37.37*/elements/*37.45*/.args.get('uploadwidget))),format.raw/*37.69*/("""");
							$('#"""),_display_(Seq[Any](/*38.13*/elements/*38.21*/.id)),format.raw/*38.24*/("""-span').html('');
						"""),format.raw("""}"""),format.raw/*39.8*/("""
					"""),format.raw("""}"""),format.raw/*40.7*/(""");
				</script>
				
				"""),_display_(Seq[Any](/*43.6*/if(elements.hasErrors)/*43.28*/ {_display_(Seq[Any](format.raw/*43.30*/("""
					<span class="help-block" >"""),_display_(Seq[Any](/*44.33*/elements/*44.41*/.errors.mkString(", "))),format.raw/*44.63*/("""</span>
				""")))})),format.raw/*45.6*/("""
		""")))}/*46.4*/else/*46.8*/{_display_(Seq[Any](format.raw/*46.9*/("""
			"""),_display_(Seq[Any](/*47.5*/if(elements.args.contains('append))/*47.40*/{_display_(Seq[Any](format.raw/*47.41*/("""<div class="input-append">""")))})),format.raw/*47.68*/("""
			"""),_display_(Seq[Any](/*48.5*/elements/*48.13*/.input)),format.raw/*48.19*/("""
			"""),_display_(Seq[Any](/*49.5*/if(elements.args.contains('append))/*49.40*/{_display_(Seq[Any](format.raw/*49.41*/("""</div>""")))})),format.raw/*49.48*/("""
			
			<!--
			<span class="help-inline">"""),_display_(Seq[Any](/*52.31*/elements/*52.39*/.infos.mkString(", "))),format.raw/*52.60*/("""
			</span>-->
			
			"""),_display_(Seq[Any](/*55.5*/if(elements.hasErrors)/*55.27*/ {_display_(Seq[Any](format.raw/*55.29*/("""
					<span class="help-block" style="margin-left:5px;padding-top:5px">"""),_display_(Seq[Any](/*56.72*/elements/*56.80*/.errors.mkString(", "))),format.raw/*56.102*/("""</span>
			""")))})),format.raw/*57.5*/("""
  			
		""")))})),format.raw/*59.4*/("""
		
		
		
  		
		"""),_display_(Seq[Any](/*64.4*/if(elements.args.contains('appendHTML))/*64.43*/{_display_(Seq[Any](format.raw/*64.44*/("""
			<div id="list"></div>
		""")))})),format.raw/*66.4*/("""
  	</div>
</div>
"""))}
    }
    
    def render(elements:helper.FieldElements) = apply(elements)
    
    def f:((helper.FieldElements) => play.api.templates.Html) = (elements) => apply(elements)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 19 13:47:31 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/extra/twitterBootstrapInput.scala.html
                    HASH: 902dba4d9b44b138a5aebb1b5a2836c7c654233a
                    MATRIX: 791->1|900->33|964->63|994->85|1033->87|1070->93|1109->98|1152->133|1190->134|1240->149|1256->157|1295->175|1328->177|1373->187|1389->195|1413->198|1462->212|1508->250|1546->251|1609->279|1625->287|1668->308|1749->357|1815->388|1831->396|1855->399|1902->411|1949->449|1988->450|2038->468|2077->472|2094->480|2122->486|2161->490|2208->528|2247->529|2289->539|2366->581|2416->622|2455->623|2541->673|2558->681|2586->687|2671->736|2688->744|2713->747|2763->761|2780->769|2827->793|2902->832|2919->840|2944->843|2988->851|3036->890|3075->891|3113->893|3131->901|3176->923|3211->925|3278->956|3295->964|3320->967|3391->991|3439->1003|3456->1011|3481->1014|3554->1041|3603->1054|3620->1062|3645->1065|3713->1086|3769->1106|3786->1114|3811->1117|3896->1155|3945->1168|3962->1176|3987->1179|4041->1197|4058->1205|4083->1208|4158->1237|4220->1252|4269->1265|4286->1273|4311->1276|4401->1320|4472->1344|4521->1357|4538->1365|4563->1368|4639->1398|4691->1403|4740->1416|4757->1424|4782->1427|4831->1440|4848->1448|4894->1472|4946->1488|4963->1496|4988->1499|5059->1524|5112->1531|5174->1558|5205->1580|5245->1582|5314->1615|5331->1623|5375->1645|5419->1658|5441->1662|5453->1666|5491->1667|5531->1672|5575->1707|5614->1708|5673->1735|5713->1740|5730->1748|5758->1754|5798->1759|5842->1794|5881->1795|5920->1802|5999->1845|6016->1853|6059->1874|6117->1897|6148->1919|6188->1921|6296->1993|6313->2001|6358->2023|6401->2035|6442->2045|6495->2063|6543->2102|6582->2103|6642->2132
                    LINES: 27->1|30->1|32->3|32->3|32->3|32->3|33->4|33->4|33->4|33->4|33->4|33->4|33->4|34->5|34->5|34->5|36->7|36->7|36->7|36->7|36->7|36->7|36->7|37->8|37->8|37->8|40->11|40->11|40->11|40->11|41->12|41->12|41->12|42->13|42->13|42->13|42->13|47->18|47->18|47->18|48->19|48->19|48->19|49->20|49->20|49->20|49->20|49->20|49->20|50->21|50->21|50->21|50->21|50->21|50->21|50->21|50->21|50->21|50->21|52->23|52->23|52->23|52->23|53->24|53->24|53->24|54->25|55->26|55->26|55->26|55->26|56->27|56->27|56->27|57->28|58->29|58->29|58->29|58->29|58->29|58->29|59->30|59->30|60->31|60->31|60->31|61->32|63->34|64->35|64->35|64->35|65->36|65->36|66->37|66->37|66->37|66->37|66->37|66->37|67->38|67->38|67->38|68->39|69->40|72->43|72->43|72->43|73->44|73->44|73->44|74->45|75->46|75->46|75->46|76->47|76->47|76->47|76->47|77->48|77->48|77->48|78->49|78->49|78->49|78->49|81->52|81->52|81->52|84->55|84->55|84->55|85->56|85->56|85->56|86->57|88->59|93->64|93->64|93->64|95->66
                    -- GENERATED --
                */
            