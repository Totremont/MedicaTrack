package com.example.medicatrack.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.medicatrack.model.enums.Frecuencia;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class Medicamento implements Parcelable
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.color);
        dest.writeString(this.forma);
        dest.writeFloat(this.concentracion);
        dest.writeString(this.frecuencia.name());
        dest.writeString(this.dias);
        dest.writeLong(this.hora.toInstant().getEpochSecond());
        dest.writeString(this.descripcion);
    }

    private Medicamento(Parcel in){
        this.id = in.readInt();
        this.nombre = in.readString();
        this.color = in.readString();
        this.forma = in.readString();
        this.concentracion = in.readFloat();
        this.frecuencia = Frecuencia.valueOf(in.readString());
        this.dias = in.readString();
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


