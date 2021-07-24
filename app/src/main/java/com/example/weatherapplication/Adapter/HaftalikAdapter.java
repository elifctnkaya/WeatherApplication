package com.example.weatherapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Model.HaftalikModel;
import com.example.weatherapplication.R;

import java.util.List;

public class HaftalikAdapter extends RecyclerView.Adapter<HaftalikAdapter.HaftalikGorunum> {
    private Context mContext;
    private List<HaftalikModel> havaDurumuList;


    public HaftalikAdapter(Context mContext, List<HaftalikModel> havaDurumuList) {
        this.mContext = mContext;
        this.havaDurumuList = havaDurumuList;
    }

    public class HaftalikGorunum extends RecyclerView.ViewHolder {

        public ImageView haftalikGorsel;
        public TextView haftalikGunIsmi;
        public TextView haftalikHavaDurumu;
        public TextView haftalikDerece;

        public HaftalikGorunum(@NonNull View itemView) {
            super(itemView);

            haftalikGorsel = itemView.findViewById(R.id.haftalikGorsel);
            haftalikGunIsmi = itemView.findViewById(R.id.haftalikGunIsmi);
            haftalikHavaDurumu = itemView.findViewById(R.id.haftalikHavaDurumu);
            haftalikDerece = itemView.findViewById(R.id.haftalikDerece);
        }
    }

    @NonNull
    @Override
    public HaftalikGorunum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.haftalik_gorunum, parent, false);

        return new HaftalikGorunum(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HaftalikGorunum holder, int position) {
        HaftalikModel haftalikModel = havaDurumuList.get(position);

        holder.haftalikGorsel.setImageResource(mContext.getResources()
                .getIdentifier(haftalikModel.getHaftalik_gorsel_adi(),"drawable",mContext.getPackageName()));
        holder.haftalikGunIsmi.setText(haftalikModel.getHaftalik_gun_adi());
        holder.haftalikHavaDurumu.setText(haftalikModel.getHaftalik_gun_hava_durumu());
        holder.haftalikDerece.setText(haftalikModel.getHaftalik_gun_derece());

    }

    @Override
    public int getItemCount() {
        return havaDurumuList.size();
    }
}
