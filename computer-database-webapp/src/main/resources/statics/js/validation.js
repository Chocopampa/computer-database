$(function () {
    $('#computerFormular').validate({
    	rules: {
    		computerName: {
    			required: true
    		}
    	},
    	messages: {
    		computerName: {
    			required: "A computer needs a name!"
    		}
    	}
    });
    
    $('#computerFormular').submit(function (e){
    	var introduced = $.trim($('#introduced').val());
    	var discontinued = $.trim($('#discontinued').val());
    	
    	if ((new Date(introduced).getTime()) > (new Date(discontinued).getTime())) {
    		alert('The introduced Date must be before the discontinued Date.');
    		e.preventDefault();
    	} else if (discontinued && !introduced) {
    		alert('Cannot input a discontinued Date without an introduced Date.');
    		e.preventDefault();
    	}
    })
});