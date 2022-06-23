package id.creatodidak.e_disposisi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

public class Home extends AppCompatActivity implements View.OnClickListener {
    CardView sm,sk,sr,st;

    Session session;
    String nama, jabatan, nip, foto;
    TextView tNama, tJabatan;


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

        session = new Session(Home.this);
        if (!session.isLoggedIn()) {
            moveToLogin();
        }

        nama = session.getUserDetail().get(Session.NAMA);
        jabatan = session.getUserDetail().get(Session.JABATAN);
        nip = session.getUserDetail().get(Session.NIP);
        foto = session.getUserDetail().get(Session.FOTO);

        tNama = findViewById(R.id.tvNama);
        tJabatan = findViewById(R.id.tvJabatan);
        ImageView fProfile = findViewById(R.id.fProfile);
        tNama.setText(nama.toUpperCase(Locale.ROOT));

        tJabatan.setText(jabatan.toUpperCase(Locale.ROOT)+"\n"+nip);

        Glide.with(this)
                .load(foto)
                .placeholder(R.drawable.load)
                .apply(RequestOptions.circleCropTransform())
                .into(fProfile);

        ImageView lgT = findViewById(R.id.Lgout);

        lgT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutSession();
                moveToLogin();
                finish();
            }
        });



    }

    private void moveToLogin() {
        Intent intent = new Intent(Home.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        finish();
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