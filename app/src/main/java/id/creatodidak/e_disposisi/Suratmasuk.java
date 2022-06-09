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
import id.creatodidak.e_disposisi.model.SuratMasuk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Suratmasuk extends AppCompatActivity {
    RecyclerView rv;
    ProgressBar pb;
    LinearLayoutManager layoutManager;
    Adapter adapter;
    List<SuratMasuk> SMasukList = new ArrayList<>();
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suratmasuk);

        rv = findViewById(R.id.daftarSurat);
        pb = findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new Adapter(SMasukList);
        rv.setAdapter(adapter);

        fetchSurat();

    }

    private void fetchSurat() {
        pb.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<SuratMasuk>> Sm = apiInterface.getDaftarSurat();

        Sm.enqueue(new Callback<List<SuratMasuk>>() {
            @Override
            public void onResponse(Call<List<SuratMasuk>> call, Response<List<SuratMasuk>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SMasukList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SuratMasuk>> call, Throwable t) {
                Toast.makeText(Suratmasuk.this, "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
