/**
 * 
 */
var nameerr = "Please, enter only letters";
var lastnameerr = "Please, enter only letters";
var passerr = "You may enter only letters and numbers in password field!";

var first;
var last;
var email;
var password;
var about_me;
var sex;
var birthday;
var education;
var area;
var language;
var marital_status;
var children;
var eye_color;
var hair_type;
var hair_color;
var origin;
var height;
var weight;
var religion;
var smoking;
var earning_level;
var intrestedIn;
var physique;
var photo;

$(document).on('pageinit', function() {

	$.validator.addMethod("RegexPassword", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9\-]+$/i.test(value);
	}, "");

	$.validator.addMethod("RegexName", function(value, element) {
		return this.optional(element) || /^[a-zA-Z\-]+$/i.test(value);
	}, "");

	$('#user_data').validate({
		rules : {
			email : {
				required : true,
				email : true
			},

			first : {
				required : true,
				RegexName : true
			},

			last : {
				required : true,
				RegexName : true
			},

			password : {
				required : true,
				loginRegex : true,

			}
		},
		messages : {
			email : {
				required : "Please enter your first name."
			},

			first : {
				RegexName : "You may enter english letters only",
				required : "Please enter your first name."
			},

			last : {
				RegexName : "You may enter english letters only",
				required : "Please enter your first name."
			},

			password : {
				required : "Please enter your last name",
				loginRegex : "You may use only english letters and numbers"
			}
		}
	});

});

$(function() {
	// add a function to the submit event
	$("#user_data").submit(function() {

		first = $("#first").val();
		last = $("#last").val();
		email = $("#user_email").val();
		password = $("#user_password").val();
		about_me = $("#about_me").val();
		sex = $("#sex").val();
		birthday = $("#birthday").val();
		education = $("#education").val();
		area = $("#area").val();
		language = $("#language").val();
		marital_status = $("#marital_status").val();
		children = $("#children").val();
		eye_color = $("#eye_color").val();
		hair_type = $("#hair_type").val();
		hair_color = $("#hair_color").val();
		origin = $("#origin").val();
		height = $("#height").val();
		weight = $("#weight").val();
		religion = $("#religion").val();
		smoking = $("#smoking").val();
		earning_level = $("#earning_level").val();
		intrestedIn = $("#intrestedIn").val();
		physique = $("#physique").val();
		photo = $("#photo").val();

		if ($("#user_data").valid()) {

			jQuery.ajax({

				data : {
					first : first,
					last : last,
					email : email,
					password : password,
					about_me : about_me,
					sex : sex,
					birthday : birthday,
					education : education,
					area : area,
					language : language,
					marital_status : marital_status,
					children : children,
					eye_color : eye_color,
					hair_type : hair_type,
					hair_color : hair_color,
					origin : origin,
					height : height,
					weight : weight,
					religion : religion,
					smoking : smoking,
					earning_level : earning_level,
					intrestedIn : intrestedIn,
					physique : physique,
					photo : photo

				},
				dataType : 'json',
				type : "POST",
				url : "acceptuserdata",
				error : function() {
					console.log("Failed to submit");
				},
				success : function(data) {
					if (data == "true") {
						self.location = "#page3";
					} else {
						printSignupStep1Error(data);
					}

				}
			});
		}
		return false;
	});
});

function printSignupStep1Error(error) {

	$("#acceptuserdataerror").empty().text(error);

};

