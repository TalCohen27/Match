/**
 * 
 */
var profile_data;

$(document).on('pagebeforeshow', "#page6", function(event) {

	// on page load...do
	$.ajax({
		data : {
			profileId : profile_data.id,
			check : -1
		// here we check for session by sending request a second time.
		},
		dataType : 'json',
		url : "partnerprofile",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if (data == "go to sign in") {
				$.mobile.changePage("#page1");
			} else {
				if (data == "true") { // in case true and not admin then profile_data.id is already blocked or banned and update admin menu
					$("#block_user_button").addClass("ui-disabled");
					$("#find_partner_user_button").removeClass("ui-disabled");
					$("#adminProfileMenu").attr("href", "#panel1");
				} else if (data == "false") {
					$("#block_user_button").removeClass("ui-disabled");
					$("#find_partner_user_button").removeClass("ui-disabled");
					$("#adminProfileMenu").attr("href", "#panel1");
				} else if (data == "trueAdmin") { // in case true and admin then profile_data.id is already blocked or banned and update admin menu
					$("#block_user_button").addClass("ui-disabled");
					$("#find_partner_user_button").addClass("ui-disabled");
					$("#adminProfileMenu").attr("href", "#panel2");
				} else if (data == "falseAdmin") {
					$("#block_user_button").removeClass("ui-disabled");
					$("#find_partner_user_button").addClass("ui-disabled");
					$("#adminProfileMenu").attr("href", "#panel2");
				}
				refreshPartnerProfileData(profile_data);
				$("#page6").trigger("create");
			}
		}
	});

	return false;
});

function showProfile(event, id) {
	$.ajax({
		data : {
			profileId : id
		},
		dataType : 'json',
		url : "partnerprofile",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			savePartner(id, data.photo, data.firstName);
			profile_data = data;
			$.mobile.changePage("#page6");
		}
	});
	return false;
};

function refreshPartnerProfileData(userdatalist) {

	$("#partner_first_name2").empty().text(userdatalist.firstName);
	$("#partner_about_me2").empty().text(userdatalist.aboutMe);
	$("#partner_sex2").empty().text(userdatalist.sex);
	$("#partner_age2").empty().text(calcAge(userdatalist.birthday));
	$("#partner_education2").empty().text(userdatalist.education);
	$("#partner_area2").empty().text(userdatalist.area);
	$("#partner_language2").empty().text(userdatalist.language);
	$("#partner_marital_status2").empty().text(userdatalist.maritalStatus);
	$("#partner_children2").empty().text(userdatalist.children);
	$("#partner_eye_color2").empty().text(userdatalist.eyeColor);
	$("#partner_hair_type2").empty().text(userdatalist.hairType);
	$("#partner_hair_color2").empty().text(userdatalist.hairColor);
	$("#partner_origin2").empty().text(userdatalist.origin);
	$("#partner_height2").empty().text(userdatalist.height);
	$("#partner_weight2").empty().text(userdatalist.weight);
	$("#partner_earning_level2").empty().text(userdatalist.earningLevel);
	$("#partner_religion2").empty().text(userdatalist.religion);
	$("#partner_smoking2").empty().text(userdatalist.smoking);
	$("#partner_physique2").empty().text(userdatalist.physique);
	$("#partner_intrested_in2").empty().text(userdatalist.intrestedIn);
	$("#partner_photo2").attr("src", userdatalist.photo);

};

function calcAge(dateString) {
	var birthday = +new Date(dateString);
	return ~~((Date.now() - birthday) / (31557600000));
}