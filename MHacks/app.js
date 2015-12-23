var hide1 = function() {
	$(".response").hide();
};


var messageResponse = function() {
	$(".input1").click(function() {
		$(".response").show();
	});
};


$(document).ready(hide1);
$(document).ready(messageResponse);