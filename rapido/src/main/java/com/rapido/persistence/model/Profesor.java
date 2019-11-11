package com.rapido.persistence.model;

import java.util.Objects;

public class Profesor implements Comparable{
    private int idProfesor;
    private String nombre;
    private String materiaClave;
    private String materiaIdCiclo;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMateriaClave() {
        return materiaClave;
    }

    public void setMateriaClave(String materiaClave) {
        this.materiaClave = materiaClave;
    }

    public String getMateriaIdCiclo() {
        return materiaIdCiclo;
    }

    public void setMateriaIdCiclo(String materiaIdCiclo) {
        this.materiaIdCiclo = materiaIdCiclo;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", nombre='" + nombre + '\'' +
                ", materiaClave='" + materiaClave + '\'' +
                ", materiaIdCiclo='" + materiaIdCiclo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profesor profesor = (Profesor) o;
        return idProfesor == profesor.idProfesor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProfesor);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
