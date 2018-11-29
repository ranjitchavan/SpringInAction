//--for data table--//
$(document).ready(function() {
	$('#customerKeyRotationTable').DataTable({
		"scrollX" : true,
		"scrollY" : "400px"
	});
	loadContent();
});
let
devicePartnoMasterId = null;
function loadContent() {
	$
			.ajax({
				type : 'GET',
				url : 'get-all-part-no',
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
					// refreshTable(data);
					table = $('#devicePartNuber')
							.DataTable(
									{
										"scrollX" : true,
										"scrollY" : "400px",
										data : data,
										columns : [
												{
													"data" : "partNo"
												},
												{
													"data" : "deviceType"
												},
												{
													"data" : "mi"
												},
												{
													"data" : "applicationType"
												},
												{
													"targets" : -1,
													"data" : null,
													"defaultContent" : '<a class="updateBtn" style="cursor: pointer;"><i class="fa fa-edit"></i></a> | '
															+ '<a class="deleteBtn"  data-toggle="tooltip" title="Delete!" style="cursor: pointer;"><i class="fa fa-trash-o"></i></a>',
													"orderable" : false,
													"searchable" : false
												} ],
									});

				},
				error : function(jqXHR, textStatus, errorThrown) {
					showAlert(textStatus + ': ' + errorThrown, "alert-danger",
							"alertContainer", "alertRow");
				}

			});
}

$('#btnSave').click(
		function() {

			let
			part_no = {
				"devicePartnoMasterId" : devicePartnoMasterId,
				"partNo" : $("#devicePartNo").val(),
				"deviceType" : $("#deviceType").val(),
				"mi" : $("#mi").val(),
				"applicationType" : $("#applicationType").val()
			}

			console.log(part_no);

			$.ajax({
				type : 'POST',
				url : 'add-device-part-no',
				contentType : 'application/json',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
				},
				data : JSON.stringify(part_no),
				beforeSend : function() {
					$('#imgLoaderDiv').show();
				},
				complete : function() {
					$('#imgLoaderDiv').hide();
				},
				success : function(data, statusText, xhr) {
					console.log(xhr);
					if(xhr.status ==  208){
						showAlert("Error:Part Number already exists", "alert-danger",
								"alertContainer", "alertRow");
					}else if (xhr.status == 201){
							showAlert("Device part-no added successfully",
									"alert-success", "alertContainer", "alertRow");
						
					}else if(xhr.status == 200){
						showAlert("Device part-no updated successfully",
								"alert-success", "alertContainer", "alertRow");
					}
					
					setTimeout(function() {
						location.reload();
					}, 3000);
				},
				error : function(data, statusText, xhr) {
					if(data.status ==  417){
						showAlert("Error:Part Number already exists", "alert-danger",
								"alertContainer", "alertRow");
					}else{
						showAlert("Error:Server error occur", "alert-danger",
								"alertContainer", "alertRow");		
					}
				}
			});
		});

function updateFormEnabled() {

	if (verifyDropdowns()) {
		$("#btnSave").prop('disabled', '');
	} else {
		$("#btnSave").prop('disabled', 'disabled');
	}
}

function verifyDropdowns() {
	if ($('#devicePartNo').val() !== null && $('#devicePartNo').val() !== ''
			&& $('#devicePartNo').val().length === 12) {
		if ($('#deviceType').val() !== null && $('#deviceType').val() !== '') {
			if ($('#mi').val() !== null && $('#mi').val() !== '') {
				if ($('#applicationType').val() !== null && $('#applicationType').val() !== '' && $('#applicationType').val() != 'Please Select' ) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}

		} else {
			return false;
		}
	} else {
		return false
	}
	
	
}

function unableData(){
	
	if($('#applicationType').val() !== null && $('#applicationType').val() !== ''){
		$('#applicationType').empty();
		$("#applicationType").append('<option value="Firmware CBM-E2">Firmware CBM-E2</option>');
		$("#applicationType").append('<option value="Firmware CBM-E3">Firmware CBM-E3</option>');
	}
	
	if($("#deviceType").val() !== 'L0'){
		$('#applicationType').empty();
		$("#applicationType").append('<option value="Firmware CBM-E3">Firmware CBM-E3</option>');
		
	}else{
		$('#applicationType').empty();
		$("#applicationType").append('<option value="Firmware CBM-E2">Firmware CBM-E2</option>');
		$("#applicationType").append('<option value="Firmware CBM-E3">Firmware CBM-E3</option>')
	}
}

$('#devicePartNo').keyup(updateFormEnabled);

$('#deviceType').change(updateFormEnabled);

$('#mi').keyup(updateFormEnabled);

$('#applicationType').keyup(updateFormEnabled);

$('#deviceType').on('change', function() {
	  unableData();
});


$('#btnReset').click(function() {
	$('#devicePartNo').val('');
	$('#deviceType').val('');
	$('#mi').val('');
	$('#applicationType').val('');
	updateFormEnabled();
	
});

$('#devicePartNuber tbody').on(
		'click',
		'.deleteBtn',
		function deleteUser() {
			let
			data = table.row($(this).parents('tr')).data();

			let
			answer = confirm("Are you sure to delete this device part-no ? ")

			if (answer) {
				$.ajax({
					type : 'POST',
					url : 'delete-device-part-no',
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
						showAlert("Success :Device Part-No deleted successfully",
								"alert-success", "alertContainer", "alertRow");

						let
						delay = 3000;
						setTimeout(function() {
							location.reload();
						}, delay);
					},
					error : function(data) {
						showAlert("Error : Deletion of part-no not performed ",
								"alert-danger", "alertContainer", "alertRow");
					}
				});
			}
		});

$('#devicePartNuber tbody').on('click', '.updateBtn', function updateUser() {
	
	let
	data = table.row($(this).parents('tr')).data();
	devicePartnoMasterId = data.devicePartnoMasterId;
	$("#devicePartNo").val(data.partNo);
	$("#deviceType").val(data.deviceType);
	$("#mi").val(data.mi);
	$("#applicationType").val(data.applicationType);
	updateFormEnabled();
	unableData();
	$("#applicationType").val(data.applicationType).attr("selected","selected");
});
