package id.creatodidak.e_disposisi.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.e_disposisi.R;
import id.creatodidak.e_disposisi.Rincian;
import id.creatodidak.e_disposisi.model.SuratMasuk;

/**
 * Created by BRIPDA ANGGI PERIANTO on 06,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<SuratMasuk> SMasukList;

    public Adapter(List<SuratMasuk> SMasukList) {
        this.SMasukList = SMasukList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listsurat, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nSurat.setText("Nomor : "+SMasukList.get(position).getnomor_surat());
        holder.jSurat.setText(SMasukList.get(position).getTentang());
        holder.tSurat.setText("Tanggal : "+SMasukList.get(position).gettanggal_surat());
        holder.aSurat.setText("Surat Dari : "+SMasukList.get(position).getasal_surat());
        String privasi, prioritas;

        privasi = SMasukList.get(position).getprivasi();
        prioritas = SMasukList.get(position).getprioritas();

        if (privasi.equals("private")){
            holder.prv.setVisibility(View.VISIBLE);
        }else{
            holder.prv.setVisibility(View.INVISIBLE);
        }

        if (prioritas.equals("high")){
            holder.pri.setImageResource(R.drawable.high);
        }

        if (prioritas.equals("low")){
            holder.pri.setImageResource(R.drawable.low);
        }

        if (prioritas.equals("med")){
            holder.pri.setImageResource(R.drawable.med);
        }

        String url = SMasukList.get(position).getfile();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, Rincian.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SMasukList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView jSurat, nSurat, tSurat, aSurat;
        CardView cv;
        ImageView prv, pri;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            prv = itemView.findViewById(R.id.privasi);
            pri = itemView.findViewById(R.id.prioritas);

            jSurat = itemView.findViewById(R.id.judulSurat);
            nSurat = itemView.findViewById(R.id.nomorSurat);
            tSurat = itemView.findViewById(R.id.tanggalSurat);
            aSurat = itemView.findViewById(R.id.pengirimSurat);
            cv = itemView.findViewById(R.id.Listsurat);
        }
    }
}