package id.creatodidak.e_disposisi;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.e_disposisi.API.ApiClient;
import id.creatodidak.e_disposisi.API.ApiInterface;
import id.creatodidak.e_disposisi.Adapter.Adapter;
import id.creatodidak.e_disposisi.Adapter.AdapterSK;
import id.creatodidak.e_disposisi.model.SuratKeluar;
import id.creatodidak.e_disposisi.model.SuratMasuk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Suratkeluar extends AppCompatActivity {
    RecyclerView rv;
    ProgressBar pb;
    LinearLayoutManager layoutManager;
    AdapterSK adapterSK;
    List<SuratKeluar> SKeluarList = new ArrayList<>();
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suratkeluar);

        rv = findViewById(R.id.daftarSurat);
        pb = findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapterSK = new AdapterSK(SKeluarList);
        rv.setAdapter(adapterSK);

        fetchSurat();

    }

    private void fetchSurat() {
        pb.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<SuratKeluar>> Sm = apiInterface.getDaftarSuratKeluar();

        Sm.enqueue(new Callback<List<SuratKeluar>>() {
            @Override
            public void onResponse(Call<List<SuratKeluar>> call, Response<List<SuratKeluar>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SKeluarList.addAll(response.body());
                    adapterSK.notifyDataSetChanged();
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SuratKeluar>> call, Throwable t) {
                Toast.makeText(Suratkeluar.this, "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
