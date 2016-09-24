
var HEARTBEAT_RATE = 1000;
setInterval(function() {
	jQuery.ajax({
		data : {},
		dataType : 'json',
		type : "POST",
		url : "heartbeat"
	});
}, HEARTBEAT_RATE);

setInterval(function() {
	jQuery.ajax({
		data : {action:"unread"},
		dataType : 'json',
		type : "POST",
		url : "messages",
		success:function(data) {
			if(data != "noSessionID"){
				jQuery(".messagesMenuOption .ui-li-count").text(data);
			}
		},
		error:function(data) {
			console.log("Error", data);
		//	$.mobile.changePage("#page1");
		}
	});
}, HEARTBEAT_RATE);