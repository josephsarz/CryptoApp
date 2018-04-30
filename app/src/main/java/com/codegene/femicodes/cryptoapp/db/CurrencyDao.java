package com.codegene.femicodes.cryptoapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.codegene.femicodes.cryptoapp.model.Currency;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by femicodes on 4/30/2018.
 */
@Dao
public interface CurrencyDao {

    @Query("select * from Currency")
    List<Currency> getAllCurrencies();

    @Query("select * from Currency where id = :id")
    Currency getItembyId(String id);

    @Insert(onConflict = REPLACE)
    void addCurrency(Currency currency);

    @Delete
    void deleteCurrency(Currency currency);

}
