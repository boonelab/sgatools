
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
object customFileChooserInput extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[helper.FieldElements,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(elements: helper.FieldElements):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.34*/("""
<div class="control-group """),_display_(Seq[Any](/*2.28*/if(elements.hasErrors)/*2.50*/ {_display_(Seq[Any](format.raw/*2.52*/("""error""")))})),format.raw/*2.58*/("""" id="cg-"""),_display_(Seq[Any](/*2.68*/elements/*2.76*/.id)),format.raw/*2.79*/("""">
	
	<label """),_display_(Seq[Any](/*4.10*/if(elements.args.contains('helpModal))/*4.48*/{_display_(Seq[Any](format.raw/*4.49*/("""onClick="labelHelpClicked('"""),_display_(Seq[Any](/*4.77*/elements/*4.85*/.args.get('helpModal))),format.raw/*4.106*/("""')" data-toggle="modal" data-target="#helpModal"""")))})),format.raw/*4.155*/("""
		class="control-label" for=""""),_display_(Seq[Any](/*5.31*/elements/*5.39*/.id)),format.raw/*5.42*/("""">
		
		
		"""),_display_(Seq[Any](/*8.4*/if(elements.args.contains('helpModal))/*8.42*/{_display_(Seq[Any](format.raw/*8.43*/(""" <abbr title=""> """)))})),format.raw/*8.61*/("""
		"""),_display_(Seq[Any](/*9.4*/elements/*9.12*/.label)),format.raw/*9.18*/("""
		"""),_display_(Seq[Any](/*10.4*/if(elements.args.contains('helpModal))/*10.42*/{_display_(Seq[Any](format.raw/*10.43*/(""" </abbr> """)))})),format.raw/*10.53*/("""
	</label>
	
	<div class="controls">
			<div class="fileupload fileupload-new" data-provides="fileupload">
				<div class="input-append">
					<div id=""""),_display_(Seq[Any](/*16.16*/elements/*16.24*/.id)),format.raw/*16.27*/("""-text" class="fileupload-preview uneditable-input" style=""""),_display_(Seq[Any](/*16.86*/if(elements.hasErrors)/*16.108*/{_display_(Seq[Any](format.raw/*16.109*/("""border: 1px solid #b94a48;""")))})),format.raw/*16.136*/(""" width:235px; color:#555555"></div>
					<span class="btn btn-file" style=""""),_display_(Seq[Any](/*17.41*/if(elements.hasErrors)/*17.63*/{_display_(Seq[Any](format.raw/*17.64*/("""border-top: 1px solid #b94a48;border-right: 1px solid #b94a48;border-bottom: 1px solid #b94a48;""")))})),format.raw/*17.160*/("""">
						<span class="fileupload-new" >Select file</span>
						<span class="fileupload-exists">Change</span>
						"""),_display_(Seq[Any](/*20.8*/elements/*20.16*/.input)),format.raw/*20.22*/("""
						
					</span>
					
				</div>
				"""),_display_(Seq[Any](/*25.6*/if(elements.hasErrors)/*25.28*/ {_display_(Seq[Any](format.raw/*25.30*/("""
					<span class="help-block">"""),_display_(Seq[Any](/*26.32*/elements/*26.40*/.errors.mkString(", "))),format.raw/*26.62*/("""</span>
				""")))})),format.raw/*27.6*/("""
			</div>
			
			
			<!--<script type="text/javascript">$(".fileupload").fileupload(name=""""),_display_(Seq[Any](/*31.74*/elements/*31.82*/.id)),format.raw/*31.85*/("""")</script>-->
		
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
                    DATE: Thu Dec 20 17:12:34 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/extra/customFileChooserInput.scala.html
                    HASH: b7757cd794107d9dd987de2fe07d72552e9d5f98
                    MATRIX: 792->1|901->33|964->61|994->83|1033->85|1070->91|1115->101|1131->109|1155->112|1204->126|1250->164|1288->165|1351->193|1367->201|1410->222|1491->271|1557->302|1573->310|1597->313|1643->325|1689->363|1727->364|1776->382|1814->386|1830->394|1857->400|1896->404|1943->442|1982->443|2024->453|2213->606|2230->614|2255->617|2350->676|2382->698|2422->699|2482->726|2594->802|2625->824|2664->825|2793->921|2945->1038|2962->1046|2990->1052|3068->1095|3099->1117|3139->1119|3207->1151|3224->1159|3268->1181|3312->1194|3440->1286|3457->1294|3482->1297
                    LINES: 27->1|30->1|31->2|31->2|31->2|31->2|31->2|31->2|31->2|33->4|33->4|33->4|33->4|33->4|33->4|33->4|34->5|34->5|34->5|37->8|37->8|37->8|37->8|38->9|38->9|38->9|39->10|39->10|39->10|39->10|45->16|45->16|45->16|45->16|45->16|45->16|45->16|46->17|46->17|46->17|46->17|49->20|49->20|49->20|54->25|54->25|54->25|55->26|55->26|55->26|56->27|60->31|60->31|60->31
                    -- GENERATED --
                */
            