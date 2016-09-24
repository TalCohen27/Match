/**
 * 
 */
$(function() {
	// add a function to the submit event
	$("#forgotpasswordform").submit(function() {
	
		var password = $("#newpassword").val();
		var email = $("#useremail").val();
		if ($("#forgotpasswordform").valid()) {
		jQuery.ajax({
			
   
			data : {
				password : password,
				email : email
			},
			dataType : 'json',
			url : "forgotpassword",
			error : function() {
				console.log("Failed to submit");
			},
			success : function(data) {
				if (data == "true"){
				self.location="#page1";
				}
				
				
				else{
					printForgotError(data);
				}


			}
		});
	}
		return false;
	});
});


function printForgotError(error) {

	$("#errorfogrotpassword").empty().text(error);

};


$(document).on('pageinit', function () {
	
    $.validator.addMethod("loginRegex", function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9\-]+$/i.test(value);
    }, "Username must contain only letters, numbers, or dashes.");
    
    
    $('#forgotpasswordform').validate({
        rules: {
        	useremail: {
                required: true,
                
                email : true
            },
            newpassword: {
                required: true,
                loginRegex: true,
                
            }
        },
        messages: {
            email: {
                required: "Please enter your first name"
            },
            password: {
                required: "Please enter your last name",
                	loginRegex: "You may use only english letters and numbers"
            }
        }
    });
    
});
