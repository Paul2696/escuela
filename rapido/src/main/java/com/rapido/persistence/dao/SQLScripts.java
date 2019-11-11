package com.rapido.persistence.dao;

import com.rapido.persistence.model.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLScripts {
    public static final String CREATE_CICLO_TABLE =
            "CREATE TABLE IF NOT EXISTS Ciclo (\n" +
            "  idCiclo VARCHAR(45) NOT NULL,\n" +
            "  FECHA_INICIO DATE NULL,\n" +
            "  FECHA_FIN DATE NULL,\n" +
            "  PRIMARY KEY (idCiclo));";

    public static final String CREATE_MATERIA_TABLE =
            "CREATE TABLE IF NOT EXISTS Materia (\n" +
            "  Clave VARCHAR(45) NOT NULL,\n" +
            "  Nombre VARCHAR(45) NULL,\n" +
            "  Departamento VARCHAR(45) NULL,\n" +
            "  Area VARCHAR(45) NULL,\n" +
            "  HRS_TEORIA VARCHAR(45) NULL,\n" +
            "  HRS_LAB VARCHAR(45) NULL,\n" +
            "  Ciclo_idCiclo VARCHAR(45) NOT NULL,\n" +
            "  Nivel VARCHAR(45) NULL,\n" +
            "  Creditos INT NULL,\n" +
            "  PRIMARY KEY (Clave, Ciclo_idCiclo),\n" +
            "    FOREIGN KEY (Ciclo_idCiclo)\n" +
            "    REFERENCES Ciclo (idCiclo)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)";

    public static final String CREATE_PROFESORES_TABLE =
            "CREATE TABLE IF NOT EXISTS Profesores (\n" +
            "  idProfesores INT NOT NULL,\n" +
            "  Nombre VARCHAR(45) NULL,\n" +
            "  Materia_Clave VARCHAR(45) NOT NULL,\n" +
            "  Materia_Ciclo_idCiclo VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (idProfesores, Materia_Clave, Materia_Ciclo_idCiclo),\n" +
            "    FOREIGN KEY (Materia_Clave , Materia_Ciclo_idCiclo)\n" +
            "    REFERENCES Materia (Clave , Ciclo_idCiclo)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    public static final String CREATE_SECCION_TABLE =
            "CREATE TABLE IF NOT EXISTS Seccion (\n" +
            "  NRC INT NOT NULL,\n" +
            "  Seccion VARCHAR(45) NULL,\n" +
            "  Cupo INT NULL,\n" +
            "  Ocup INT NULL,\n" +
            "  Disp INT NULL,\n" +
            "  Edif VARCHAR(45) NULL,\n" +
            "  Aula VARCHAR(45) NULL,\n" +
            "  Profesores_idProfesores INT NOT NULL,\n" +
            "  Profesores_Materia_Clave VARCHAR(45) NOT NULL,\n" +
            "  Profesores_Materia_Ciclo_idCiclo VARCHAR(45) NOT NULL,\n" +
            "  Status VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (NRC, Profesores_idProfesores, Profesores_Materia_Clave, Profesores_Materia_Ciclo_idCiclo),\n" +
            "    FOREIGN KEY (Profesores_idProfesores , Profesores_Materia_Clave , Profesores_Materia_Ciclo_idCiclo)\n" +
            "    REFERENCES Profesores (idProfesores , Materia_Clave , Materia_Ciclo_idCiclo)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    public static final String CREATE_HORARIO_TABLE =
            "CREATE TABLE IF NOT EXISTS Horario (\n" +
            "  Seccion_NRC INT NOT NULL,\n" +
            "  Ini INT NULL,\n" +
            "  Fin INT NULL,\n" +
            "  L BOOLEAN NULL,\n" +
            "  M BOOLEAN NULL,\n" +
            "  I BOOLEAN NULL,\n" +
            "  J BOOLEAN NULL,\n" +
            "  V BOOLEAN NULL,\n" +
            "  S BOOLEAN NULL,\n" +
            "  PRIMARY KEY (Seccion_NRC),\n" +
            "  CONSTRAINT fk_Horario_Seccion1\n" +
            "    FOREIGN KEY (Seccion_NRC)\n" +
            "    REFERENCES Seccion (NRC)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    public static String getTable(String table){
        String consult = "SELECT * FROM " + table;
        return consult;
    }

    public static String insertIntoCiclo(Ciclo ciclo){
        String insertCiclo =  "INSERT INTO Ciclo(idCiclo, FECHA_INICIO, FECHA_FIN) " +
                "VALUES ('" + ciclo.getIdCiclo() + "','" + ciclo.getInicio() + "','" + ciclo.getFin() + "')";
        return insertCiclo;
    }

    public static String insertIntoMateria(Materia materia){
        String insertMateria = "INSERT INTO Materia(Clave, Nombre, Departamento, " +
                "Area, HRS_TEORIA, HRS_LAB, Ciclo_idCiclo, Nivel, Creditos)" +
                "VALUES ('" + materia.getClave() + "', '" + materia.getNombre() + "', '" + materia.getDepartamento() + "', '"
         + materia.getArea() + "', '" + materia.getHrsTeoria() + "', '" + materia.getHrsLab() + "', '" + materia.getCiclo() + "', " +
                "'" + materia.getNivel() + "', '" + materia.getCreditos() + "')";
        return insertMateria;
    }

    public static String insertIntoProfesor(Profesor profesor){
        String insertProfesor = "INSERT INTO Profesores(idProfesores, Nombre, Materia_Clave, Materia_Ciclo_idCiclo)" +
                "VALUES ('" + profesor.getIdProfesor() + "', '" + profesor.getNombre() + "', '" + profesor.getMateriaClave() + "', " +
                "'" + profesor.getMateriaIdCiclo() + "')";
        return insertProfesor;
    }

    public static String insertIntoSeccion(Seccion seccion){
        String insertSeccion = "INSERT INTO Seccion(NRC, Seccion, Cupo, Ocup, Disp, Edif, Aula, Profesores_idProfesores, " +
                "Profesores_Materia_Clave, Profesores_Materia_Ciclo_idCiclo, Status)" +
                "VALUES('" + seccion.getNrc() + "', '" + seccion.getSeccion() + "', '" + seccion.getCupo() + "', " +
                "'" + seccion.getOcup() + "', '" + seccion.getDisp() + "', " +
                "'" + seccion.getEdif() + "', '" + seccion.getAula() + "', '" + seccion.getProfesorId() + "', " +
                "'" + seccion.getProfesorMateriaClave() + "', " +  "'" + seccion.getProfesorCiclo() + "', '" + seccion.getStatus() + "')";
        return insertSeccion;
    }

    public static String insertIntoHorario(Horario horario){
        String insertHorario = "INSERT INTO Horario(Seccion_NRC, Ini, Fin, L, M, I, J, V, S)" +
                "VALUES('" + horario.getSeccionNrc() + "', '" + horario.getIni() + "', '" + horario.getFin() + "', " +
                "'" + horario.isLunes() + "', '" + horario.isMartes() + "', " +
                "'" + horario.isMiercoles() + "', '" + horario.isJueves() + "', '" + horario.isViernes() + "', " +
                "'" + horario.isSabado() + "')";
        return insertHorario;
    }
}
