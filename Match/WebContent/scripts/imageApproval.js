/**
 * 
 */

/**
 * 
 */

$(document).on('pagebeforeshow', "#new_photos", function(event) { 
	$.ajax({
		
		data : {
			isBan : "false",
			isChangeFlag : "false"
		},
		
		dataType : 'json',	
		url : "ImageApprovalServlet",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if(data == "go to sign in"){
				$.mobile.changePage("#page1");
			}
			else{
			refreshImageList(data);
			$("#new_photos").trigger("create");
			}
		}
	});

	$("#new_photos").trigger("create");
	return false;
});

function setAdminFlagAndDeleteFromList(event,user_id) {
	
	$(function() {
		jQuery.ajax({
			
			data : {
				isBan : "false",
				isChangeFlag : "true",
				profileId : user_id
			},
		
	dataType : 'json',
	url : "ImageApprovalServlet",
	error : function() {
		console.log("Failed to submit");
	},
	success : function(data) {
		if (data == "go to sign in") {
			$.mobile.changePage("#page1");
		}
		else{
			refreshImageList(data);
			$("#new_photos").trigger("create");
			}
		}, 
	});
	
	});
	
	$("#new_photos").trigger("create");
}

function banAndSendMessage(event,user_id) {
	
	$(function() {
		jQuery.ajax({
			
			data : {
			
				isChangeFlag : "true",
				isBan : "true",
				profileId : user_id
			},
			
			dataType : 'json',	
			url : "ImageApprovalServlet",
			timeout : 2000,
			error : function() {
				console.log("Failed to submit");
			},
			success : function(data) {
				if(data == "go to sign in"){
					$.mobile.changePage("#page1");
				}
				else{
				refreshImageList(data);
				$("#new_photos").trigger("create");
				}
			},
		});
	});
		$("#new_photos").trigger("create");
		return false;		
}

	
function refreshImageList(imageList) {
	
 	$("#start_create_new_photos").empty();
	i = 1;
	$.each(imageList, function(index, val) {	
		ch = "b";
		if(!val.seenFlag) {
			ch = "e";
		} 
		$("#start_create_new_photos").append(
				[" <li data-corners=\"false\" data-shadow=\"false\" data-iconshadow=\"true\" data-wrapperels=\"div\" data-icon=\"arrow-r\" data-iconpos=\"right\" data-theme=\"" + ch + "\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-thumb ui-btn-up-" + ch + "\"> ",
				 " 	<div class=\"ui-btn-inner ui-li\"> ",
				 " 		<div class=\"ui-btn-text\"> ",
				 "       <img id=\"photo" + i +"\" src=\"images/profile.jpg\" onclick=\"return showProfile(event, " + val.user_id + ");\" alt=\"image\" class=\"ui-li-thumb\"> ",
				 " 				<p id=\"activity_time" + i +"\" class=\"ui-li-aside\"><font size=\"1\"><dfn></dfn><br><span></span></font></p> ",
				 " 				<h2 class=\"ui-li-heading\" id=\"user_id" + i +"\"></h2> ",
				 " 				<p class=\"ui-li-desc\" id=\"photo" + i +"\"></p> ",
				 "</div>",
				 "<br>",
				 "<br>",
				 "<br>",
				 "  	<div class=\"ui-grid-b\"> ",
				 "			<div class=\"ui-block-a\"> ",
				 "				<a data-role=\"button\" href=\"#\" onclick=\"return setAdminFlagAndDeleteFromList(event, " + val.user_id + ");\">Approve</a> ",
				 " 			</div> ",
				 "			<div class=\"ui-block-b\"> ",
				 "				<a data-role=\"button\" data-theme=\"b\" href=\"#\" onclick=\"return banAndSendMessage(event, " + val.user_id + ");\">Decline</a> ",
				 "			</div> ",
				 " 		</div> ",
				 "		<span class=\"ui-icon ui-icon-arrow-r ui-icon-shadow\">&nbsp;</span> ",
				 "	  </div> ",
				 "</div> ",
			
				 " </li> "].join(""));
		
		i = i + 1;
	});
	$("#new_photos").trigger("create");
	
	i = 1;
	$.each(imageList, function(index, val) {
		$("#photo" + i).attr("src", val.image);
		$("#user_id" + i).text(val.user_id);
		$("#activity_time" + i + " font span").text(val.activity_time);
		i = i + 1;
	});
	$("#start_create_new_photos").listview("refresh");
};
 