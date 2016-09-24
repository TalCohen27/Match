/**
 * 
 */

$(function() {
	// add a function to the submit event
	$("#delete_button").submit(function() {
		jQuery.ajax({
			dataType : 'json',
			url : "deleteuserservlet",
			error : function() {
				console.log("Failed to submit");
			},
			success : function(data) {
				if (data == "true") {
					$.mobile.changePage("#page1");
				} else {
					printDeleteError(data);
				}
			}
		});
		return false;
	});
});

function printDeleteError(error) {
	$("#errordeleteuser").empty().text(error);
};
