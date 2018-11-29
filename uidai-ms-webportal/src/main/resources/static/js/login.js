
var login = false;
$(document).keypress(function(e) {
	if (e.which == 13) {
		authUser();
	}
});
	
function authUser() {
	var authUser = {
		"userLoginId" : $("#userLoginId").val(),
		"userPassword" : $("#password").val()
	}
	emptyCheck();
	if (login == true) {
		$.ajax({
			type : 'POST',
			url : 'login',
			contentType : 'application/json',
			beforeSend : function() {
				$('#imgLoaderDiv').show();
			},
			complete : function() {
				$('#imgLoaderDiv').hide();
			},
			data : JSON.stringify(authUser),
			success : function(data, statusText, xhr) {
				if ((xhr.status == 200)) {
					console.log(data);
					if (data.newUser == true) {
						window.location = "changePassword.html";
					} else {
						window.location = "index.html";
					}
					$.cookie("JSESSIONID", data.token);

				}
			},
			error : function(data, statusText, xhr) {
				if (data.status == 423) {
					showAlert("Error : Your account is InActive.",
							"alert-danger", "alertContainer", "alertRow");
				} else {
					showAlert("Error : Invalid User ID or Password. Please enter correct User ID and Password",
							"alert-danger", "alertContainer", "alertRow");
				}
			}
		});
	}

}

function emptyCheck() {
	if ($("#userLoginId").val().length == 0 && $("#password").val().length == 0) {
		login = false;
		showAlert("User ID and Password can not be blank.", "alert-danger",
				"alertContainer", "alertRow");
	} else if ($("#userLoginId").val().length == 0) {
		login = false;
		showAlert("User ID can not be blank.", "alert-danger",
				"alertContainer", "alertRow");
	} else if ($("#password").val().length == 0) {
		login = false;
		showAlert("Password can not be blank.", "alert-danger",
				"alertContainer", "alertRow");
	} else {
		login = true;
		$('#alertRow').hide();
	}
}
function reset() {
	$("#userLoginId").val("");
	$("#password").val("");

}