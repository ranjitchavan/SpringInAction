$(document).ready(function() {
	loadContent();
});

function loadContent() {
	
	hideAlert();
	//call api to get content
	$.ajax({
				type : 'GET',
				url : 'key-rotation-policy',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},

				success : function(data, statusText, xhr) {
					if (xhr.status === 200) {
						setFields(data);
					} else {
						showAlert("Error: Missing key rotation policy configuration information.",  "alert-danger" ,"alertContainer","alertRow");
					}
				},
				error : function() {
					showAlert("An error occured while obtaining key rotation policy configuration information.", "alert-danger" ,"alertContainer","alertRow");
				}

			});
}

function hideAlert(){
	if($('#alertRow').is(':visible'))
		$("#alertRow").hide();
}

$("#keyRotationForm").submit(function(e) {
	e.preventDefault();
	submitForm();
});

function submitForm() {
	const keyRotationId = $('#keyRotationId').val();

	if ((keyRotationId !== '') && (keyRotationId.length !== 0)) {
		var daysl0 = $("#level0Days").val();
		var hoursl0 = $("#level0Hours").val();
		var minl0 = $("#level0Mins").val();
		var totalMinsLevel0 =  (daysl0 * 24 * 60) + (hoursl0 * 60) + (minl0 *1); 
		
		var daysl1 = $("#level1Days").val();
		var hoursl1 = $("#level1Hours").val();
		var minl1 = $("#level1Mins").val();
		var totalMinsLevel1 =  (daysl1 * 24 * 60) + (hoursl1 * 60) + (minl1 *1); 
		
		const keyRotationPolicy = {
			"keyRotationId" : keyRotationId,
			"keyRotationPolicyLevel0" : totalMinsLevel0,
			"keyRotationReminderLevel0" : $("#level0Reminder").val(),
			"keyRotationPolicyLevel1" : totalMinsLevel1,
			"keyRotationReminderLevel1" : $("#level1Reminder").val()
		};

		if (Number(keyRotationPolicy.keyRotationReminderLevel0) > Number(daysl0)) {
			showAlert("Error: Key Rotation Reminder(before days) cannot be greater than Level0 Device(days).",  "alert-danger" ,"alertContainer","alertRow");
		} else if (Number(keyRotationPolicy.keyRotationReminderLevel1) > Number(daysl1)) {
			showAlert("Error: Key Rotation Reminder(before days) cannot be greater than Level1 Device(days).",  "alert-danger" ,"alertContainer","alertRow");
		} else {
			updatePolicy(keyRotationPolicy);
		}
	} else {
		showAlert("Error: Cannot update non-existing key rotation policy configuration information.",  "alert-danger" ,"alertContainer","alertRow");
	}
}

function updatePolicy(keyRotationPolicy) {
	//calling api to update key rotation configurations
	$.ajax({
		type : 'PUT',
		url : 'key-rotation-policy/' + keyRotationPolicy.keyRotationId,
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		data : JSON.stringify(keyRotationPolicy),
		success : function(data, statusText, xhr) {
			if (xhr.status === 200) {
				showAlert("Successfully saved device key rotation configuration information.", "alert-success","alertContainer","alertRow");
				setFields(data);
			} else {
				showAlert("Device key rotation configuration information could not be saved.",  "alert-danger" ,"alertContainer","alertRow");
			}
		},
		error : function() {
			showAlert("An error occured while updating key rotation configuration information.",  "alert-danger" ,"alertContainer","alertRow");
		}
	});
}

function setFields(data) {
	var totalMinsLevel0 = data.keyRotationPolicyLevel0;

	var totalMinsLevel1 = data.keyRotationPolicyLevel1;
	
	$('#keyRotationId').val(data.keyRotationId);
	$('#level0Days').val(Math.floor(totalMinsLevel0/24/60));
	$('#level0Hours').val(Math.floor(totalMinsLevel0/60%24));
	$('#level0Mins').val(Math.floor(totalMinsLevel0%60));
	$('#level0Reminder').val(data.keyRotationReminderLevel0);
	
	$('#level1Days').val(Math.floor(totalMinsLevel1/24/60));
	$('#level1Hours').val(Math.floor(totalMinsLevel1/60%24));
	$('#level1Mins').val(Math.floor(totalMinsLevel1%60));
	$('#level1Reminder').val(data.keyRotationReminderLevel1);
}