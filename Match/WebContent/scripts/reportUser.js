/**
 * 
 */

	
$(function() {
	// add a function to the submit event
	$("#report_user").submit(function() {
		
		var content = $("#complaint").val();		
		jQuery.ajax({
			
			data : {
				content : content,
				reported : profile_data.id
			},
			
			dataType : 'json',
			url : "ReportUserServlet",
			error : function() {
				console.log("Failed to submit");
			},
			success : function(data) {
				if (data == "true") {
					$.mobile.changePage("#page4");
				} else if(data == "go to sign in") {
					$.mobile.changePage("#page1");
				}
			}
		});
		return false;
	});
});


