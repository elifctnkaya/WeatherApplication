package com.example.weatherapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Konum;
import com.example.weatherapplication.R;

import java.util.ArrayList;

public class KonumAdapter extends RecyclerView.Adapter<KonumAdapter.ViewHolder>{


    public Context context;
    public ArrayList<Konum> mList;
    public View.OnClickListener mOnItemClickListener;

    public KonumAdapter(Context context, ArrayList<Konum> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public KonumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_konum_layout, parent, false);
        return new KonumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KonumAdapter.ViewHolder holder, int position) {
        holder.tv_konum.setText(mList.get(position).getKonum_adi());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_konum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_konum = itemView.findViewById(R.id.tv_konum);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
}
