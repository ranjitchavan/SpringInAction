

$(document).ready(function() {
	getCustomer();
	
});

function uploadCert() {
	var files = [];
	$.each($("input[type=file]"), function(i, obj) {
		$.each(obj.files, function(i, file) {
			files.push(file);
		})
	});

	var msg = "";
	if (files.length < 2) {
		msg = msg + "  Please upload all certificates";
	} else {
		var filename = files[0].name;
		var fileExt = getExt(filename)
		/*if (fileExt != "crt") {
			msg = msg + "  Please select valid certificate ";

		}*/

		filename = files[1].name;
		fileExt = getExt(filename)
		/*if (fileExt != "crt") {
			msg = msg + "  Please select valid certificate ";

		}*/
	}

	/*if ($("#expiryDate").val() == "") {
		msg = msg + "Please enter expiry date<br/>";
	}*/
	if($("#dpid").val() == "" ){
		
		if(msg.length >0){
			msg = msg +","+" Please enter DP Id ";
		}else{
			msg = msg + " Please enter DP Id ";
		}
	}
	
	if($("#dpprivatekey").val() == "" ){
		if(msg.length>0){
			msg = msg+ " ,"+" Please enter DP Private Key Label"
		}else{
			msg = msg+ " Please enter DP Private Key Label "
		}
	}
	
	if($('#SelectCustomer').find(":selected").text().length == 0 || $('#SelectCustomer').find(":selected").text() == "Please Select"){
		
		if(msg.length>0){
			msg = msg + ","+" Please Select customers <br/>";
		}else{
			msg = msg + " Please Select customers <br/>";
		}
	}

	if (msg.length == 0) {
		var form = new FormData();
		form.append("dpcert", files[0]);
		form.append("uicert", files[1]);
		form.append("expiryDate", $("#expiryDate").val());
		form.append("dpprivatekey", $("#dpprivatekey").val());
		form.append("dpid", $("#dpid").val());
		form.append("customerName", $("#sbTwo").val());
		//form.append("customerName", $('#SelectCustomer').find(":selected").text());

		$
				.ajax({
					url : 'save-vender-certificate',
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
						showAlert("SUCCESS: Certificates uploaded successfully.",
								"alert-success", "errorAlertContainer",
								"errorAlertRow");
						var delay = 3000;
						setTimeout(function() {
							window.location = "vendorCertificate.html";
						}, delay);
						
					},
					error : function(response) {
						console.log(response.status);
						if(response.status == 406){
							showAlert("ERROR: Invalid certificate format.",
									"alert-danger", "errorAlertContainer",
									"errorAlertRow");	
						}else{
							showAlert("Error during certificate upload",
									"alert-danger", "errorAlertContainer",
									"errorAlertRow");
						}
					}
				});
	} else {
		showAlert("ERROR: " + msg, "alert-danger", "errorAlertContainer",
				"errorAlertRow");
	}
}

function getExt(filename) {
	var ext = filename.split('.').pop();
	if (ext == filename)
		return "";
	return ext;
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
			console.log(data);
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
	console.log(customerArray);
	var customer = "";
	for(var i = 0; i < customerArray.length; i++) {
		alert(customerArray[i].customerName);
		customer += "<option value='" + customerArray[i].customerName + "'>" + customerArray[i].customerName + "</option>";
	}
	
	$( 'select[name="SelectCustomer"]' ).append( customer );
}





$(function () { function moveItems(origin, dest) {
    $(origin).find(':selected').appendTo(dest);
}
 
function moveAllItems(origin, dest) {
    $(origin).children().appendTo(dest);
}
 
$('#left').click(function () {
    moveItems('#sbTwo', '#SelectCustomer');
});
 
$('#right').on('click', function () {
    moveItems('#SelectCustomer', '#sbTwo');
});
 
$('#leftall').on('click', function () {
    moveAllItems('#sbTwo', '#SelectCustomer');
});
 
$('#rightall').on('click', function () {
    moveAllItems('#SelectCustomer', '#sbTwo');
});
});