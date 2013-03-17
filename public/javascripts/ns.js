/*!
* Scripts for normalization/scoring page: form
*/

//Initialize stuff
$(document).ready(function() {
	// Handler for .ready() called.
	if(!$('#doLinkage').is(":checked")){
		$('#cg-linkageCutoff').addClass('hide');
	}
	if(!$('#doArrayDef').is(":checked")){
		//AD options hide toggle
		$('#arrDefOpts').toggleClass('hide');
	}
	if(!$('#doScoring').is(":checked")){
		$('#cg-scoringFunction').toggleClass('hide');
	}
	
	//Hide array definition stuff !!!!!!! If !checkedTODO..
	$('#cg-arrayDefCustomFile').hide();
	
	//Trigger change
	$('#arrayDefPredefined').change();
	

});

//Show linkage when its checkbox is clicked
$('#doLinkage').click(function(){
	$('#cg-linkageCutoff').slideToggle('fast');
});

//Show scoring function when its checkbox is clicked
$('#doScoring').click(function(){
	$('#cg-scoringFunction').slideToggle('fast');
	//Modify submit button text
	$('#doScoring').is(':checked') ? $("#nssubmit").html('Normalize and score') : $("#nssubmit").html('Normalize');
});

$('#doArrayDef').click(function(){
	$('#arrDefOpts').slideToggle('fast');
});

$('#arrayDefPredefined').change(function(){
	var i = $("#arrayDefPredefined").prop("selectedIndex");//selected index
	var n = $('#arrayDefPredefined option').size()-1; //Custom file index
	
	//If our index is at 0 or at custom file
	if(i == 0 || i == n){
		//Hide plate dropdowns and view button
		$('[id $=plates]').hide();
		$('#viewPlate').hide();
	}
	
	if(i == 0){
		$('#cg-arrayDefCustomFile').hide();
	}else if(i == n){
		//Show array def file chooser
		$('#cg-arrayDefCustomFile').show();
	}else{
		//Show plate dropdown for selected one, hide the rest
		select = $('[id ^='+$(this).val()+'][id $=plates]');
		unselect = $('[id $=plates]').not('[id ^='+$(this).val()+']');
		
		select.show();
		select.attr('name', 'selectedArrayDefPlate')
		
		unselect.hide();
		unselect.attr('name', '')
		
		//Show view button
		var i_plate = select.prop('selectedIndex');
		if(i_plate > 0) { 
			$('#viewPlate').show(); 
		}else{
			$('#viewPlate').hide(); 
		}
		
		//Hide file chooser 
		$('#cg-arrayDefCustomFile').hide();
	}
});

$('[id $=plates]').change(function(){
	var i_plate = $(this).prop('selectedIndex');//index of plate
	if(i_plate > 0){
		$('#viewPlate').show();
	}else{
		$('#viewPlate').hide();
	}
})

$('#viewPlate').click(function(){
	arrayDefValue =  $('#arrayDefPredefined').val();
	plateValue = $('[name=selectedArrayDefPlate]').val();
	var link = "/assets/data/array-definitions/"+ arrayDefValue +"/"+plateValue;
    console.log(link);
       
    $('#arrayDefModalHeading').html(arrayDefValue + " <small>"+plateValue+"</small>");      
    $('#arrayDefModalBody').CSVToTable(link,
    	{loadingText: 'Loading array definition data...',
         startLine: 4,
         separator: "\t",
         tableClass:"table table-hover" }
    );
	
})



$('#advancedOptsBtn').click(function(){
	$('#advancedOpts').slideToggle('fast');
	$('#advancedOptsIcon').toggleClass('icon-chevron-right');
	$('#advancedOptsIcon').toggleClass('icon-chevron-down');
});

/*Validation*/


function nsFormValid(){
	console.log('=====Validating=====')
	var ret = true;
	
	var i = $("#arrayDefPredefined").prop("selectedIndex");//selected index
	var n = $('#arrayDefPredefined option').size()-1; //Custom file index
	if($('#doArrayDef').is(':checked')){
		
		if(i == 0){ 
			console.log('AD dropdown: 0 index, returning false');
			ret = false; 
		}else if(i == n){ 
			if( $('#arrayDefCustomFile').attr('valid') == 'false' ){
				console.log('AD file: not valid, returning false');
				ret =  false;
			}else if($('#arrayDefCustomFile').prop('files').length == 0){
				console.log('AD file: no files selected, returning false');
				ret = false;
			}
		}
	}else if((i != 0 && i != n) | !$('#doArrayDef').is(':checked')){
		console.log('AD file: resetted');
		$('#errorBox').find('.ad-error').remove();
		$('#arrayDefCustomFile').replaceWith($('#arrayDefCustomFile').clone( true ));
		$('#arrayDefCustomFile-span').html('');
		$('#arrayDefCustomFile-btn').val('Select plate layout file');
		$('#cg-arrayDefCustomFile').removeClass('error');
	}
	
	if($('#doLinkage').is(':checked')){
		if(! /^\d+$/.test( $('#linkageCutoff').val()) ){
			console.log('linkage invalid');
			ret = false;
		}
	}
	
	if($('#plateFiles').is(':visible')){
		if($('#plateFiles').prop('files').length == 0){
			console.log('PL files empty, returning false');
			ret = false;
		}
		if($('#plateFiles').attr('valid') == 'false'){
			console.log('PL files invalid, returning false');
			ret = false;
		}
	}
	
	console.log('=====Returning true=====')
	
	return(ret);

}
function readAndValidate(evt) {
	var errorId = evt.data.errorId;
	var inputId = evt.data.inputId;
	//Patterns
	var commentPat = new RegExp(evt.data.commentPat);
	var linePat = new RegExp(evt.data.linePat);
	
	var ifSkip = evt.data.ifSkip;
	var skipPat = new RegExp(ifSkip.regex);
	
    //Retrieve all the files from the FileList object
    var files = evt.target.files;
    if(files.length == 0){
    	$('#errorBox .'+ errorId).remove();
    }
    if(files.length > 0){
    	$('#nssubmit').removeAttr('disabled');
		$('#errorBox').empty();
    }
    $('#'+inputId).attr('valid', 'true');
    $('#cg-'+inputId).removeClass('error');
    
    if (files) {
        for (var i = 0, f; f = files[i]; i++) {
        	//Create new file reader
            var r = new FileReader();
            //On load call
            r.onload = (function (f) {
            	if($('#'+inputId).attr('valid') == "false"){
                	return;
        		}
            	return function (e) {
                	//If we already found one invalid, ignore
                	var contents = e.target.result;
                    var lines = contents.split('\n');
                    
                    var skip = 0;
				    for(var i=0; i<lines.length; i++){
				    	line = jQuery.trim(lines[i]);
				    	if(line == ""){
				    		continue;
				    	}
				    	if(skipPat.test(line)){
				    		skip = ifSkip.skip;
				    	}
				    	if(commentPat.test(line)){ 
				    		continue; 
				    	}
				    	if(skip > 0){
				    		skip--;
				    		continue;
				    	}
				    	if(!linePat.test(line)) { 
				    		//console.log('Found invalid line');
				    		var a = " Invalid file format in <strong>" +f.name + "</strong> on line <strong>" + (i+1) + "</strong>";
				    		
				    		$('#cg-'+inputId).addClass('error');
				    		$('#'+inputId).attr('valid', 'false');
				    		$('#errorBox').html('<div class="alert alert-error '+errorId+'" id="errorChild"><strong>Error</strong> '+a+'</div>');
							$('#nssubmit').attr('disabled', 'disabled');
							
							nsFormValid();
							break;
				    	}
				    }
                };
            })(f);
            r.readAsText(f);
        }
    } else {
        alert("Fatal error: failed to load files");
    }
}	

$(document).ready(function() {

	var readAndValidateCallback = function(e){readAndValidate(e, function(){console.log('CALL BACK CALLED');})}
	$("#plateFiles").on("change", 
		{ errorId: "pl-error", 
		  inputId: "plateFiles", 
		  commentPat: "^#|^@", linePat: "^(\\d+)(\\s+)(\\d+)(\\s+)(\\d+(\\.\\d*)?)(\\s.*[^\\s])*$", 
		  ifSkip:{regex:"^Colony Project Data File$", skip:13} 
		},
		readAndValidate);
		
	$("#arrayDefCustomFile").on("change", 
		{ errorId: "ad-error", 
		  inputId: "arrayDefCustomFile",
		  commentPat: "^#|^@", linePat: "^(\\d+)(\\s+)(\\d+)(\\s+)([^\\s]+)$", 
		  ifSkip:{regex:"^(ID)(\\s+)(\\d+)$", skip:5} 
		},
		readAndValidate);
	
	$("#plateFiles").change(function(){
		var files = $('#plateFiles').prop('files');
		if(files.length == 0){
			$('#loadedFilesBox').hide();
			return;
		}
		var fileNames = [];
		jQuery.each(files, function() {
	      fileNames.push(this.name);
	    });
	    console.log(fileNames.fileTableSGA())
	    $('#loadedFilesTable').html(fileNames.fileTableSGA());
	    $('#loadedFilesBox').show();
	});	
		
	function inputChanged() {
  		console.log('CHANGED');
        if(nsFormValid()){ $('#nssubmit').removeAttr('disabled'); }
        else { $('#nssubmit').attr('disabled', 'disabled') }
	}
    $('input,select').keyup(inputChanged).change(inputChanged).click(inputChanged);
    
    inputChanged();
});
 