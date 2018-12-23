let idleTime = 0;

$(document).ready(function() {

	$.ajax({
		type : 'GET',
		url : 'get-loggedin-user',
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		success : function(data) {
			if (data != null) {
				$("#loggeduser").html("<b>Welcome : </b>" + data.firstName);
			} else {
			}
		},
		error : function(data) {
			document.cookie = 'JSESSIONID' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;'
			window.location = "/uidai-ms-webportal-ui/";
		}
	});

	//Increment the idle time counter every minute.
	var idleInterval = setInterval(timerIncrement, 60000); // 1 minute

	//Zero the idle timer on mouse movement.
	$(this).mousemove(function(e) {
		idleTime = 0;
	});
	$(this).keypress(function(e) {
		idleTime = 0;
	});
});

if ($.cookie("JSESSIONID") == "") {
	window.location = "/uidai-ms-webportal-ui/";
}

if (typeof $.cookie('JSESSIONID') === 'undefined') {
	window.location = "/uidai-ms-webportal-ui/";
} 

function timerIncrement() {
	idleTime = idleTime + 1;
	if (idleTime >= 60) { // 20 minutes
		$.cookie("JSESSIONID", "");
		document.cookie = 'JSESSIONID'
				+ '=; expires=Thu, 01-Jan-70 00:00:01 GMT;'
		logout();
	}
}

function logout() {
	$.ajax({
		type : 'GET',
		url : 'logout',
		contentType : 'application/json',
		success : function(data) {
			window.location = "/uidai-ms-webportal-ui/";
		},
		error : function(data) {

		}
	});
	document.cookie = 'JSESSIONID' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;'
	window.location = "/uidai-ms-webportal-ui/";
}




