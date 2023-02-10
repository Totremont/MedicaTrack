package com.example.medicatrack.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.medicatrack.model.enums.Color;
import com.example.medicatrack.model.enums.Forma;
import com.example.medicatrack.model.enums.Unidad;
import com.example.medicatrack.model.enums.Frecuencia;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Medicamento implements Parcelable
{
    private final int id;
    private String nombre;
    private Color color;
    private Forma forma;
    private float concentracion;
    private Unidad unidad;
    private Frecuencia frecuencia;
    private String dias;
    private ZonedDateTime fechaInicio;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
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

    public ZonedDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public ZonedDateTime getHora() {
        return hora;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.color.name());
        dest.writeString(this.forma.name());
        dest.writeFloat(this.concentracion);
        dest.writeString(this.unidad.name());
        dest.writeString(this.frecuencia.name());
        dest.writeString(this.dias);
        dest.writeLong(this.fechaInicio.toInstant().getEpochSecond());
        dest.writeLong(this.hora.toInstant().getEpochSecond());
        dest.writeString(this.descripcion);
    }

    private Medicamento(Parcel in){
        this.id = in.readInt();
        this.nombre = in.readString();
        this.color = Color.valueOf(in.readString());
        this.forma = Forma.valueOf(in.readString());
        this.concentracion = in.readFloat();
        this.unidad = Unidad.valueOf(in.readString());
        this.frecuencia = Frecuencia.valueOf(in.readString());
        this.dias = in.readString();
        this.fechaInicio = ZonedDateTime.ofInstant(Instant.ofEpochSecond(in.readLong()), ZoneId.of("America/Argentina/Buenos_Aires"));
        this.hora = ZonedDateTime.ofInstant(Instant.ofEpochSecond(in.readLong()), ZoneId.of("America/Argentina/Buenos_Aires"));
        this.descripcion = in.readString();
    }

    public static final Parcelable.Creator<Medicamento> CREATOR  = new Parcelable.Creator<Medicamento>() {
        @Override
        public Medicamento createFromParcel(Parcel source) {
            return new Medicamento(source);
        }

        @Override
        public Medicamento[] newArray(int size) {
            return new Medicamento[0];
        }
    };
}


