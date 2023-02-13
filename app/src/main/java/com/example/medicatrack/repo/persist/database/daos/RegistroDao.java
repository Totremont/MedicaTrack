package com.example.medicatrack.repo.persist.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicatrack.repo.persist.entities.RegistroEntity;

import java.util.List;
import java.util.UUID;

@Dao
public interface RegistroDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(RegistroEntity registro);

    @Update
    void update(RegistroEntity registro);

    @Query("SELECT * FROM RegistroEntity WHERE id = :id")
    RegistroEntity getById(UUID id);

    @Query("SELECT * FROM RegistroEntity WHERE (medicamento_id = :medicamentoId AND estado = :estado)")
    List<RegistroEntity> getAllFromWhere(UUID medicamentoId, String estado);

    @Query("SELECT * FROM RegistroEntity WHERE medicamento_id = :medicamentoId")
    List<RegistroEntity> getAllFrom(UUID medicamentoId);

    @Query("SELECT * FROM RegistroEntity")
    List<RegistroEntity> getAll();

}
