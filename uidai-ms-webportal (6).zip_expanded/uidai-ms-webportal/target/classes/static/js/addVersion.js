//--for toggle menu--//
$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
});

function goBack() {
	window.history.back();
}

// --for data table--//
$(document)
		.ready(
				function() {

					if ($.cookie("JSESSIONID") === "") {
						window.location = "/uidai-ms-webportal-ui/";
					}

					$('#example').DataTable();

					$('#farmer_list').DataTable({
						"scrollX" : true,
						"scrollY" : "300px"
					});

					$.ajax({
						type : 'GET',
						url : 'get-os-type',
						headers : {
							"X-Auth-Token" : $.cookie("JSESSIONID")
						},
						success : function(data) {
							OnSuccess(data);

						},
						error : function() {
							showAlert("Error : Getting os", "alert-danger",
									"alertContainer", "alertRow");
						}
					});

					function OnSuccess(r) {
						const ddlCustomers = $("[id=os]");
						ddlCustomers
								.empty()
								.append(
										'<option selected="selected" value="">Please select</option>');
						for (let i = 0; i < r.length; i++) {
							ddlCustomers.append("<option value='" + r[i] + "'>"
									+ r[i] + '</option>');
						}
					}
					getCustomer();
					$("#customerBlock").hide();

				});

function updateFormEnabled() {

	if (verifyDropdowns()) {
		$("#change").prop('disabled', '');
	} else {
		$("#change").prop('disabled', 'disabled');
	}
}

function verifyDropdowns() {

	let notEmpty = (isNotEmpty('applicationType')
			&& isNotEmpty('latestVersion') && isNotEmpty('os')
			&& isNotEmpty('file') && isNotEmpty('osSubType'));

	if (notEmpty) {
		const applicationType = $("#applicationType").val().toUpperCase();
		if (applicationType === "RD service".toUpperCase()
				|| applicationType === "Management Client".toUpperCase()) {
			notEmpty = isNotEmpty('signatureFile');
		}
	}

	if ($("#applicationType").val() === "RD service"
			|| $("#applicationType").val() === "Management Client") {
		$("#customerBlock").show();
	} else {
		$("#customerBlock").hide();
	}

	return notEmpty;
}

function isNotEmpty(elementId) {
	return ($("#" + elementId).val() != null)
			&& ($("#" + elementId).val() !== '');
}

$('#applicationType').change(updateFormEnabled);

$('#latestVersion').keyup(updateFormEnabled);

$('#latestVersion').change(updateFormEnabled);

$('#os').change(updateFormEnabled);

$('#osSubType').change(updateFormEnabled);

$('#os').change(getOsSubType);

$('#file').change(updateFormEnabled);

$('#signatureFile').change(updateFormEnabled);

function uploadVersion() {

	const files = [];
	$.each($("input[type=file]"), function(i, obj) {
		$.each(obj.files, function(i, file) {
			files.push(file);
		});
	});
	const form = new FormData();
	form.append("version", files[0]);
	form.append("applicationType", $("#applicationType").val());
	form.append("os", $("#os").val());
	form.append("osSubType", $("#osSubType").val());
	form.append("latestVersion", $("#latestVersion").val());

	if ($("#applicationType").val() === "RD service"
			|| $("#applicationType").val() === "Management Client") {
		form.append("dpId", $("#SelectCustomer").val());
		// alert("from data")
	} else {
		form.append("dpId", "");
	}
	// alert($("#SelectCustomer").val());

	if ($('#signatureFile').val() !== '') {
		const signatureFile = $('#signatureFile').prop('files')[0];
		form.append("signatureFile", signatureFile);
	}

	$.ajax({
		url : 'add-latest-version',
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
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function() {
			showAlert("Success: Version added successfully", "alert-success",
					"alertContainer", "alertRow");
			$("#applicationType").val('');
			$("#os").val('');
			$("#latestVersion").val('');
			$("input[name=file]").val('');
			$("#signatureFile").val('');
			$("#SelectCustomer").val('');

			setTimeout(function() {
				window.location.href = "currentVersionList.html";
			}, 3000);
		},
		error : function(data) {
			if (data.status === 409) {
				showAlert("Version is already available.", "alert-danger",
						"alertContainer", "alertRow");
			} else {
				showAlert("Error occured during version update.",
						"alert-danger", "alertContainer", "alertRow");
			}
		}
	});

}

// To enable Driver option only for Windows.
$('#applicationType').on('change', function() {
	const driverType = "Driver";
	const osType = "Windows";
	const applicationType = this.value;
	if (applicationType === driverType) {
		$("#os option:not(:contains(" + osType + "))").filter(function() {
			return !($.trim(this.value).length === 0);
		}).attr("disabled", true).addClass('disabledOption');

		if ($("#os").val() == null) {
			$('#os').val('');
		}
	} else {
		$("#os option").attr("disabled", false).removeClass('disabledOption');
	}
});

function getOsSubType() {
	varsion = {
		"os" : $("#os").val()
	}
	$.ajax({
		type : 'POST',
		url : 'get-os-subtype',
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		data : JSON.stringify(varsion),
		beforeSend : function() {
			$('#imgLoaderDiv').show();
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function(data, statusText, xhr) {
			if ((xhr.status === 200)) {
				osSubTypeArray = data;
				fillOsSubtype();
			} else {
				showAlert("Error : In fatching os data.", "alert-error",
						"alertContainer", "alertRow");
			}
		},
		error : function() {

			showAlert("Error:Server error occur", "alert-danger",
					"alertContainer", "alertRow");
		}
	});

}

function getCustomer() {
	$.ajax({
		url : 'get-dp-id',
		type : 'GET',
		cache : false,
		contentType : false,
		processData : false,
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		beforeSend : function() {
			$('#imgLoaderDiv').show();
		},
		success : function(data) {
			customerArray = data;
			$('#imgLoaderDiv').hide();
			fillCustomerCombo();
		},
		error : function() {
			$('#imgLoaderDiv').hide();
		}
	});
}

function fillCustomerCombo() {
	var customer = "";
	for (var i = 0; i < customerArray.length; i++) {
		customer += "<option value='" + customerArray[i] + "'>"
				+ customerArray[i] + "</option>";
	}

	$('select[name="SelectCustomer"]').append(customer);
}

function fillOsSubtype() {
	const ddlCustomers = $("[id=osSubType]");
	ddlCustomers.empty().append();
	for (let i = 0; i < osSubTypeArray.length; i++) {
		ddlCustomers.append("<option value='" + osSubTypeArray[i] + "'>"
				+ osSubTypeArray[i] + '</option>');
	}
}

$("#selectAllCustomers").click(function() {
	$('#osSubType option').prop('selected', true);
});

$("#unselectAllCustomers").click(function() {
	$('#osSubType option').prop('selected', false);
});
