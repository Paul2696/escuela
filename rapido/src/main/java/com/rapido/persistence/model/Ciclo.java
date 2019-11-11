package com.rapido.persistence.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Ciclo implements Comparable{
    private String idCiclo;
    private Date inicio;
    private Date fin;

    public String getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(String idCiclo) {
        this.idCiclo = idCiclo;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Ciclo{" +
                "idCiclo='" + idCiclo + '\'' +
                ", inicio=" + inicio +
                ", fin=" + fin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciclo ciclo = (Ciclo) o;
        return Objects.equals(idCiclo, ciclo.idCiclo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCiclo);
    }

    @Override
    public int compareTo(Object o) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.inicio);
        if(o == null){
            throw new NullPointerException();
        }
        if(!(o instanceof Ciclo)){
            throw new IllegalArgumentException();
        }
        Ciclo ciclo = (Ciclo) o;
        if(ciclo.equals(this)){
            return 0;
        }
        return calendar.before(ciclo.getInicio()) ? -1 : 1;
    }
}
