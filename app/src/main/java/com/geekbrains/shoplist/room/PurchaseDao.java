package com.geekbrains.shoplist.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PurchaseDao {
    @Query("SELECT * FROM table_purchase")
    Single<List<Purchase>> getAll();

    @Query("DELETE FROM table_purchase")
    Single<Integer> deleteAll();

    @Query("SELECT * FROM table_purchase WHERE id = :id")
    Single<List<Purchase>> getAllById(int id);

    @Insert
    Single<Long> insert(Purchase purchase);

    @Insert
    Single<List<Long>> insertList(List<Purchase> purchase);

    @Delete
    Single<Integer> delete(Purchase purchase);

    @Update
    Single<Integer> update(Purchase purchase);
}
