$(document).ready(function() {
	getApp();
	checkFeilds();
});
let
id = null;
let
table = null;
function checkFeilds() {

	$("input:radio").change(function() {
		$("#btnApply").attr("disabled", false);
	});

	var toValidate = $('#appName, #ipAddress'), valid = false;
	toValidate
			.keyup(function() {
				if ($(this).val().length > 0) {
					$(this).data('valid', true);
				} else {
					$(this).data('valid', false);
				}
				if ($('#appName').val() !== null && $('#appName').val() !== '') {
					if ($('#ipAddress').val() !== null
							&& $('#ipAddress').val() !== '') {

						valid = true;

					} else {
						valid = false;

					}

				} else {
					valid = false;
				}

				if (valid === true) {
					$('input[type=submit]').prop('disabled', false);
				} else {
					$('input[type=submit]').prop('disabled', true);
				}
			});
}

$('#users tbody').on(
		'click',
		'.deleteBtn',
		function deleteApp() {
			let
			data = table.row($(this).parents('tr')).data();

			let
			answer = confirm("Are you sure to delete this App server ? ")

			if (answer) {
				$.ajax({
					type : 'POST',
					url : 'delete-app',
					beforeSend : function() {
						$('#imgLoaderDiv').show();
					},
					complete : function() {
						$('#imgLoaderDiv').hide();
					},
					contentType : 'application/json',
					headers : {
						"X-Auth-Token" : $.cookie("JSESSIONID"),
					},
					data : JSON.stringify(data),
					success : function(data, statusText, xhr) {
						console.log(xhr);
						if (xhr.status == 200) {
							showAlert(
									"SUCCESS: App Server deleted successfully",
									"alert-success", "errorAlertContainer",
									"errorAlertRow");

							delay = 3000;
							setTimeout(function() {
								location.reload();
							}, delay);

						} else {

						}
					},
					error : function(data) {
						{
							showAlert("ERROR: Internal server error occur ",
									"alert-danger", "errorAlertContainer",
									"errorAlertRow");
						}
					}
				});
			} else {
				// some code
			}

		});

$('#users tbody').on('click', '.updateBtn', function updateApp() {
	let
	data = table.row($(this).parents('tr')).data();
	console.log(data);

	$("#userUpdate").modal('show');
	let
	appName = $("#appName").val(data.appName);
	let
	ipAddress = $("#ipAddress").val(data.ipAddress);
	id = data.id;
	$('#btnSave').hide();
	$('#btnUpdate').show();
});

function saveUpdatedAppUser() {

	let appObj = {
		"ipAddress" : $("#ipAddress").val(),
		"appName" : $("#appName").val(),
		"id" : id
	}

	console.log(appObj);
	
	if ($("#ipAddress").val() != ""  && $("#appName").val() != "") {
		if($("#ipAddress").val().indexOf(":") !== -1){
			var ipAddress = $("#ipAddress").val().split(":");
			if(ValidateIPaddress(ipAddress[0])){
				if(ipAddress.length >1){
					$.ajax({
						type : 'POST',
						url : 'add-app',
						contentType : 'application/json',
						headers : {
							"X-Auth-Token" : $.cookie("JSESSIONID")
						},
						data : JSON.stringify(appObj),

						beforeSend : function() {
							$('#imgLoaderDiv').show();
						},
						complete : function() {
							$('#imgLoaderDiv').hide();
						},
						success : function(data, statusText, xhr) {
							console.log(xhr);
							if (xhr.status == 200) {
								showAlert("Updated App successfully", "alert-success",
										"alertContainer", "alertRow");

								setTimeout(function() {
									location.reload();
								}, 3000);

							}
							
							if (xhr.status == 208) {
								showAlert(" App Already Present ", "alert-danger",
										"alertContainer", "alertRow");
							}
												},
						error : function(data, statusText, xhr) {
							showAlert("Error:Server error occur", "alert-danger",
									"alertContainer", "alertRow");
						}
					});

				}else{
					showAlert("Please enter valid port number along with Ip address ", "alert-danger", "alertContainer",
					"alertRow");
				}
			}else{
				showAlert("Please enter valid port number along with Ip address ", "alert-danger", "alertContainer",
				"alertRow");
			}
			
		}else{
			showAlert("Please enter port number along with Ip address ", "alert-danger", "alertContainer",
			"alertRow");
		}
		
     }else{
    	 showAlert("Please fill all details ", "alert-danger", "alertContainer",
			"alertRow");
   }

}

function getApp() {
	$
			.ajax({
				type : 'GET',
				url : 'get-app',
				beforeSend : function() {
					$('#imgLoaderDiv').show();
				},
				complete : function() {
					$('#imgLoaderDiv').hide();
				},
				contentType : 'application/json',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID"),
				},
				success : function(data, statusText, xhr) {
					console.log(data);
					table = $('#users')
							.DataTable(
									{
										"scrollX" : true,
										"scrollY" : "400px",
										data : data,
										columns : [
												{
													"data" : "appName"
												},
												{
													"data" : "ipAddress"
												},
												{
													"data" : "status"
												},
												{
													"targets" : -1,
													"data" : null,
													"defaultContent" : "<a class='updateBtn btn btn-success btn-sm' data-toggle='tooltip' title='Update!''><i class='fa fa-edit'></i></a> <a class='deleteBtn btn btn-danger btn-sm' data-toggle='tooltip' title='Delete!'' style='margin-left:2%;''><i class='fa fa-trash-o'></i></a> ",
													"orderable" : false,
													"searchable" : false,
												}

										],

										"dom" : '<"top"i>rt<"bottom"flp><"clear">',
										"bFilter" : false,
									})
				},
				error : function(data) {
					showAlert("Error : Error in getting App", "alert-danger",
							"alertContainer", "alertRow");
				}
			});
}

function updateFormEnabled() {
	if (verifyDropdowns()) {
		//alert(verifyDropdowns());
		$("btnSave").prop("disabled", "");
	} else {
		$("btnSave").prop("disabled", "disabled");
	}
}

function verifyDropdowns() {
	if ($('#appName').val() !== null && $('#appName').val() !== '') {
		if ($('#ipAddress').val() !== null && $('#ipAddress').val() !== '') {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

function onApply() {
	console.log($('input[name=optradio]:checked').val());
	$.ajax({
		type : 'POST',
		url : 'enable-disable-logs',
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		data : JSON.stringify($('input[name=optradio]:checked').val()),
		beforeSend : function() {
			$('#imgLoaderDiv').show();
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function(data, statusText, xhr) {
			console.log(xhr);
			if (xhr.status == 200) {
				showAlert("Successfully "
						+ $('input[name=optradio]:checked').val() + " logs ",
						"alert-success", "alertContainer", "alertRow");
			}

			if (xhr.status = 500) {
				showAlert("Internal Server error occur ", "alert-success",
						"alertContainer", "alertRow");

			}

			setTimeout(function() {
				location.reload();
			}, 2000);

		},
		error : function(data, statusText, xhr) {

			if (xhr.status = 500) {
				showAlert("Internal Server error occur ", "alert-success",
						"alertContainer", "alertRow");

			}

			setTimeout(function() {
				location.reload();
			}, 2000);

		}
	});

}

function ValidateIPaddress() {
	var inputText = $("#ipAddress").val();
	var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	if (inputText.match(ipformat)) {
		isIpAddress = true;
		$("#alertRow").hide();
		return true;
	} else {
		showAlert(
				"Error : Incorrect IP Address ! Please Enter Correct IP Address",
				"alert-danger", "alertContainer", "alertRow");
		isIpAddress = false;
		return false;
	}

}

function openAddNewAppPopUp() {

	$("#userUpdate").modal('show');
	$('#btnSave').show();
	$('#btnUpdate').hide();

	$("#ipAddress").val('');
	$("#appName").val('');

}

function isNumber(evt) {
    
	evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    
    if( charCode === 58 || charCode === 46 ){
    	 return true;
    }
    
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    
    return true;
}

// Used to save app
function saveApp() {
	//alert("saveApp");
	id = null;
	let
	appObj = {
		"ipAddress" : $("#ipAddress").val(),
		"appName" : $("#appName").val()
	}

	if ($("#ipAddress").val() != ""  && $("#appName").val() != "") {
		if($("#ipAddress").val().indexOf(":") !== -1){
			var ipAddress = $("#ipAddress").val().split(":");
			if(ValidateIPaddress(ipAddress[0])){
				$.ajax({
					type : 'POST',
					url : 'add-app',
					contentType : 'application/json',
					headers : {
						"X-Auth-Token" : $.cookie("JSESSIONID")
					},
					data : JSON.stringify(appObj),

					beforeSend : function() {
						$('#imgLoaderDiv').show();
					},
					complete : function() {
						$('#imgLoaderDiv').hide();
					},
					success : function(data, statusText, xhr) {
						
						console.log(xhr);

						if (xhr.status == 200) {
							showAlert("Added App successfully", "alert-success",
									"alertContainer", "alertRow");

							setTimeout(function() {
								location.reload();
							}, 3000);
						}
						
						if (xhr.status == 208) {
							showAlert("App  Already present", "alert-danger",
									"alertContainer", "alertRow");
						}
						

					},
					error : function(data, statusText, xhr) {
						showAlert("Error:Server error occur", "alert-danger",
								"alertContainer", "alertRow");
					}
				});
			}else{
				showAlert("Please enter valid port number along with Ip address ", "alert-danger", "alertContainer",
				"alertRow");
			}
		}else{
			showAlert("Please enter port number along with Ip address ", "alert-danger", "alertContainer",
			"alertRow");
		}
		
     }else{
    	 showAlert("Please fill all details ", "alert-danger", "alertContainer",
			"alertRow");
   }
}

function ValidateIPaddress(ipaddress) {  
	  if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ipaddress)) {  
	    return (true)  
	  }  
	//  alert("You have entered an invalid IP address!")  
	  return (false)  
	}  
