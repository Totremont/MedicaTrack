package com.example.medicatrack.model;

import androidx.annotation.Nullable;

import com.example.medicatrack.model.enums.Frecuencia;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Medicamento
{
    private final int id;
    private String nombre;
    private String color;
    private String forma;
    private float concentracion;

    private Frecuencia frecuencia;

    private String dias;

    private ZonedDateTime hora;

    private String descripcion;

    public Medicamento(int id)
    {
        this.id = id;
    }

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

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public ZonedDateTime getHora() {
        return hora;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicamento that = (Medicamento) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


