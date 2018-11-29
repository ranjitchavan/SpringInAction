$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
});
// --for data table--//
$(document).ready(function() {
	if ($.cookie("JSESSIONID") === "") {
		window.location = "/uidai-ms-webportal-ui/";
	}
	$.fn.dataTable.moment( 'MMMM Do YYYY' );

});

$.ajax({
	type : 'GET',
	url : 'get-previous-versions',
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
		$('#previousVersions').DataTable({
			"scrollX" : true,
			"scrollY" : "300px",
			data : data,
			order: [[ 3, "desc" ]],
			columns : [ {
				data : "dpId"
			},{
				"data" : "application_type"
			}, {
				"data" : "osType"
			}, {
				"data" : "current_version"
			}, {
				"data" : "creation_time",
				"render" : function(data) {
					let dtStart = new Date(data);
					let dtStartWrapper = moment(dtStart);
					return dtStartWrapper
							.format('MMMM Do YYYY');
				}
			}, {
				"data" : "userName"
			}, {
				"targets" : -1,
				"data" : null,
				"orderable" : false,
				"searchable" : false,
				"render" : function(data) {
					let action = "<a title='Download file' class='downloadVersion' href='download-version/"
							+ data.version_management_id
							+ "'><i class='fa fa-download'></i></a>";
					if(data.signatureExist){
						action+="<a title='Download signature' class='downloadSignature' href='download-signature/"
							+ data.version_management_id
							+ "'><i class='fa fa-download'></i></a>";
					}else{
						action+="<a title='Download signature' class='downloadSignature' style='color: grey;'><i class='fa fa-download'></i></a>";
					}

					return action;					
				}
			}]
		});
	}

});

