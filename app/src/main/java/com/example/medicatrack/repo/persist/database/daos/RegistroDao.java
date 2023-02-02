package com.example.medicatrack.repo.persist.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicatrack.repo.persist.entities.RegistroEntity;

import java.util.List;

@Dao
public interface RegistroDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(RegistroEntity registro);

    @Update
    void update(RegistroEntity registro);

    @Query("SELECT * FROM RegistroEntity WHERE id = :id")
    RegistroEntity getById(int id);

    @Query("SELECT * FROM RegistroEntity WHERE (medicamento_id = :medicamentoId AND estado = :estado)")
    List<RegistroEntity> getAllFromWhere(int medicamentoId, String estado);

    @Query("SELECT * FROM RegistroEntity WHERE medicamento_id = :medicamentoId")
    List<RegistroEntity> getAllFrom(int medicamentoId);

    @Query("SELECT * FROM RegistroEntity")
    List<RegistroEntity> getAll();

}
