/**
 * 
 */

		var partner_education;
		var partner_area;
		var partner_language;
		var partner_marital_status;
		var partner_eye_color;
		var partner_hair_type;
		var partner_hair_color;
		var partner_origin;
		var partner_height_min;
		var partner_height_max;
		var partner_religion;
		var partner_smoking ;
		var partner_earning_level;
		var partner_intrested_in ;
		var partner_physique ;
		var partner_age_min ;
		var partner_age_max ;
		
		
$(function() {
	// add a function to the submit event
	$("#partner_data").submit(function() {


		partner_education = $("#partner_education").val();
		partner_area = $("#partner_area").val();
		partner_language = $("#partner_language").val();
		partner_marital_status = $("#partner_marital_status").val();
		partner_eye_color = $("#partner_eye_color").val();
		partner_hair_type = $("#partner_hair_type").val();
		partner_hair_color = $("#partner_hair_color").val();
		partner_origin = $("#partner_origin").val();
		partner_height_min = $("#partner_height_min").val();
		partner_height_max = $("#partner_height_max").val();
		partner_religion = $("#partner_religion").val();
		partner_smoking = $("#partner_smoking").val();
		partner_earning_level = $("#partner_earning_level").val();
		partner_physique = $("#partner_physique").val();
		partner_physique = $("#partner_physique").val();
		partner_age_min = $("#partner_age_min").val();
		partner_age_max = $("#partner_age_max").val();
		
		
		
		jQuery.ajax({
			
   
			data : {
				partner_education : partner_education,
				partner_area : partner_area,
				partner_language : partner_language,
				partner_marital_status : partner_marital_status,
				partner_eye_color : partner_eye_color,
				partner_hair_type : partner_hair_type,
				partner_hair_color : partner_hair_color,
				partner_origin : partner_origin,
				partner_height_min : partner_height_min,
				partner_height_max : partner_height_max,
				partner_religion : partner_religion,
				partner_smoking : partner_smoking,
				partner_earning_level : partner_earning_level,
				partner_intrested_in : partner_intrested_in,
				partner_physique : partner_physique,
				partner_age_min : partner_age_min,
				partner_age_max : partner_age_max,
				
		
				
		
				
			},
			dataType : 'json',
			type : "POST",
			url : "acceptpartnerdata",
			error : function() {
				console.log("Failed to submit");
			},
			success : function(data) {
				self.location="#upload";
			

			}
		});
		return false;
	});
});