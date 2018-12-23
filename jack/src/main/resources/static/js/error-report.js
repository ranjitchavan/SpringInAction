$(document).ready(function() {	
	$.fn.dataTable.moment( 'MMMM Do YYYY' );
	initComponents();	
	loadContent('GET','customer-names');	
	setActionHandlers();	
	
});


function loadContent(methodType,url){
	$.ajax({
		type : methodType,
		url : url,
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		success : function(data) {
			if(data.length){
				fillCustomerDropDown(data);
			}
		},
		error : function() {	
			showAlert ("Unable to load Customer Name." ,  "alert-danger" ,"errorAlertContainer","errorAlertRow");
		}

	});
}

function initComponents(){	 
	const start = moment().subtract(29, 'days');
	const end = moment();
	$('input[id="dateRange"]').daterangepicker({
		 startDate: start,
		 endDate:end,
		 locale: {
	            format: 'DD/MM/YYYY'
	        }
		 
	});
	
	$('#errorLogTable').DataTable({
		"scrollX" : true,
		"scrollY" : "270px",
		dom : 'Bfrtip',
		//	buttons : [ 'copy', 'csv', 'excel', 'pdf', 'print' ]
		buttons : [ {
			extend : 'excelHtml5',
			title : 'Error Report ' + new Date()
		}]
	});
	
}

function setActionHandlers(){
	setBackActionHandler();
	setDateResetHandler();
	setSelectAllCustomerHandler();
	setUnselectAllCustomerHandler();
}
function setSelectAllCustomerHandler(){
	$( "#selectAllCustomers" ).click(function() {
	    $('#customerId option').prop('selected', true);
	});
}

function setUnselectAllCustomerHandler(){
	$( "#unselectAllCustomers" ).click(function() {
	    $('#customerId option').prop('selected', false);
	});
}

function setBackActionHandler(){
	$( "#backButton" ).click(function() {
		showSearch();
	});
}
function setDateResetHandler(){
	$( "#dateResetButton" ).click(function() {
		$('#dateRange').val('');
	});
}
//fill customer drop down
function fillCustomerDropDown(customerArray){	
	var option = '';
	const arrayLength = customerArray.length;
	for (var i = 0; i < arrayLength; i++) {
	    option += '<option value="'+ customerArray[i].cutomerId+ '" >' +customerArray[i].customerName + '</option>';		
	}
	$('#customerId').append(option);
} 

$("#errorLogSearchForm").submit(function(e) {
	e.preventDefault();
	submitForm($(this));
});

function submitForm(form) {
	const oneFilled = checkFields(form);
	if(!oneFilled){
		showAlert ("Error: At least one form field should be provided.", "alert-danger" ,"alertContainer","alertRow");
	}else{
		const dateRange =  $("#dateRange").val();
		var startDateInMillis = null;
		var endDateInMillis = null;
		if(dateRange!=null && dateRange!==''){

			const dateRangeArray = dateRange.split("-");
			const from = dateRangeArray[0].split("/");
			const to = dateRangeArray[1].split("/");
			startDateInMillis = new Date(from[2], from[1] - 1, from[0]).getTime();
			const endDate = new Date(to[2], to[1] - 1, to[0]);
			endDate.setHours(23,59,59,999); //set time to eod 
			endDateInMillis = endDate.getTime();	
		}		

		const errorReportRequest = {
			"customerId" : $("#customerId").val(),
			"startDate" : startDateInMillis,
			"endDate" : endDateInMillis,
			"deviceId" : $("#deviceId").val(),
			"l0Device" : $("#l0").is(":checked"),
			"l1Device" : $("#l1").is(":checked")
		};
		
		searchErrorLogs(errorReportRequest)
	}
	
}

function searchErrorLogs(errorReportRequest){
	//calling API to get filtered devices
	$.ajax({
		type : 'POST',
		url : 'errorReport',
		contentType : 'application/json',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		data : JSON.stringify(errorReportRequest),
		beforeSend : function() {
			hideAlert("alertRow");
			$('#imgLoaderDiv').show();
		},
		complete : function() {
			$('#imgLoaderDiv').hide();
		},
		success : function(data, statusText, xhr) {
			if (xhr.status === 200) {
				processResponse(data);
			} else {
				showAlert("Error: Cannot generate error report.",
						"alert-danger", "alertContainer", "alertRow");
			}
		},
		error : function() {
			showAlert("An error occured while generating error report.", "alert-danger",
					"alertContainer", "alertRow");
		}
	});
}

function processResponse(data){				
	
	//show error message if any
	if(data.failureMessage.length){
		showAlert (data.failureMessage, "alert-danger" ,"alertContainer","alertRow");
	}else{		
		showSearchResults();
		const errorLogs=data.list;
		if((errorLogs==null) || (errorLogs.length===0)){
			clearTable();
		}else {
			refreshTable(errorLogs);
		}	
	}	
}

function clearTable(){	
	$('#errorLogTable').DataTable().clear().draw();	
}
function refreshTable(errorLogs) {
	// populate data table
	$('#errorLogTable')
			.DataTable(
					{
						dom : 'lBfrtip',
						buttons : [ {
							extend : 'excelHtml5',
							title : 'Error Report -' + new Date()
						}],
						destroy : true,
						data : errorLogs,
						order : [], // follow API defined ordering
						columns : [
								{
									"data" : "serialNumber"
								},
								{
									"data" : "partNumber"
								},
								{
									"data" : "customerName"
								},
								{
									"data" : "requestType"
								},
								{
									"data" : "attemptNumber"
								},
								{
									"data" : "comments"
								},
								{
									"data" : "creationTime",
									"render" : function(data) {
										if(data != null){
											const dtStart = new Date(data);
											const dtStartWrapper = moment(dtStart);
											return dtStartWrapper.format('MMMM Do YYYY');
										}else{
											return null;
										}

									}
								}
								]
					});
}

function showSearch(){
	$("#searchRow").show();
	$("#searchResultRow").hide();
}

function showSearchResults(){
	$("#searchRow").hide();
	$("#searchResultRow").show();
}

function checkLength(val){
    $("#deviceId").attr('title', val.length +" of 24")
    .tooltip('fixTitle')
    .tooltip('show');
}

function hideAlert(alertRow){
	$("#"+alertRow).hide();
}