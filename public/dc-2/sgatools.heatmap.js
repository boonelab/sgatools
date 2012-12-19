// Popup
var tooltip = d3.select("body")
	.append('div')
	.style('position','absolute')
	.style('background','#FFF')
	.style('opacity',0.9)
	.style('top',0)
	.style('padding',10)
	.style('left',40)
	.style('box-shadow', '2px 2px 2px 2px #8b8b8b')//Adjust for cross browser later
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
        'domainLow'  : -1,			//Lower extreme of numerical values
        'domainMed'  : 0,			//Medium extreme of numerical values
        'domainHigh' : 1,			//Upper extreme of numerical values
        'colorLow'   : 'red',		//Low color
        'colorMed'   : 'black',		//Medium color
        'colorHigh'  : 'green', 	//High color
        'colorNA'    : 'gray',		//Non-numerical color
        'heatmapContainer'   : 'body'		//Selector to which heatmap is added to
    };
    // Extend optional parameters
    $.extend(params,paramsInput);
    
      
	
    d3.csv(params.dataPath, function(sgadata){
       
       // Clear contents of any previous heatmap
       $(params.heatmapContainer).empty()

	   
	   var gridSize = params.gridSize;
	   
	   // Height/width of each row in the heatmap
	   var h = params.gridSize,
	   	   w = params.gridSize;
	   
	   	   
	   var rowNest = d3.nest().key(function(d) { return d.row; }).entries(sgadata);
	   var colNest = d3.nest().key(function(d) { return d.col; }).entries(sgadata);
	   
	   // Number of rows and column on plate
	   var nRows = rowNest.length,
		   nCols = colNest.length;
		   
	   
	   var width = (gridSize * nCols)+50,
	   	   height = (gridSize * nRows)+50;

	  
	   // Define color scale
	   var colorScale = d3.scale.linear()
	   		.domain([params.domainLow, params.domainMed, params.domainHigh])
	   		.range([params.colorLow, params.colorMed, params.colorHigh]);
	   
	   // Create SVG centered
	   var svg = d3.select(params.heatmapContainer)
	   		.append("center")
	   		.append("svg")
	   		.attr("width", width)
	   		.attr("height", height)
	   		.attr("style", "margin-left:auto; margin-right:auto;display:block")
	   		.append("g");
	   
	   // Create initial heatmap
	   var heatMap = svg.selectAll(".heatmap")
	   		.data(sgadata, function(d) { return d.row - 1 + ':' + d.col - 1; })
	   		.enter().append("svg:rect")
	   		.attr("x", function(d) { return (d.col-1) * w; })
	   		.attr("y", function(d) { return (d.row-1) * h; })
	   		.attr("width", function(d) { return w; })
	   		.attr("height", function(d) { return h; })
	   		.attr("style", "shape-rendering: crispEdges;")
	   		.attr("stroke", "black")
	   		.attr("stroke-width", 1)
	   		.style("fill", function(d) { return isNaN(+d.score) ? params.colorNA : colorScale(d.score); });
	   	
	   var rowLabel = svg.selectAll(".rowLabel")
	   		.data(rowNest)
	   		.enter().append('svg:text')
	   		.attr('x', gridSize * nCols + 5)
	   		.attr('y', function(d,i) { return ((i+.75) * gridSize) ;})
	   		.attr('class','label')
	   		.attr("text-anchor", "start")
	   		.text(function(d) {return d.key;});
	   
	   var columnLabel = svg.selectAll(".colLabel")
	   		.data(colNest)
	   		.enter().append('svg:text')
	   		.attr('x', function(d,i) {return ((i+0.4) * w);})
	   		.attr('y', gridSize * nRows + 7)
	   		.attr('class','label')
	   		.style('text-anchor','start')
	   		.attr("transform", function(d, i) {
			return "rotate(90 " + ((i + 0.4) * w) + ","+(gridSize * nRows + 7)+")"; })
			.text(function(d) {return d.key;});
	
	    heatMap.on('mousemove', function(d) {
		    	d3.select(this)
		    		.attr('stroke-width',1)
		    		.attr('stroke','yellow')
		    	
		    	output = d.query+ ' and '+ d.array +'<br>'+d.score;
		   
		    	//var x = window.event.clientX  + $(window).scrollLeft();
		    	//var y = window.event.clientY + $(window).scrollTop();
		    	var m = d3.mouse(d3.select('html')[0][0])
		    	var x = m[0] ;
		    	var y = m[1];
		    	console.log(+x+'  '+y)
		    	tooltip
		    		.style('top',y + 10)
		    		.style('left',x + 10)
		    		.style('display','block')
		    		.html(output);
		   }).on('mouseout', function(d,i) {
				d3.select(this)
		    		.attr('stroke-width',1)
					.attr('stroke','#000');
				
				tooltip
					.style('display','none')
		});
	})

}

/*
var rowNest, colNest;

d3.csv('sgadata-real.csv', function(sgadata){
	   //height of each row in the heatmap
	   //width of each column in the heatmap
	   var gridSize = 10,
	   h = gridSize,
	   w = gridSize;
	   
	   var colorLow = 'red', colorMed = 'black', colorHigh = 'green';
	   
	   
	   rowNest = d3.nest().key(function(d) { return d.row; }).entries(sgadata);
	   colNest = d3.nest().key(function(d) { return d.col; }).entries(sgadata);
	   var nRows = rowNest.length,
		   nCols = colNest.length;
	   
	   var width = (gridSize * nCols)+50,
	   height = (gridSize * nRows)+50;
	   
	   //Empty heatmap
	   $('#heatmap').empty()
	   
	   var colorScale = d3.scale.linear()
	   .domain([-1, 0, 1])
	   .range([colorLow, colorMed, colorHigh]);
	   
	   var svg = d3.select("#heatmap").append("center").append("svg")
	   .attr("width", width)
	   .attr("height", height)
	   .attr("style", "margin-left:auto; margin-right:auto;display:block")
	   .append("g");
	   
	   var heatMap = svg.selectAll(".heatmap")
	   .data(sgadata, function(d) { return d.row-1 + ':' + d.col-1; })
	   .enter().append("svg:rect")
	   .attr("x", function(d) { return (d.col-1) * w; })
	   .attr("y", function(d) { return (d.row-1) * h; })
	   .attr("width", function(d) { return w; })
	   .attr("height", function(d) { return h; })
	   .attr("style", "shape-rendering: crispEdges;")
	   .style("fill", function(d) { return isNaN(+d.score) ? 'gray' : colorScale(d.score); });
	   				   
	   var rowLabel = svg.selectAll(".rowLabel")
	   .data(rowNest)
	   .enter().append('svg:text')
	   .attr('x', gridSize * nCols + 5)
	   .attr('y', function(d,i) { return ((i+.75) * gridSize) ;})
	   .attr('class','label')
	   .attr("text-anchor", "start")
	   .text(function(d) {return d.key;});
	   
	   var columnLabel = svg.selectAll(".colLabel")
	   .data(colNest)
	   .enter().append('svg:text')
	   .attr('x', function(d,i) {return ((i+0.4) * w);})
	   .attr('y', gridSize * nRows + 7)
	   .attr('class','label')
	   .style('text-anchor','start')
	   .attr("transform", function(d, i) {
			return "rotate(90 " + ((i + 0.4) * w) + ","+(gridSize * nRows + 7)+")"; })
	   .text(function(d) {return d.key;});
	   
	   heatMap
	   .on('mousemove', function(d) {
		   d3.select(this)
		   .attr('stroke-width',2)
		   .attr('stroke','yellow')
		   
		   output = d.row+ ' and '+ d.col +'<br>'+d.score;
		   
		   var x = window.event.clientX;
		   var y = window.event.clientY;
		   expLab
		   .style('top',y + 10)
		   .style('left',x + 10)
		   .style('display','block')
		   .html(output);
		   })
	   .on('mouseout', function(d,i) {
			d3.select(this)
			.attr('stroke-width',0)
			.attr('stroke','none')
			expLab
			.style('display','none')
		});

});

*/
