//--for data table--//
var customerId = null;

$(document).ready(
		function() {
			$('#customerTable').DataTable({
				"scrollX" : true,
				"scrollY" : "400px"
			});
			loadContent();
			setResetAction();

			$('#customerTable tbody').on(
					'click',
					'.updateBtn',
					function updateCustomer() {
						console.log("update clicked");

						let data = table.row($(this).parents('tr')).data();
						console.log(data);
						$("#customerUpdate").modal('show');

						customerId = data.cutomerId;
						$("#customerNameUpdate").val(data.customerName);

						getTokens("tokenNameUpdate");

						$("option[value='" + data.tokenNameId + "']").prop(
								'selected', true);

					});

		});

function loadContent() {
	$.ajax({
		type : 'GET',
		url : 'customer-names',
		beforeSend : function() {
			$('#imgLoaderDiv').show();
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function(data) {

			refreshTable(data);
			// fillCustomerDropDown(customerMap);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			showAlert(textStatus + ': ' + errorThrown, "alert-danger",
					"alertContainer", "alertRow");
		}

	});

	getTokens("tokenName");
}

function getTokens(labelName) {
	$('#'+labelName+'').find('option').remove();

	$.ajax({
		type : 'GET',
		url : 'get-token-names',
		beforeSend : function() {
			$('#imgLoaderDiv').show();
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function(data) {

			var token = "";
			token += "<option value=''>Please Select</option>";

			for (var i = 0; i < data.length; i++) {

				token += "<option value='" + data[i].tokenNameId + "'>"
						+ data[i].tokenName + "</option>";
			}
			// $('#'+labelName+'').val('');

			$('select[name=' + labelName + ']').append(token);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			showAlert(textStatus + ': ' + errorThrown, "alert-danger",
					"alertContainer", "alertRow");
		}

	});
}

var table = null;

function refreshTable(data) {
	// populate data table
	table = $('#customerTable')
			.DataTable(
					{
						destroy : true,
						data : data,
						order : [], // follow API defined ordering
						columns : [

								{
									"data" : "cutomerId"
								},
								{
									"data" : "customerName"
								},
								{
									"data" : "tokenName"
								},
								{
									"data" : "tokenNameId",
									"visible" : false,
									"searchable" : false
								},
								{
									"data" : null,
									"className" : "center",
									"class" : "action",
									"defaultContent" : '<a class="updateBtn" style="cursor: pointer;"><i class="fa fa-edit"></i></a>',
									"orderable" : false, // disable sorting
									// on action
									"searchable" : false
								// disable searching on action
								} ],

					});
}

function enableCustomerSelection() {
	$("#customerName").prop('disabled', false);
}

$("#addNewCustomerForm").submit(function(e) {
	e.preventDefault();
	submitForm();
});

function updateCustomer() {
	$("#updateAlertRow").hide();
	var updateCustomer = {
		"customerName" : $("#customerNameUpdate").val(),
		"customerMasterId" : customerId,
		"tokenNameId" : $("#tokenNameUpdate").val()
	}
	if (updateCustomer.customerName == "" || updateCustomer.tokenNameId == "") {
		$("#updateAlertRow").show();
		showAlert("Error:Customer name can not be empty", "alert-danger",
				"updateAlertRow", "alertRow");
	} else {
		$("#updateAlertRow").hide();
		$.ajax({
			type : 'PUT',
			url : 'update-customer',
			contentType : 'application/json',
			headers : {
				"X-Auth-Token" : $.cookie("JSESSIONID"),
			},
			data : JSON.stringify(updateCustomer),
			success : function(data, statusText, xhr) {
				console.log(xhr);
				if (xhr.status == 200) {

					$("#updateAlertRow").show();
					showAlert("SUCCESS: Updated customer successfully",
							"alert-success", "updateAlertRow", "alertRow");
					setTimeout(function() {
						$("#updateAlertRow").hide();
						$("#customerUpdate").modal('hide');
						loadContent();
						resetForm();
					}, 3000);
					// $("#customerUpdate").modal('hide');
				} else {
					showAlert("Error: Occur during  customer update",
							"alert-danger", "updateAlertRow", "alertRow");
					$("#updateAlertRow").show();
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$("#updateAlertRow").hide();
				if (jqXHR.status == 302) {
					showAlert("Error: Customer already exists", "alert-danger",
							"updateAlertRow", "alertRow");
					$("#updateAlertRow").show();
				} else {
					showAlert("Error: Occur during customer update",
							"alert-danger", "updateAlertRow", "alertRow");
					$("#updateAlertRow").show();
				}
			}
		});
	}

}
function submitForm() {

	var addNewCustomer = {
		"customerName" : $("#customerName").val(),
		"tokenNameId" : $("#tokenName").val(),
	}
	console.log(addNewCustomer);

	if ((addNewCustomer.customerName) == "" || (addNewCustomer.tokenName) == "") {
		showAlert("Error: Customer name can not be empty", "alert-danger",
				"alertContainer", "alertRow");
	} else {
		saveNewCustomer(addNewCustomer);
	}
}

function saveNewCustomer(addNewCustomer) {
	// calling api to save new customer

	console.log(addNewCustomer);
	$.ajax({
		type : 'POST',
		url : 'add-new-customer',
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID"),
		},
		data : JSON.stringify(addNewCustomer),
		success : function(data, statusText, xhr) {
			console.log(data);
			console.log(xhr.status);
			if (xhr.status == 200) {
				showAlert("SUCCESS: Added customer successfully",
						"alert-success", "alertContainer", "alertRow");

				loadContent();
				resetForm();

			} else if (xhr.status == 302) {
				showAlert("WARNING:  Customer already exists", "alert-warning",
						"alertContainer", "alertRow");

				loadContent();
				resetForm();
			}

			else {
				showAlert("Error: Occur during  customer save", "alert-danger",
						"alertContainer", "alertRow");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {

			showAlert(textStatus + ': ' + errorThrown, "alert-danger",
					"alertContainer", "alertRow");
			// showAlert("WARNING: Customer already exists", "alert-warning",
			// "alertContainer", "alertRow");

			loadContent();
			resetForm();
		}
	});
}

// Function to override reset behavior to reset hidden fields too
function setResetAction() {
	$("input[type='reset']").on("click", function(event) {
		// to handle edit -> cancel scenario
		$("#customerName").val('');
		hideAlert();
	});
}

function hideAlert() {
	console.log("hiding alert..");
	if ($('#alertRow').is(':visible'))
		$("#alertRow").hide();
}

function unsetCustomer() {
	// remove currently selected customer option if exists
	$("#customerName").prop('disabled', false);
}

function resetForm() {
	$("#customerName").val('');
}
