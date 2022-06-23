package id.creatodidak.e_disposisi;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonObject;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.kinda.alert.KAlertDialog;

import java.io.File;
import java.util.ArrayList;

import id.creatodidak.e_disposisi.API.ApiInterface;
import id.creatodidak.e_disposisi.Callback.CustomCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tambahsurat extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = Tambahsurat.class.getSimpleName();
    private String selectedFilePath;
    private String SERVER_URL = "https://polreslandak.id/upload.php";

    private int STORAGE_PERMISSION_CODE = 1;
    private int PICK_FILE_PDF = 2;
    private File FILE_UPLOAD = null;
    private PickiT pickiT;

    Session session;

    String[] bulan = { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    String[] jenis = {"Pilih Jenis Surat", "Surat Masuk", "Surat Keluar"};
    String[] sifat = {"Pilih Sifat Surat", "Rahasia", "Reguler"};
    String[] prioritas = {"Pilih Prioritas Surat", "HIGH", "MEDIUM", "LOW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahsurat);


        Button btnUpload = findViewById(R.id.btnUpload);
        TextView pickpdf = findViewById(R.id.pickpdf);
        Spinner jenisSurat = findViewById(R.id.jenisSurat);
        EditText asalSurat = findViewById(R.id.asalSurat);
        EditText alamatSurat = findViewById(R.id.alamatSurat);
        EditText noSurat = findViewById(R.id.noSurat);
        EditText perihal = findViewById(R.id.perihal);
        EditText tglSurat = findViewById(R.id.etTanggalSurat);
        Spinner bulanSurat = findViewById(R.id.spTanggalSurat);
        EditText tahunSurat = findViewById(R.id.etTahunSurat);
        EditText tglSuratDiterima = findViewById(R.id.etTanggalSuratDiterima);
        Spinner bulanSuratDiterima = findViewById(R.id.spTanggalSuratDiterima);
        EditText tahunSuratDiterima = findViewById(R.id.etTahunSuratDiterima);
        Spinner sifatSurat = findViewById(R.id.sifatSurat);
        Spinner prioritasSurat = findViewById(R.id.prioritasSurat);
        session = new Session(this);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bulan);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bulanSurat.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bulan);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bulanSuratDiterima.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3;
        dataAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jenis);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jenisSurat.setAdapter(dataAdapter3);

        ArrayAdapter<String> dataAdapter4;
        dataAdapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sifat);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sifatSurat.setAdapter(dataAdapter4);

        ArrayAdapter<String> dataAdapter5;
        dataAdapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, prioritas);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        prioritasSurat.setAdapter(dataAdapter5);


        pickpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(Tambahsurat.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                    intentPDF.setType("application/pdf");
                    intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intentPDF, PICK_FILE_PDF);

                } else{
                    requestStoragePermission();
                }


            }
        });

        pickiT = new PickiT(this, new PickiTCallbacks() {
            @Override
            public void PickiTonUriReturned() {

            }

            @Override
            public void PickiTonStartListener() {

            }

            @Override
            public void PickiTonProgressUpdate(int progress) {

            }

            @Override
            public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {

                FILE_UPLOAD = new File(path);
                pickpdf.setText(FILE_UPLOAD.getName());

            }

            @Override
            public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

            }
        }, this);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jenis_surat = jenisSurat.getSelectedItem().toString();
                String sifat_surat = sifatSurat.getSelectedItem().toString();
                String bulan_surat = bulanSurat.getSelectedItem().toString().toUpperCase();
                String bulan_suratditerima = bulanSuratDiterima.getSelectedItem().toString().toUpperCase();
                String prioritas_surat = prioritasSurat.getSelectedItem().toString();
                String asal_Surat = asalSurat.getText().toString().toUpperCase();
                String alamat_Surat = alamatSurat.getText().toString().toUpperCase();
                String no_Surat = noSurat.getText().toString();
                String perihalSurat = perihal.getText().toString().toUpperCase();
                String tgl_Surat = tglSurat.getText().toString();
                String tahun_Surat = tahunSurat.getText().toString();
                String tgl_SuratDiterima = tglSuratDiterima.getText().toString();
                String tahun_SuratDiterima = tahunSuratDiterima.getText().toString();
                String user = session.getUserDetail().get(Session.NIP);


                String TanggalSuratOk = tgl_Surat+" "+bulan_surat+" "+tahun_Surat;
                String TanggalSuratDiterimaOk = tgl_SuratDiterima+" "+bulan_suratditerima+" "+tahun_SuratDiterima;






                if(jenis_surat.isEmpty()){

                    KAlertDialog pDialog = new KAlertDialog(Tambahsurat.this, KAlertDialog.WARNING_TYPE);
                    pDialog.setTitleText("Failed");
                    pDialog.setContentText("Judul Harus di isi");
                    pDialog.setCancelable(false);
                    pDialog.show();

                } else if(FILE_UPLOAD != null){

                    KAlertDialog pDialog = new KAlertDialog(Tambahsurat.this, KAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Memproses..");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    upload_Pdf(FILE_UPLOAD, FILE_UPLOAD.getName(), asal_Surat, alamat_Surat, no_Surat, perihalSurat, TanggalSuratOk, TanggalSuratDiterimaOk, sifat_surat, jenis_surat, prioritas_surat, user, new CustomCallback() {
                        @Override
                        public String onSucess(JsonObject value) {

                            pDialog.dismiss();

                            KAlertDialog pDialog = new KAlertDialog(Tambahsurat.this, KAlertDialog.SUCCESS_TYPE);
                            pDialog.setTitleText("Success");
                            pDialog.setContentText("Surat berhasil di upload");
                            pDialog.setCancelable(false);
                            pDialog.show();
                            pickpdf.setText("KETUK DISINI UNTUK UPLOAD FILE");

                            FILE_UPLOAD = null; //reset

                            return null;
                        }

                        @Override
                        public void onFailure(String s) {
                            pDialog.dismiss();

                            KAlertDialog pDialog = new KAlertDialog(Tambahsurat.this, KAlertDialog.WARNING_TYPE);
                            pDialog.setTitleText("Failed");
                            pDialog.setContentText("Maaf surat gagal diupload "+s);
                            pDialog.setCancelable(false);
                            pDialog.show();

                        }
                    });

                } else{
                    Toast.makeText(Tambahsurat.this, "Silahkan pilih file dahulu", Toast.LENGTH_SHORT).show();;

                }


            }
        });

    }

    public void upload_Pdf(File fl, String filename,
                           String asal_Surat,
                           String alamat_Surat,
                           String no_Surat,
                           String perihalSurat,
                           String tanggalSuratOk,
                           String tanggalSuratDiterimaOk,
                           String sifat_surat,
                           String jenis_surat,
                           String prioritas_surat,
                           String user, CustomCallback customCallback){

        RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), fl);
        RequestBody submit = RequestBody.create(MediaType.parse("text/plain"), filename);

        RequestBody asalSURAT = RequestBody.create(MediaType.parse("text/plain"), asal_Surat);
        RequestBody alamatSURAT = RequestBody.create(MediaType.parse("text/plain"), alamat_Surat);
        RequestBody noSURAT = RequestBody.create(MediaType.parse("text/plain"), no_Surat);
        RequestBody perihalSURAT = RequestBody.create(MediaType.parse("text/plain"), perihalSurat);
        RequestBody tanggalSURAT = RequestBody.create(MediaType.parse("text/plain"), tanggalSuratOk);
        RequestBody tanggalditerimaSURAT = RequestBody.create(MediaType.parse("text/plain"), tanggalSuratDiterimaOk);
        RequestBody sifatSURAT = RequestBody.create(MediaType.parse("text/plain"), sifat_surat);
        RequestBody jenisSURAT = RequestBody.create(MediaType.parse("text/plain"), jenis_surat);
        RequestBody prioritasSURAT = RequestBody.create(MediaType.parse("text/plain"), prioritas_surat);
        RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), user);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.WEB+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = api.uploadPdf(requestFile, submit, asalSURAT,alamatSURAT,noSURAT,perihalSURAT,tanggalSURAT,tanggalditerimaSURAT,sifatSURAT,jenisSURAT,prioritasSURAT, Username);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().get("error") != null)
                {
                    if ((response.body().get("error").toString().replace("\"", "")).equals("1"))
                    {

                        if(response.body().get("mes") != null){
                            customCallback.onFailure((response.body().get("mes").toString().replace("\"", "")));
                        } else{
                            customCallback.onFailure("Error Kode #51");
                        }

                    } else {
                        customCallback.onSucess(response.body());
                    }
                } else {
                    customCallback.onSucess(response.body());
                }

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                customCallback.onFailure("Error Koneksi.. "+t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FILE_PDF) {
                pickiT.getPath(data.getData(),  Build.VERSION.SDK_INT);
            }
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Tambahsurat.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }



}