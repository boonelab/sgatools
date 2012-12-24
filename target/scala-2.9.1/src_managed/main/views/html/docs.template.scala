
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
object docs extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply():play.api.templates.Html = {
        _display_ {

Seq[Any](_display_(Seq[Any](/*2.2*/main("Help", nav="hp")/*2.24*/ {_display_(Seq[Any](format.raw/*2.26*/("""
    
    <!--<h1>Some help goes here</h1>-->
    <br>
    
	<div class="tabbable tabs-right">
      <ul class="nav nav-tabs">
        <li class="active"><a href="#gs" data-toggle="tab"><i class="icon-bookmark"></i> Getting started</a></li>
        <li><a href="#fn" data-toggle="tab"><i class="icon-bookmark"></i> File naming</a></li>
        <li><a href="#ia" data-toggle="tab"><i class="icon-bookmark"></i> Image analysis</a></li>
        <li><a href="#ns" data-toggle="tab"><i class="icon-bookmark"></i> Normalization / Scoring</a></li>
        <li><a href="#da" data-toggle="tab"><i class="icon-bookmark"></i> Data analysis</a></li>
        <li><a href="#fa" data-toggle="tab"><i class="icon-bookmark"></i> FAQs</a></li>
      </ul>
	
      <div class="tab-content">
        
        <div class="tab-pane active" id="gs">
	       	<div class="page-header">
	        	<h2>Getting started</h2>
	        </div>
			
			Workflow of pipeline<br>
			<img src="http://placehold.it/500x300&text=Workflow+pipeline">
        </div>
        
        <div class="tab-pane" id="fn">
	        <div class="page-header">
	        	<h2>File naming</h2>
	        </div>
	        
	        <div id="file-naming-help">
	        	<p>
	        	In order to properly score your data and utilize array layout files, additional information must be supplied via the file name of the image or dat file you are using.
	        	This information includes
	        	
	        	<ul>
	        		<li>Screen type ― control vs case</li>
	        		<li>Query name / ORF</li>
	        		<li>Array plate id </li>
	        	</ul>
	        	
	        	This information is supplemented through the file name in the following format:
	        	
	        	</p>
	        	
	        	<pre>"""),format.raw("""{"""),format.raw/*48.17*/("""username"""),format.raw("""}"""),format.raw/*48.26*/("""_"""),format.raw("""{"""),format.raw/*48.28*/("""screentype"""),format.raw("""}"""),format.raw/*48.39*/("""_"""),format.raw("""{"""),format.raw/*48.41*/("""query"""),format.raw("""}"""),format.raw/*48.47*/("""_"""),format.raw("""{"""),format.raw/*48.49*/("""arrayplateid"""),format.raw("""}"""),format.raw/*48.62*/(""" …</pre>
	        	
	        	<p>such that the screen type name is prefixed by <code>wt-</code> or <code>ctrl-</code> to indicate a control plate. Anything else will define the plate as a case
	        	
	        	</p>
	        	
	        	<p>For example:</p>
	        	
	        	<ul>
		        	<li>
			        	<pre>michael_double-mutant_YDL108W_1_boone_15-12-12.jpg</pre>
		        		<p>Indicates a <code>case</code> screen carried out by <code>michael</code> with the screen having the query <code>YDL108W</code> and an array plate id of <code>1</code>
		        	</li>
		        	
		        	<li>
			        	<pre>charlie_ctrl_-YOR341W_9_sga_3-10-11.jpg</pre>
		        		<p>Indicates a <code>control</code> screen carried out by <code>charlie</code> with the screen having the query <code>YOR341W</code> and an array plate id of <code>9</code>
	        		</li>
	        	</ul>
	        	
	        	<hr>
	        	<strong>Note:</strong> It is not mandatory to name the files in the mentioned format. Files named with a format other than the one mentioned above will not me mapped a query/array ORF and will not be scored
	        	
	        </div>
        </div>
        
        
		<div class="tab-pane" id="ia">
        	<div class="page-header">
	        	<h2>Image analysis</h2>
	        </div>
	        
	        <p>
	        Image analysis is the first step of the SGAtools pipeline and involves fitting a grid onto your plate images, then using the bounds of the grid to quantify the colony size using pixel intensities
	        </p>
	        
	        <h3 style="text-decoration:underline">Input</h3>
	        <div id="ia-plate-files">
	        	<h4>1. Plate images</h4>
	        	<p>Images of your screen in <code>.jpg</code> format. Images are expected to be an approximate resolution of 160 dots per cm and 
	        	should either be cropped to the size of the experimental plate or have a black background outside of the plate. 
	        	Note: the higher resolution the image is, the more accurate the quantified colony size is.</p>
	        </div>	
	        
	        <div id="ia-plate-format">
	        	<h4>2. Plate format</h4>
	        	<p>SGAtools supports 4 main screen formats</p>
	        	
	        	<table class="table">
	        		<thead>
	        		<tr>
				      <th>Screen format</th>
				      <th>Number of rows</th>
				      <th>Number of columns</th>
				      <th>Example</th>
				    </tr>
				    </thead>
				    <tbody>
					    <tr>
					      <td>1536</td>
					      <td>32</td>
					      <td>48</td>
					      <td><img src="http://placehold.it/200x130&text=1536+plate"></td>
					    </tr>
					    
					     <tr>
					      <td>768 -diagonal replicates</td>
					      <td>16</td>
					      <td>24</td>
					      <td><img src="http://placehold.it/200x130&text=768+plate"></td>
					    </tr>
					    
					    <tr>
					      <td>384</td>
					      <td>16</td>
					      <td>24</td>
					      <td><img src="http://placehold.it/200x130&text=384+plate"></td>
					    </tr>
					    
					    <tr>
					      <td>96</td>
					      <td>8</td>
					      <td>12</td>
					      <td><img src="http://placehold.it/200x130&text=96+plate"></td>
					    </tr>
					</tbody>
	        	</table>
	        </div>
			
	       	<div id="ia-crop-option">
	        	<h4>3. Crop option</h4>
	        	<code>Images already cropped to plate edges</code>
        	</div>
	        
	        
	        <br>
	        <br>
	        <h3 style="text-decoration:underline">Output</h3>
	        <div id="ia-output">
	        	<p>After your images have been processed, you will be directed to a page containing a summary of the analysis.
	        	This page allows you to</p>
	        	Download the quantified colony sizes. The resulting files will be space-delimited and will have the following columns
	        			
	        			<ol>
	        				<li>Row</li>
	        				<li>Column</li>
	        				<li>Quantified colony size</li>
	        				<li>Circularity</li>
	        				<li>Median</li>
	        			</ol>
	        			
	        			For example:
	        			
	        			<pre>1	1	1239	0.821	205
1	2	1087	0.841	196
1	3	966	0.792	191
1	4	955	0.809	194
1	5	1168	0.848	207
...</pre>
	        	
	        	<br>
	        	Review the gridding applied to your images
	        	<br>The gridded image will be displayed. Hover over it for a zoom in on specific colonies to ensure correct gridding
	        	<img src="http://placehold.it/300x200&text=Input+image" style="display:inline"> 
	        	<i class="icon-circle-arrow-right muted" style="font-size:30px;display:inline"></i> 
	        	<img src="http://placehold.it/300x200&text=Gridded+image" style="display:inline">
	        	
	        	<br>
	        	Proceed to normalization
	        	
	        	<p>To proceed to normalization, select the desired plates you would like normalized and/or scored and click normalize and score. 
	        	This will direct you to the normalization & scoring page with your analyzed image data preloaded</p>
	        	
	        </div>
	        
        </div>
        
        <div class="tab-pane" id="ns">
        	<div class="page-header">
	        	<h2>Normalization & Scoring</h2>
	        </div>
	        <h3 style="text-decoration:underline">Input</h3>
	        
	        <div class="ns-plate-files">
	        	<h4>Plate files</h4>
	        	The main input to normalization & scoring is the quantified colony sizes in a space-delimited file with 3 or more columns as follows:
	        	<ol>
    				<li>Row</li>
    				<li>Column</li>
    				<li>Quantified colony size</li>
    			</ol>
    			
    			Comment lines begin with a <code>#</code> and are ignored during the analysis
    			<br>Any additional columns are ignored 
    			
	        	
	        	<pre><comment style="color:#990000"># This is a commented line and is ignored
# rows	columns	size	circularity: </comment>
1	1	2205	0.981750  
1	2	1734	1.065585  
1	3	1996	1.057621  
1	4	1704	1.032656  
1	5	1755	1.109302  
...
	        	</pre>
	        	
	        	Note: In the example above, the circularity column and any column that follows it are ignored
	        </div>
	        
	        
	        <div id="ns-array-layout-file">
	        	<h4>Array layout file</h4>
	        	This file allows a mapping from row and columns to a gene name so that results are more meaningful. There are two ways of selecting an array layout file: <br>
	        	<br>
	        	Selecting a predefined array layout file: from the drop down, you can select predefined array layout files. 
	        	These files are mapped to corresponding files depending on their array plate id supplemented via the file name
	        	
	        	<br>
	        	Uploading a custom array layout file: from the drop down, you can upload your own array layout file which is mapped to <b>all</b> data files.
	        	The typical array definition file is space-delimited and has 3 columns as follows:
	        	
	        	<ol>
    				<li>Column</li>
    				<li>Row</li>
    				<li>Gene name</li>
    			</ol>
    			
    			Comment lines begin with a <code>#</code> and are ignored during the analysis
    			
    			<br>
    			For example:
    			<pre><comment style="color:#990000"># c	r	Gene</comment>
1	1	YDL227C_SN851
2	1	YGR083C_TSQ1941
3	1	YCR035C_TSQ1723
4	1	YBR160W_TSQ1502
5	1	YNL188W_TSQ2267
6	1	YPL210C_TSQ2401
...
    			</pre>
	        </div>
	        
	        <div id="ns-replicates">
	        	<h4>Replicates</h4>
	        	This is the number of replicates in the experiment. 
	        	<br>A value of <code>4</code> indicates replicas are in quadruples <img src="http://placehold.it/20x20">
	        	<br>A value of <code>1</code> indicates there is only one replicate<img src="http://placehold.it/20x20">
	        </div>
	        
	        <div id="ns-linkage-correction">
	        	<h4>Linkage correction</h4>
	        	This step ignores interactions between genes within a specific proximity to one another on the same chromosome from the analysis as it is considered an artifact. 
	        	The proximity is provided in kilobases (KB) as the <b>linkage cutoff</b>  
	        </div>
	        
	         <div id="ns-linkage-correction">
	        	<h4>Score results</h4>
	        	If this option is selected, a score will be computed for non-control plates with a corresponding control plate of the same array plate id.
	        	There are two ways SGAtools can score screens:
	        	
	        	  
	        	<ol>
    				<li><img src="http://latex.codecogs.com/svg.latex?\small%20\inline%20\dpi"""),format.raw("""{"""),format.raw/*270.83*/("""300"""),format.raw("""}"""),format.raw/*270.87*/("""%20C_"""),format.raw("""{"""),format.raw/*270.93*/("""ij"""),format.raw("""}"""),format.raw/*270.96*/("""%20-%20C_"""),format.raw("""{"""),format.raw/*270.106*/("""i"""),format.raw("""}"""),format.raw/*270.108*/("""C_"""),format.raw("""{"""),format.raw/*270.111*/("""j"""),format.raw("""}"""),format.raw/*270.113*/(""""></li>
    				<li><img src="http://latex.codecogs.com/svg.latex?\small%20\inline%20\dpi"""),format.raw("""{"""),format.raw/*271.83*/("""300"""),format.raw("""}"""),format.raw/*271.87*/("""%20C_"""),format.raw("""{"""),format.raw/*271.93*/("""ij"""),format.raw("""}"""),format.raw/*271.96*/("""%20/%20C_"""),format.raw("""{"""),format.raw/*271.106*/("""i"""),format.raw("""}"""),format.raw/*271.108*/("""C_"""),format.raw("""{"""),format.raw/*271.111*/("""j"""),format.raw("""}"""),format.raw/*271.113*/(""""></li>
    			</ol>
    			
    			Such that Cij represents the fitness of the double mutant, Ci the single mutant fitness of the query, Cj the single mutant fitness of the array
	        </div>
	        
	        
	        <br><br>
	        <h3 style="text-decoration:underline">Output</h3>
	        
	        <div id="ns-output">
	        	<p>After your data has been normalized and/or scored, you will be directed to a page containing a summary of the analysis. You will be able to:<br>
	        	
	        	
	        	Download your processed data files<br>
	        	These files will be tab-delimited with 9 columns as follows:
	        	</p>
	        	
	        	
	        	<ol>
    				<li>Row</li>
    				<li>Column</li>
    				<li>Raw colony size</li>
    				<li>Plate id - set as file name</li>
    				<li>Query gene name/ORF</li>
    				<li>Array gene name/ORF</li>
    				<li>Normalized colony size</li>
    				<li>Score</li>
    				<li>Additional information as key-value pairs</li>
    			</ol>
	        	
	        	An example is shown below:
	        	
	        	<pre><comment style="color:#990000">#row	col	size	plateid	query	array	norm	score			kvp</comment>
3	6	196	file-name	Y8835	YBR138C	523.39519285736		-0.0085	NA
3	7	173	file-name	Y8835	YBR028C	448.318881141177	-0.0975	NA
3	8	205	file-name	Y8835	YBR028C	526.612225935		0.05573	NA
3	9	181	file-name	Y8835	YBR137W	NA			NA	"""),format.raw("""{"""),format.raw/*308.42*/("""status=JK"""),format.raw("""}"""),format.raw/*308.52*/("""
3	10	198	file-name	Y8835	YBR137W	520.888524830129	0.06475	NA
3	11	186	file-name	Y8835	YBR027C	489.085823628913	-0.0449	NA
3	12	191	file-name	Y8835	YBR027C	501.445922661162	-0.0207	NA
3	13	172	file-name	Y8835	YBR134W	NA			NA	"""),format.raw("""{"""),format.raw/*312.43*/("""status=JK"""),format.raw("""}"""),format.raw/*312.53*/("""
3	14	204	file-name	Y8835	YBR134W	534.838411726621	0.02867	NA
...
</pre>

			Additional information returned from SGAtools includes status codes for colonies that did not meet a filter. The codes and their corresponding descriptions are listed below:
			
			<table class="table table-condensed">
			  <thead>
			    <tr>
			      <th>Status code</th>
			      <th>Description</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<tr>
			      <td>LK</td>
			      <td>Linkage correction description</td>
			    </tr>
			    <tr>
			      <td>JK</td>
			      <td>Jackknife filter description</td>
			    </tr>
			    <tr>
			      <td>BG</td>
			      <td>Big replicates description</td>
			    </tr>
			    <tr>
			      <td>CP</td>
			      <td>Cap filter</td>
			    </tr>
			  </tbody>
			</table>
			
			
			Visualize your results<br>
			To proceed to data analysis, click data analysis. This will direct you to the data analysis page
			
			
	        </div>
        </div>
        
        <div class="tab-pane" id="da">
          	<div class="page-header">
	        	<h2>Data analysis</h2>
	        </div>
        </div>
        
        <div class="tab-pane" id="fa">
          	<div class="page-header">
	        	<h2>FAQs</h2>
	        </div>
        </div>
      </div>
    </div>
""")))})))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Dec 23 15:49:46 EST 2012
                    SOURCE: /Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/app/views/docs.scala.html
                    HASH: 999bf3f71d73ca3a91eb6befa0ecf13c38bad410
                    MATRIX: 827->2|857->24|896->26|2692->1775|2748->1784|2797->1786|2855->1797|2904->1799|2957->1805|3006->1807|3066->1820|11681->10387|11733->10391|11787->10397|11838->10400|11897->10410|11948->10412|12000->10415|12051->10417|12189->10507|12241->10511|12295->10517|12346->10520|12405->10530|12456->10532|12508->10535|12559->10537|14009->11939|14067->11949|14341->12175|14399->12185
                    LINES: 30->2|30->2|30->2|76->48|76->48|76->48|76->48|76->48|76->48|76->48|76->48|298->270|298->270|298->270|298->270|298->270|298->270|298->270|298->270|299->271|299->271|299->271|299->271|299->271|299->271|299->271|299->271|336->308|336->308|340->312|340->312
                    -- GENERATED --
                */
            