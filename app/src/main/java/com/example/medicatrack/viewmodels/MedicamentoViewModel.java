package com.example.medicatrack.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicatrack.model.Medicamento;


public class MedicamentoViewModel extends ViewModel
{
    private Medicamento medicamento;
    public MutableLiveData<Integer> flag = new MutableLiveData<Integer>();  //0 Nada, 1 Registro , 2 Medicamento, 3 Info

    public MedicamentoViewModel()
    {
        flag.setValue(0);
    }

    public void insertData(Medicamento medicamento)
    {
        this.medicamento = medicamento;
    }

    public Medicamento getData()
    {
        return medicamento;
    }



}
