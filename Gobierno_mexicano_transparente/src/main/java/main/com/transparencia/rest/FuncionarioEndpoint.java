package com.transparencia.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.transparencia.manager.*;
import com.google.gson.Gson;

@Path("/funcionario")
public class FuncionarioEndpoint{
	private FuncionarioManager userManager = new FuncionarioManager();

	@Path("/login")
	@POST
	public Response login(String credenciales){
		Gson gson = new Gson();
		System.out.println(credenciales);
		FuncionarioGobierno usuario = gson.fromJson(credenciales, FuncionarioGobierno.class);
		System.out.println(usuario.getId());
		System.out.println(usuario.getPassword());
		if(userManager.login(usuario)){
			return Response.ok().status(200).build();
		}
		else{
			return Response.serverError().status(403).build();
		}
	}

	@Path("/reporte")
	@POST
	public Response levantarReporte(String reporteJson){
		Gson gson = new Gson();
		Reporte reporte = gson.fromJson(reporteJson, Reporte.class);
		int id = userManager.getLastId();
		id++;
		reporte.setId(id);
		try{
			userManager.levantarReporte(reporte);
			return Response.ok().status(200).build();
		}
		catch(Exception e){
			return Response.serverError().entity(e.getMessage()).status(500).build();
		}

	}
	

}