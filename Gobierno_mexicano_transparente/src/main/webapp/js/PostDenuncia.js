$(document).ready(function(){
	ko.applyBindings(reportViewModel);
	$("#main-contact-form").submit(doPost);
});

var reportViewModel = {
	mensaje : ko.observable(),
	involucrados : ko.observable()
};

function doPost(){
	var reporte = {
		mensaje : reportViewModel.mensaje(),
		involucrados : reportViewModel.involucrados()
	}
	var reporteJson = JSON.stringify(reporte);
	$.post("http://localhost:8080/Gobierno_mexicano_transparente/resources/funcionario/reporte", reporteJson, function(){
		console.log("Exito");
	});
}

