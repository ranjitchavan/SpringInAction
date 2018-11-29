//--for data table--//
$(document).ready(function() {
	$('#customerKeyRotationTable').DataTable({
		"scrollX" : true,
		"scrollY" : "400px"
	});	
	$.fn.dataTable.moment( 'MMMM Do YYYY' );
	loadContent();
	setEditPolicyAction();
	setDeletePolicyAction();
	setResetAction();
});

function loadContent() {
	$
			.ajax({
				type : 'GET',
				url : 'customer-keyrotation-policies',
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
					const policyList = data.customerKeyRotationPolicyDtoList;
					const customerMap = data.customerMasterMap;
					refreshTable(policyList);

					fillCustomerDropDown(customerMap);
				},
				error : function() {
					showAlert(
							"An error occured while obtaining key rotation configuration(s).",
							"alert-danger", "alertContainer", "alertRow");
				}

			});
}

// fill customer drop down
function fillCustomerDropDown(customerMap) {
	let option = '';
	for ( const key in customerMap) {
		if (customerMap.hasOwnProperty(key)) {
			option += '<option value="' + key + '" >' + customerMap[key]
					+ '</option>';
		}
	}
	$('#customerName').empty();
	$('#customerName').append(option);
}

function refreshTable(policyList) {
	// populate data table
	$('#customerKeyRotationTable')
			.DataTable(
					{
						destroy : true,
						data : policyList,
						order : [], // follow API defined ordering
						columns : [
								{
									"data" : "customerName"
								},
								{
									"data" : "customerKeyRotationPolicy.keyRotationPolicyLevel0",
									"render" : function(data) {
											if (data != null) {
												return 	Math.floor(data/24/60)+"d "+Math.floor(data/60%24)+"h "+Math.floor(data%60)+"m";
											} else {
												return null;
											}

										}
								},
								{
									"data" : "customerKeyRotationPolicy.keyRotationPolicyLevel1",
										"render" : function(data) {
											if (data != null) {
												return 	Math.floor(data/24/60)+"d "+Math.floor(data/60%24)+"h "+Math.floor(data%60)+"m";
											} else {
												return null;
											}

										}
								},
								{
									"data" : "customerKeyRotationPolicy.keyRotationReminderLevel0"
								},
								{
									"data" : "customerKeyRotationPolicy.keyRotationReminderLevel1"
								},
								{
									"data" : "customerKeyRotationPolicy.updationTime",
									"render" : function(data) {
										if (data != null) {
											const dtStart = new Date(data);
											const dtStartWrapper = moment(dtStart);
											return dtStartWrapper
													.format('MMMM Do YYYY');
										} else {
											return null;
										}

									}
								},
								{
									"data" : "userName"
								},
								{
									"data" : null,
									"className" : "center",
									"class" : "action",
									"defaultContent" : '<a class="action-edit" style="cursor: pointer;"><i class="fa fa-edit"></i></a> | '
											+ '<a class="action-delete" style="cursor: pointer;"><i class="fa fa-trash-o"></i></a>',
									"orderable" : false, // disable sorting
									// on action
									"searchable" : false
								// disable searching on action
								} ],
						rowId : "customerKeyRotationPolicy.customerKeyRotationPolicyId"
					});
}

function setEditPolicyAction() {
	$('#customerKeyRotationTable').find('tbody').on(
			'click',
			'tr td.action a.action-edit',
			function() {
				hideAlert();
				showOptions();
				const tr = $(this).closest('tr');
				const customerKeyRotationPolicyId = tr.attr('id');

				$('#customerKeyRotationPolicyId').val(
						customerKeyRotationPolicyId);

				getCustomerKeyRotationPolicy(customerKeyRotationPolicyId);

			});
}
function setDeletePolicyAction() {
	$('#customerKeyRotationTable')
			.find('tbody')
			.on(
					'click',
					'tr td.action a.action-delete',
					function() {
						hideAlert();
						const result = confirm("Are you sure, you want to delete this record?");
						if (result) {
							const tr = $(this).closest('tr');
							const customerKeyRotationPolicyId = tr.attr('id');
							deletePolicy(customerKeyRotationPolicyId);
						}

					});
}
function getCustomerKeyRotationPolicy(customerKeyRotationPolicyId) {
	// calling api to get key rotation configurations
	$
			.ajax({
				type : 'GET',
				url : 'customer-keyrotation-policy/'
						+ customerKeyRotationPolicyId,
				contentType : 'application/json',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},
				success : function(data, statusText, xhr) {
					if ((xhr.status === 200) && (data.length !== 0)) {
						const customerKeyRotationPolicy = data.customerKeyRotationPolicy;
						setFields(customerKeyRotationPolicy);
						unsetCustomer(); // to handle edit -> edit scenario
						setCustomer(customerKeyRotationPolicy.customerMasterId,
								data.customerName);
					} else {
						showAlert(
								"Error: Key rotation configuration information could not be found.",
								"alert-danger", "alertContainer", "alertRow");
					}
				},
				error : function() {
					showAlert(
							"An error occured while obtaining key rotation configuration information.",
							"alert-danger", "alertContainer", "alertRow");
				}
			});
}
function setCustomer(customerId, customerName) {
	if ((customerId.length !== 0) && (customerName.length !== 0)) {
		$("#customerName")
				.prepend(
						"<option id='currentCustomer' value='" + customerId
								+ "' selected='selected'>" + customerName
								+ "</option>");
		$("#customerName").prop('disabled', true);
	}
}
function setFields(data) {
	var totalMinsLevel0 = data.keyRotationPolicyLevel0;

	var totalMinsLevel1 = data.keyRotationPolicyLevel1;

	$('#level0Days').val(Math.floor(totalMinsLevel0/24/60));
	$('#level0Hours').val(Math.floor(totalMinsLevel0/60%24));
	$('#level0Mins').val(Math.floor(totalMinsLevel0%60));
	$('#level0Reminder').val(data.keyRotationReminderLevel0);
	
	$('#level1Days').val(Math.floor(totalMinsLevel1/24/60));
	$('#level1Hours').val(Math.floor(totalMinsLevel1/60%24));
	$('#level1Mins').val(Math.floor(totalMinsLevel1%60));
	$('#level1Reminder').val(data.keyRotationReminderLevel1);
	

	$("#customerName").val(data.customerMasterId);
}

function deletePolicy(customerKeyRotationPolicyId) {
	// calling api to delete key rotation configurations
	$
			.ajax({
				type : 'DELETE',
				url : 'customer-keyrotation-policy/'
						+ customerKeyRotationPolicyId,
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},
				contentType : 'application/json',
				success : function(data, statusText, xhr) {
					if (xhr.status === 200) {
						showAlert(
								"Successfully deleted key rotation configuration information.",
								"alert-success", "alertContainer", "alertRow");
						loadContent();
						resetForm();
						enableCustomerSelection();
					} else {
						showAlert(
								"Error: Key rotation configuration information could not be deleted.",
								"alert-danger", "alertContainer", "alertRow");
					}
				},
				error : function() {
					showAlert(
							"An error occured while deleting key rotation configuration information.",
							"alert-danger", "alertContainer", "alertRow");
				}
			});
}
function enableCustomerSelection() {
	$("#customerName").prop('disabled', false);
}
$("#customerKeyRotationForm").submit(function(e) {
	e.preventDefault();
	submitForm();
});

function submitForm() {
	const customerKeyRotationPolicyId = $('#customerKeyRotationPolicyId').val();

	var daysl0 = $("#level0Days").val();
	var hoursl0 = $("#level0Hours").val();
	var minl0 = $("#level0Mins").val();
	var totalMinsLevel0 =  (daysl0 * 24 * 60) + (hoursl0 * 60) + (minl0 *1); 
	
	var daysl1 = $("#level1Days").val();
	var hoursl1 = $("#level1Hours").val();
	var minl1 = $("#level1Mins").val();
	var totalMinsLevel1 =  (daysl1 * 24 * 60) + (hoursl1 * 60) + (minl1 *1); 
	
	const customerKeyRotationPolicy = {
		"customerMasterId" : $("#customerName").val(),
		"keyRotationPolicyLevel0" : totalMinsLevel0,
		"keyRotationReminderLevel0" : $("#level0Reminder").val(),
		"keyRotationPolicyLevel1" :totalMinsLevel1,
		"keyRotationReminderLevel1" : $("#level1Reminder").val()
	};

	if (Number(customerKeyRotationPolicy.keyRotationReminderLevel0) > Number(daysl0)) {
		showAlert(
				"Error: Key Rotation Reminder Level0 (Days) cannot be greater than Rotation Policy Level0 (Days).",
				"alert-danger", "alertContainer", "alertRow");
	} else if (Number(customerKeyRotationPolicy.keyRotationReminderLevel1) > Number(daysl1)) {
		showAlert(
				"Error: Key Rotation Reminder Level1 (Days) cannot be greater than Rotation Policy Level1 (Days).",
				"alert-danger", "alertContainer", "alertRow");
	} else {
		if ((customerKeyRotationPolicyId !== '')
				&& (customerKeyRotationPolicyId.length !== 0)) {
			customerKeyRotationPolicy.customerKeyRotationPolicyId = customerKeyRotationPolicyId;
			updatePolicy(customerKeyRotationPolicy);
		} else {
			 savePolicy(customerKeyRotationPolicy);
		}

	}

}

function savePolicy(customerKeyRotationPolicy) {

	// calling api to save key rotation configurations
	$
			.ajax({
				type : 'POST',
				url : 'customer-keyrotation-policy',
				contentType : 'application/json',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},
				data : JSON.stringify(customerKeyRotationPolicy),
				success : function(data, statusText, xhr) {
					if (xhr.status === 200 && (data.length !== 0)) {
						showAlert(
								"Successfully saved key rotation configuration information.",
								"alert-success", "alertContainer", "alertRow");
						$('#customerKeyRotationPolicyId').val(
								data.customerKeyRotationPolicyId);
						loadContent();
						resetForm();
					} else {
						showAlert(
								"Error: Key rotation configuration information could not be saved.",
								"alert-danger", "alertContainer", "alertRow");
					}
				},
				error : function() {
					showAlert(
							"An error occured while saving key rotation configuration information.",
							"alert-danger", "alertContainer", "alertRow");
				}
			});
}

function updatePolicy(customerKeyRotationPolicy) {
	// calling api to update key rotation configurations
	$
			.ajax({
				type : 'PUT',
				url : 'customer-keyrotation-policy/'
						+ customerKeyRotationPolicy.customerKeyRotationPolicyId,
				contentType : 'application/json',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},
				data : JSON.stringify(customerKeyRotationPolicy),
				success : function(data, statusText, xhr) {
					if (xhr.status === 200) {
						showAlert(
								"Successfully updated key rotation configuration information.",
								"alert-success", "alertContainer", "alertRow");
						loadContent();
						resetForm();
						enableCustomerSelection();
					} else {
						showAlert(
								"Error: Key rotation configuration information could not be updated.",
								"alert-danger", "alertContainer", "alertRow");
					}
				},
				error : function() {
					showAlert(
							"An error occured while updating key rotation configuration information.",
							"alert-danger", "alertContainer", "alertRow");
				}
			});
}

// Function to override reset behavior to reset hidden fields too
function setResetAction() {
	$("input[type='reset']").on("click", function() {
		hideAlert();
		unsetCustomer(); // to handle edit -> cancel scenario
		$("#customerKeyRotationPolicyId").val('');
		hideAlert();
	});
}

function hideAlert() {
	if ($('#alertRow').is(':visible')) {
		$("#alertRow").hide();
	}
}

function unsetCustomer() {
	// remove currently selected customer option if exists
	if ($('#currentCustomer').length) {
		$('#currentCustomer').remove();
	}
	$("#customerName").prop('disabled', false);
}

function resetForm() {
	$('#customerKeyRotationForm')[0].reset();
	$("#customerKeyRotationPolicyId").val('');
}

$('#showAddDiv').click(function() {
	showOptions();
});

$('#hideAddDiv').click(function() {
	hideOptions();
});
function showOptions(){
	hideAlert();
	$('#addKeyDiv').show();
	$('#hideAddDiv').show();
	$('#showAddDiv').hide();	
}

function hideOptions(){
	hideAlert();
	$('#addKeyDiv').hide();
	$('#hideAddDiv').hide();
	$('#showAddDiv').show();
}