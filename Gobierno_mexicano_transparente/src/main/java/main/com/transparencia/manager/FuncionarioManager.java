package com.transparencia.manager;

import java.util.*;
import com.transparencia.db.*;

public class FuncionarioManager{

	private DataBaseManager db = new DataBaseManager();

	public List<FuncionarioGobierno> getAllUsers(){
		return db.selectAllUsers();
	}

	public boolean login(FuncionarioGobierno usuario){
		return db.login(usuario);
	}

	public void levantarReporte(Reporte reporte) throws Exception{
		db.insertReport(reporte);
	}

	public int getLastId() {
		return db.getLastId("Reporte");
	}
}