/**
 * 
 */

$(document).on('pagebeforeshow', "#blocks", function(event) { // on page
	// load...do
	if (storeObject.isBannedUser == "true"){
		$("#blocks_banned_label").empty().text("your profile has been banned from UpDate!!");
		var img = document.createElement("img");
		img.src = "http://www.google.com/intl/en_com/images/logo_plain.png";

		var src = document.getElementById("blocks_banned_label");
		src.appendChild(img);
	}
	else{
		$("#blocks_banned_label").empty().text("");
	}
		
	$.ajax({
		dataType : 'json',
		url : "BlockingListServlet",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if (data == "go to sign in") {
				$.mobile.changePage("#page1");
			} else {
				$("#start_create_blocking").empty();
				refreshBlockData(data);
				$("#blocks").trigger("create");
			}
		}
	});

	$("#blocks").trigger("create");
	return false;
});

function refreshBlockData(returningJson) {
    var blocklist = returningJson.userList;
	i = 1;
	var str;
	if (returningJson.isAdmin == true){
		 $("#blocks_banned_lable").empty().text("");
		 str = "UnBan";
		 $("#blocktitle").empty().text("Banned List");
		 $("#blocksPanel").empty().append([" <li data-role=\"list-divider\">Search User</li> ",
			" <li data-theme=\"b\"><a href=\"#reports\" data-transition=\"slide\">reported users</a></li>",
			" <li data-theme=\"b\"> ",
			"	<a href=\"#new_photos\" data-transition=\"slide\"> ",
			" 		accept new photos<span class=\"ui-li-count\"> 14 </span> ",
			" 	</a> ",
			" </li> ",
			" <li data-theme=\"b\"><a href=\"#adminSearchAndBan\" data-transition=\"slide\">Admin Search</a></li> ",
			" <li data-role=\"list-divider\"></li> ",
			" <li data-theme=\"b\"><a href=\"#signout\" data-transition=\"slide\">Log Out</a></li>"].join(""));
		 
	} else {
		 str = "UnBlock";
		 $("#blocktitle").empty().text("Blocked List");
		 $("#blocksPanel").empty().append([" <li data-role=\"list-divider\">Match</li> ",
		      " <li data-theme=\"b\"><a href=\"#matches\" data-transition=\"slide\"> Find Partner </a></li> ",
		      " <li data-theme=\"b\"><a href=\"#page4\" data-transition=\"slide\"> My Profile </a></li> ",
		      " <li data-theme=\"b\"> ",
		      " 	<a href=\"#page7\" data-transition=\"slide\"> ",
		      " 		Profile Views<span class=\"ui-li-count\"> 14 </span> ",
		      "		</a> ",
		      " </li> ",
		      "	<li data-theme=\"b\"> ",
		      " 	<a href=\"#messages\" data-transition=\"slide\"> ",
		      "			Messages <span class=\"ui-li-count\"> 12 </span> ",
		      "		</a> ",
		      " </li> ",
			  " <li data-role=\"list-divider\"></li> ",
			  " <li data-theme=\"b\"><a href=\"#signout\" data-transition=\"slide\">Log Out</a></li>"].join(""));		 
	}
	
	$("#blocksPanel").listview("refresh");
	
	$.each(blocklist, function(index, val) {
						$("#start_create_blocking").append(
								[" <br> ",
								 " <div class=\"ui-grid-a\" style=\"width: 100%; height: 100%;\" id=\"block" + i + "\"> ",
								 " 	<div class=\"ui-block-a\" style=\"width: 50%;\"> ",
								 " 		<div style=\"position: relative;\" data-controltype=\"image\"> ",
								 " 			<img id=\"blocking_photo" + i + "\" src=\"\" alt=\"image\" style=\"width: 7em;\"> ",
								 " 		</div> ",
								 " 	</div> ",
								 " 	<div class=\"ui-block-b\"> ",
								 " 		<div data-controltype=\"textblock\"> ",
								 " 			<b id=\"blocking_about_me" + i + "\"> Here will be (about me) content... </b> ",
								 " 		</div> ",
								 "	</div> ",
								 " </div> ",
								 " <div data-role=\"collapsible-set\" id=\"collapsible" + i + "\"> ",
								 " 	<div data-role=\"collapsible\" data-collapsed=\"true\" data-mini=\"true\"> ",
								 " 		<h3> ",
								 "			<div class=\"ui-grid-a\"> ",
								 "				<div class=\"ui-block-a\" > ",
								 "             		<div data-controltype=\"textblock\"> ",
								 "						<b> <p id=\"blocking_full_name" + i + "\"> Bar </p> </b> ",
								 "             		</div> ",
								 "         		</div> ",
								 "			</div> ",
								 " 		</h3> ",
								 " 		<div class=\"container-fluid\">	",
								 " 			<section name = \"unseen\"> ",
								 " 			<table class=\"table-striped table-condensed\"  CELLSPACING=10 border=\"0\"  width= \"100%\"> ",
								 "				<thead>	",
								 "					<tr> ",
								 "						<th>Sex</th> ",
								 "						<th>birthdate</th> ",
								 "						<th>language</th> ",
								 "						<th>children</th> ",
								 "						<th>education</th> ",
								 "						<th>hair color</th>	",
								 "					</tr> ",
								 "				</thead> ",
								 "				<tbody>	",
								 "					<tr align=\"center\"> ",
								 "						<td id=\"blocking_sex" + i + "\">Woman</td> ",
								 "						<td id=\"blocking_birthday" + i + "\">27</td> ",
								 "						<td id=\"blocking_language" + i + "\">Merkaz</td> ",
								 "						<td id=\"blocking_children" + i + "\">Student</td> ",
								 "						<td id=\"blocking_education" + i	+ "\">low</td> ",
								 "						<td id=\"blocking_hair_color" + i + "\">1.65</td> ",
								 "					</tr> ",
								 "				</tbody> ",
								 "				<thead>	",
								 "					<tr> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 "					</tr> ",
								 "				</thead> ",
								 "				<thead> ",
								 "					<tr> ",
								 "						<th>intrested in</th> ",
								 "						<th>height</th>	",
								 "						<th>origin</th>	",
								 "						<th>marital status</th>	",
								 "						<th>earning level</th> ",
								 "						<th>eye color</th> ",
								 "					</tr> ",
								 "				</thead> ",
								 "				<tbody>	",
								 "					<tr align=\"center\"> ",
								 "						<td id=\"blocking_intrested_in" + i + "\">Woman</td>	",
								 "						<td id=\"blocking_height" + i + "\">27</td> ",
								 "						<td id=\"blocking_origin" + i + "\">Merkaz</td> ",
								 "						<td id=\"blocking_marital_status" + i + "\">Student</td> ",
								 "						<td id=\"blocking_earning_level" + i + "\">low</td> ",
								 "						<td id=\"blocking_eye_color" + i	+ "\">1.65</td>	",
								 "					</tr> ",
								 "				</tbody> ",
								 "				<thead> ",
								 "					<tr> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 " 						<th></th> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 "						<th></th> ",
								 " 					</tr> ",
								 "				</thead> ",
								 "				<thead> ",
								 "					<tr> ",
								 "						<th>area</th> ",
								 "						<th>weight</th>	",
								 "						<th>religion</th> ",
								 "						<th>smoking</th> ",
								 "						<th>physique</th> ",
								 "						<th>hair type</th> ",
								 "					</tr> ",
								 "				</thead> ",
								 "				<tbody>	",
								 "					<tr align=\"center\"> ",
								 "						<td id=\"blocking_area" + i + "\">Woman</td> ",
								 "						<td id=\"blocking_weight" + i + "\">27</td> ",
								 "						<td id=\"blocking_religion" + i + "\">Merkaz</td> ",
								 "						<td id=\"blocking_smoking" + i + "\">Student</td> ",
								 "						<td id=\"blocking_physique" + i + "\">low</td> ",
								 "						<td id=\"blocking_hair_type" + i + "\">1.65</td> ",
								 "					</tr> ",
								 "				</tbody> ",
								 "			</table> ",
								 "			<div class=\"ui-grid-a\"> ",
								 "				<div class=\"ui-block-a\"> ",
								 "					<a data-role=\"button\" href=\"#\" onclick=\"return showProfile(event, " + val.id + ");\">Profile</a> ",
								 " 				</div> ",
								 "				<div class=\"ui-block-b\"> ",
								 "					<a  id=\"dinamica\" data-role=\"button\" data-theme=\"b\" href=\"#\"  onclick=\"return unBlockOrUnBanProfile(event, " + val.id + ");\">" + str + "</a> ",
								 "				</div> ",
								 "			</div> ",
								 " 		</div> ",
								 " 	</div> ",
								 " </div> ",
								 " <br> ",
								 " <hr> ",
								 " <br> "].join(""));									 
						i = i + 1;
					});
	$("#blocks").trigger("create");
	
	i = 1;
	$.each(blocklist, function(index, val) {

		$("#blocking_photo" + i).attr("src", val.photo);
		$("#blocking_full_name" + i).empty().text(val.firstName);
		$("#blocking_full_name" + i).append(" " + val.lastName);
		$("#blocking_about_me" + i).empty().text("About me: " + val.aboutMe);
		$("#blocking_sex" + i).empty().text(val.sex);
		$("#blocking_birthday" + i).empty().text(val.birthday);
		$("#blocking_education" + i).empty().text(val.education);
		$("#blocking_area" + i).empty().text(val.area);
		$("#blocking_language" + i).empty().text(val.language);
		$("#blocking_marital_status" + i).empty().text(val.maritalStatus);
		$("#blocking_children" + i).empty().text(val.children);
		$("#blocking_eye_color" + i).empty().text(val.eyeColor);
		$("#blocking_hair_type" + i).empty().text(val.hairType);
		$("#blocking_hair_color" + i).empty().text(val.hairColor);
		$("#blocking_origin" + i).empty().text(val.origin);
		$("#blocking_height" + i).empty().text(val.height);
		$("#blocking_weight" + i).empty().text(val.weight);
		$("#blocking_earning_level" + i).empty().text(val.earningLevel);
		$("#blocking_religion" + i).empty().text(val.religion);
		$("#blocking_smoking" + i).empty().text(val.smoking);
		$("#blocking_physique" + i).empty().text(val.physique);
		$("#blocking_intrested_in" + i).empty().text(val.intrestedIn);

		i = i + 1;
	});

};