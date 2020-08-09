package com.geekbrains.shoplist.room;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_purchase")
public class Purchase {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String txt;

    public Integer check;
}
