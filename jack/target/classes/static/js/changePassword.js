//--for toggle menu--//
$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
});

let ucaseS = false;
let lcaseS = false;
let numS = false;
let scharS = false;
let lengS = false;
let confirmS = false;
let currNotEmpty = false;

// --for data table--//
$(document).ready(function() {
	if ($.cookie("JSESSIONID") === "") {
		window.location = "/uidai-ms-webportal-ui/";
	}

	$('#example').DataTable();
	$('#farmer_list').DataTable({
		"scrollX" : true
	});

	if ($("#newPassword").val() === $("#confirmPassowrd").val()) {
		$("#pwmatch").removeClass("glyphicon-remove");
		$("#pwmatch").addClass("glyphicon-ok");
		$("#pwmatch").css("color", "#00A41E");
		confirmS = true;
	} else {
		$("#pwmatch").removeClass("glyphicon-ok");
		$("#pwmatch").addClass("glyphicon-remove");
		$("#pwmatch").css("color", "#FF0004");
		confirmS = false;
	}

	// showAlert ("Error: Key rotation configuration information could not be
	// found", "alert-danger" ,"errorAlertContainer","errorAlertRow");

});

$("#currentPassword").keyup(function() {
	if ($("#currentPassword").val().length >= 1) {
		currNotEmpty = true;
	} else {
		currNotEmpty = false;
	}

	disableEnableButton();
});
$("#newPassword").keyup(function() {

	const ucase = new RegExp("[A-Z]+");
	const lcase = new RegExp("[a-z]+");
	const num = new RegExp("[0-9]+");
	const schar = /^[a-zA-Z0-9 ]*$/;

	if ($("#newPassword").val().length >= 8) {
		$("#8char").removeClass("glyphicon-remove");
		$("#8char").addClass("glyphicon-ok");
		$("#8char").css("color", "#00A41E");
		lengS = true;
	} else {
		$("#8char").removeClass("glyphicon-ok");
		$("#8char").addClass("glyphicon-remove");
		$("#8char").css("color", "#FF0004");
		lengS = false;
	}

	if (ucase.test($("#newPassword").val())) {
		$("#ucase").removeClass("glyphicon-remove");
		$("#ucase").addClass("glyphicon-ok");
		$("#ucase").css("color", "#00A41E");
		ucaseS = true;
	} else {
		$("#ucase").removeClass("glyphicon-ok");
		$("#ucase").addClass("glyphicon-remove");
		$("#ucase").css("color", "#FF0004");
		ucaseS = false;
	}

	if (lcase.test($("#newPassword").val())) {
		$("#lcase").removeClass("glyphicon-remove");
		$("#lcase").addClass("glyphicon-ok");
		$("#lcase").css("color", "#00A41E");
		lcaseS = true;
	} else {
		$("#lcase").removeClass("glyphicon-ok");
		$("#lcase").addClass("glyphicon-remove");
		$("#lcase").css("color", "#FF0004");
		lcaseS = false;
	}

	if (num.test($("#newPassword").val())) {
		$("#num").removeClass("glyphicon-remove");
		$("#num").addClass("glyphicon-ok");
		$("#num").css("color", "#00A41E");
		numS = true;
	} else {
		$("#num").removeClass("glyphicon-ok");
		$("#num").addClass("glyphicon-remove");
		$("#num").css("color", "#FF0004");
		numS = false;
	}

	if (!schar.test($("#newPassword").val())) {
		$("#schar").removeClass("glyphicon-remove");
		$("#schar").addClass("glyphicon-ok");
		$("#schar").css("color", "#00A41E");
		scharS = true;
	} else {
		$("#schar").removeClass("glyphicon-ok");
		$("#schar").addClass("glyphicon-remove");
		$("#schar").css("color", "#FF0004");
		scharS = false;
	}
	if ($("#newPassword").val() === $("#confirmPassowrd").val()) {
		$("#pwmatch").removeClass("glyphicon-remove");
		$("#pwmatch").addClass("glyphicon-ok");
		$("#pwmatch").css("color", "#00A41E");
		confirmS = true;
	} else {
		$("#pwmatch").removeClass("glyphicon-ok");
		$("#pwmatch").addClass("glyphicon-remove");
		$("#pwmatch").css("color", "#FF0004");
		confirmS = false;
	}
	disableEnableButton();
})

$("#confirmPassowrd").keyup(function() {
	if ($("#newPassword").val() === $("#confirmPassowrd").val()) {
		$("#pwmatch").removeClass("glyphicon-remove");
		$("#pwmatch").addClass("glyphicon-ok");
		$("#pwmatch").css("color", "#00A41E");
		confirmS = true;
	} else {
		$("#pwmatch").removeClass("glyphicon-ok");
		$("#pwmatch").addClass("glyphicon-remove");
		$("#pwmatch").css("color", "#FF0004");
		confirmS = false;
	}
	disableEnableButton();
})

function disableEnableButton() {

	if (ucaseS && lcaseS && numS && scharS && lengS && confirmS && currNotEmpty) {
		$("#changePassword").prop('disabled', '');
	} else {
		$("#changePassword").prop('disabled', 'disabled');
	}
}
function updatePassword() {
	

	const old_password = $("#currentPassword").val();
	const password = $("#newPassword").val();

	$.ajax({
		type : 'POST',
		url : 'change-password',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		data : {
			password : old_password,
			new_password : password
		},
		success : function(data, statusText, xhr) {
			if (xhr.status === 200) {
				showAlert("Success: Password has been sucessfully changed",
						"alert-success", "alertContainer", "alertRow");
			} else {
				//alert("Please enter correct current password");
				showAlert("Error: Please enter correct current password",
						"alert-danger", "alertContainer", "alertRow");
			}
		}
	});

}

function goBack() {
	window.history.back();
}
