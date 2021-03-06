window.firstCallHs = []
//dataName, divChart, divDataTable, maxDataRows, chartWidth, chartHeight
function drawBarChart(settings) {
	var ndx, all, score, scores;
	var barChart;
	
	var options = {
        maxDataRows	: 10,
        chartWidth	: 600,
        chartHeight	: 200,
        columnToUse	: 'score',
        domainLow	: -1,			//Lower extreme of numerical values
        domainMed	: 0,			//Medium extreme of numerical values
        domainHigh	: 1,			//Upper extreme of numerical values
    };
    $.extend(options,settings);
	
	console.log(options);
	
	// Replaced d3.tsv(options.dataName, function(data)
	d3.text(options.dataName, 'text/tsv',function(tsv){	
		tsv = tsv.replace(/^[#@][^\r\n]+[\r\n]+/mg, '');
    	tsv = "row	col	colonysize	plateid	query	array	ncolonysize	score	kvp\n" + tsv;
		data = d3.tsv.parse(tsv);
		
		//Filter data for numerical scores only - ignore NAs
		data = data.filter(function(d,i){
			s = +( (+d[options.columnToUse]).toFixed(3) );
			if(isNaN(s)){ return false; }
			d.index = i;
			d.score = s;
			
			d.query = d.query.split('_')[0];
			d.array = d.array.split('_')[0];
			
			if(isCombined){
				d.groupTable = 'Combined data';
			}else{
				d.groupTable = d.array;
				d.groupTable = 'Data table';
			}
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

			return true;
		});
		
		//If we have no data (most likely wt screens)
		if(data.length == 0){
			$(options.divChart).html('<center><div id="no-data-error" class="span12 muted" style="padding:50px 0 120px 0"> \
									<h1><i class="icon-exclamation-sign" style="/*color:#B94A48*/"></i> No scores</h1> \
									<h3>No scores were found for this screen</h3></div></center>');
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
		
		maxVal = d3.max(data, function(d) { return d.score; });
		minVal = d3.min(data, function(d) { return d.score; });
		
		
		if (options.columnToUse == "score") {
		    minVal = (Math.floor(minVal * 10) / 10) - .2;
		} else {
    		if(minVal < 0.5) 
    			minVal = Math.floor(minVal);
    		else
    			minVal = Math.ceil(minVal);
		}
			
		if(maxVal < 0) 
			maxVal = Math.floor(maxVal);
		else
		    if (options.columnToUse == "score") {
		        maxVal = (Math.ceil(maxVal * 10) / 10) + .1;
		    } else {
	            maxVal = Math.ceil(maxVal);
		    }
		
		console.log('maxVal='+maxVal);
		console.log('minVal='+minVal);
		sub = maxVal - minVal;
		console.log('max-min:'+sub);
		
		if(sub < 1) sub = 2
		sub = sub/120;
		console.log('sub='+sub);
		
		if(options.columnToUse == "ncolonysize" || options.columnToUse == "colonysize"){
			scores = score.group(function(d) { return Math.round(d/sub)*sub; });
			//scores = score.group(function(d) { return Math.round(d*sub)/sub; });
			//window.scoredData = false;
			window.scoredData = true;
		}else{
			scores = score.group(function(d) { return Math.round(d/sub)*sub; });
			window.scoredData = true;
		}
		
		//t = options.dataName + "_" + options.columnToUse
		//if($.inArray(t, firstCallHs) < 0){
		options.domainLow = minVal;
		options.domainHigh = maxVal;
			//firstCallHs.push(t);
			//console.log("pushed into firstCall");
			//console.log(firstCallHs);
		//}
		
		//$('#domainLowInput').val(options.domainLow)
		//$('#domainHighInput').val(options.domainHigh)
		//options.domainLow = Math.floor(options.domainLow);
		//options.domainHigh = Math.floor(options.domainHigh);
		
		//Data count
		dc.dataCount(options.divDataCount)
			.dimension(ndx) // set dimension to all data
			.group(all); // set group to ndx.groupAll()   
		
		window.histogramChart = dc.barChart(options.divChart)
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
		window.dataTable = dc.dataTable(options.divDataTable)
		    // set dimension
		    .dimension(score)
		    // data table does not use crossfilter group but rather a closure as a grouping function
		    .group(function(d) {
		        return d.groupTable;
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