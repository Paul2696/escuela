$(document).ready(function(){
	ko.applyBindings(documentViewModel);
	$("#documento").submit(doPost);
});

var documentViewModel = {
	url : ko.observable(),
	nombreDocumento : ko.observable()
};

function doPost(){
	var documento = {
		"url" : documentViewModel.url(),
		"nombreDocumento" : documentViewModel.nombreDocumento()
	}
	var documentJson = JSON.stringify(documento);
	$.post("http://localhost:8080/Gobierno_mexicano_transparente/resources/document", documentJson);
}

