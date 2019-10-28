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
	$.post("http://localhost:8080/Gobierno_mexicano_transparente/resources/funcionario/login", loginJson, function(){
		window.location.href = "http://localhost:8080/Gobierno_mexicano_transparente/html/Consultar_Documento.html"
	});
	return false;
}

