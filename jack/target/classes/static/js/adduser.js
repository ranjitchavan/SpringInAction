$(document).ready(function() {

	$("#userRegister").submit(function(e) {
		e.preventDefault();
		submitForm();
	});

});

function goBack() {
	window.history.back();
}

function submitForm() {
	const firstName = $("#first_name").val();
	const middleName = $("#middle_name").val();
	const lastName = $("#last_name").val();
	const emailId = $("#email_id").val();
	const phone = $("#phone").val();
	const address = $("#address").val();
	const password = $("#password").val();
	const user = {
		"firstName" : firstName,
		"lastName" : lastName,
		"middleName" : middleName,
		"email" : emailId,
		"phone" : phone,
		"address" : address,
		"password" : password

	};

	const nameValidator = /^[a-zA-Z]+$/;
	const emailValidator = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	let msg = "";
	if (password.length === 0) {
		msg = msg + "Password cannot be blank <br/>";
	}
	
	if (firstName.length === 0) {
		msg = msg + "Firstname cannot be blank <br/>";
	}
	if (lastName.length === 0) {
		msg = msg + "LastName cannot be blank <br/>";
	}

	if (emailId.length === 0) {
		msg = msg + "Email cannot be blank <br/>";
	}
	
	if (password !== $("#password_two").val()) {
		msg = msg + "Password and confirm password must be same <br/>";
	}

	if(firstName.length && !nameValidator.test(firstName)){
		msg = msg + "First Name should only contain letters <br/>";
	}
	
	if(middleName.length && !nameValidator.test(middleName)){
		msg = msg + "Middle Name should only contain letters <br/>";
	}
	if(lastName.length && !nameValidator.test(lastName)){
		msg = msg + "Last Name should only contain letters <br/>";
	}
	if(emailId.length && !emailValidator.test(emailId)){
		msg = msg + "Please enter valid E-mail. i.e. user@mailserver.com <br/>";
	}
	if (!checkPwd(password)) {
		msg = msg
				+ "Password Contains  At least 8 characters , 1 small letter, 1 capital letter,  1 special character , 1 digit <br/>"
	}

	if (phone.length > 0) {
		const phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;

		if (!phone.match(phoneno)) {
			msg = msg + "Phone No. is not valid.";
		}
	}

	if (msg.length === 0) {
		addUser(user);		
	} else {
		showAlert(msg, "alert-danger", "alertContainer", "alertRow");
	}
}
function addUser(user){
	$
	.ajax({
		type : 'POST',
		url : 'add-user',
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		data : JSON.stringify(user),
		beforeSend : function() {
			$('#imgLoaderDiv').show();
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function(data, statusText, xhr) {
			if ((xhr.status === 200) && (data.length !== 0)) {
				showAlert("User Added Successfully",
						"alert-success", "alertContainer",
						"alertRow");
				const delay = 3000;
				setTimeout(function() {
					window.location = "activeUserList.html";
				}, delay);
			} else if (xhr.status === 208) {
				showAlert("Error:User already exists",
						"alert-danger", "alertContainer",
						"alertRow");
			} else if (xhr.status === 226) {
				showAlert(
						"Warning : User is in-active. Ask admin to re-activate the user.",
						"alert-warning", "alertContainer",
						"alertRow");
			}
		},
		error : function() {

			showAlert("Error:Server error occur", "alert-danger",
					"alertContainer", "alertRow");
		}
	});
}
function checkPwd(str) {
	const ucase = new RegExp("[A-Z]+");
	const lcase = new RegExp("[a-z]+");
	const num = new RegExp("[0-9]+");
	const schar = /^[a-zA-Z0-9 ]*$/;

	if (str.length < 8) {
		return false;
	}

	if (ucase.test(str) === false) {
		return false;
	}

	if (lcase.test(str) === false) {
		return false;
	}

	if (num.test(str) === false) {
		return false;
	}

	if (schar.test(str)) {
		return false;
	}

	return true;
}