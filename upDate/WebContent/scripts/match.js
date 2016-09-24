/**
 * 
 */

$(document).on('pagebeforeshow', "#matches", function(event) { // on page
	// load...do
	$.ajax({
		dataType : 'json',
		url : "matches",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if (data == "go to sign in") {
				$.mobile.changePage("#page1");
			} else if (data == "bannedUser"){
				$("#matches_banned_label").empty().text("your profile has been banned from UpDate!!");
				var img = document.createElement("img");
				img.src = "http://www.google.com/intl/en_com/images/logo_plain.png";

				var src = document.getElementById("matches_banned_label");
				src.appendChild(img);
				storeObject.isBannedUser = "true";
				$("#start_create").empty();
				refreshMatchData(data);
				$("#matches").trigger("create");
			}
			else{
				$("#matches_banned_label").empty().text("");
				$("#start_create").empty();
				refreshMatchData(data);
				$("#matches").trigger("create");
			}
		}
	});

	$("#matches").trigger("create");
	return false;
});

function refreshMatchData(matchlist) {

	i = 1;
	$.each(matchlist, function(index, val) {
		$("#start_create").append(
			[" <br> ",
			 " <div class=\"ui-grid-a\" style=\"width: 100%; height: 100%;\" id=\"block" + i + "\"> ",
			 " 	<div class=\"ui-block-a\" style=\"width: 50%;\"> ",
			 " 		<div style=\"position: relative;\" data-controltype=\"image\"> ",
			 " 			<img id=\"profile_photo" + i + "\" src=\"\" alt=\"image\" style=\"width: 7em;\"> ",
			 " 		</div> ",
			 " 	</div> ",
			 " 	<div class=\"ui-block-b\"> ",
			 " 		<div data-controltype=\"textblock\"> ",
			 " 			<b id=\"profile_about_me" + i + "\"> Here will be (about me) content... </b> ",
			 " 		</div> ",
			 "	</div> ",
			 " </div> ",
			 " <div data-role=\"collapsible-set\" id=\"collapsible" + i + "\"> ",
			 " 	<div data-role=\"collapsible\" data-collapsed=\"true\" data-mini=\"true\"> ",
			 " 		<h3> ",
			 "			<div class=\"ui-grid-a\"> ",
			 "				<div class=\"ui-block-a\" > ",
			 "             		<div data-controltype=\"textblock\"> ",
			 "						<b> <p id=\"profile_full_name" + i + "\"> Bar </p> </b> ",
			 "             		</div> ",
			 "         		</div> ",
			 "     			<div class=\"ui-block-b\"> ",
			 "					<div data-controltype=\"textblock\" style=\" margin: 10px;\"> ",
			 "						<p id=\"profile_score" + i + "\"> 100 </p> ",
			 "					</div> ",
			 "				</div> ",
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
			 "						<td id=\"profile_sex" + i + "\">Woman</td> ",
			 "						<td id=\"profile_birthday" + i + "\">27</td> ",
			 "						<td id=\"profile_language" + i + "\">Merkaz</td> ",
			 "						<td id=\"profile_children" + i + "\">Student</td> ",
			 "						<td id=\"profile_education" + i	+ "\">low</td> ",
			 "						<td id=\"profile_hair_color" + i + "\">1.65</td> ",
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
			 "						<td id=\"profile_intrested_in" + i + "\">Woman</td>	",
			 "						<td id=\"profile_height" + i + "\">27</td> ",
			 "						<td id=\"profile_origin" + i + "\">Merkaz</td> ",
			 "						<td id=\"profile_marital_status" + i + "\">Student</td> ",
			 "						<td id=\"profile_earning_level" + i + "\">low</td> ",
			 "						<td id=\"profile_eye_color" + i	+ "\">1.65</td>	",
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
			 "						<td id=\"profile_area" + i + "\">Woman</td> ",
			 "						<td id=\"profile_weight" + i + "\">27</td> ",
			 "						<td id=\"profile_religion" + i + "\">Merkaz</td> ",
			 "						<td id=\"profile_smoking" + i + "\">Student</td> ",
			 "						<td id=\"profile_physique" + i + "\">low</td> ",
			 "						<td id=\"profile_hair_type" + i + "\">1.65</td> ",
			 "					</tr> ",
			 "				</tbody> ",
			 "			</table> ",
			 "			<div class=\"ui-grid-a\"> ",
			 "				<div class=\"ui-block-a\"> ",
			 "					<a data-role=\"button\" href=\"#\" onclick=\"return showProfile(event, " + val.id + ");\">Profile</a> ",
			 " 				</div> ",
			 "				<div class=\"ui-block-b\"> ",
			 "					<a data-role=\"button\" href=\"#\" onclick=\"return goToChatWithParam(event, " + val.id + ");\">Message</a> ",
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
	$("#matches").trigger("create");

	i = 1;
	$.each(matchlist, function(index, val) {

		$("#profile_photo" + i).attr("src", val.photo);
		$("#profile_full_name" + i).empty().text(val.firstName);
		$("#profile_full_name" + i).append(" " + val.lastName);
		$("#profile_score" + i).empty().text("score: " + (val.score + 1));
		$("#profile_about_me" + i).empty().text("About me: " + val.aboutMe);
		$("#profile_sex" + i).empty().text(val.sex);
		$("#profile_birthday" + i).empty().text(val.birthday);
		$("#profile_education" + i).empty().text(val.education);
		$("#profile_area" + i).empty().text(val.area);
		$("#profile_language" + i).empty().text(val.language);
		$("#profile_marital_status" + i).empty().text(val.maritalStatus);
		$("#profile_children" + i).empty().text(val.children);
		$("#profile_eye_color" + i).empty().text(val.eyeColor);
		$("#profile_hair_type" + i).empty().text(val.hairType);
		$("#profile_hair_color" + i).empty().text(val.hairColor);
		$("#profile_origin" + i).empty().text(val.origin);
		$("#profile_height" + i).empty().text(val.height);
		$("#profile_weight" + i).empty().text(val.weight);
		$("#profile_earning_level" + i).empty().text(val.earningLevel);
		$("#profile_religion" + i).empty().text(val.religion);
		$("#profile_smoking" + i).empty().text(val.smoking);
		$("#profile_physique" + i).empty().text(val.physique);
		$("#profile_intrested_in" + i).empty().text(val.intrestedIn);

		i = i + 1;
	});

	// Refresh the students list - this will pull the persisted
	// student data out of the SQLite database and put it into
	// the UL element.
	getPartners(refreshLastSeenPartners);
};

// I refresh the last seen partners list.
var refreshLastSeenPartners = function(results) {
	// Clear out the list of last seen partners.
	$("#last_seen_partners").empty();
	$("#matches").trigger("create");

	// Check to see if we have any results.
	if (!results) {
		return;
	}

	$("#last_seen_partners")
			.append(
					"<li data-role=\"list-divider\" role=\"heading\">last seen partners:</li>");

	// Loop over the current list of partners and add them
	// to the visual list.
	for (var i = 0; i < results.rows.length; i++) {
		var row = results.rows.item(i);

		// Append the list item.
		$("#last_seen_partners")
				.append(
						"<li data-theme=\"b\"><a href=\"#\" data-transition=\"slide\" onclick=\"return showProfile(event, "
								+ row.id
								+ ");\">"
								+ "<img id=\"last_seen_photo"
								+ row.id
								+ "\" src=\""
								+ row.photo
								+ "\" alt=\"image\" style=\"width: 5em;\">"
								+ "</a></li>"
								+ "<li data-theme=\"b\"><a href=\"#\" data-transition=\"slide\" onclick=\"return showProfile(event, "
								+ row.id
								+ ");\">"
								+ row.first
								+ "</a></li>"
								+ "<li data-role=\"list-divider\" role=\"heading\"></li>");
	}
	$("#last_seen_partners").listview("refresh");
	$("#matches").trigger("create");
};