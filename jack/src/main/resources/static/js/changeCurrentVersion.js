//--for toggle menu--//
$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
});

// --for data table--//
$(document).ready(function() {
	$('#example').DataTable();
	$('#farmer_list').DataTable({
		"scrollX" : true
	});
	getCustomer();
});

function goBack() {
	window.history.back();
}

$.ajax({
	type : 'GET',
	headers : {
		"X-Auth-Token" : $.cookie("JSESSIONID")
	},
	url : 'get-only-software',
	success : function(data) {
		OnSuccessSoftware(data);

	},
	error : function() {
		showAlert("Error: Getting software", "alert-danger", "alertContainer",
				"alertRow");
	}
});

function OnSuccessSoftware(r) {
	const
	ddlCustomers = $("[id*=applicationType]");
	ddlCustomers.empty().append(
			'<option selected="selected" value="">Please select</option>');
	for (let i = 0; i < r.length; i++) {
		ddlCustomers.append("<option value='" + r[i] + "'>" + r[i]
				+ '</option>');
	}
}

$("select#applicationType").on('change', function() {
	getOsSubType();
	getVersions();
});
function getOsSubType() {
	const
	applicationType = $("#applicationType").val();

	const	version = {
		"application_type" : applicationType
	};

	$.ajax({
		type : 'POST',
		url : 'get-only-os-subtype',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		contentType : 'application/json',
		data : JSON.stringify(version),
		success : function(data) {
			OnSuccessApplication(data);
		},
		error : function() {
			showAlert("Error: Getting os", "alert-danger", "alertContainer",
					"alertRow");
		}
	});
}

function OnSuccessApplication(r) {
	const ddlCustomers = $("[id*=os]");
	ddlCustomers.empty().append(
			'<option selected="selected" value="">Please select</option>');
	for (let i = 0; i < r.length; i++) {
		ddlCustomers.append("<option value='" + r[i] + "'>" + r[i]
				+ '</option>');
	}
}

$("select#osType").on('change', function() {
	getVersions();
	
});

function getVersions() {
	const applicationType = $("#applicationType").val();
	const osType = $("#osType").val();
	const version = {
		"application_type" : applicationType,
		"osType" : osType
	};

	$.ajax({
		type : 'POST',
		url : 'get-only-versions',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		contentType : 'application/json',
		data : JSON.stringify(version),
		success : function(data) {
			OnSuccessOs(data);
		},
		error : function() {
			showAlert("Error: Getting versions", "alert-danger",
					"alertContainer", "alertRow");
		}
	});

}

function OnSuccessOs(r) {
	const
	ddlCustomers = $("[id*=versionList]");
	ddlCustomers.empty().append(
			'<option selected="selected" value="">Please select</option>');
	for (let i = 0; i < r.length; i++) {
		ddlCustomers.append("<option value='" + r[i] + "'>" + r[i]
				+ '</option>');
	}

	updateFormEnabled();
}

$("select#versionList").on('change', function() {
	getDpId();
});


function getDpId() {
	const applicationType = $("#applicationType").val();
	const osType = $("#osType").val();
	const version = $("#versionList").val();
	const versionArray = {
		"application_type" : applicationType,
		"osType" : osType,
		"current_version" : version
		
	};

	$.ajax({
		type : 'POST',
		url : 'get-only-dpId',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		contentType : 'application/json',
		data : JSON.stringify(versionArray),
		success : function(data) {
			OnSuccessVersion(data);
		},
		error : function() {
			showAlert("Error: Getting os", "alert-danger", "alertContainer",
					"alertRow");
		}
	});
}

function OnSuccessVersion(r) {
	const
	ddlCustomers = $("[id*=SelectCustomer]");
	ddlCustomers.empty().append(
			'<option selected="selected" value="">Please select</option>');
	for (let i = 0; i < r.length; i++) {
		ddlCustomers.append("<option value='" + r[i] + "'>" + r[i]
				+ '</option>');
	}

	updateFormEnabled();
}

function updateCurrentVersion() {

	const applicationType = $("#applicationType").val();
	const osType = $("#osType").val();
	const versionList = $("#versionList").val();

	
	let version = {};

	if($("#applicationType").val().trim()==="RD Service" || $("#applicationType").val().trim()==="Management Client"){
		console.log( $("#SelectCustomer").val());
		
		version = {
				"application_type" : applicationType,
				"osType" : osType,
				"current_version" : versionList,
				"dpId" :  $("#SelectCustomer").val()
			};
		
	}else{
		version = {
				"application_type" : applicationType,
				"osType" : osType,
				"current_version" : versionList,
				"dpId" : null
			};
	}
	
	
	
	console.log(version);
	
	
	$.ajax({
		type : 'POST',
		url : 'update-current-version',
		headers : {
			"X-Auth-Token" : $.cookie("JSESSIONID")
		},
		contentType : 'application/json',
		data : JSON.stringify(version),
		success : function() {
			showAlert("Success: Version updated successfully", "alert-success",
					"alertContainer", "alertRow");
			$("#applicationType").val('');
			$("#os").val('');
			$("#versionList").val('');

			setTimeout(function() {
				window.location.href = "currentVersionList.html";
			}, 3000);
		},
		error : function() {
			showAlert("Error occured during updating version", "alert-danger",
					"alertContainer", "alertRow");
		}
	});

}

$('#change').attr('disabled', 'disabled');

function updateFormEnabled() {
	if (verifyDropdowns()) {
		$("#change").prop('disabled', '');
	} else {
		$("#change").prop('disabled', 'disabled');
	}
}

function verifyDropdowns() {

	if($("#applicationType").val().trim()==="RD Service" || $("#applicationType").val().trim()==="Management Client"){
		$("#customerBlock").show();
		return (isNotEmpty('applicationType') && isNotEmpty('versionList') && isNotEmpty('osType') && isNotEmpty('SelectCustomer'));
		
	}else{
		$("#customerBlock").hide();
		return (isNotEmpty('applicationType') && isNotEmpty('versionList') && isNotEmpty('osType'));
	}


}

function isNotEmpty(elementId){
	return ($("#"+elementId).val() != null) && ($("#"+elementId).val()!== '') ;
}



function getCustomer(){
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
			console.log(data);
			dpIdArray = data;
			$('#imgLoaderDiv').hide();
			fillCustomerCombo();
		},
		error : function(){
			$('#imgLoaderDiv').hide();
		}
	});
}


$('#applicationType').change(updateFormEnabled);

$('#versionList').change(updateFormEnabled);

$('#osType').change(updateFormEnabled);

$('#SelectCustomer').change(updateFormEnabled);

if ($.cookie("JSESSIONID") === "") {
	window.location = "/uidai-ms-webportal-ui/";
}

function fillCustomerCombo(){
	
	var customer = "";
	for(var i = 0; i < dpIdArray.length; i++) {
		customer += "<option value='" + dpIdArray[i] + "'>" + dpIdArray[i] + "</option>";
	}
	
	$( 'select[name="SelectCustomer"]' ).append( customer );
	updateFormEnabled();

}