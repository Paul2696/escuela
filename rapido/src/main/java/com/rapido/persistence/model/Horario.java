package com.rapido.persistence.model;

import java.util.Date;
import java.util.Objects;

public class Horario implements Comparable{
    private int seccionNrc;
    private int ini;
    private int fin;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;

    public int getSeccionNrc() {
        return seccionNrc;
    }

    public void setSeccionNrc(int seccionNrc) {
        this.seccionNrc = seccionNrc;
    }

    public int getIni() {
        return ini;
    }

    public void setIni(int ini) {
        this.ini = ini;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return Objects.equals(seccionNrc, horario.seccionNrc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seccionNrc);
    }

    @Override
    public String toString() {
        return "Horario{" +
                "seccionNrc='" + seccionNrc + '\'' +
                ", ini=" + ini +
                ", fin=" + fin +
                ", lunes=" + lunes +
                ", martes=" + martes +
                ", miercoles=" + miercoles +
                ", jueves=" + jueves +
                ", viernes=" + viernes +
                ", sabado=" + sabado +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
