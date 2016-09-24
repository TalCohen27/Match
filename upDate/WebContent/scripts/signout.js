/**
 * 
 */

$(document).on('pagebeforeshow', "#signout", function(event) { // on page
	// load...do
	$.ajax({
		dataType : 'json',
		url : "CloseSessionServlet",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if(data == "true"){
				$.mobile.changePage("#page1");
			}

		}
	});


});

