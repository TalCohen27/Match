/**
 * Sign In
 */
var storeObject = {
	    storeID : '',
	    isBannedUser : ''	
	};

$(document).on('pagebeforeshow', "#page1", function(event) { // on page
	// load...do
	$("#email").val('');
	$("#password").val('');
	$("#error").empty();
});

$(document).on('pageinit', function validator() {

	$.validator.addMethod("loginRegex", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9\-]+$/i.test(value);
	}, "Username must contain only letters, numbers, or dashes.");

	$('#sign_in_button').validate({

		rules : {
			email : {
				required : true,
				email : true
			},
			password : {
				required : true,
				loginRegex : true,
			}
		},
		messages : {
			email : {
				required : "Please enter your email."
			},
			password : {
				required : "Please enter your password",
				loginRegex : "You may use only english letters and numbers"
			}
		}
	});

});

$(function() {
	// add a function to the submit event
	$("#sign_in_button").submit(function() {
		var email = $("#email").val();
		var password = $("#password").val();

		if ($("#sign_in_button").valid()) {
			jQuery.ajax({

				data : {
					email : email,
					password : password
				},
				dataType : 'json',
				url : "checkemailandpassword",
				error : function() {
					console.log("Failed to submit");
				},
				success : function(data) {
					if (data == "true") {
						$.mobile.changePage("#matches");
					} else if (data == "admin") {
						$.mobile.changePage("#adminSearchAndBan");
					} else if (data == "false") {

					} else {
						printLoginError(data);
					}
				}
			});
		}
		return false;
	});
});

function printLoginError(error) {
	$("#error").empty().text(error);
};

$(function() {
	// add a function to the submit event
	$("#sign_up_button").submit(function() {
		$.mobile.changePage("#page2");
		return false;
	});
});
