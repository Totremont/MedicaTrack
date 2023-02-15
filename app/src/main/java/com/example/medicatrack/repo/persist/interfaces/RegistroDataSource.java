package com.example.medicatrack.repo.persist.interfaces;


import com.example.medicatrack.model.Registro;
import com.example.medicatrack.model.enums.RegistroEstado;

import java.time.ZonedDateTime;

public interface RegistroDataSource
{
    void insert(Registro registro, CallbacksDataSource.InsertCallback callback);
    void update(Registro registro, CallbacksDataSource.UpdateCallback callback);
    void getById(int id, CallbacksDataSource.GetByIdCallback<Registro> callback);
    void getAll(CallbacksDataSource.GetAllCallback<Registro> callback);
    void getAllFrom(int medicamentoId, CallbacksDataSource.GetAllCallback<Registro> callback);
    void getAllFromDate(ZonedDateTime fecha, CallbacksDataSource.GetAllCallback<Registro> callback);
}
