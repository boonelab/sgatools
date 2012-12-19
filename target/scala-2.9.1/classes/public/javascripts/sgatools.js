
String.prototype.parseSGAFileName = function() {
    var sp=this.split("_");
    var pat = new RegExp("^[^_]+_[^_]+_\\d+([_|\.].*)?$");
    var screenType = "―";
    var queryName = "―";
    var arrayPlateId = "―";
    
    if(pat.test(this)){
    	arrayPlateId = sp[2];
    	queryName = sp[1];
    	if(/^(wt|ctrl)/i.test(queryName)){
    		screenType = "Control";
    		console.log(queryName.split('-'));
    		var tmp = queryName.split('-')[1];
    		if(undefined != tmp){
    			queryName = tmp;
    		}
    	}else{
    		screenType = "Double mutant"
    	}
    }
    toReturn = [this.toString(),screenType, queryName, arrayPlateId];
    
    return toReturn;
};

Array.prototype.fileTableSGA = function(){
	var r = [];
	jQuery.each(this, function(i, fileName) {
		parsed = fileName.parseSGAFileName();
		t = jQuery.each(parsed, function(j, value) {
			if(j == 0){
				parsed[j] = "<td><i class='icon-file'></i> "+value+"</td>";
			}else{
				parsed[j] = "<td>"+value+"</td>";
			}
			
		});
		r.push("<tr>" + t.join('') + "</tr>");
	});
	head = "<thead><tr><th>File name</th><th>Screen type</th><th>Query name</th><th>Array plate id</th></tr></thead>";
	body = "<tbody>"+r.join('')+"</tbody>";
	style = "<style>thead tr th{text-align:left;}</style>"
	return head+body+style;
};
