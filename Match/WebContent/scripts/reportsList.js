/**
 * 
 */

$(document).on('pagebeforeshow', "#reports", function(event) { 
	$.ajax({
		
		data : {
			
			isDelete : "false",
			isChangeFlag : "false"
		},
		
		dataType : 'json',	
		url : "ReportsListServlet",
		timeout : 2000,
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			if(data == "go to sign in"){
				$.mobile.changePage("#page1");
			}
			else{
			refreshReportsList(data);
			$("#reports").trigger("create");
			}
		}
	});

	$("#reports").trigger("create");
	return false;
});

function deleteReportAndBan(event,user_id,reported) {
	
	$(function() {
		jQuery.ajax({
			
			data : {
			
				isDelete : "true",
				isBan : "true",
				reporterId : user_id,
				reportedId : reported	
			},
			
			dataType : 'json',	
			url : "ReportsListServlet",
			timeout : 2000,
			error : function() {
				console.log("Failed to submit");
			},
			success : function(data) {
				if (data == "go to sign in") {
					$.mobile.changePage("#page1");
				} else {
					refreshReportsList(data);
					$("#reports").trigger("create");
				}
			}
		});
	});
	$("#reports").trigger("create");
		
}

function setSeenFlagAndShowProfile(event,seenFlag,reporterId,reportedId,timeStamp)
{	
	
	if(seenFlag == 0)
    {
	
	$(function() {
		jQuery.ajax({
			
			data : {
				
				isDelete : "false",
				isChangeFlag : "true",
				timeStamp : timeStamp,
				reporterId : reporterId,
				reportedId : reportedId
			},
			dataType : 'json',
			url : "ReportsListServlet",
			error : function() {
				console.log("Failed to submit");
			},
			success : function(data) {
				if (data == "go to sign in") {
					$.mobile.changePage("#page1");
				} 
			}
		});
		
});
	
    }
	
	showProfile(event,reportedId);
	
}

function deleteReport(event,userId,reportedId){
	
		// add a function to the submit event
	$(function() {
			jQuery.ajax({
				
				data : {
					
					isDelete : "true",	
					isBan : "false",
					reporterId : userId,
					reportedId : reportedId
				//	timeStamp : timeStamp
				},
				dataType : 'json',
				url : "ReportsListServlet",
				error : function() {
					console.log("Failed to submit");
				},
				success : function(data) {
					if (data == "go to sign in") {
						$.mobile.changePage("#page1");
					} else {
						refreshReportsList(data);
						$("#reports").trigger("create");
					}
				}
			});	
	});
	
	$("#reports").trigger("create");
	return false;	
}

function refreshReportsList(reportList) {
	
 	$("#start_create_reports").empty();
	i = 1;
	$.each(reportList, function(index, val) {	
		ch = "b";
		if(!val.seenFlag) {
			ch = "e";
		} 
		$("#start_create_reports").append(
				[" <li data-corners=\"false\" data-shadow=\"false\" data-iconshadow=\"true\" data-wrapperels=\"div\" data-icon=\"arrow-r\" data-iconpos=\"right\" data-theme=\"" + ch + "\" class=\"ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-li-has-thumb ui-btn-up-" + ch + "\"> ",
				 " 	<div class=\"ui-btn-inner ui-li\"> ",
				 " 		<div class=\"ui-btn-text\"> ",
				 " 				<p id=\"report_timestamp" + i +"\" class=\"ui-li-aside\"><font size=\"1\"><dfn></dfn><br><span></span></font></p> ",
				 " 				<h2 class=\"ui-li-heading\" id=\"reporter_id" + i +"\"></h2> ",
				 " 				<p class=\"ui-li-desc\" id=\"report_content" + i +"\"></p> ",
				 "  	<div class=\"ui-grid-c\"> ",
				 "			<div class=\"ui-block-a\"> ",
				 "				<a data-role=\"button\" href=\"#\" onclick=\"return setSeenFlagAndShowProfile(event, " + val.seenFlag + ", " + val.userId + ", " + val.reported + ", "  + "\'" + val.timeStamp + "\'" + ");\">Profile</a> ",
				 " 			</div> ",
				 "			<div class=\"ui-block-b\"> ",
				 "				<a data-role=\"button\" data-theme=\"b\" href=\"#\" onclick=\"return deleteReportAndBan(event, " + val.userId + ", " + val.reported + ");\">Ban User</a> ",
				 "			</div> ",
				 "				<div class=\"ui-block-c\"> ",
				 "					<a data-role=\"button\" data-theme=\"b\" href=\"#\" onclick=\"return deleteReport(event, " + val.userId + ", " + val.reported + ");\">Done</a> ",
				 "			  </div> ",
				 " 		</div> ",
				 "		<span class=\"ui-icon ui-icon-arrow-r ui-icon-shadow\">&nbsp;</span> ",
				 "	  </div> ",
				 "</div> ",
				 " </li> "].join(""));
		
		i = i + 1;
	});
	$("#reports").trigger("create");
	
	i = 1;
	$.each(reportList, function(index, val) {
		
		$("#reporter_id" + i).text(val.userId);
		if(!val.seenFlag) {
			$("#report_content" + i).append("<b>" + val.content + "</b>");
		} else {
			$("#report_content" + i).text(val.content);
		}
		$("#message_timestamp" + i + " font dfn").text(val.inOut + " at:");
		$("#message_timestamp" + i + " font span").text(val.timeStamp);
		i = i + 1;
	});
	$("#start_create_reports").listview("refresh");
};
 
