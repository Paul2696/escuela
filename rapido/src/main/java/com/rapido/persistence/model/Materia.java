package com.rapido.persistence.model;

import java.util.Objects;

public class Materia implements Comparable{
    private String clave;
    private String nombre;
    private String departamento;
    private String area;
    private String hrsTeoria;
    private String hrsLab;
    private String ciclo;
    private String nivel;
    private int creditos;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHrsTeoria() {
        return hrsTeoria;
    }

    public void setHrsTeoria(String hrsTeoria) {
        this.hrsTeoria = hrsTeoria;
    }

    public String getHrsLab() {
        return hrsLab;
    }

    public void setHrsLab(String hrsLab) {
        this.hrsLab = hrsLab;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                ", area='" + area + '\'' +
                ", hrsTeoria='" + hrsTeoria + '\'' +
                ", hrsLab='" + hrsLab + '\'' +
                ", ciclo='" + ciclo + '\'' +
                ", nivel='" + nivel + '\'' +
                ", creditos=" + creditos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia materia = (Materia) o;
        return Objects.equals(clave, materia.clave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clave);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
