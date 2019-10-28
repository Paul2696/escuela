$(document).ready(function(){
	ko.applyBindings(DocumentViewModel);
	$.get("http://localhost:8080/Gobierno_mexicano_transparente/resources/document/all", function(data){
		var docs = JSON.parse(data);
		DocumentViewModel.documents(docs);
	});
	$("#documento").submit(doPost);
});

var DocumentViewModel = {
		documents : ko.observableArray(),
		url : ko.observable(),
		nombreDocumento : ko.observable()
	};


function doPost(){
	var documento = {
		url : DocumentViewModel.url(),
		nombreDocumento : DocumentViewModel.nombreDocumento()
	}
	if(!documento.url.match("^(http://|https://)")){
		$("#message").text("La url debe comenzar con http:// o https://");
		return false;
	}
	var documentJson = JSON.stringify(documento);
	$.post("http://localhost:8080/Gobierno_mexicano_transparente/resources/document", documentJson);
}

