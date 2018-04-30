package com.codegene.femicodes.cryptoapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.codegene.femicodes.cryptoapp.R;
import com.codegene.femicodes.cryptoapp.adapter.CurrencyAdapter;
import com.codegene.femicodes.cryptoapp.db.AppDatabase;
import com.codegene.femicodes.cryptoapp.model.Currency;
import com.codegene.femicodes.cryptoapp.rest.ApiClient;
import com.codegene.femicodes.cryptoapp.rest.ApiInterface;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_currency_list)
    RecyclerView mRecyclerView;
    CurrencyAdapter mCurrencyAdapter;
    List<Currency> mCurrencyList;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getCurrencies();
    }

    private void getCurrencies() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Observable.just(apiInterface)
                .subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    Observable<List<Currency>> currencyObservable
                            = s.getSomeCurrency("10").subscribeOn(Schedulers.io());
                    return currencyObservable;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: handleResults, this::handleError);
                


//        Call<List<Currency>> call = apiInterface.getSomeCurrency("10");
//        call.enqueue(new Callback<List<Currency>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Currency>> call, @NonNull Response<List<Currency>> response) {
//                mCurrencyList = response.body();
//                Log.d("response", response.body().toString());
//
//                 //save to db
//                Executors.newSingleThreadExecutor().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i =0; i <mCurrencyList.size();i++){
//                            Currency currency = mCurrencyList.get(i);
//                            AppDatabase.getDatabase(getApplicationContext()).mCurrencyDao().addCurrency(currency);
//                        }
//                    }
//                });
//                setupRecyclerView();
//            }
//
//            @Override
//            public void onFailure(Call<List<Currency>> call, Throwable t) {
//
//                Toast.makeText(getApplicationContext(),"unable to connect!!",Toast.LENGTH_LONG).show();
//
//                Executors.newSingleThreadExecutor().execute(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Log.d("status", "before fetching");
//                        mCurrencyList = AppDatabase.getDatabase(getApplicationContext()).mCurrencyDao().getAllCurrencies();
//                        runOnUiThread (new Thread(new Runnable() {
//                            public void run() {
//                                setupRecyclerView();
//                            }
//                        }));
//
//
//                    }
//                });
//
//            }
//        });
//


    }

    private void handleResults(List<Currency> currencies) {

        if(currencies != null){
            mCurrencyList = currencies;

                 //save to db
                Executors.newSingleThreadExecutor().execute(() -> {
                    for (int i =0; i <mCurrencyList.size();i++){
                        Currency currency = mCurrencyList.get(i);
                        AppDatabase.getDatabase(getApplicationContext()).mCurrencyDao().addCurrency(currency);
                    }
                });
                setupRecyclerView();
        }

    }

    private void handleError(Throwable t){
        Log.e("Observer", ""+ t.toString());
        Toast.makeText(getApplicationContext(),"unable to connect!!",Toast.LENGTH_LONG).show();

                Executors.newSingleThreadExecutor().execute(() -> {

                    Log.d("status", "before fetching");
                    mCurrencyList = AppDatabase.getDatabase(getApplicationContext()).mCurrencyDao().getAllCurrencies();
                    runOnUiThread (new Thread(this::setupRecyclerView));

                });
    }

    @Override
    protected void onDestroy() {
        //dispose subscriptions
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
        super.onDestroy();
    }

    public void setupRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setHasFixedSize(true);
        mCurrencyAdapter = new CurrencyAdapter(mRecyclerView.getContext(), mCurrencyList);
        mRecyclerView.setAdapter(mCurrencyAdapter);
    }



}
