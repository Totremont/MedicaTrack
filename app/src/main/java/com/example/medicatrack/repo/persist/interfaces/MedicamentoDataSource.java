package com.example.medicatrack.repo.persist.interfaces;

import com.example.medicatrack.model.Medicamento;

public interface MedicamentoDataSource
{
    void insert(Medicamento medicamento, CallbacksDataSource.InsertCallback callback);
    void update(Medicamento medicamento, CallbacksDataSource.UpdateCallback callback);
    void getById(int id, CallbacksDataSource.GetByIdCallback<Medicamento> callback);
    void getAll(CallbacksDataSource.GetAllCallback<Medicamento> callback);
}
