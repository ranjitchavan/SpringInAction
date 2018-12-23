//variable declaration 
var version_management_id;
var customerId =  null;
$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
});
// --for data table--//
$(document).ready(function() {

	if ($.cookie("JSESSIONID") === "") {
		window.location = "/uidai-ms-webportal-ui/";
	}

	$("#LoadingDiv").hide();
	$.fn.dataTable.moment('MMMM Do YYYY');
});
$("#LoadingDiv").show();
$
		.ajax({
			type : 'GET',
			url : 'get-current-versions',
			headers : {
				"X-Auth-Token" : $.cookie("JSESSIONID")
			},
			beforeSend : function() {
				$('#imgLoaderDiv').show();
			},
			complete : function() {
				$('#imgLoaderDiv').hide();
			},
			success : function(data) {
				$("#LoadingDiv").hide();
				$('#currentVersions')
						.DataTable(
								{
									"scrollX" : true,
									"scrollY" : "300px",
									data : data,
									order : [ [ 3, "desc" ] ],
									columns : [
											{
												data : "dpId"
											},
											{
												data : "application_type"
											},
											{
												data : "os",
												 "visible": false
											},
											{
												data : "osType"
											},
											{
												data : "current_version"
											},
											{
												data : "creation_time",

												"render" : function(data) {
													const
													dtStart = new Date(data);
													const
													dtStartWrapper = moment(dtStart);
													return dtStartWrapper
															.format('MMMM Do YYYY');
												}
											},
											{
												data : "userName"
											},
											{
												"targets" : -1,
												"data" : null,
												"orderable" : false,
												"searchable" : false,
												"render" : function(data) {
													let
													action = "<a title='Edit' class='updateBtn' style='cursor: pointer;'><i class='fa fa-edit'></i></a> &nbsp;&nbsp;&nbsp;<a title='Download file' class='downloadVersion' href='download-version/"
															+ data.version_management_id
															+ "'><i class='fa fa-download'></i></a>";
													if (data.signatureExist) {
														action += "<a title='Download signature' class='downloadSignature' href='download-signature/"
																+ data.version_management_id
																+ "'><i class='fa fa-download'></i></a>";
													} else {
														action += "<a title='Download signature' class='downloadSignature' style='color: grey;'><i class='fa fa-download'></i></a>";
													}
													return action;
												}
											}

									]
								});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('error: ' + textStatus + ': ' + errorThrown);
			}

		// Edit record

		});

$('#currentVersions').find('tbody').on('click', '.updateBtn', function() {
	
	customerId = null;
	
	const
	data = $('#currentVersions').DataTable().row($(this).parents('tr')).data();
	$("#versionUpdate").modal('show');
	$("#applicationType").val(data.application_type);
	$("#osType").val(data.osType);
	$("#latestVersion").val(data.current_version);
	$("#os").val(data.os);

	version_management_id = data.version_management_id;
	customerId = data.customerId;
	
	
	if($("#applicationType").val() == "RD Service" || $("#applicationType").val() == "Management Client"){
		$("#popUpCustomerBlock").show();
		$("#dpId").val(data.dpId);
	}else{
		$("#popUpCustomerBlock").hide();
	}

});

function uploadVersion() {
	const
	applicationType = $("#applicationType").val();
	const
	os = $("#os").val();
	const
	osType = $("#osType").val();
	const
	latestVersion = $("#latestVersion").val();

	const
	
	version = {
		"application_type" : applicationType,
		"os" : os,
		"osType" : osType,
		"current_version" : latestVersion,
		"version_management_id" : version_management_id,
		"customerId" : customerId
	};

	$.ajax({
		type : 'POST',
		url : 'edit-version',
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		data : JSON.stringify(version),
		success : function() {
			
			showAlert("Version updated  successfully", "alert-success",
					"alertContainer", "alertRow");
			
			const
			delay = 3000;
			setTimeout(function() {
				$("#versionUpdate").hide();
				location.reload();
			}, delay);
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

function updateFormEnabled() {
	if (verifyDropdowns()) {
		$("#change").prop('disabled', '');
	} else {
		$("#change").prop('disabled', 'disabled');
	}
}

function verifyDropdowns() {
	return (isNotEmpty('applicationType') && isNotEmpty('latestVersion') && isNotEmpty('osType'));
}

function isNotEmpty(elementId) {
	return ($("#" + elementId).val() != null)
			&& ($("#" + elementId).val() !== '');
}

$('#applicationType').change(updateFormEnabled);

$('#latestVersion').keyup(updateFormEnabled);

$('#latestVersion').change(updateFormEnabled);

$('#os').change(updateFormEnabled);
