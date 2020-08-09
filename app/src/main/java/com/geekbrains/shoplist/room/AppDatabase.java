package com.geekbrains.shoplist.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Purchase.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PurchaseDao purchaseDao();
}

