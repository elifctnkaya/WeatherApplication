package com.example.weatherapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Model.SaatlikModel;
import com.example.weatherapplication.R;

import java.util.List;

public class SaatlikAdapter extends  RecyclerView.Adapter<SaatlikAdapter.SaatlikVeriGorunum>{
    private Context mContext;
    private List<SaatlikModel> saatlikModelList;

    public SaatlikAdapter(Context mContext, List<SaatlikModel> saatlikModelList) {
        this.mContext = mContext;
        this.saatlikModelList = saatlikModelList;
    }

    public class SaatlikVeriGorunum extends RecyclerView.ViewHolder{

        public TextView saat;
        public ImageView saatlikGorsel;
        public TextView saatlikDerece;
        public TextView saatlikYagmurDurumu;

        public SaatlikVeriGorunum(@NonNull View itemView) {
            super(itemView);
            saat = itemView.findViewById(R.id.saatDilimi);
            saatlikGorsel = itemView.findViewById(R.id.saatlikGorsel);
            saatlikDerece = itemView.findViewById(R.id.saatlikDerece);
            saatlikYagmurDurumu = itemView.findViewById(R.id.saatlikYagmurDurumu);
        }
    }

    @NonNull
    @Override
    public SaatlikVeriGorunum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saatlik_gorunum,parent,false);

        return new SaatlikVeriGorunum(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SaatlikAdapter.SaatlikVeriGorunum holder, int position) {
        SaatlikModel saatlikModel = saatlikModelList.get(position);

        holder.saat.setText(saatlikModel.getSaat_dilimi());
        holder.saatlikGorsel.setImageResource(mContext.getResources()
                .getIdentifier(saatlikModel.getSaatlik_gorsel_adi(),"drawable",mContext.getPackageName()));
        holder.saatlikDerece.setText(saatlikModel.getSaatlik_derece());
        holder.saatlikYagmurDurumu.setText(saatlikModel.getSaatlik_yagmur_durumu());

    }

    @Override
    public int getItemCount() {
        return saatlikModelList.size();
    }
}
