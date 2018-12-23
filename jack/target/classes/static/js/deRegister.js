//--for toggle menu--//
$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
});
// --for data table--//
$(document).ready(function() {

	if ($.cookie("JSESSIONID") == "") {
		window.location = "/uidai-ms-webportal-ui/";
	}
});
function goBack() {
	window.history.back();
}

function deRegister() {

	const
	files = [];
	$.each($("input[type=file]"), function(i, obj) {
		$.each(obj.files, function(i, file) {
			files.push(file);
		});
	});
	const
	signatureFile = $('#file').prop('files')[0];
	const
	form = new FormData();
	form.append("file", files[0]);

	$
			.ajax({
				url : 'de-register-device',
				type : 'POST',
				data : form,
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},
				cache : false,
				contentType : false,
				processData : false,
				beforeSend : function() {
					$('#imgLoaderDiv').show();
					$("#import").prop('disabled', 'disabled');
					$("#cancel").prop('disabled', 'disabled');
					$("#file").prop('disabled', 'disabled');
				},
				complete : function() {
					$('#imgLoaderDiv').hide();
				},
				success : function(data) {
					showAlert(
							"De-registration process completed successfully.",
							"alert-success", "alertContainer", "alertRow");
					$('#fileUploadStatus').show();
					$('#success').html(data[0]);
					$('#fail').html(data[1]);
					$("#file").val('');
					$("#import").prop('disabled', '');
					$("#cancel").prop('disabled', '');
					$("#file").prop('disabled', '');
				},
				error : function(data) {
					if (data.status === 403) {
						showAlert(
								"Error : De-registration api is not working please try after sometime.",
								"alert-danger", "alertContainer", "alertRow");
					}
					$("#file").val('');
					$("#import").prop('disabled', '');
					$("#cancel").prop('disabled', '');
					$("#file").prop('disabled', '');
				}
			});
	return false;
};

function updateFormEnabled() {

	if (verifyDropdowns()) {
		$("#import").prop('disabled', '');
	} else {
		$("#import").prop('disabled', 'disabled');
	}
}

function verifyDropdowns() {
	const files = $("#file")[0].files[0];
	if (files.type !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
		showAlert("Error: File with xlsx format can only be uploaded.", "alert-danger", "alertContainer",
				"alertRow");
		$("input[name=file]").val('');
		return false;
	} else {
		$("#alertRow").hide();
	}
	return ($('#file').val() != null && $('#file').val() !== '');
}

$('#file').change(updateFormEnabled);