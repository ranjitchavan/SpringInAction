let
userId = null;
let
table = null;
$(document).ready(function() {
	$.fn.dataTable.moment( 'MMMM Do YYYY' );
	getUsers();
	setEditCloseAction();
});

function setEditCloseAction(){
	$("#userUpdate").on("hide.bs.modal", function () {
		hideAlert("updateAlertRow");
	});
}

$('#users tbody').on('click', '.updateBtn', function updateUser() {
	let
	data = table.row($(this).parents('tr')).data();
	$("#userUpdate").modal('show');
	let
	firstName = $("#first_name").val(data.firstName);
	let
	middleName = $("#middle_name").val(data.middleName);
	let
	lastName = $("#last_name").val(data.lastName);
	let
	emailId = $("#email_id").val(data.email);
	let
	phone = $("#phone").val(data.phone);
	let
	address = $("#address").val(data.address);
	userId = data.user_id;
	
});


$('#users tbody')
		.on(
				'click',
				'.delteBtn',
				function updateUser() {
					let
					data = table.row($(this).parents('tr')).data();

					let
					answer = confirm("Are you sure to de-activate this User? ")

					if (answer) {
						$
								.ajax({
									type : 'POST',
									url : 'deactivate-user',
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
										console.log(xhr);
										if (xhr.status == 403) {
											showAlert(
													"ERROR:  Currently  logged in user cannot be deleted",
													"alert-danger",
													"errorAlertContainer",
													"errorAlertRow");
										} else {
											showAlert(
													"SUCCESS: User de-activated Successfully",
													"alert-success",
													"errorAlertContainer",
													"errorAlertRow");

											let
											delay = 3000;
											setTimeout(function() {
												location.reload();
											}, delay);

										}

									},
									error : function(data) {
										if (data.status == 403) {
											showAlert(
													"ERROR: Currently  logged in user cannot be deleted",
													"alert-danger",
													"errorAlertContainer",
													"errorAlertRow");
										} else if (data.status == 400) {
											showAlert(
													"ERROR: Super admin account cannot be deleted ",
													"alert-danger",
													"errorAlertContainer",
													"errorAlertRow");
										}

									}
								});
					} else {
						// some code
					}

				});
function updateUser() {
	let firstName = $("#first_name").val();
	let middleName = $("#middle_name").val();
	let lastName = $("#last_name").val();
	let emailId = $("#email_id").val();
	let phone = $("#phone").val();
	let address = $("#address").val();
	let createdDate = $("#created_date").val();

	var user = {
		"firstName" : firstName,
		"lastName" : lastName,
		"middleName" : middleName,
		"email" : emailId,
		"phone" : phone,
		"address" : address,
		"userId" : userId,
		"createdDate" : createdDate

	}

	const nameValidator = /^[a-zA-Z]+$/;
	let msg = "";
	
	if (firstName.length == 0) {
		msg = msg + "Firstname cannot be blank <br/>";
	}
	if (lastName.length == 0) {
		msg = msg + "Lastname cannot be blank <br/>";
	}

	if(firstName.length && !nameValidator.test(firstName)){
		msg = msg + "First Name should only contain letters <br/>";
	}
	
	if(middleName.length && !nameValidator.test(middleName)){
		msg = msg + "Middle Name should only contain letters <br/>";
	}
	if(lastName.length && !nameValidator.test(lastName)){
		msg = msg + "Last Name should only contain letters <br/>";
	}
	
	if (phone.length > 0) {
		var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;

		if (phone.match(phoneno)) {

		} else {
			msg = msg + "Phone No. is not valid.";
		}

	}
	if (msg.length == 0) {
		$.ajax({
			type : 'POST',
			url : 'update-user',
			contentType : 'application/json',
			beforeSend : function() {
				$('#imgLoaderDiv').show();
			},
			complete : function() {
				$('#imgLoaderDiv').hide();
			},
			headers : {
				"X-Auth-Token" : $.cookie("JSESSIONID"),
			},
			data : JSON.stringify(user),
			success : function(data) {
				showAlert("Success : User updated Successfully",
						"alert-success", "updateAlertContainer",
						"updateAlertRow");

				setTimeout(function() {
					$("#userUpdate").hide();
					location.reload();

				}, 3000);

			},
			error : function(data) {
				showAlert("Error : Occur during user updation", "alert-danger",
						"updateAlertContainer", "updateAlertRow");
			}
		});
	} else {
		showAlert(msg, "alert-danger", "updateAlertContainer", "updateAlertRow");

	}

}

function getUsers() {
	var users = {
		"isActive" : true
	};
	
	$
			.ajax({
				type : 'POST',
				url : 'get-all-user',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID"),
				},
				data : users,
				beforeSend : function() {
					$('#imgLoaderDiv').show();
				},
				complete : function() {
					$('#imgLoaderDiv').hide();
				},
				success : function(data) {
					table = $('#users')
							.DataTable(
									{
										"scrollX" : true,
										"scrollY" : "310px",
										data : data,
										columns : [
												{
													"data" : "username"
												},
												{
													"data" : "phone"
												},
												{
													"data" : "email"
												}/*
													 * , { "data" : "address" }
													 */,
												{
													"data" : "updationTime",
													"render" : function(data,
															type, full) {
														let
														dtStart = new Date(data);
														let
														dtStartWrapper = moment(dtStart);
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
													"defaultContent" : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='updateBtn' data-toggle='tooltip' title='Update!'' style='    cursor: pointer;'><i class='fa fa-edit'></i> </a>",
													"orderable" : false,
													"searchable" : false,
												},
												{
													"targets" : -1,
													"data" : null,
													"defaultContent" : "&nbsp;&nbsp;&nbsp;<a class='delteBtn btn btn-danger btn-sm' data-toggle='tooltip' title='Delete!''>Deactivate</a>  ",
													"orderable" : false,
													"searchable" : false,
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

}


function hideAlert(alertRowId){
	if($('#'+alertRowId).is(':visible')){
		$('#'+alertRowId).hide();
	}	
}