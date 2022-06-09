package id.creatodidak.e_disposisi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by BRIPDA ANGGI PERIANTO on 06,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class Popupaksi extends AppCompatActivity {
    Spinner sptujuan, spaksi;
    String[] aksi = {"Setuju dan Tindak Lanjuti", "Tolak", "Bicarakan Dengan Saya", "Balas"};
    String[] tujuan = {"Kepala Badan (Kaban)", "Sekretaris", "Kepala Bidang Perencanaan, Evaluasi dan Pengendalian Pembangunan ( Kabid PEPP)", "Kepala Bidang Perencanaan Sosial Budaya (Kabid SosBud)", "Kepala Bidang Perencanaan Ekonomi, Fisik dan Prasarana (Kabid PEFP)", "Kepala Bidang Penelitian dan Pengembangan (Kabid Litbang)", "Kasubbag Umum dan Kepegawaian", "Kasubbag Keuangan dan Aset"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_aksi);

        spaksi = findViewById(R.id.spAksi);
        sptujuan = findViewById(R.id.spTujuan);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aksi);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spaksi.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tujuan);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sptujuan.setAdapter(dataAdapter2);
    }
}
