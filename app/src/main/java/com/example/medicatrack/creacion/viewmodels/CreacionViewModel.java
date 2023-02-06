package com.example.medicatrack.creacion.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CreacionViewModel extends AndroidViewModel {

    private MutableLiveData<String> nombreMed;
    private MutableLiveData<Float> concentracion;
    private MutableLiveData<String> forma;

    public CreacionViewModel(@NonNull Application application) {
        super(application);
        nombreMed = new MutableLiveData<String>();
        concentracion = new MutableLiveData<Float>();
        forma = new MutableLiveData<String>();
    }

    public LiveData<String> getNombreMed() {
        return nombreMed;
    }

    public LiveData<Float> getConcentracion() {
        return concentracion;
    }

    public LiveData<String> getForma() {
        return forma;
    }

    public void setNombreMed(String nombreMed) {
        this.nombreMed.postValue(nombreMed);
    }

    public void setConcentracion(Float concentracion) {
        this.concentracion.postValue(concentracion);
    }

    public void setForma(String forma) {
        this.forma.postValue(forma);
    }
}
