/*!
* Scripts for image analysis page: form
*/
 function validateForm(){
 //TOFINISH
	if($('#plateImages')[0].files.length == 0){
		alert('Must select at least one file');
		return;
	}
}
	
var currList = document.getElementById('loaded-table');
function handleFileSelect(evt) {
	console.log($('#plateImages').prop('files'))
	evt.stopPropagation();
    evt.preventDefault();
	var files = evt.target.files; // FileList object
	if(files == undefined){
		files = evt.dataTransfer.files;
		$('#plateImages').prop('files', files);
		$('#drop_zone h2').text(files.length + ' files dropped')
		return;
	}
	if(files.length == 0){ 
		$('#loaded-plates').hide();
		$('#iasubmit').attr('disabled', 'disabled');
		return; 
	}
	
	$('#iasubmit').removeAttr('disabled');
	$('#loaded-plates').show();
	$('#loaded-table tbody').html('');
	
	// Loop through the FileList and render image files as thumbnails.
	
	passed = 0;
	for (var i = 0, f; f = files[i]; i++) {
		// Only process image files.
		if (!f.type.match('image.*')) { continue; }
		passed++;
		var reader = new FileReader();
		
		// Closure to capture the file information.
		reader.onload = (function(theFile) {
		return function(e) {
			// Render thumbnail
		    var fileName = decodeURIComponent(theFile.name);
		    var parsed = fileName.parseSGAFileName();
		    /**************
		    var rowc = ['<td style="height:170px; width:250px; vertical-align:middle">',
		    			'<div class="thumbnail"><img style="height:170px; width:250px;" src="', e.target.result , '" title="' ,
		    			escape(theFile.name) , ' "/></div></td>'];
		    ***************/
		    var rowc = ['<tdvertical-align:middle"></td>'];
		    rowc = [];
		    jQuery.each(parsed, function(i, val) {
		       if(i == 0){
		       	rowc.push('<td style="vertical-align:middle;"><i class="icon-picture"></i> ' + val + '</td>');
		       }else{
		       	rowc.push('<td style="vertical-align:middle;">' + val + '</td>');
		       }
		    });
		    
		    $('#loaded-table tbody').append('<tr>' + rowc.join('') + '</tr>');
		};
		})(f);
		
		// Read in the image file as a data URL.
		reader.readAsDataURL(f);
	}
	if(passed == 0){
		$('#loaded-plates').hide();
		$('#iasubmit').attr('disabled', 'disabled');
	}
}
	
	  
/*
For drag and drop
*/
function handleDragOver(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
}


/*!
* Scripts for image analysis page: summary
*/

function populateSelectedPlates(){
	var toReturn = new Array(0);
	$(".plateData").each(function(i,v){
 		toReturn.push($(this).attr("data-path"));
	});
	return toReturn;
}

function selectPlateClicked(button){
    var dataPath = $(".plateData[id='" + button.id + "']").attr('data-path');
	if ($(button).val() == 1) {
		if(selectedPlates.indexOf(dataPath) == -1){
        	selectedPlates.push(dataPath)
        	console.log('added plate: '+dataPath);
        	console.log(selectedPlates);
        }else{
        	console.log('duplicate click');
        }
        $("button[id='" + button.id + "']:eq(0)").addClass('btn-success').find('i').addClass('icon-white');
        $("button[id='" + button.id + "']:eq(1)").removeClass('btn-danger').find('i').removeClass('icon-white');
        $('.hide_if_no_icon').show();
        
    } else {
    	if(selectedPlates.indexOf(dataPath) != -1){
			selectedPlates.splice(selectedPlates.indexOf(dataPath),1); 
	        console.log('removed plate: '+dataPath);
	        console.log(selectedPlates);
	    }else{
	    	console.log('already removed');
	    }
    
        $("button[id='" + button.id + "']:eq(0)").removeClass('btn-success').find('i').removeClass('icon-white');
        $("button[id='" + button.id + "']:eq(1)").addClass('btn-danger').find('i').addClass('icon-white');
        $('.hide_if_no_icon').hide();
    }
    
}

$('#advancedOptsBtn').click(function(){
    $('#advancedOpts').slideToggle('fast');
    $('#advancedOptsIcon').toggleClass('icon-chevron-right');
    $('#advancedOptsIcon').toggleClass('icon-chevron-down');
});
