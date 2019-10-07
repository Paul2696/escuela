package com.transparencia.manager;

import java.util.Date;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;

public class Reporte{
	private int id;
	private String mensaje;
	private String involucrados;

	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void setMensaje(String mensaje){
		this.mensaje = mensaje;
	}
	public String getMensaje(){
		return mensaje;
	}
	public void setInvolucrados(String involucrados){
		this.involucrados = involucrados;
	}
	public String getInvolucrados(){
		return this.involucrados;
	}

	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}