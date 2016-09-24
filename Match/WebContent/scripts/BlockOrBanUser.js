var is_Block; // block action->true, ban action->false
var is_Insert;// insert action->true, delete action->false

$(document).on('pagebeforeshow', "#adminBanPage", function(event) {

	// on page load...do
	$.ajax({
		data : {
			// here we check for session by sending request and enable or disable the ban button.
			profileId : storeObject.storeID,
			check : -1
		},
		dataType : 'json',
		url : "BlockOrBanServlet",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if (data == "go to sign in") {
				$.mobile.changePage("#page1");
			} else {
				if (data == "true") {
					$("#ban_button").addClass("ui-disabled");
				} else {
					$("#ban_button").removeClass("ui-disabled");
				}
				$("#adminBanPage").trigger("create");
			}
		}
	});

	return false;
});

function blockProfile(event) {
	is_Block = "true";
	is_Insert = "true";
	$.ajax({
		
		data : {
			is_Block : is_Block,
			is_Insert : is_Insert,
			profileId : profile_data.id
		},
		dataType : 'json',
		url : "BlockOrBanServlet",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			//TODO
			// if(data == "go to sign in"){
			// $.mobile.changePage("#page1");
			// }
			// else{
			$.mobile.changePage("#matches");
			// }
		}
	});
	return false;
};

function BanUserHelper(event) {
	  var selects = document.getElementById("ban_reason_select_id");
	  var ban_reason = selects.options[selects.selectedIndex].value;
	  var other_ban_reason = document.getElementById('text_search_reason').value;
	 
	  BanUser(event,storeObject.storeID,ban_reason,other_ban_reason);
}

function BanUser(event, id, ban_reason, other_ban_reason) {
	is_Block = "false";
	is_Insert = "true";
	$.ajax({
		
		data : {
			is_Block : is_Block,
			is_Insert : is_Insert,
			ban_reason : ban_reason,
			other_ban_reason : other_ban_reason,
			profileId : id
		},
		dataType : 'json',
		url : "BlockOrBanServlet",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			//TODO
			// if(data == "go to sign in"){
			// $.mobile.changePage("#page1");
			// }
			// else{
			$.mobile.changePage("#adminSearchAndBan");
			// }
		}
	});
	return false;
};

function actionOnBanOptionClick() {
	  document.getElementById('text_search_reason').value = ""; 
	  var selects = document.getElementById("ban_reason_select_id");
	  var ban_reason = selects.options[selects.selectedIndex].value;
	  if  (ban_reason == 2)
	  {
		  $("#lable_input_reason").text("admin's comment: ");
	  }
	  else
	  {
		  $("#lable_input_reason").text("other - type the reason here: ");
	  }
};

function unBlockOrUnBanProfile(event, id) {
	is_Block = "true";
	is_Insert = "false";
	$.ajax({
		data : {
			is_Block : is_Block,
			is_Insert : is_Insert,
			profileId : id
		},
		dataType : 'json',
		url : "BlockOrBanServlet",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			//TODO
			// if(data == "go to sign in"){
			// $.mobile.changePage("#page1");
			// }
			// else{
			if (data == "admin") {
				$.mobile.changePage("#adminSearchAndBan");
			} else {
				console.log("Success Unblock");
				$.mobile.changePage("#matches");
			}
			// }
		}
	});
	return false;
};