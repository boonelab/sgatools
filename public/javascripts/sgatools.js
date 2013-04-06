
String.prototype.parseSGAFileName = function() {
    var sp=this.split(new RegExp('_|\\.'));
    var pat = new RegExp("^[^_]+_[^_]+_[^_]+_\\d+([_|\.].*)?$");
    var screenType = "―";
    var queryName = "―";
    var arrayPlateId = "―";
    
    if(pat.test(this)){
    	arrayPlateId = sp[3];
    	queryName = sp[2];
    	if(/^(wt|ctrl)/i.test(sp[1])){
    		screenType = "Control";
    	}else{
    		screenType = "Case"
    	}
    }
    toReturn = [this.toString(),screenType, queryName, arrayPlateId];
    
    return toReturn;
};


Array.prototype.fileTableSGA = function(){
	var r = [];
	var nctrl = 0, ncase = 0, nvalid=0;
	jQuery.each(this.sort(), function(i, fileName) {
		parsed = fileName.parseSGAFileName();
		
		screenType = parsed[1];
		filename = parsed[0];
		if(/^sgatools/i.test(filename)) isExample = true;
		if(screenType != '―') nvalid++;
		if(/Control/i.test(screenType)) nctrl++;
		if(/Case/i.test(screenType)) ncase++;
		
		t = jQuery.each(parsed, function(j, value) {
			if(j == 0){
				parsed[j] = "<td><i class='icon-file'></i> "+value+"</td>";
			}else{
				parsed[j] = "<td>"+value+"</td>";
			}
			
		});
		r.push("<tr>" + t.join('') + "</tr>");
	});
	
	console.log($('#doArrayDef').prop('checked'))
	//////////////
	if(nvalid > 0 && $('#doArrayDef').prop('checked') == false)
		$('#doArrayDef').prop('checked', true);
		$('#arrDefOpts').slideDown('fast');
	if(isExample){
		$('option:contains("sga-array-ver2-1536")').prop('selected',true);
		$('option:contains("sga-array-ver2-1536")').parent().change();
	}
		
	
	if(nctrl > 0 && ncase > 0){
		$('#doScoring').prop('checked', true);
		$('#cg-scoringFunction').slideDown('fast');
		$("#nssubmit").html('Normalize and score');
	}else{
		$('#doScoring').prop('disabled', true);
		$('#doScoring').parent().find('span').html('Must have at least one control screen and one case screen to score')
	}
	nsFormValid();
	//////////////
	
	head = "<thead><tr><th>File name</th><th>Screen type</th><th>Query name</th><th>Array plate id</th></tr></thead>";
	body = "<tbody>"+r.join('')+"</tbody>";
	style = "<style>thead tr th{text-align:left;}</style>"
	return head+body+style;
};



//Triggers help modal/updates content
function labelHelpClicked(containerId){
    $('#helpModalHeading').html('Quick help');
    $('#helpModalBody').load('/help #'+containerId);
}
