package com.example.medicatrack.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.Registro;

import java.time.ZonedDateTime;
import java.util.List;

public class RegistroViewModel extends ViewModel
{
    //Obtiene los medicamentos a consumir en la fecha y crea registros de ser necesario

    public void getMedicamentosFrom(ZonedDateTime fecha, List<Medicamento> medicamentos, List<Registro> registros)   //Todos los registros de esa fecha
    {

    }
}
