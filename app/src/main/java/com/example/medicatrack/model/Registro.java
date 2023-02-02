package com.example.medicatrack.model;

import com.example.medicatrack.model.enums.RegistroEstado;

import java.time.ZonedDateTime;

public class Registro
{
    private final int id;
    private Medicamento medicamento;
    private ZonedDateTime fecha;
    private RegistroEstado estado;

    public Registro(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public RegistroEstado getEstado() {
        return estado;
    }

    public void setEstado(RegistroEstado estado) {
        this.estado = estado;
    }
}
