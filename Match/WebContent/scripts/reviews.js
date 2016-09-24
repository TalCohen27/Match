/**
 * 
 */
$(document).on('pagebeforeshow', "#reviews", function(event) { // on page
	// load...do
	if (storeObject.isBannedUser == "true"){
		$("#reviews_banned_label").empty().text("your profile has been banned from UpDate!!");
		var img = document.createElement("img");
		img.src = "http://www.google.com/intl/en_com/images/logo_plain.png";

		var src = document.getElementById("reviews_banned_label");
		src.appendChild(img);
	}
	else{
		$("#reviews_banned_label").empty().text("");
	}
	$.ajax({
		dataType : 'json',
		url : "reviews",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if(data == "go to sign in"){
				$.mobile.changePage("#page1");
			}
			else{
			refreshReviewsData(data);
			}
		}
	});
	
	$("#reviews").trigger("create");
	return false;
});

function refreshReviewsData(reviewlist) {
	$("#start_create_reviews").empty();
	i = 1;
	$.each(reviewlist,function(index, val) {		
		$("#start_create_reviews").append( "<li data-corners=\"false\" data-shadow=\"false\" data-iconshadow=\"true\" data-wrapperels=\"div\" data-icon=\"arrow-r\" data-iconpos=\"right\" data-theme=\"b\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-thumb ui-btn-up-b\"><div class=\"ui-btn-inner ui-li\"><div class=\"ui-btn-text\">" + 
	  " <a href=\"#chat\" class=\"ui-link-inherit\">"+
      " <img id=\"review_partner_photo" + i +"\" src=\"images/profile.jpg\" alt=\"image\" class=\"ui-li-thumb\">" +
      " <p id=\"review_seen" + i +"\" class=\"ui-li-aside\"><strong ></strong></p>"+
      " <h2 class=\"ui-li-heading\" id=\"review_partner_name" + i +"\"> </h2>" +
	  " <a data-role=\"button\" href=\"#\" onclick=\"return showProfile(event, " + val.reviewerId + ");\">Profile</a> ", +
      " </div><span class=\"ui-icon ui-icon-arrow-r ui-icon-shadow\">&nbsp;</span></div></li>");
		i = i + 1;
	});
	$("#reviews").trigger("create");
	
	i = 1;
	$.each(reviewlist, function(index, val) {
		
		$("#review_partner_photo" + i).attr("src", val.partnerPhoto);
		$("#review_partner_name" + i).append(" " + val.reviewerName);	
		$("#review_seen" + i).empty().text(val.seenFlag);

		i = i + 1;
	});
	
	$("#start_create_reviews").listview("refresh");
};