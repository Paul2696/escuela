package com.transparencia.manager;

import java.util.*;
import com.transparencia.db.*;

public class DocumentManager{

	private DataBaseManager db = new DataBaseManager();

	public List<DocumentoTransparencia> getAllDocuments(){
		return db.selectAllDocuments();
	}

	public List<DocumentoTransparencia> getAllDocumentsPersonas(){
		return db.selectAllDocumentsPersonas();
	}

	public void guardarDocumento(DocumentoTransparencia documento) throws Exception{
		documento.setDate(new Date());
		int id = getLastId();
		id++;
		documento.setId(id);
		db.insertDocumento(documento);
	}

	public int getLastId() {
		return db.getLastId("Documento_Transparencia");
	}

}