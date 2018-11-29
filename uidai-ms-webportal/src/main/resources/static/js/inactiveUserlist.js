let
userId = null;
let
table = null;
$(document).ready(function() {
	$.fn.dataTable.moment( 'MMMM Do YYYY' );
	getUsers();
});

$('#users').find('tbody').on(
		'click',
		'.delteBtn',
		function () {
			const data = table.row($(this).parents('tr')).data();
			const answer = confirm("Are you sure to re-activate this user? ");

			if (answer) {
				$.ajax({
					type : 'POST',
					url : 'activate-user',
					beforeSend : function() {
						$('#imgLoaderDiv').show();
					},
					complete : function() {
						$('#imgLoaderDiv').hide();
					},
					contentType : 'application/json',
					headers : {
						"X-Auth-Token" : $.cookie("JSESSIONID")
					},
					data : JSON.stringify(data),
					success : function() {

						showAlert("SUCCESS: User re-activated successfully",
								"alert-success", "errorAlertContainer",
								"errorAlertRow");

						const delay = 3000;
						setTimeout(function() {
							location.reload();
						}, delay);

					}
				});
			} else {
				// some code
			}

		});

function getUsers() {
	const users = {
		"isActive" : false
	};

	$
			.ajax({
				type : 'POST',
				url : 'get-all-user',
				headers : {
					"X-Auth-Token" : $.cookie("JSESSIONID")
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
												},
												{
													"data" : "address"
												},
												{
													"data" : "updationTime",
													"render" : function(data) {
														const dtStart = new Date(data);
														const dtStartWrapper = moment(dtStart);
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
													"defaultContent" : "&nbsp;&nbsp;&nbsp;&nbsp;<a class='delteBtn btn btn-success btn-sm' data-toggle='tooltip' title='Update!'' >Reactivate </a><span> ",
													"orderable" : false,
													"searchable" : false
												}

										]

									});
				}
			});

	if ($.cookie("JSESSIONID") === "") {
		window.location = "/uidai-ms-webportal-ui/";
	}
}