package com.transparencia.manager;

import java.util.Date;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;

public class DocumentoTransparencia{
	
	private int id;
	private String nombreDocumento;
	private Date date;
	private String url;
	private List<String> personasInvolucradas = new ArrayList<>();

	public void setDate(Date date){
		this.date = date;
	}
	public Date getDate(){
		return date;
	}
	public void setNombreDocumento(String nombreDocumento){
		this.nombreDocumento = nombreDocumento;
	}
	public String getNombreDocumento(){
		return nombreDocumento;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void addPersonaInvolucrada(String personaInvolucrada){
		this.personasInvolucradas.add(personaInvolucrada);
	}
	public List<String> getPersonasInvolucradas(){
		return personasInvolucradas;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return url;
	}

	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if (!(obj instanceof DocumentoTransparencia)){
			return false;
		}
		DocumentoTransparencia d = (DocumentoTransparencia) obj;
		return d.getId() == id;		
	}


	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}