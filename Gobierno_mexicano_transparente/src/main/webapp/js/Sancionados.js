$(document).ready(function(){
	ko.applyBindings(DocumentViewModel);
	$.get("http://localhost:8080/Gobierno_mexicano_transparente/resources/document/sanciones", function(data){
		var docs = JSON.parse(data);
		DocumentViewModel.documents(docs);
	});
});

var DocumentViewModel = {
		documents : ko.observableArray()
	};



