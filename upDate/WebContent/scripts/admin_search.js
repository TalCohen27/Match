var elementSelected;
var valueSelected;

$(document).on('pagebeforeshow', "#adminSearchAndBan", function(event) {
	$("#adminSearchAndBan").trigger("create");
	return false;
});

function actionOnOptionClick() {
	elementSelected = document.getElementById("select_id");
	valueSelected = elementSelected.options[elementSelected.selectedIndex].text;
	$("#lable_input_search").empty().text(valueSelected);
	if (valueSelected == "sex") {
		$("#input_search").empty().append([" <select id=\"select_sex_id\"> ",
		                                   " 	<option value=\"male\" selected>Male</option> ",
		                                   " 	<option value=\"female\">Female</option> ",
		                                   " </select> "].join(''));		
	} else {
		$("#input_search").empty().append([" <input name=\"text_search_input\" id=\"text_search_input\" type=\"text\"> "].join(''));
	}
	$("#adminSearchAndBan").trigger("create");
};

function continuePage(event,id) {
	storeObject.storeID = id;
	$.mobile.changePage("#adminBanPage");
}

function show_search_results(event) {
	var search_value;
	
	if (valueSelected == "sex") {
		var sexElementSelected = document.getElementById("select_sex_id");
		search_value = sexElementSelected.options[sexElementSelected.selectedIndex].text;
	} else {
		search_value = document.getElementById('text_search_input').value;
	}
	
	elementSelected = document.getElementById("select_id");
	valueSelected = elementSelected.options[elementSelected.selectedIndex].text;

	$.ajax({
		data : {
			search_field : valueSelected,
			search_value : search_value
		},
		dataType : 'json',
		url : "adminSearchServlet",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(resultUserList) {
			// //TODO
			// // if(data == "go to sign in"){
			var myNode = document.getElementById("start_create_search");
			while (myNode.firstChild) {
				myNode.removeChild(myNode.firstChild);
			}
			refreshSearchData(resultUserList);
		}
	});
	return false;
}

function refreshSearchData(resultUserList) {

	i = 1;
	$
			.each(
					resultUserList,
					function(index, val) {
						$("#start_create_search").append(
								[" <br> ",
								 " <div class=\"ui-grid-a\" style=\"width: 100%; height: 100%;\" id=\"block" + i + "\"> ",
								 " 	<div class=\"ui-block-a\" style=\"width: 50%;\"> ",
								 " 		<div style=\"position: relative;\" data-controltype=\"image\"> ",
								 " 			<img id=\"search_photo" + i + "\" src=\"\" alt=\"image\" style=\"width: 7em;\"> ",
								 " 		</div> ",
								 " 	</div> ",
								 " 	<div class=\"ui-block-b\"> ",
								 " 		<div data-controltype=\"textblock\"> ",
								 " 			<b id=\"search_about_me" + i + "\"> Here will be (about me) content... </b> ",
								 " 		</div> ",
								 "	</div> ",
								 " </div> ",
								 " <div data-role=\"collapsible-set\" id=\"collapsible" + i + "\"> ",
								 " 	<div data-role=\"collapsible\" data-collapsed=\"true\" data-mini=\"true\"> ",
								 " 		<h3> ",
								 "			<div class=\"ui-grid-a\"> ",
								 "				<div class=\"ui-block-a\" > ",
								 "             		<div data-controltype=\"textblock\"> ",
								 "						<b> <p id=\"search_full_name" + i + "\"> Bar </p> </b> ",
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
								 "						<td id=\"search_sex" + i + "\">Woman</td> ",
								 "						<td id=\"search_birthday" + i + "\">27</td> ",
								 "						<td id=\"search_language" + i + "\">Merkaz</td> ",
								 "						<td id=\"search_children" + i + "\">Student</td> ",
								 "						<td id=\"search_education" + i	+ "\">low</td> ",
								 "						<td id=\"search_hair_color" + i + "\">1.65</td> ",
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
								 "						<td id=\"search_intrested_in" + i + "\">Woman</td>	",
								 "						<td id=\"search_height" + i + "\">27</td> ",
								 "						<td id=\"search_origin" + i + "\">Merkaz</td> ",
								 "						<td id=\"search_marital_status" + i + "\">Student</td> ",
								 "						<td id=\"search_earning_level" + i + "\">low</td> ",
								 "						<td id=\"search_eye_color" + i	+ "\">1.65</td>	",
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
								 "						<td id=\"search_area" + i + "\">Woman</td> ",
								 "						<td id=\"search_weight" + i + "\">27</td> ",
								 "						<td id=\"search_religion" + i + "\">Merkaz</td> ",
								 "						<td id=\"search_smoking" + i + "\">Student</td> ",
								 "						<td id=\"search_physique" + i + "\">low</td> ",
								 "						<td id=\"search_hair_type" + i + "\">1.65</td> ",
								 "					</tr> ",
								 "				</tbody> ",
								 "			</table> ",
								 "			<div class=\"ui-grid-a\"> ",
								 "				<div class=\"ui-block-a\"> ",
								 "					<a data-role=\"button\" href=\"#\" onclick=\"return showProfile(event, " + val.id + ");\">Profile</a> ",
								 " 				</div> ",
								 "				<div class=\"ui-block-b\"> ",
								 "					<a data-role=\"button\" data-theme=\"b\" href=\"#\" onclick=\"return continuePage(event, " + val.id + ");\">Ban User</a> ",
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
	$("#adminSearchAndBan").trigger("create"); // //////////////////////////////////TODO
	i = 1;
	$.each(resultUserList, function(index, val) {

		$("#search_photo" + i).attr("src", val.photo);
		$("#search_full_name" + i).empty().text(val.firstName);
		$("#search_full_name" + i).append(" " + val.lastName);
		$("#search_about_me" + i).empty().text("About me: " + val.aboutMe);
		$("#search_sex" + i).empty().text(val.sex);
		$("#search_birthday" + i).empty().text(val.birthday);
		$("#search_education" + i).empty().text(val.education);
		$("#search_area" + i).empty().text(val.area);
		$("#search_language" + i).empty().text(val.language);
		$("#search_marital_status" + i).empty().text(val.maritalStatus);
		$("#search_children" + i).empty().text(val.children);
		$("#search_eye_color" + i).empty().text(val.eyeColor);
		$("#search_hair_type" + i).empty().text(val.hairType);
		$("#search_hair_color" + i).empty().text(val.hairColor);
		$("#search_origin" + i).empty().text(val.origin);
		$("#search_height" + i).empty().text(val.height);
		$("#search_weight" + i).empty().text(val.weight);
		$("#search_earning_level" + i).empty().text(val.earningLevel);
		$("#search_religion" + i).empty().text(val.religion);
		$("#search_smoking" + i).empty().text(val.smoking);
		$("#search_physique" + i).empty().text(val.physique);
		$("#search_intrested_in" + i).empty().text(val.intrestedIn);

		i = i + 1;
	});

};