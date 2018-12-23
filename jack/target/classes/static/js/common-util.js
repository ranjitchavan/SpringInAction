/**
 * 
 * @param alertMessage : Message to diplay on UI
 * @param alertType : type of alert; success/failure/info
 * @param alertContainer : Container for alert message
 * @param alertRow : Row containing alertContainer
 */
function showAlert (alertMessage,alertType,alertContainer,alertRow){
	const html = "<div class='alert " + alertType + " alert-dismissable'>" +
			"<span>" +alertMessage+"</span>" +
			"<a href='#' class='close' data-dismiss='alert' aria-label='close'>×</a>" +
			"</div>";
	$("#"+alertContainer).html(html);	
	$("#"+alertRow).show();
}


function checkFields(form) {
	const checks_radios = form.find(':checkbox, :radio'),
        inputs = form.find(':input').not(checks_radios).not('[type="submit"],[type="button"],[type="reset"]'),
        checked = checks_radios.filter(':checked'),
        filled = inputs.filter(function(){
            return $.trim($(this).val()).length > 0;
        });

    if(checked.length + filled.length === 0) {
        return false;
    }

    return true;
}





