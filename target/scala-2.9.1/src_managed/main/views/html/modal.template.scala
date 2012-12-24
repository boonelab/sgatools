
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
object modal extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template3[String,String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(modalId: String, headingId: String, bodyId: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.54*/("""

<div id=""""),_display_(Seq[Any](/*3.11*/modalId)),format.raw/*3.18*/("""" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby=""""),_display_(Seq[Any](/*3.90*/headingId)),format.raw/*3.99*/("""" aria-hidden="true">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
      <h3 id=""""),_display_(Seq[Any](/*6.16*/headingId)),format.raw/*6.25*/("""">Heading</h3>
    </div>
    <div class="modal-body" id=""""),_display_(Seq[Any](/*8.34*/bodyId)),format.raw/*8.40*/("""">
    	
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">Close</button>
    </div>
</div>"""))}
    }
    
    def render(modalId:String,headingId:String,bodyId:String) = apply(modalId,headingId,bodyId)
    
    def f:((String,String,String) => play.api.templates.Html) = (modalId,headingId,bodyId) => apply(modalId,headingId,bodyId)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Dec 20 17:12:33 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/modal.scala.html
                    HASH: a8888a7c6314ccad541ae4027677c07637b169be
                    MATRIX: 769->1|898->53|945->65|973->72|1080->144|1110->153|1312->320|1342->329|1436->388|1463->394
                    LINES: 27->1|30->1|32->3|32->3|32->3|32->3|35->6|35->6|37->8|37->8
                    -- GENERATED --
                */
            