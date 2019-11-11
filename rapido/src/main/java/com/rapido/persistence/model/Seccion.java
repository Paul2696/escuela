package com.rapido.persistence.model;

import java.util.Objects;

public class Seccion implements Comparable{
    private int nrc;
    private String seccion;
    private int cupo;
    private int ocup;
    private int disp;
    private String edif;
    private String aula;
    private int profesorId;
    private String profesorMateriaClave;
    private String profesorCiclo;
    private String status;

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getOcup() {
        return ocup;
    }

    public void setOcup(int ocup) {
        this.ocup = ocup;
    }

    public int getDisp() {
        return disp;
    }

    public void setDisp(int disp) {
        this.disp = disp;
    }

    public String getEdif() {
        return edif;
    }

    public void setEdif(String edif) {
        this.edif = edif;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
    }

    public String getProfesorMateriaClave() {
        return profesorMateriaClave;
    }

    public void setProfesorMateriaClave(String profesorMateriaClave) {
        this.profesorMateriaClave = profesorMateriaClave;
    }

    public String getProfesorCiclo() {
        return profesorCiclo;
    }

    public void setProfesorCiclo(String profesorCiclo) {
        this.profesorCiclo = profesorCiclo;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seccion seccion = (Seccion) o;
        return Objects.equals(nrc, seccion.nrc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrc);
    }

    @Override
    public String toString() {
        return "Seccion{" +
                "nrc='" + nrc + '\'' +
                ", seccion='" + seccion + '\'' +
                ", cupo=" + cupo +
                ", ocup=" + ocup +
                ", disp=" + disp +
                ", edif='" + edif + '\'' +
                ", aula='" + aula + '\'' +
                ", profesorId=" + profesorId +
                ", profesorMateriaClave='" + profesorMateriaClave + '\'' +
                ", profesorCiclo='" + profesorCiclo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
