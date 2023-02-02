package com.example.medicatrack.repo.persist.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MedicamentoEntity
{
    @PrimaryKey(autoGenerate = true) private int id;
    private String nombre;
    private String color;
    private String forma;
    private float concentracion;
    private String frecuencia;

    public void setId(int id){this.id = id;}

    public int getId()
    {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public float getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(float concentracion) {
        this.concentracion = concentracion;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
}
