var chatCounter = 0;

$(function() { // on load function
	// add a function to the submit event
	$("#chatSendButton")
			.click(
					function() {
						if (chatCounter <= 100) {
							var messageText = $("#messageText").val();
							jQuery
									.ajax({
										data : {
											messageText : messageText,
											targetId : storeObject.storeID
										},
										dataType : 'json',
										url : "chat",
										type : "POST",
										error : function() {
											console
													.log("chat.js: function on load failed to send ajax");
										},
										success : function(data) {
											$("#messageText").val("");
										}
									});
						}
					});
});

var stopCheckMessages = null;
var stopCheckHeartbeat = null;
$(document).on('pageshow', '#chat', function() {
	showConversator();
	$('#content').height(getRealContentHeight());
	stopCheckMessages = setInterval(checkMessages, HEARTBEAT_RATE);
	if (storeObject.storeID != 0) {
		stopCheckHeartbeat = setInterval(checkHeartbeat, HEARTBEAT_RATE);
	}
	$('#messageText').val('');
	$('#messageText').css('height', '50px');
	$("#chat_counter").empty();
	$("#online_status").empty();

	// if message from the administrator then disable send button
	if (storeObject.storeID == 0) {
		$("#chatSendButton").addClass("ui-disabled");
	} else {
		$("#chatSendButton").removeClass("ui-disabled");
	}
	$("#chat").trigger("create");
	chatCounter = 0;
});

$(document).on('pagehide', '#chat', function() {
	jQuery("#incomingMessages div").remove();
	clearInterval(stopCheckMessages);
	clearInterval(stopCheckHeartbeat);
});

function getRealContentHeight() {
	var header = $.mobile.activePage.find("div[data-role='header']:visible");
	var footer = $.mobile.activePage.find("div[data-role='footer']:visible");
	var content = $.mobile.activePage
			.find("div[data-role='content']:visible:visible");
	var viewport_height = $(window).height();

	var content_height = viewport_height - header.outerHeight()
			- footer.outerHeight();
	if ((content.outerHeight() - header.outerHeight() - footer.outerHeight()) <= viewport_height) {
		content_height -= (content.outerHeight() - content.height());
	}
	return content_height - 2;
}

function showConversator() {

	jQuery.ajax({
		data : {
			profileId : storeObject.storeID
		},
		dataType : 'json',
		url : "partnerprofile",
		error : function() {
			console.log("Failed to submit");
		},
		success : function(data) {
			$("#conversator_name").empty().text(data.firstName);
			$("#conversator_photo").attr("src", data.photo);
		}
	});
}

function checkMessages() {

	jQuery
			.ajax({
				data : {
					targetId : storeObject.storeID
				},
				dataType : 'json',
				type : "GET",
				url : "chat",
				error : function(error) {
					console.log(
							"chat.js: function on load failed to send ajax",
							error);
					$.mobile.changePage("#messages");
				},
				success : function(data) {
					jQuery("#incomingMessages div").remove();
					chatCounter = data.length;

					if (storeObject.storeID != 0) {
						$("#chat_counter").empty().text(100 - chatCounter);
					}

					if (chatCounter > 100 || storeObject.storeID == 0) {
						$("#chatSendButton").addClass("ui-disabled");
					} else {
						$("#chatSendButton").removeClass("ui-disabled");
					}
					$("#chat").trigger("create");

					if (storeObject.storeID != 0) { // message from
						// administrator
						jQuery("#incomingMessages")
								.append(
										"<div class = 'message'><div class = 'realityCheck' style='width:80%'><strong>"
												+ "Hello upDaters!<br />"
												+ "Welcome to the chat room. You should know that this chat is not like any other!<br />"
												+ "It is limited to 100 messages to the two of you together, and there is a reason for that!<br />"
												+ "We want you to go out, date, converse in real life and venture! <br />We don’t want our app "
												+ "to bind you to the limited boundaries of the virtual world, so go ahead and get to know each other, "
												+ "but don’t forget there’s a real life upDate here waiting to happen :) "
												+ "</strong></div></div>");
					}

					for (var i = 0; i < data.length; i++) {

						var message = "<font color='#2c1608' style='font-size:10px'>"
								+ data[i].timeStamp + "</font><br />";

						message += data[i].content;

						if (data[i].inOut == 'sent') {
							jQuery("#incomingMessages").append(
									"<div class = 'message'><div class = 'bubble me'>"
											+ message + "</div></div>");
						} else {
							jQuery("#incomingMessages").append(
									"<div class = 'message'><div class = 'bubble you'>"
											+ message + "</div></div>");
						}
						if (storeObject.storeID != 0) { // message from
							// administrator
							if (i == 29) {
								jQuery("#incomingMessages")
										.append(
												"<div class = 'message'><div class = 'realityCheck' style='width:80%'><strong>"
														+ "Hey you two! 70 messages left. I feel an upDate coming up!"
														+ "</strong></div></div>");
							}

							if (i == 59) {
								jQuery("#incomingMessages")
										.append(
												"<div class = 'message'><div class = 'realityCheck' style='width:80%'><strong>"
														+ "I see you two are hitting it off :)<br />"
														+ "Have you exchanged phone numbers yet? 40 messages left!"
														+ "</strong></div></div>");
							}

							if (i == 89) {
								jQuery("#incomingMessages")
										.append(
												"<div class = 'message'><div class = 'realityCheck' style='width:80%'><strong>"
														+ "Knock knock! Who’s there? A reality check! Reality check who? Only 10 messages left!"
														+ "</strong></div></div>");
							}

							if (i == 99) {
								jQuery("#incomingMessages")
										.append(
												"<div class = 'message'><div class = 'realityCheck' style='width:80%'><strong>"
														+ "Uh oh…you have used up all your messages."
														+ "</strong></div></div>");
								break;
							}
						}
					}
				}
			});
}

function checkHeartbeat() {

	jQuery
			.ajax({
				data : {
					targetId : storeObject.storeID
				},
				dataType : 'json',
				type : "GET",
				url : "heartbeat",
				error : function(error) {
					console.log(
							"chat.js: function on load failed to send ajax",
							error);
					$.mobile.changePage("#messages");
				},
				success : function(data) {
					var currentTimestamp = (new Date()).getTime();
					if (currentTimestamp - data > HEARTBEAT_RATE * 2) {
						$("div[data-role='footer'] label:first").css('color',
								'#FDB805');
						$("#online_status").empty().text("offline");
					} else {
						$("div[data-role='footer'] label:first").css('color',
								'#23FD05');
						$("#online_status").empty().text("online");
					}

					console.log(data, currentTimestamp, HEARTBEAT_RATE,
							(currentTimestamp - data));
				}
			});
}
