package com.codegene.femicodes.cryptoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.codegene.femicodes.cryptoapp.R;
import com.codegene.femicodes.cryptoapp.model.Currency;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by femicodes on 4/30/2018.
 */


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>{

    Context mContext;
    List<Currency> mCurrencyList;

    public CurrencyAdapter(Context context, List<Currency> currencyList) {
        mContext = context;
        mCurrencyList = currencyList;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_row, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {

        holder.name.setText(mCurrencyList.get(position).getName());
        holder.rank.setText(mCurrencyList.get(position).getRank());
        holder.usdPrice.setText(mCurrencyList.get(position).getPrice_usd());
        holder.btcPrice.setText(mCurrencyList.get(position).getPrice_btc());

    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }


    public class CurrencyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_rank)
        TextView rank;
        @BindView(R.id.tv_usd_price)
        TextView usdPrice;
        @BindView(R.id.tv_btc_price)
        TextView btcPrice;


        public CurrencyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "clicked ", Toast.LENGTH_SHORT ).show();
                }
            });
        }
    }
}
