package com.transparencia.db;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.transparencia.manager.*;

public class DataBaseManager{
	private static Connection conexion = null;
	public DataBaseManager(){
		try{
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection(){
		if(conexion == null){
			System.out.println("La conexion no existe, creando conexion");
			try{
				String url = "jdbc:postgresql://localhost/gobierno_transparente_mexicano";
				Properties props = new Properties();
				props.setProperty("user","paul");
				props.setProperty("password","maguss296");
				conexion = DriverManager.getConnection(url, props);
			}
			catch(Exception e){
				throw new RuntimeException(e);
			}
			System.out.println("La conexion se ha creado");
		}
		System.out.println("Obteniendo conexion");
		return conexion;
	}

	public void consulta(){
		Statement query = null;
		String consulta = "SELECT * FROM \"Funcionario_Gobierno\";";
		try{
			query = getConnection().createStatement();
			ResultSet rs = query.executeQuery(consulta);
			System.out.println("Funcionario_Gobierno");
			System.out.println("ID_FNC");
			while(rs.next()){
				System.out.println(rs.getString("ID_FNC"));
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}

		query = null;
		consulta = "SELECT * FROM \"Sancion\";";
		try{
			query = getConnection().createStatement();
			ResultSet rs = query.executeQuery(consulta);
			System.out.println("Sancion");
			System.out.println("ID_SAN");
			while(rs.next()){
				System.out.println(rs.getString("ID_SAN"));
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}

	}

	public List<DocumentoTransparencia> selectAllDocuments(){
		Statement query = null;
		String consulta = "SELECT * FROM \"Documento_Transparencia\";";
		List<DocumentoTransparencia> lista = new ArrayList<>();
		try{
			query = getConnection().createStatement();
			ResultSet rs = query.executeQuery(consulta);
			while(rs.next()){
				DocumentoTransparencia doc = new DocumentoTransparencia();
				doc.setId(rs.getInt("ID_DOC"));
				doc.setDate(rs.getDate("creado_en"));
				doc.setNombreDocumento(rs.getString("nombre_documento"));
				doc.setUrl(rs.getString("Archivo"));
				lista.add(doc);
			}
			return lista;
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			return lista;
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}

	public List<FuncionarioGobierno> selectAllUsers(){
		Statement query = null;
		String consulta = "SELECT * FROM \"Funcionario_Gobierno\";";
		List<FuncionarioGobierno> lista = new ArrayList<>();
		try{
			query = getConnection().createStatement();
			ResultSet rs = query.executeQuery(consulta);
			while(rs.next()){
				FuncionarioGobierno doc = new FuncionarioGobierno();
				doc.setId(rs.getInt("ID_FNC"));
				doc.setPassword(rs.getString("password"));
				lista.add(doc);
			}
			return lista;
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			return lista;
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}

	public void insertReport(Reporte reporte) throws Exception{
		Statement query = null;
		System.out.println(reporte.getInvolucrados());
		System.out.println(reporte.getMensaje());
		String consulta = "INSERT INTO \"Reporte\" (\"ID_REP\", \"Involucrados\", \"Mensaje\") "
		+ "VALUES(" + reporte.getId() + ", '" + reporte.getInvolucrados() + "', '" + reporte.getMensaje() +"');";
		try{
			query = getConnection().createStatement();
			int rs = query.executeUpdate(consulta);
			System.out.println("Se registraron: " + rs);
			if(rs < 1){
				throw new Exception("Ocurrio un error al insertar el reporte");
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}

	public void insertDocumento(DocumentoTransparencia documento) throws Exception{
		Statement query = null;
		String consulta = "INSERT INTO \"Documento_Transparencia\" (\"ID_DOC\", \"creado_en\", \"nombre_documento\", \"Archivo\") "
		+ "VALUES(" + documento.getId() + ", '" + documento.getDate() + "', '" + documento.getNombreDocumento() +"', '" + documento.getUrl() +"');";
		try{
			query = getConnection().createStatement();
			int rs = query.executeUpdate(consulta);
			System.out.println("Se registraron: " + rs);
			if(rs < 1){
				throw new Exception("Ocurrio un error al insertar el documento");
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}


	public List<DocumentoTransparencia> selectAllDocumentsPersonas(){
		Statement query = null;
		String consulta = "SELECT * FROM \"Documento_Transparencia\" AS dt " 
		+ "LEFT JOIN \"Documento_PInvolucrada\" AS dp ON dt.\"ID_DOC\" = dp.\"ID_DocTrns\" "
		+ "LEFT JOIN \"Persona_Involucrada\" AS pi ON dp.\"ID_PeInv\" = pi.\"ID_PINV\";";
		List<DocumentoTransparencia> lista = new ArrayList<>();
		try{
			query = getConnection().createStatement();
			ResultSet rs = query.executeQuery(consulta);
			while(rs.next()){
				DocumentoTransparencia doc = new DocumentoTransparencia();
				doc.setId(rs.getInt("ID_DOC"));
				doc.setDate(rs.getDate("creado_en"));
				doc.setNombreDocumento(rs.getString("nombre_documento"));
				if(lista.contains(doc)){
					int posicion = lista.indexOf(doc);
					doc = lista.remove(posicion);
				}
				doc.addPersonaInvolucrada(rs.getString("nombre"));
				lista.add(doc);
			}
			return lista;
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			return lista;
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}

	public boolean login(FuncionarioGobierno usuario){
		Statement query = null;
		String consulta = "SELECT * FROM \"Funcionario_Gobierno\" WHERE \"ID_FNC\" =" + usuario.getId() + " AND \"password\" = '" + usuario.getPassword() + "';";
		try{
			query = getConnection().createStatement();
			ResultSet rs = query.executeQuery(consulta);
			return rs.next();
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}

	public int getLastId(String tabla) {
		Statement query = null;
		String consulta = "SELECT * FROM \""+ tabla +"\";";
		try{
			query = getConnection().createStatement();
			ResultSet rs = query.executeQuery(consulta);
			int lastId = 0;
			while(rs.next()) {
				lastId++;
			}
			return lastId;
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			return -1;
		}
		finally{
			if(query != null){
				try{
					query.close();
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}

	public void closeConnection(){
		try{
			if (conexion != null){
				conexion.close();
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
	}
}