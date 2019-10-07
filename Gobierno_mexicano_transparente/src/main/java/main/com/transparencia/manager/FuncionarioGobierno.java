package com.transparencia.manager;

import java.util.Date;
import com.google.gson.Gson;

public class FuncionarioGobierno{
	
	private int id;
	private String password;

	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}

	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}