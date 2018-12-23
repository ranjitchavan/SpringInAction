$(document).ready(function() {
	initComponents();
	loadContent('GET', 'customer-names');
	// setActionHandlers();
	setCustomerSelectionListener();
	setSelectAllHandler();
});

function setSelectAllHandler(){
	$("#selectAll").change(function(){
	    if($(this).is(':checked')){
	    	selectAllDevices(true);
	    }else{
	    	selectAllDevices(false);
	    }
	});
}
function selectAllDevices(checked){
    $('input:checkbox').prop('checked',checked);
}

function setSelectAll(checked){
    $('#selectAll').prop('checked',checked);
}

function loadContent(methodType, url) {
	$.ajax({
		type : methodType,
		url : url,
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		success : function(data) {
			if (data.length) {
				fillCustomerDropDown(data);
			}
		},
		error : function() {
			showAlert("Unable to load Customer Name.", "alert-danger",
					"alertContainer", "alertRow");
		}

	});
}

function initComponents() {
	$('#deviceTable').DataTable({
		"scrollX" : true,
		"scrollY" : "400px",
		columnDefs : [ {
			orderable : false,
			className : 'select-checkbox',
			targets : 0
		} ],
		select : {
			style : 'os',
			selector : 'td:first-child'
		},
		order : [ [ 1, 'asc' ] ]
	});

}

// fill customer drop down
function fillCustomerDropDown(customerArray) {
	let option = '';
	const arrayLength = customerArray.length;
	for (let i = 0; i < arrayLength; i++) {
		option += '<option value="' + customerArray[i].cutomerId + '" >'
				+ customerArray[i].customerName + '</option>';
	}
	$('#customerId').append(option);
	
	if(arrayLength > 0){
		getDeregisteredDevices(customerArray[0].cutomerId);
	}
}

$("#deregisteredDevicesForm").submit(function(e) {
	e.preventDefault();
	submitForm();
});

function submitForm() {
	hideAlert("alertRow");
	// check if any of the devices is selected or not
	if (!$(".checkbox:checked").length){
		showAlert("At least one Device Id should be selected.",
				"alert-danger", "alertContainer", "alertRow");
	}else{		
		const deviceMasterIds = getSelectedDevices();
		updateDeviceStatus(deviceMasterIds);
		setSelectAll(false);
	}
}

function getSelectedDevices(){
	 const deviceMasterIds = []; 
	 $('#deviceTable').DataTable().$('input[type="checkbox"]').each(function() {
		if (this.checked) {
			deviceMasterIds.push(this.value);
		}
	});
	return deviceMasterIds;
}
function updateDeviceStatus(deviceMasterIds) 
{
		const updateStatus = confirm("Are you sure, you want to update status of selected device(s)?");
		if (updateStatus) {		
			$.ajax({
				type : 'POST',
				data : JSON.stringify(deviceMasterIds), 
				url : 'notregistered-device',
				contentType : 'application/json',
				beforeSend : function() {
					hideAlert("alertRow");
					$('#imgLoaderDiv').show();
				},
				complete : function() {
					$('#imgLoaderDiv').hide();
				},
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},
				success : function(data) {
					
						showAlert("Successfully updated device status.",
								"alert-success", "alertContainer", "alertRow");
						
						let delay = 3000;
						setTimeout(function() {
							getDeregisteredDevices(jQuery("#customerId option:selected").val());
						}, delay);
						
				},
				error : function() {
					showAlert("An error occured while updating device status.",
							"alert-danger", "alertContainer", "alertRow");
				}
			});
		}

	
	

	console.log(deviceMasterIds);
	
}
function setCustomerSelectionListener() {
	$('#customerId').on('change', function() {
		getDeregisteredDevices(this.value);
		setSelectAll(false);
	})
}

function getDeregisteredDevices(customerId) {
	// calling API to get filtered devices
	$.ajax({
		type : 'GET',
		url : 'deregistered-devices',
		data : {
			"customerId" : customerId
		},
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		beforeSend : function() {
			hideAlert("alertRow");
			$('#imgLoaderDiv').show();
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function(data, statusText, xhr) {
			if (xhr.status === 200 || xhr.status === 204) {
				processResponse(data);
			}
		},
		error : function() {
			showAlert("An error occured while obtaining deregistered devices.",
					"alert-danger", "alertContainer", "alertRow");
			clearTable();
		}
	});
}
function processResponse(data) {
	if ((data !== undefined) && data.length) {
		refreshTable(data);
	} else {
		clearTable();
	}

}

function refreshTable(deviceList) {
	console.log("deviceList is -> " + deviceList);
	$('#deviceTable')
			.DataTable(
					{
						data : deviceList,
						columnDefs : [
								{
									orderable : false,
									targets : 0,
									data : "deviceMasterId",
									render : function(data) {

										return '<input class="checkbox" type="checkbox" value="'
												+data
												+ '">';
									}

								}, {
									orderable : true,
									targets : 1,
									data : "deviceId"
								}, {
									orderable : true,
									targets : 2,
									data : "status"
								} ],
						select : {
							style : 'multi',
							selector : 'td:first-child'
						},
						order : [ [ 1, 'asc' ] ],
						"destroy" : true,
						"scrollX" : true,
						"scrollY" : "400px",
						"rowId" : "deviceMasterId"
					});
	$("#submit").attr("disabled", false);

}
function clearTable() {
	$('#deviceTable').DataTable().clear().draw();
	$("#submit").attr("disabled", true);
}


function hideAlert(alertRow) {
	$("#" + alertRow).hide();
}
