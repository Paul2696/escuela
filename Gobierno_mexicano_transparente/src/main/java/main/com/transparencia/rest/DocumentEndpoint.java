package com.transparencia.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.transparencia.manager.*;
import com.google.gson.Gson;

@Path("/document")
public class DocumentEndpoint{
	private DocumentManager docManager = new DocumentManager();

	@Path("/all")
	@GET
	public String getAllDocuments(){
		Gson gson = new Gson();
		return gson.toJson(docManager.getAllDocuments());
	}

	@Path("/sanciones")
	@GET
	public String getAllDocumentsPersonas(){
		Gson gson = new Gson();
		return gson.toJson(docManager.getAllDocumentsPersonas());
	}

	@POST
	public Response guardarDocumento(String documentJson){
		Gson gson = new Gson();
		DocumentoTransparencia documento = gson.fromJson(documentJson, DocumentoTransparencia.class);
		try{
			docManager.guardarDocumento(documento);
			return Response.ok().status(200).build();
		}
		catch(Exception e){
			return Response.serverError().entity(e.getMessage()).status(500).build();
		}
	}
}