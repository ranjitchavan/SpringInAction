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

	if ($.cookie("JSESSIONID") === "") {
		window.location = "/uidai-ms-webportal-ui/";
	}
	
});

var customerArray = [];

function goBack() {
	window.history.back();
}

var fileName = null;
function importDevice() {
	$("#alertRow").hide();

	const
	files = [];
	$.each($("input[type=file]"), function(i, obj) {
		$.each(obj.files, function(i, file) {
			files.push(file);
		});
	});

	const
	signatureFile = $('#file').prop('files')[0];

	form = new FormData();
		form.append("file", files[0]);
		$.ajax({
					url : 'enable-L1HL0-Devices',
					type : 'POST',
					data : form,
					cache : false,
					contentType : false,
					processData : false,
					headers : {
						"X-Auth-Token" : $.cookie("JSESSIONID")
					},
					beforeSend : function() {
						$('#imgLoaderDiv').show();
						$("#import").prop('disabled', 'disabled');
						$("#cancel").prop('disabled', 'disabled');
						$("#file").prop('disabled', 'disabled');
					},
					success : function(data, statusText, xhr) {
						console.log(xhr.status);
						console.log(data);
						if (xhr.status == 200) {
							if(data.length >=3){
								var message = "Enable migration Completed. Total records :" + data[2] +",  Success: "+data[0] +",  Failure :"+
								data[1];
		
								fileName = data[3];
	
							}else{
								var message = 
								+ " Devices uploaded successfully";
							}
						
							showAlert(message, "alert-success",
									"alertContainer", "alertRow");

							$("#file").val('');
							$("#import").prop('disabled', 'disabled');
							$("#cancel").prop('disabled', '');
							$("#file").prop('disabled', '');
							//$('#SelectCustomer').prop('selectedIndex', 0);
						}

						$('#imgLoaderDiv').hide();

					},
					error : function(data, statusText, xhr) {

						$('#imgLoaderDiv').hide();

						console.log(data);
						console.log(statusText);
						console.log(xhr.status);
						if (data.status == 412) {
							var message = "ERROR: Certificate expired for selected customer";
							showAlert(message, "alert-danger",
									"alertContainer", "alertRow");
							$("#import").prop('disabled', 'disabled');
							$('#downloadUnimport').hide();

						}

						else if (data.status == 406) {
							var message = "ERROR:  Please upload valid certificate for selected customer";
							showAlert(message, "alert-danger",
									"alertContainer", "alertRow");
							$("#import").prop('disabled', 'disabled');
							$('#downloadUnimport').hide();

						} else {

							var message = "Error in upload device";
							showAlert(message, "alert-danger",
									"alertContainer", "alertRow");
							$("#import").prop('disabled', 'disabled');
							$('#downloadUnimport').hide();

						}
						
						$("#file").val('');
						//$("#import").prop('disabled', '');
						$("#cancel").prop('disabled', '');
						$("#file").prop('disabled', '');
					}
				});

	//} else {
	//	var message = " Please select customer name";
		//showAlert(message, "alert-danger", "alertContainer", "alertRow");
	//}

	//return false;
};

function updateFormEnabled() {

	if (verifyDropdowns(this)) {
		$("#import").prop('disabled', '');
	} else {
		$("#import").prop('disabled', 'disabled');
	}
}

function verifyDropdowns(browseFile) {
	//console.log("I am heree");
	const files = $("#file")[0].files[0];
	console.log("files.type::"+files.type);
	var validExts = new Array(".xlsx", ".xls");
	var fileExt = browseFile.value;

	console.log("files.type::"+fileExt);
	fileExt = fileExt.substring(fileExt.lastIndexOf('.'));

	if (validExts.indexOf(fileExt) < 0) {
		 showAlert("Error: File with xlsx/xls format can only be uploaded.",
					"alert-danger", "alertContainer", "alertRow");
		 $("input[name=file]").val('');
		 $('#downloadUnimport').hide();
		 return false;
	 } else {
		 $('#downloadUnimport').show();
		 $("#alertRow").hide();
	}

	 return ($('#file').val() != null && $('#file').val() !== '');
}


function viewUnImportDevice(e) {
	console.log(" download page");
	e.href = "";
	e.href = "get-unimport-Device?filename="+fileName;
	window.open($(this).attr("href"));
}

$('#file').change(updateFormEnabled);