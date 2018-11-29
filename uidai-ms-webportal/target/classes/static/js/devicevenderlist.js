$(document).ready(function() {
	$.fn.dataTable.moment( 'MMMM Do YYYY' );
	
});

var deviceProviderCertificateId = null;

$
		.ajax({
			type : 'GET',
			url : 'get-vender-certificates',
			headers : {
				"X-Auth-Token" : $.cookie("JSESSIONID"),
			},
			beforeSend : function() {
				$('#imgLoaderDiv').show();
			},
			complete : function() {
				$('#imgLoaderDiv').hide();
			},
			success : function(data) {
				table = $('#venders')
						.DataTable(
								{
									"scrollX" : true,
									"scrollY" : "300px",
									data : data,
									columns : [
											{
												"data" : "dpId"
											},
											{
												"data" : "customerName"
											},
											{
												"data" : "expiryDate",
												"render" : function(data, type,
														full) {
													var dtStart = new Date(data);
													var dtStartWrapper = moment(dtStart);
													return '<a href ="#">'
															+ dtStartWrapper
																	.format('MMMM Do YYYY')
															+ '</a>';
												}
											},
											{
												"data" : "dpPrivateKeyLabel"
											},
											{
												"data" : "dpCertificateIdentifier"
											},
											{
												"data" : "updationTime",
												"render" : function(data, type,
														full) {
													var dtStart = new Date(data);
													var dtStartWrapper = moment(dtStart);
													return dtStartWrapper
															.format('MMMM Do YYYY');
												}
											},

											{
												"data" : "createdBy"
											},
											{
												"targets" : -1,
												"data" : null,
												"defaultContent" : " <a class='updateBtn' style='cursor: pointer;'><i class='fa fa-edit'></i></a>",
												"orderable" : false, // disable
												// sorting
												// on
												// action
												"searchable" : false, // disable
											// searching
											// on
											// action
											}

									]

								});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				// alert('error: ' + textStatus + ': ' +
				// errorThrown);
			}
		// Edit record
		});

if ($.cookie("JSESSIONID") == "") {
	window.location = "/uidai-ms-webportal-ui/";
}
var customerSelected = null;
$('#venders tbody').on(
		'click',
		'.updateBtn',
		function updateUser() {
			var data = table.row($(this).parents('tr')).data();
			console.log(data);
			customerSelected = data.customerName;
			$("#updateDeviceAlertRow").hide();
			getCustomer();
			$("#deviceCertUpdate").modal('show');

			//$('#SelectCustomer').val(customerSelected);
			
			var dpId = $("#dpId").val(data.dpId);
			var dpPrivateKeyLabel = $("#dpPrivateKeyLabel").val(
					data.dpPrivateKeyLabel);
		
			var email_id = $("#revoke").val(data.revoke);
			var phone = $("#phone").val(data.phone);
			if (data.revoked == 1) {
				$('#revoked').prop('checked', true);
			} else {
				$('#revoked').prop('checked', false);
			}
			
			deviceProviderCertificateId = data.deviceProviderCertificateId;
			
		});


function viewVenderCert(e) {
	e.href = "";
	e.href = "get-vender-cert" + "/" + deviceProviderCertificateId;
	window.open($(this).attr("href"));
}

function viewUIDAICert(e) {
	e.href = "";
	e.href = "get-uidai-cert" + "/" + deviceProviderCertificateId;
	window.open($(this).attr("href"));
}

var msg =  "";
function updateUIDAICertificate() {
	msg = "";
		if($('#SelectCustomer').find(":selected").text() != null &&  $('#SelectCustomer').find(":selected").text() != "" && $('#SelectCustomer').find(":selected").text() != "Please Select"){

		var vendercert = $('#vendercert_file')[0].files[0]
		if (vendercert) {
			console.log(vendercert.name);
		}
		var uidaicert = $('#uidaicert_file')[0].files[0]
		if (uidaicert) {
			console.log(uidaicert.name);
		}
	
		if($("#dpPrivateKeyLabel").val() == "" ){
			
			
			if(msg.length >0){
				msg = msg+ " Please enter DP Private Key Label "
			}else{
				msg = msg+ ","+" Please enter DP Private Key Label "
			}
		}
		if($("#dpId").val() == "" ){
			if(msg.length >0){
				msg = msg+ ","+" Please enter DP key"
			}else{
				msg = msg+ " Please enter DP key"
			}
		}
		
		
		if($('#SelectCustomer').find(":selected").text().length == 0 || $('#SelectCustomer').find(":selected").text() == "Please Select"){
			msg = msg + " Please Select customer <br/>";
		}
		
		var form = new FormData();
		if (vendercert != null) {
			form.append("dpcert", vendercert);
		}
		
		if (uidaicert != null) {
			form.append("uicert", uidaicert);
		}

		form.append("dpprivatekey", $("#dpPrivateKeyLabel").val());
		form.append("dpid", $("#dpId").val());
		form.append("deviceProviderCertificateId",deviceProviderCertificateId);
		form.append("customerName",$('#SelectCustomer').find(":selected").text());
		
		if($("#revoked").is(':checked') == true){
			form.append("revoked",1);
		}else{
			form.append("revoked",0);
		}

		if(msg.length == 0){
			
			$.ajax({
				url : 'update-vender-certificate',
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
				success : function(response) {
					//alert("Document uploaded successfully.");
					showAlert("SUCCESS: Certificates updated successfully.",
							"alert-success", "deviceupdateAlertContainer", "updateDeviceAlertRow");
					var delay = 3000;
					setTimeout(function() {
						$("#deviceCertUpdate").modal('hide');
						location.reload();
					}, delay);
					
				},
				error : function(response) {
					showAlert("ERROR: occur during  certificates update.",
							"alert-danger", "deviceupdateAlertContainer", "updateDeviceAlertRow");
				}
			});
		}else{
			showAlert("ERROR:  "+msg,
					"alert-danger", "deviceupdateAlertContainer", "updateDeviceAlertRow");
		}
	}else{
		showAlert("ERROR: Please select customer.",
				"alert-danger", "deviceupdateAlertContainer", "updateDeviceAlertRow");
	}
}

function hideAlert(alertRowId){
	if($('#'+alertRowId).is(':visible')){
		$('#'+alertRowId).hide();
	}	
}



var customerArray = [];

function getCustomer(){
	$.ajax({
		url : 'customer-names',
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
		error : function(){
			$('#imgLoaderDiv').hide();
		}
	});
}

function fillCustomerCombo(){
	var customer = "";
	for(var i = 0; i < customerArray.length; i++) {
		customer += "<option value='" + customerArray[i].customerName + "'>" + customerArray[i].customerName + "</option>";
		
		
	}
	//$('select[name="SelectCustomer"]' ).append(customer);
	//$("#SelectCustomer").val(customerSelected);
}

