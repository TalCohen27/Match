/**
 * 
 */

function printEditError(error) {
	$("#edit_error").empty().text(error);
};

$(function() {
	// add a function to the submit event
	$("#edit_form").submit(function() {
		//alert("submit check!!!");
		$(this).ajaxSubmit({
			dataType : 'json',
			error : function() {
				console.log("Failed to submit");
				printEditError("Failed to submit, please try one more time");
			},
			success : function(data) {
				
			
					
				
				if (data == "true") {
					$.mobile.changePage("#page4");
				} else {
					printEditError(data);
				}
			
			}
		});

		return false;
	});
});

$(document).on('pagebeforeshow', "#page5", function(event) {
	// on page load...do
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
				
			
			refreshEditUserData(data);
			}
		}
	});

	$("#page5").trigger("create");
	return false;
});

function refreshEditUserData(userdatalist) {

	$("#update_first").attr("value", userdatalist.firstName);
	$("#update_last").attr("value", userdatalist.lastName);
	$("#update_about_me").empty().text(userdatalist.aboutMe);
	$("#update_sex").val(userdatalist.sex);
	$("#update_sex").selectmenu('refresh');
	$("#update_birthday").val(
			(Date.parse(userdatalist.birthday)).toString('yyyy-MM-dd'));
	$("#update_education").val(userdatalist.education);
	$("#update_education").selectmenu('refresh');
	$("#update_area").val(userdatalist.area);
	$("#update_area").selectmenu('refresh');
	$("#update_language").val(userdatalist.language);
	$("#update_language").selectmenu('refresh');
	$("#update_marital_status").val(userdatalist.maritalStatus);
	$("#update_marital_status").selectmenu('refresh');
	$("#update_children").val(userdatalist.children);
	$("#update_children").selectmenu('refresh');
	$("#update_eye_color").val(userdatalist.eyeColor);
	$("#update_eye_color").selectmenu('refresh');
	$("#update_hair_type").val(userdatalist.hairType);
	$("#update_hair_type").selectmenu('refresh');
	$("#update_hair_color").val(userdatalist.hairColor);
	$("#update_hair_color").selectmenu('refresh');
	$("#update_origin").val(userdatalist.origin);
	$("#update_origin").selectmenu('refresh');
	$("#update_height").val(userdatalist.height);
	$("#update_height").selectmenu('refresh');
	$("#update_weight").val(userdatalist.weight);
	$("#update_weight").selectmenu('refresh');
	$("#update_earning_level").val(userdatalist.earningLevel);
	$("#update_earning_level").selectmenu('refresh');
	$("#update_religion").val(userdatalist.religion);
	$("#update_religion").selectmenu('refresh');
	$("#update_smoking").val(userdatalist.smoking);
	$("#update_smoking").selectmenu('refresh');
	$("#update_physique").val(userdatalist.physique);
	$("#update_physique").selectmenu('refresh');
	$("#update_intrested_in").val(userdatalist.intrestedIn);
	$("#update_intrested_in").selectmenu('refresh');
	$("#update_photo").attr("src", userdatalist.photo);
	// ------------------Partner's Preferences----------------------------
	$("#update_education2").val(userdatalist.partner_education);
	$("#update_education2").selectmenu('refresh');
	$("#update_area2").val(userdatalist.partner_area);
	$("#update_area2").selectmenu('refresh');
	$("#update_language2").val(userdatalist.partner_language);
	$("#update_language2").selectmenu('refresh');
	$("#update_marital_status2").val(userdatalist.partner_maritalStatus);
	$("#update_marital_status2").selectmenu('refresh');
	$("#update_eye_color2").val(userdatalist.partner_eyeColor);
	$("#update_eye_color2").selectmenu('refresh');
	$("#update_hair_type2").val(userdatalist.partner_hairType);
	$("#update_hair_type2").selectmenu('refresh');
	$("#update_hair_color2").val(userdatalist.partner_hairColor);
	$("#update_hair_color2").selectmenu('refresh');
	$("#update_origin2").val(userdatalist.partner_origin);
	$("#update_origin2").selectmenu('refresh');
	$("#update_minheight2").val(userdatalist.partnerMinHeight);
	$("#update_minheight2").selectmenu('refresh');
	$("#update_maxheight2").val(userdatalist.partnerMaxHeight);
	$("#update_maxheight2").selectmenu('refresh');
	$("#update_earning_level2").val(userdatalist.partner_earningLevel);
	$("#update_earning_level2").selectmenu('refresh');
	$("#update_religion2").val(userdatalist.partner_religion);
	$("#update_religion2").selectmenu('refresh');
	$("#update_smoking2").val(userdatalist.partner_smoking);
	$("#update_smoking2").selectmenu('refresh');
	$("#update_physique2").val(userdatalist.partner_physique);
	$("#update_physique2").selectmenu('refresh');
	$("#update_min_age2").val(userdatalist.partnerMinAge);
	$("#update_min_age2").selectmenu('refresh');
	$("#update_max_age2").val(userdatalist.partnerMaxAge);
	$("#update_max_age2").selectmenu('refresh');

};
