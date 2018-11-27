$('#from').datepicker({
	dateFormat: "dd-mm-yy",
});
$('#to').datepicker({
	dateFormat: "dd-mm-yy",
	defaultDate : "+1w",
	beforeShow : function() {
		$(this).datepicker('option', 'minDate', $('#from').val());
		if ($('#from').val() === '')
			$(this).datepicker('option', 'minDate', 0);
	}
});