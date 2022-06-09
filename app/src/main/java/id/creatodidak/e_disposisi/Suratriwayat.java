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
import id.creatodidak.e_disposisi.Adapter.AdapterRiwayat;
import id.creatodidak.e_disposisi.model.SuratRiwayat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Suratriwayat extends AppCompatActivity {
    RecyclerView rv;
    ProgressBar pb;
    LinearLayoutManager layoutManager;
    AdapterRiwayat adapter;
    List<SuratRiwayat> SRiwayatList = new ArrayList<>();
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suratriwayat);

        rv = findViewById(R.id.daftarSurat);
        pb = findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new AdapterRiwayat(SRiwayatList);
        rv.setAdapter(adapter);

        fetchSurat();

    }

    private void fetchSurat() {
        pb.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<SuratRiwayat>> Sm = apiInterface.getDaftarSuratRiwayat();

        Sm.enqueue(new Callback<List<SuratRiwayat>>() {
            @Override
            public void onResponse(Call<List<SuratRiwayat>> call, Response<List<SuratRiwayat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SRiwayatList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SuratRiwayat>> call, Throwable t) {
                Toast.makeText(Suratriwayat.this, "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
