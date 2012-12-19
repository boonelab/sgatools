
//dataName, divChart, divDataTable, maxDataRows, chartWidth, chartHeight
function drawBarChart(settings) {
	var ndx, all, score, scores;
	var barChart;
	
	var options = {
        'maxDataRows':10,
        'chartWidth': 600,
        'chartHeight': 200,
        'columnToUse': 'score',
        'domainLow'  : -1,			//Lower extreme of numerical values
        'domainMed'  : 0,			//Medium extreme of numerical values
        'domainHigh' : 1,			//Upper extreme of numerical values
    };
    $.extend(options,settings);
	
	console.log(options);
	
	d3.tsv(options.dataName, function(data) {
		//Filter data for numerical scores only - ignore NAs
		data = data.filter(function(d,i){
			//if(d.plateid != options.selectedPlateId) {return false;}
			if(isNaN(d[options.columnToUse])){ return false; }
			d.index = i
			d.score = +d[options.columnToUse];
			return true;
		});
		
		//If we have no data (most likely wt screens)
		if(data.length == 0){
			$(options.divChart).html('<div id="no-data-error" class="span12 muted" style="padding:50px 0 120px 0"> \
									<h1><i class="icon-exclamation-sign" style="/*color:#B94A48*/"></i> No data</h1> \
									<h3>Please check to make sure data points are available for your selected options and try again</h3></div>');
			$(options.divDataTable).hide();
			$(options.divDataCount).hide();
			return;
		}else{
			$(options.divChart).empty();
			$(options.divDataTable).show();
			$(options.divDataCount).show();
		}
		
		//Initalize cross filter and grouping
		ndx = crossfilter(data),
		all = ndx.groupAll(),
		score = ndx.dimension(function(d) { return d.score; });
		
		if(options.columnToUse == "ncolonysize" || options.columnToUse == "colonysize"){
			scores = score.group(function(d) { return Math.round(d*0.1)/0.1; });
		}else{
			scores = score.group(function(d) { return Math.round(d*40)/40; });
		}
		
		
		//Data count
		dc.dataCount(options.divDataCount)
			.dimension(ndx) // set dimension to all data
			.group(all); // set group to ndx.groupAll()   
		
		window.barChart = dc.barChart(options.divChart)
			.width(options.chartWidth) // (optional) define chart width, :default = 200
			.height(options.chartHeight) // (optional) define chart height, :default = 200
			.transitionDuration(500) // (optional) define chart transition duration, :default = 500
			.dimension(score) // set dimension
			.group(scores) // set group
			.x(d3.scale.linear().domain([options.domainLow, options.domainHigh]))
		   
		   
	   /* Create a data table widget and use the given css selector as anchor. You can also specify
		 * an optional chart group for this chart to be scoped within. When a chart belongs
		 * to a specific group then any interaction with such chart will only trigger redraw
		 * on other charts within the same chart group. */
		dc.dataTable(options.divDataTable)
		    // set dimension
		    .dimension(score)
		    // data table does not use crossfilter group but rather a closure as a grouping function
		    .group(function(d) {
		        return "Plate: "+d.plateid;
		    })
		    // (optional) max number of records to be shown, :default = 25
		    .size(options.maxDataRows)
		    // dynamic columns creation using an array of closures
		    .columns([
		        function(d) { return d.row; },
		        function(d) { return d.col; },
		        function(d) { return d.query; },
		        function(d) { return d.array; },
		        function(d) { return d.score; }
		    ])
		    // (optional) sort using the given field, :default = function(d){return d;}
		    .sortBy(function(d){ return d.score; })
		    // (optional) sort order, :default ascending
		    .order(d3.ascending);
		  
		  //Hack - remove plate id at top
		  
		   dc.renderAll();
		   
		
				   
	});
	
	return barChart;
}