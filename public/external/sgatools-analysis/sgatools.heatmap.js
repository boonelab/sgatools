window.firstCallHm = [];

// Popup
var tooltip = d3.select("body")
	.attr('id', 'tooltip')
	.append('div')
	.style('position','absolute')
	.style('background','#FFF')
	.style('opacity',0.9)
	.style('top',"0px")
	.style('padding',10)
	.style('left',"40px")
	.style('box-shadow', '2px 2px 2px 2px #8b8b8b')//Adjust for cross browser later
	.style('display','none');

var tooltip = d3.select("body")
	.attr('id', 'tooltip')
	.append('div')
	.style('position','absolute')
	.style('border','1px solid #bbbbbb')
	.style('color','black')
	.style('background-color','white')
	.style('box-shadow','0 2px 4px rgba(0,0,0,0.3)')
	.style('font-family', 'Arial, Helvetical, sans-serif')
	.style('max-width','160px')
	.style('top',"0px")
	.style('padding','5px')
	.style('left',"40px")
	.style('display','none');



function pathExists(url){
    var http = new XMLHttpRequest();
    http.open('HEAD', url, false);
    http.send();
    return http.status!=404;
}

function drawHeatmap(paramsInput){
	// set up the defaults;
    var params = {
        'gridSize'   : 13, 			//Height/width of each cell in heatmap
        'maxwidth'  : 440, 			//Height/width of each cell in heatmap
        'domainLow'  : -1,			//Lower extreme of numerical values
        'domainMed'  : 0,			//Medium extreme of numerical values
        'domainHigh' : 1,			//Upper extreme of numerical values
        'columnToUse': 'score',
        'colorLow'   : 'red',		//Low color
        'colorMed'   : 'black',		//Medium color
        'colorHigh'  : 'green', 	//High color
        'colorNA'    : 'black',		//Non-numerical color
        'heatmapContainer'   : 'body'		//Selector to which heatmap is added to
    };
    // Extend optional parameters
    $.extend(params,paramsInput);
    
    //console.log(params);
	
	//Replaced d3.tsv(params.dataPath, sgatools)
    d3.text(params.dataPath, 'text/tsv',function(tsv){
    	tsv = tsv.replace(/^[#@][^\r\n]+[\r\n]+/mg, '');
    	tsv = "row	col	colonysize	plateid	query	array	ncolonysize	score	kvp\n" + tsv;
    	sgadata = d3.tsv.parse(tsv);
		sgadata = sgadata.filter(function(d,i){
			d.query = d.query.split('_')[0];
			d.array = d.array.split('_')[0];
			
			d.queryStandard = d.query;
			d.arrayStandard = d.array;
			
			//Try map query and array names from systematic name to standard name
			q = genemap[d.query];
			a = genemap[d.array];
			
			if(q != undefined){
				 d.query = d.query + " (" + q +")";
				 d.queryStandard = q;
			}
			if(a != undefined){
				d.array = d.array + " (" + a +")";
				d.arrayStandard = a;
			} 
			
			val = +d[params.columnToUse];
			d.value = +val.toFixed(2);
			
			if(d.kvp == "NA"){
				d.kvp = "";
			}else{
				d.kvp = d.kvp.split('=')[1];
			}
			return true;
		});
       //console.log(sgadata);
       // Clear contents of any previous heatmap
       $(params.heatmapContainer).empty()
       
       var rowNest = d3.nest().key(function(d) { return d.row; }).entries(sgadata);
	   var colNest = d3.nest().key(function(d) { return d.col; }).entries(sgadata);
	   
	   // Number of rows and column on plate
	   var nRows = rowNest.length,
		   nCols = colNest.length;
	   
	   var gridSize = params.gridSize;
	   
	   //Hack grid size to fit container
	   gridSize = Math.floor(params.maxwidth / nCols);
	   console.log("gridSize="+gridSize);
	   
	    
	   // Height/width of each row in the heatmap
	   var h = gridSize,
	   	   w = gridSize;
	   
	   var width = (gridSize * nCols)+20,
	   	   height = (gridSize * nRows)+20;

	  //Adjust color scale to be low/med/high
	  t = params.dataPath + "_" + params.columnToUse;
	  if($.inArray(t, firstCallHm) < 0){
	  	maxVal = d3.max(sgadata, function(d) { return d.value; });
	  	minVal = d3.min(sgadata, function(d) { return d.value; });
	  	medVal = d3.median(sgadata, function(d) { return d.value; });
	  	params.domainLow = minVal;
	  	params.domainMed = medVal;
	  	params.domainHigh = maxVal;
	  	firstCallHm.push(t);
	  }
	  $('#domainLowInput').val(params.domainLow);
	  $('#domainMedInput').val(params.domainMed);
	  $('#domainHighInput').val(params.domainHigh);
	  
	   // Define color scale
	   var colorScale = d3.scale.linear()
	   		.domain([params.domainLow, params.domainMed, params.domainHigh])
	   		.range([params.colorLow, params.colorMed, params.colorHigh]);
	   
	   // Create SVG centered
	   var svg = d3.select(params.heatmapContainer)
	   		//.append("center")
	   		.append("svg")
	   		.attr("class", "heatmapSVG")
	   		.attr("width", width)
	   		.attr("height", height)
	   		//.attr("style", "margin-left:auto; margin-right:auto;display:block")
	   		.append("g");
	   
	   // Create initial heatmap
	   var heatMap = svg.selectAll(".heatmap")
	   		.data(sgadata, function(d) { return d.row - 1 + ':' + d.col - 1; })
	   		.enter().append("svg:rect")
	   		.attr("font-size", "5px")
	   		.attr("x", function(d) { return (d.col-1) * w; })
	   		.attr("y", function(d) { return (d.row-1) * h; })
	   		.attr("width", function(d) { return w; })
	   		.attr("height", function(d) { return h; })
	   		.attr("style", "shape-rendering: crispEdges;")
	   		.attr("stroke", "black")
	   		.attr("stroke-width", 1)
	   		.style("fill", function(d) { return isNaN(+d[params.columnToUse]) ? params.colorNA : colorScale(d[params.columnToUse]); });
	   	
	   var rowLabel = svg.selectAll(".rowLabel")
	   		.data(rowNest)
	   		.enter().append('svg:text')
	   		.attr("font-size", "5px")
	   		.attr('x', gridSize * nCols + 5)
	   		.attr('y', function(d,i) { return ((i+.75) * gridSize) ;})
	   		.attr('class','label')
	   		.attr("text-anchor", "start")
	   		.text(function(d) {return d.key;});
	   
	   var columnLabel = svg.selectAll(".colLabel")
	   		.data(colNest)
	   		.enter().append('svg:text')
	   		.attr("font-size", "5px")
	   		.attr('x', function(d,i) {return ((i+0.4) * w);})
	   		.attr('y', gridSize * nRows + 7)
	   		.attr('class','label')
	   		.style('text-anchor','start')
	   		.attr("transform", function(d, i) {
			return "rotate(90 " + ((i + 0.4) * w) + ","+(gridSize * nRows + 7)+")"; })
			.text(function(d) {return d.key;});
		
		$('#plateImage')
				.css({ height: $('.heatmapSVG').height() - 20,
						width: $('.heatmapSVG').width() - 20});
						
	    heatMap.on('mousemove', function(d) {
		    	d3.select(this)
		    		.attr('stroke-width',1)
		    		.attr('stroke','yellow')
		    	
		    	
		    	tab = '<center><table class="hm-popup-table">'+
		    		  '<tr><td>Value</td><td>'+d.value+'</td></tr>'+
		    		  '<tr><td>Row</td><td>'+d.row+'</td></tr>'+
		    		  '<tr><td>Column</td><td>'+d.col+'</td></tr>';
		    	
		    	if(d.kvp != ""){
		    		tab = tab+'<tr><td>Status code</td><td>'+d.kvp+'</td></tr>';
		    	}
		    	
		    	tab = tab+'</table></center>';
		    	output = '<div style="text-align:center"><p class="hm-popup-value">'+
		    			 d.query+ '</p>'+
		    			 '<p class="hm-popup-key" style="margin:0">and</p>'+
		    			 '<p class="hm-popup-value">'+ d.array +'</p></div>'+
		    			 '<hr style="margin:3px">'+tab;
		    	
		    	//var x = window.event.clientX  + $(window).scrollLeft();
		    	//var y = window.event.clientY + $(window).scrollTop();
		    	var m = d3.mouse(d3.select('html')[0][0]);
		    	var x = m[0];
		    	var y = m[1];
		    	
		    	x = (x+10) + "px"
		    	y = (y+10) + "px"
		    	tooltip
		    		.style('top',y)
		    		.style('left',x)
		    		.style('display','block')
		    		.html(output);
		   }).on('mouseout', function(d,i) {
				d3.select(this)
		    		.attr('stroke-width',1)
					.attr('stroke','#000');
				
				tooltip.style('display','none')
		});
	})

}
