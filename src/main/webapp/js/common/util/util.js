function sign_in(a) {
	if (a.login == "success") {
		if (a.details == "freenom") {
			window.location = "https://register.freenom.com/cgi-bin/login02"
		} else {
			if (a.details == "domainshare") {
				window.location = "http://my.domainshare.tk"
			}
		}
	} else {
		$("#" + a.details).show() // sign_in({"details":"invalid","login":"fail"});
	}
}
function send_password(a) {
	$("#" + a.result).show()
}
$(function() {
	$("#sign_in")
			.click(
					function() {
						$(".login_err").hide();
						$(".send_email_result").hide();
						var a = $("#fldemail").val();
						var b = $("#fldpassword").val();
						var c = "https://register.freenom.com/connect/login.json?email="
								+ a + "&password=" + b;
						$.getScript(c)
					});
	$("#send_email")
			.click(
					function() {
						$(".login_err").hide();
						$(".send_email_result").hide();
						var a = $("#fldemail").val();
						var b = "https://register.freenom.com/cgi-bin/emailpasswdjson?email="
								+ a;
						$.getScript(b)
					});
	$("#send_signup")
			.click(
					function() {
						$(".login_err").hide();
						$(".send_email_result").hide();
						var a = $("#fldemail").val();
						var b = "https://register.freenom.com/cgi-bin/emailnewregjson?email="
								+ a;
						$.getScript(b)
					})
});