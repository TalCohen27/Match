/**
 * 
 */

/**
 * 
 */
$(document).on('pagebeforeshow', "#messages", function(event) { // on page
	// load...do
	if (storeObject.isBannedUser == "true"){
		$("#massages_banned_label").empty().text("your profile has been banned from UpDate!!");
		var img = document.createElement("img");
		img.src = "http://www.google.com/intl/en_com/images/logo_plain.png";

		var src = document.getElementById("massages_banned_label");
		src.appendChild(img);
	}
	else{
		$("#massages_banned_lable").empty().text("");
	}
	$.ajax({
		dataType : 'json',
		url : "messages",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if(data == "go to sign in"){
				$.mobile.changePage("#page1");
			}
			else{
				refreshMessagesData(data);
				$("#messages").trigger("create");
			}
		}
	});

	return false;
});

function refreshMessagesData(messagelist) {
	$("#start_create_messages").empty();
	i = 1;
	$.each(messagelist,function(index, val) {	
		ch = "b";
		if(!val.seenFlag && val.inOut == "received") {
			ch = "e";
		} 
		$("#start_create_messages").append(  // IMPORTANT: without ui-btn-up-b or ui-btn-up-e the objects will be desplayed incorrectly
				[" <li data-corners=\"false\" data-shadow=\"false\" data-iconshadow=\"true\" data-wrapperels=\"div\" data-icon=\"arrow-r\" data-iconpos=\"right\" data-theme=\"" + ch + "\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-thumb ui-btn-up-" + ch + "\"> ",
				 " 	<div class=\"ui-btn-inner ui-li\"> ",
				 " 		<div class=\"ui-btn-text\"> ",
				 " 			<a href=\"#\" onclick=\"return goToChatWithParam(event, " + val.secondPartyId + ");\" class=\"ui-link-inherit\">",
				 " 				<img id=\"message_partner_photo" + i +"\" src=\"images/profile.jpg\" onclick=\"return showProfile(event, " + val.secondPartyId + ");\" alt=\"image\" class=\"ui-li-thumb\"> ",
				 " 				<p id=\"message_timestamp" + i +"\" class=\"ui-li-aside\"><font size=\"1\"><dfn></dfn><br><span></span></font></p> ",
				 " 				<h2 class=\"ui-li-heading\" id=\"message_partner_name" + i +"\"></h2> ",
				 " 				<p class=\"ui-li-desc\" id=\"message_content" + i +"\"></p> ",
				 "          </a> ",
				 " 		</div> ",
				 "		<span class=\"ui-icon ui-icon-arrow-r ui-icon-shadow\">&nbsp;</span>",
				 "	</div> ",
				 " </li>"].join(""));
		i = i + 1;
	});
	
	i = 1;
	$.each(messagelist, function(index, val) {
		
		$("#message_partner_photo" + i).attr("src", val.partnerPhoto);
		$("#message_partner_name" + i).text(val.secondPartyName);
		if(!val.seenFlag && val.inOut == "received") {
			$("#message_content" + i).append("<b>" + val.content + "</b>");
		} else {
			$("#message_content" + i).text(val.content);
		}
		$("#message_timestamp" + i + " font dfn").text(val.inOut + " at:");
		$("#message_timestamp" + i + " font span").text(val.timeStamp);
		i = i + 1;
	});
	$("#start_create_messages").listview("refresh");
};

function goToChatWithParam(event,id) {
	storeObject.storeID = id;
	$.mobile.changePage("#chat");
}