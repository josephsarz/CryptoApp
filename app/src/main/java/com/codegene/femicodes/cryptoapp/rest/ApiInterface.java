package com.codegene.femicodes.cryptoapp.rest;


import com.codegene.femicodes.cryptoapp.model.Currency;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by femicodes on 4/30/2018.
 */

public interface ApiInterface {

    @GET("ticker")
    Call<List<Currency>> getAllCurrency();

    @GET("ticker")
    Observable<List<Currency>> getSomeCurrency(@Query("limit") String limit);

}
