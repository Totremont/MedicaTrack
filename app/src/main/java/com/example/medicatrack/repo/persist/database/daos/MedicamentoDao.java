package com.example.medicatrack.repo.persist.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicatrack.repo.persist.entities.MedicamentoEntity;


@Dao
public interface MedicamentoDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(MedicamentoEntity medicamento);

    @Update
    void update(MedicamentoEntity medicamento);

    @Query("SELECT * FROM MedicamentoEntity WHERE id = :id")
    MedicamentoEntity getById(int id);

    @Query("SELECT * FROM MedicamentoEntity")
    MedicamentoEntity getAll();

}
