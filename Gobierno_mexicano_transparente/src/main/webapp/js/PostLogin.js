$(document).ready(function(){
	ko.applyBindings(logInViewModel);
	$("#main-contact-form").submit(doPost);
});

var logInViewModel = {
	ID_FUNC : ko.observable(),
	password : ko.observable()
};

function doPost(){
	var logIn = {
		"id" : logInViewModel.ID_FUNC(),
		"password" : logInViewModel.password()
	}
	var loginJson = JSON.stringify(logIn);
	$.post("http://localhost:8080/account/login", loginJson, function(){
		window.location.href = "http://localhost:8080/html/log_in.html"
	});
	return false;
}

