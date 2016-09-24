/**
 * My profile
 */

// $(document).on("pagebeforeshow", "#page4", function() {
// alert("pagebeforeshow");
// });

$(document).on('pagebeforeshow', "#page4", function(event) { // on page
	// load...do
	$.ajax({
		dataType : 'json',
		url : "profiledata",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if(data == "go to sign in"){
				$.mobile.changePage("#page1");
			}
			else{
			refreshUserData(data);
			}
		}
	});

	$("#page4").trigger("create");
	return false;
});

function refreshUserData(userdatalist) {

	$("#profile_first_name").empty().text(userdatalist.firstName);
	$("#profile_about_me").empty().text(userdatalist.aboutMe);
	$("#profile_sex").empty().text(userdatalist.sex);
	$("#profile_birthday").empty().text(userdatalist.birthday);
	$("#profile_education").empty().text(userdatalist.education);
	$("#profile_area").empty().text(userdatalist.area);
	$("#profile_language").empty().text(userdatalist.language);
	$("#profile_marital_status").empty().text(userdatalist.maritalStatus);
	$("#profile_children").empty().text(userdatalist.children);
	$("#profile_eye_color").empty().text(userdatalist.eyeColor);
	$("#profile_hair_type").empty().text(userdatalist.hairType);
	$("#profile_hair_color").empty().text(userdatalist.hairColor);
	$("#profile_origin").empty().text(userdatalist.origin);
	$("#profile_height").empty().text(userdatalist.height);
	$("#profile_weight").empty().text(userdatalist.weight);
	$("#profile_earning_level").empty().text(userdatalist.earningLevel);
	$("#profile_religion").empty().text(userdatalist.religion);
	$("#profile_smoking").empty().text(userdatalist.smoking);
	$("#profile_physique").empty().text(userdatalist.physique);
	$("#profile_intrested_in").empty().text(userdatalist.intrestedIn);
	$("#profile_photo").attr("src", userdatalist.photo);

//------------------Partner's Preferences----------------------------
	$("#education2").empty().text(userdatalist.partner_education);
	$("#area2").empty().text(userdatalist.partner_area);
	$("#language2").empty().text(userdatalist.partner_language);
	$("#marital_status2").empty().text(userdatalist.partner_maritalStatus);
	$("#eye_color2").empty().text(userdatalist.partner_eyeColor);
	$("#hair_type2").empty().text(userdatalist.partner_hairType);
	$("#hair_color2").empty().text(userdatalist.partner_hairColor);
	$("#origin2").empty().text(userdatalist.partner_origin);
	$("#height2").empty().text(userdatalist.partnerMinHeight + " - " + userdatalist.partnerMaxHeight);
	$("#earning_level2").empty().text(userdatalist.partner_earningLevel);
	$("#religion2").empty().text(userdatalist.partner_religion);
	$("#smoking2").empty().text(userdatalist.partner_smoking);
	$("#physique2").empty().text(userdatalist.partner_physique);
	$("#age2").empty().text(userdatalist.partnerMinAge + " - " + userdatalist.partnerMaxAge);

};