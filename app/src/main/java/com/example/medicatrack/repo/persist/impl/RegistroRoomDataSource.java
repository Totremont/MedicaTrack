package com.example.medicatrack.repo.persist.impl;

import android.content.Context;

import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.Registro;
import com.example.medicatrack.model.enums.RegistroEstado;
import com.example.medicatrack.repo.persist.database.Database;
import com.example.medicatrack.repo.persist.database.daos.RegistroDao;
import com.example.medicatrack.repo.persist.entities.RegistroEntity;
import com.example.medicatrack.repo.persist.interfaces.CallbacksDataSource;
import com.example.medicatrack.repo.persist.interfaces.RegistroDataSource;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class RegistroRoomDataSource implements RegistroDataSource
{

    private static RegistroDao registroDao;
    private static RegistroRoomDataSource INSTANCE = null;
    private final Context context;

    private RegistroRoomDataSource(final Context context)
    {
        this.context = context;
        Database bd = Database.getInstance(context);
        registroDao = bd.registroDao();
    }

    public synchronized static RegistroRoomDataSource getInstance(final Context context)
    {
        if(INSTANCE == null) INSTANCE = new RegistroRoomDataSource(context);
        return INSTANCE;
    }

    @Override
    public void insert(Registro registro, CallbacksDataSource.InsertCallback callback)
    {
        if(registro == null) callback.onInsert(false);
        else
        {
            RegistroEntity entity = new RegistroEntity();
            entity.setEstado(registro.getEstado().name());
            entity.setFecha(registro.getFecha().toInstant().getEpochSecond());
            entity.setMedicaId(registro.getMedicamento().getId());
            try
            {
                registroDao.insert(entity);
                callback.onInsert(true);

            }catch (Exception e)
            {
                callback.onInsert(false);
            }
        }
    }

    @Override
    public void update(Registro registro, CallbacksDataSource.UpdateCallback callback)
    {
        //To do
    }

    @Override
    public void getById(int id, CallbacksDataSource.GetByIdCallback<Registro> callback)
    {
        try
        {
            RegistroEntity entity = registroDao.getById(id);
            Registro registro = new Registro(entity.getId());
            registro.setEstado(RegistroEstado.valueOf(entity.getEstado()));
            registro.setFecha(ZonedDateTime.ofInstant(Instant.ofEpochSecond(entity.getFecha()),ZoneId.of("America/Argentina/Buenos_Aires")));
            final Medicamento[] medicamento = new Medicamento[1];
            MedicamentoRoomDataSource.getInstance(context).getById(entity.getMedicaId(),(result, value) ->
            {
                if(result) medicamento[0] = value;
                else medicamento[0] = null;
            });
            registro.setMedicamento(medicamento[0]);
            callback.onGetById(true,registro);
        } catch(Exception e)
        {
            callback.onGetById(false,null);
        }
    }

    @Override
    public void getAll(CallbacksDataSource.GetAllCallback<Registro> callback)
    {
        //To do
    }

    @Override
    public void getAllFrom(int medicamentoId, CallbacksDataSource.GetAllCallback<Registro> callback)
    {
        //To do
    }

    @Override
    public void getAllFromWhere(int medicamentoId, RegistroEstado estado, CallbacksDataSource.GetAllCallback<Registro> callback)
    {
        //To do
    }
}
