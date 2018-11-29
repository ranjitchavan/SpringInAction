let table;

$(document)
		.ready(
				function() {
					$.fn.dataTable.moment( 'MMMM Do YYYY' );

					$
							.ajax({
								type : 'GET',
								url : 'get-all-uidai-public-certificate',
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
									
									table = $('#uidai_list')
											.DataTable(
													{
														"scrollX" : true,
														"scrollY" : "400px",
														data : data,
														columns : [
																{
																	"data" : "certificateType"
																},
																{
																	"data" : "expiryDate",

																	"render" : function(
																			data,
																			type,
																			full) {
																		let dtStart = new Date(
																				data);
																		let dtStartWrapper = moment(dtStart);
																		return '<a href="#">'
																				+ dtStartWrapper
																						.format('MMMM Do YYYY')
																				+ '</a>';
																	}

																},
																{
																	"data" : "updationTime",
																	"render" : function(
																			data,
																			type,
																			full) {
																		let dtStart = new Date(
																				data);
																		let dtStartWrapper = moment(dtStart);
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
																	"defaultContent" : "<a class='delteBtn' style='cursor: pointer;'><i class='fa fa-trash-o'></i></a>",
																	"orderable" : false, // disable
																	// sorting
																	// on
																	// action
																	"searchable" : false
																// disable
																// searching on
																// action
																}

														]

													});
								},
								error : function(jqXHR, textStatus, errorThrown) {
									
								}
							// Edit record
							});

				});

$('#uidai_list tbody')
		.on(
				'click',
				'.delteBtn',
				function updateUser() {
					let data = table.row($(this).parents('tr')).data();
					console.log(data);
					let answer = confirm("Are you sure , You want to delete UIDAI public certificate? ")

					if (answer) {
						$
								.ajax({
									type : 'POST',
									url : 'delete-uidai-public-certificate',
									contentType : 'application/json',
									headers : {
										"X-Auth-Token" : $.cookie("JSESSIONID")
									},
									beforeSend : function() {
										$('#imgLoaderDiv').show();
									},
									complete : function() {
										$('#imgLoaderDiv').hide();
									},
									data : JSON.stringify(data),
									success : function(data) {
										showAlert(
												"SUCCESS: certificate deleted Successfully",
												"alert-success",
												"errorAlertContainer",
												"errorAlertRow");
										let delay = 3000;
										setTimeout(function() {
											location.reload();
										}, delay);
									},
									error : function(data) {
										showAlert(
												"ERROR: Error occur during certificate deletion",
												"alert-danger",
												"errorAlertContainer",
												"errorAlertRow");
									}
								});
					} else {
						// some code
					}

				});

function saveUIDAICert() {
	let files = [];
	$.each($("input[type=file]"), function(i, obj) {
		$.each(obj.files, function(i, file) {
			files.push(file);
		})
	});

	let msg = "";
	if (files.length < 1) {
		msg = msg + " Please upload Certificate ";
	} else {

		let filename = files[0].name;
		let fileExt = getExt(filename)
		/*if (fileExt != "crt") {
			msg = msg + " Please select valid certificate ";

		}*/
	}

	if ($("#envType").val() == "" || $("#envType").val() == null) {
		if (msg.length > 0) {
			msg = msg + " ,Please select Environment Type  ";
		} else {
			msg = msg + " Please select Environment Type  ";
		}

	}

	

	if (msg.length == 0) {
		let form = new FormData();
		form.append("uidaicert", files[0]);
		form.append("envtype", $("#envType").val());
		form.append("expiryDate", $("#expiryDate").val());

		$
				.ajax({
					url : 'save-uidai-public-certificate',
					type : 'POST',
					data : form,
					cache : false,
					contentType : false,
					headers : {
						"X-Auth-Token" : $.cookie("JSESSIONID")
					},
					processData : false,
					success : function(response) {
						showAlert(
								"SUCCESS: Certificates uploaded successfully.",
								"alert-success", "errorAlertContainer",
								"errorAlertRow");
						let delay = 3000;
						setTimeout(function() {
							location.reload();
						}, delay);
						
					},
					error : function(response) {
						if(response.status == 406){
							showAlert(
									"ERROR: Invalid certificate format.",
									"alert-danger", "errorAlertContainer",
									"errorAlertRow");
						}else{
							showAlert(
									"ERROR: Occur during uploading certificates .",
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
	let ext = filename.split('.').pop();
	if (ext == filename)
		return "";
	return ext;
}

$('#showAddDiv').click(function(){
	$('#addCertDiv').show();
	$('#hideAddDiv').show();
	$('#showAddDiv').hide();
});
$('#hideAddDiv').click(function(){
	$('#addCertDiv').hide();
	$('#hideAddDiv').hide();
	$('#showAddDiv').show();
});


