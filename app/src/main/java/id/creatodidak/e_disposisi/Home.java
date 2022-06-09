package id.creatodidak.e_disposisi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Home extends AppCompatActivity implements View.OnClickListener {
    CardView sm,sk,sr,st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sm = findViewById(R.id.MenuSuratMasuk);
        sk = findViewById(R.id.MenuSuratKeluar);
        sr = findViewById(R.id.MenuSuratRiwayat);
        st = findViewById(R.id.MenuSuratTambah);
        sm.setOnClickListener(this);
        sk.setOnClickListener(this);
        sr.setOnClickListener(this);
        st.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.MenuSuratMasuk) {
            Intent sma = new Intent(Home.this, Suratmasuk.class);
            startActivity(sma);
        }

        if (view.getId() == R.id.MenuSuratKeluar) {
            Intent ska = new Intent(Home.this, Suratkeluar.class);
            startActivity(ska);
        }

        if (view.getId() == R.id.MenuSuratRiwayat) {
            Intent sra = new Intent(Home.this, Suratriwayat.class);
            startActivity(sra);
        }

        if (view.getId() == R.id.MenuSuratTambah) {
            Intent sta = new Intent(Home.this, Tambahsurat.class);
            startActivity(sta);
        }
    }
}