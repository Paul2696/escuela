package com.rapido.rest;

import com.rapido.persistence.dao.DBManager;
import com.rapido.persistence.dao.SQLScripts;
import com.rapido.persistence.model.*;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/oferta")
public class OfertaEndpoint {
    private DBManager dbManager = new DBManager();
    private int nrc = 0;
    private String status = null;
    private String departamento = null;
    private String area = null;
    private String clave = null;
    private String materia = null;
    private String hrsTeoria = null;
    private String hrsLab = null;
    private String seccion = null;
    private int creditos = 0;
    private int cupo = 0;
    private int ocup = 0;
    private int disp = 0;
    private int ini = 0;
    private int fin = 0;
    private boolean lunes = false;
    private boolean martes = false;
    private boolean miercoles = false;
    private boolean jueves = false;
    private boolean viernes = false;
    private boolean sabado = false;
    private String edif = null;
    private String aula = null;
    private String profesor = null;
    private String profesorApellido = null;
    private String profesorNombre = null;
    private int profesorId = 0;
    private Date fechaInicio = null;
    private Date fechaFin = null;
    private String nivel = null;
    private String ciclo = null;
    private int idAuto = 0;
    private Ciclo cicloObj;
    private Horario horarioObj;
    private Materia materiaObj;
    private Profesor profesorObj;
    private Seccion seccionObj;
    private Set<Ciclo> ciclos = new HashSet<>();
    private Set<Horario> horarios = new HashSet<>();
    private Set<Materia> materias = new HashSet<>();
    private Set<Profesor> profesors = new HashSet<>();
    private Set<Seccion> seccions = new HashSet<>();


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getOfertaFile(@Multipart(value = "file", required = false)Attachment uploadedInputStream){
        try {
            InputStream stream = uploadedInputStream.getDataHandler().getInputStream();
            byte[] buff = IOUtils.toByteArray(stream);
            String oferta = new String(buff);
            StringReader sr = new StringReader(oferta);
            BufferedReader br = new BufferedReader(sr);
            String line = null;
            int counter = 0;
            while((line = br.readLine()) != null){
                String[] attribs = line.trim().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                for(String attr : attribs){
                    setAtributes(counter, attr);
                    counter++;
                }
                setObjs();
                ciclos.add(cicloObj);
                horarios.add(horarioObj);
                materias.add(materiaObj);
                profesors.add(profesorObj);
                seccions.add(seccionObj);
                counter = 0;
            }
            addToDb();
            System.out.println(ciclos.size());
            System.out.println(horarios.size());
            System.out.println(materias.size());
            System.out.println(profesors.size());
            System.out.println(seccions.size());
            System.out.println("Ciclo: ");
            System.out.println(dbManager.consultCicloTable(SQLScripts.getTable("Ciclo")));
            System.out.println("Materia: ");
            System.out.println(dbManager.consultCicloTable(SQLScripts.getTable("Materia")));
            System.out.println("Profesores: ");
            System.out.println(dbManager.consultCicloTable(SQLScripts.getTable("Profesores")));
            System.out.println("Seccion: ");
            System.out.println(dbManager.consultCicloTable(SQLScripts.getTable("Seccion")));
            System.out.println("Horario: ");
            System.out.println(dbManager.consultCicloTable(SQLScripts.getTable("Horario")));
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(500).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataBase(){
        System.out.println(dbManager.consultCicloTable(SQLScripts.getTable("Ciclo")));
        System.out.println(dbManager.consultMateriaTable(SQLScripts.getTable("Materia")));
        System.out.println(dbManager.consultProfesorTable(SQLScripts.getTable("Profesores")));
        System.out.println(dbManager.consultSeccionTable(SQLScripts.getTable("Seccion")));
        System.out.println(dbManager.consultHorarioTable(SQLScripts.getTable("Horario")));
        return Response.ok().build();
    }

    public void setAtributes(int counter, String data) throws ParseException {
        switch(counter){
            case 0:
                nrc = Integer.parseInt(data);
                break;
            case 1:
                status = data;
                break;
            case 2:
                departamento = data;
                break;
            case 3:
                area = data;
                break;
            case 4:
                clave = data;
                break;
            case 5:
                materia = data;
                break;
            case 6:
                hrsTeoria = data;
                break;
            case 7:
                hrsLab = data;
                break;
            case 8:
                seccion = data;
                break;
            case 9:
                if(data == null || data.equals("")){
                    creditos = 0;
                    break;
                }
                try{
                    creditos = Integer.parseInt(data);
                }catch(NumberFormatException nfe){
                    System.out.println(nrc+","+status + "," + departamento + "," + area + "," +clave +  "," +materia +  ","+hrsTeoria + ","+hrsLab + ","+seccion);

                }

                break;
            case 10:
                if(data == null || data.equals("")){
                    cupo = 0;
                    break;
                }
                cupo = Integer.parseInt(data);
                break;
            case 11:
                if(data == null || data.equals("")){
                    ocup = 0;
                    break;
                }
                ocup = Integer.parseInt(data);
                break;
            case 12:
                if(data == null || data.equals("")){
                    disp = 0;
                    break;
                }
                disp = Integer.parseInt(data);
                break;
            case 13:
                if(data == null || data.equals("")){
                    ini = 0;
                    break;
                }
                ini = Integer.parseInt(data);
                break;
            case 14:
                if(data == null || data.equals("")){
                    fin = 0;
                    break;
                }
                fin = Integer.parseInt(data);
                break;
            case 15:
                if(data.equals("")){
                    lunes = false;
                }
                else{
                    lunes = true;
                }
                break;
            case 16:
                if(data.equals("")){
                    martes = false;
                }
                else{
                    martes = true;
                }
                break;
            case 17:
                if(data.equals("")){
                    miercoles = false;
                }
                else{
                    miercoles = true;
                }
                break;
            case 18:
                if(data.equals("")){
                    jueves = false;
                }
                else{
                    jueves = true;
                }
                break;
            case 19:
                if(data.equals("")){
                    viernes = false;
                }
                else{
                    viernes = true;
                }
                break;
            case 20:
                if(data.equals("")){
                    sabado = false;
                }
                else{
                    sabado = true;
                }
                break;
            case 21:
                edif = data;
                break;
            case 22:
                aula = data;
                break;
            case 23:
                getTeacherAttrs(data);
                break;

            case 24:
                if(data.equals("")){
                    fechaInicio = null;
                    break;
                }
                fechaInicio = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                break;
            case 25:
                if(data.equals("")){
                    fechaFin = null;
                    break;
                }
                fechaFin = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                break;
            case 26:
                nivel = data;
                break;
        }
    }

    public void getTeacherAttrs(String data){
        String[] attrs = data.split("[(]");
        profesorNombre = attrs[0].trim();
        try{
            profesorId = Integer.parseInt(attrs[1].substring(0, attrs[1].length() - 2));
        }catch(Exception e){
            profesorNombre = null;
            profesorId = idAuto;
            idAuto++;
        }
    }

    public void setCiclo(Date fechaInicio){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String inicio;
        if(fechaInicio == null){
            inicio = "01/08/2016";
        }
        else{
            inicio = formatter.format(fechaInicio);
        }
        StringBuilder idCiclo = new StringBuilder();
        idCiclo.append(inicio.substring(6));
        if(Integer.parseInt(inicio.substring(3, 5)) > 7){
            idCiclo.append("B");
        }
        else{
            idCiclo.append("A");
        }
        ciclo = idCiclo.toString();
    }

    public void setObjs(){
        setCiclo(fechaInicio);
        cicloObj = new Ciclo();
        horarioObj = new Horario();
        materiaObj = new Materia();
        profesorObj = new Profesor();
        seccionObj = new Seccion();
        //Ciclo
        cicloObj.setIdCiclo(ciclo);
        cicloObj.setInicio(fechaInicio);
        cicloObj.setFin(fechaFin);
        //Horario
        horarioObj.setSabado(sabado);
        horarioObj.setViernes(viernes);
        horarioObj.setJueves(jueves);
        horarioObj.setMiercoles(miercoles);
        horarioObj.setMartes(martes);
        horarioObj.setLunes(lunes);
        horarioObj.setSeccionNrc(nrc);
        horarioObj.setFin(fin);
        horarioObj.setIni(ini);
        //Materia
        materiaObj.setCreditos(creditos);
        materiaObj.setNivel(nivel);
        materiaObj.setCiclo(ciclo);
        materiaObj.setHrsLab(hrsLab);
        materiaObj.setHrsTeoria(hrsTeoria);
        materiaObj.setArea(area);
        materiaObj.setDepartamento(departamento);
        materiaObj.setNombre(materia);
        materiaObj.setClave(clave);
        //Profesor
        profesorObj.setMateriaIdCiclo(ciclo);
        profesorObj.setMateriaClave(clave);
        profesorObj.setNombre(profesorNombre);
        profesorObj.setIdProfesor(profesorId);
        //Seccion
        seccionObj.setSeccion(seccion);
        seccionObj.setProfesorCiclo(ciclo);
        seccionObj.setProfesorMateriaClave(clave);
        seccionObj.setProfesorId(profesorId);
        seccionObj.setAula(aula);
        seccionObj.setEdif(edif);
        seccionObj.setDisp(disp);
        seccionObj.setOcup(ocup);
        seccionObj.setCupo(cupo);
        seccionObj.setNrc(nrc);
        seccionObj.setStatus(status);
    }

    public void addToDb(){
        for(Ciclo ciclo : ciclos){
            dbManager.insertIntoDB(SQLScripts.insertIntoCiclo(ciclo));
        }
        for(Materia materia : materias){
            dbManager.insertIntoDB(SQLScripts.insertIntoMateria(materia));
        }
        for(Profesor profesor : profesors){
            dbManager.insertIntoDB(SQLScripts.insertIntoProfesor(profesor));
        }
        for(Seccion seccion : seccions){
            dbManager.insertIntoDB(SQLScripts.insertIntoSeccion(seccion));
        }
        for(Horario horario : horarios){
            dbManager.insertIntoDB(SQLScripts.insertIntoHorario(horario));
        }

    }

}
