package id.creatodidak.e_disposisi;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class Rincian extends AppCompatActivity {
    PDFView pdfView;
    String pdfurl, url, nip;
    TextView wm1, wm2;
    ExtendedFloatingActionButton fb;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian);
        pdfView = findViewById(R.id.pdfView);
        wm1 = findViewById(R.id.wmDate);
        wm2 = findViewById(R.id.wmDate1);
        Date currentTime = Calendar.getInstance().getTime();
        wm1.setText(currentTime.toString());
        wm2.setText("POLRES LANDAK");
        fb = findViewById(R.id.tindak);
        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");

        pdfurl = url;
        new RetrivePDFfromUrl().execute(pdfurl);

        fb.setOnClickListener(view -> ask());
        session = new Session(this);
        nip = session.getUserDetail().get(Session.NIP);
        TextView wm = findViewById(R.id.wmUsername);

        wm.setText(nip);

    }

    private void ask() {


        View alertCustomdialog = LayoutInflater.from(Rincian.this).inflate(R.layout.activity_popup_aksi, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertCustomdialog);
        final AlertDialog dialog = alert.create();

        Spinner sptujuan, spaksi;
        String[] aksi = {"Tanggapan dan Saran", "Proses Lebih Lanjut", "Koordinasi/Konfirmasi", "Untuk Diketahui", "Lainnya"};
        //String[] tujuan = {"Sekretaris", "Kabid PEPP", "Kabid SosBud", "Kabid PEFP", "Kabid Litbang", "Kasubbag Umum dan Kepegawaian", "Kasubbag Keuangan dan Aset", "Lainnya"};
        String[] tujuan = {"KABAG", "KASAT", "KASI"};
        spaksi = alertCustomdialog.findViewById(R.id.spAksi);
        sptujuan = alertCustomdialog.findViewById(R.id.spTujuan);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aksi);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spaksi.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tujuan);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TextView lbtujuan = alertCustomdialog.findViewById(R.id.lbTujuan);
        EditText tjCustom = alertCustomdialog.findViewById(R.id.tjCustom);

        TextView lbaksi = alertCustomdialog.findViewById(R.id.lbAksi);
        EditText akCustom = alertCustomdialog.findViewById(R.id.akCustom);

        sptujuan.setAdapter(dataAdapter2);

        sptujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Lainnya")) {
                    tjCustom.setVisibility(View.VISIBLE);
                    lbtujuan.setText("Silahkan Ketik Tujuan Disposisi");

                } else {
                    tjCustom.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spaksi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Lainnya")) {
                    akCustom.setVisibility(View.VISIBLE);
                    lbaksi.setText("Silahkan Ketik Aksi Disposisi");

                } else {
                    akCustom.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }
}