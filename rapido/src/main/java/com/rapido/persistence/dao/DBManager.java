package com.rapido.persistence.dao;

import com.rapido.persistence.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBManager{
    private static final String CREATE_SCORE_TABLE = "CREATE TABLE IF NOT EXISTS scoreboard (NAME VARCHAR(45) NULL,SCORE int NULL)";
    private static Connection conn;

    public DBManager() {
    }

    public static Connection getConnection() {
        String url = "jdbc:sqlite:Oferta.db";
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(url);
                if (conn != null) {
                    createTable(SQLScripts.CREATE_CICLO_TABLE);
                    createTable(SQLScripts.CREATE_MATERIA_TABLE);
                    createTable(SQLScripts.CREATE_PROFESORES_TABLE);
                    createTable(SQLScripts.CREATE_SECCION_TABLE);
                    createTable(SQLScripts.CREATE_HORARIO_TABLE);
                    return conn;
                }
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }

        return conn;
    }

    public static void createTable(String table) {
        try {
            PreparedStatement statement = conn.prepareStatement(table);
            statement.executeUpdate();
        } catch (SQLException var1) {
            var1.printStackTrace();
        }
    }

    public void insertIntoDB(String sqlScript) {
        getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sqlScript);
            statement.executeUpdate();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
    }

    public List consultCicloTable(String consult){
        getConnection();
        List ciclos = new ArrayList();
        try {
            PreparedStatement statement = conn.prepareStatement(consult);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Ciclo ciclo = new Ciclo();
                ciclo.setIdCiclo(rs.getString("idCiclo"));
                ciclo.setInicio(new SimpleDateFormat("EEE, MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH).parse(rs.getString("FECHA_INICIO")));
                ciclo.setFin(new SimpleDateFormat("EEE, MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH).parse(rs.getString("FECHA_FIN")));
                ciclos.add(ciclo);
            }
        } catch (SQLException | ParseException var6) {
            var6.printStackTrace();
        }

        return ciclos;
    }

    public List consultMateriaTable(String consult){
        getConnection();
        List materias = new ArrayList();
        try {
            PreparedStatement statement = conn.prepareStatement(consult);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Materia materia = new Materia();
                materia.setClave(rs.getString("Clave"));
                materia.setNombre(rs.getString("Nombre"));
                materia.setDepartamento(rs.getString("Departamento"));
                materia.setArea(rs.getString("Area"));
                materia.setHrsTeoria(rs.getString("HRS_TEORIA"));
                materia.setHrsLab(rs.getString("HRS_LAB"));
                materia.setCiclo(rs.getString("Ciclo_idCiclo"));
                materia.setNivel(rs.getString("Nivel"));
                materia.setCreditos(rs.getInt("Creditos"));
                materias.add(materia);
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return materias;
    }

    public List consultProfesorTable(String consult){
        getConnection();
        List profesores = new ArrayList();
        try {
            PreparedStatement statement = conn.prepareStatement(consult);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Profesor profesor = new Profesor();
                profesor.setIdProfesor(rs.getInt("idProfesores"));
                profesor.setNombre(rs.getString("Nombre"));
                profesor.setMateriaClave(rs.getString("Materia_Clave"));
                profesor.setMateriaIdCiclo(rs.getString("Materia_Ciclo_idCiclo"));
                profesores.add(profesor);
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return profesores;
    }

    public List consultSeccionTable(String consult){
        getConnection();
        List secciones = new ArrayList();
        try {
            PreparedStatement statement = conn.prepareStatement(consult);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Seccion seccion = new Seccion();
                seccion.setNrc(rs.getInt("NRC"));
                seccion.setSeccion(rs.getString("Seccion"));
                seccion.setCupo(rs.getInt("Cupo"));
                seccion.setOcup(rs.getInt("Ocup"));
                seccion.setDisp(rs.getInt("Disp"));
                seccion.setEdif(rs.getString("Edif"));
                seccion.setAula(rs.getString("Aula"));
                seccion.setProfesorId(rs.getInt("Profesores_idProfesores"));
                seccion.setProfesorMateriaClave(rs.getString("Profesores_Materia_Clave"));
                seccion.setProfesorCiclo(rs.getString("Profesores_Materia_Ciclo_idCiclo"));
                seccion.setSeccion(rs.getString("Status"));
                secciones.add(seccion);
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return secciones;
    }

    public List consultHorarioTable(String consult){
        getConnection();
        List horarios = new ArrayList();
        try {
            PreparedStatement statement = conn.prepareStatement(consult);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Horario horario = new Horario();
                horario.setSeccionNrc(rs.getInt("Seccion_NRC"));
                horario.setIni(rs.getInt("Ini"));
                horario.setFin(rs.getInt("Fin"));
                horario.setLunes(rs.getBoolean("L"));
                horario.setMartes(rs.getBoolean("M"));
                horario.setMiercoles(rs.getBoolean("I"));
                horario.setJueves(rs.getBoolean("J"));
                horario.setViernes(rs.getBoolean("V"));
                horario.setSabado(rs.getBoolean("S"));
                horarios.add(horario);
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return horarios;
    }
}
