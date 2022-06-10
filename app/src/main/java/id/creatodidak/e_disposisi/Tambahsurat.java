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
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView ivAttachment;
    Button bUpload;
    TextView tvFileName;
    String docFilePath;
    String filepath = "";

    private int STORAGE_PERMISSION_CODE = 1;
    private int PICK_FILE_PDF = 2;
    private File FILE_UPLOAD = null;
    private PickiT pickiT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahsurat);


        TextView pickpdf = findViewById(R.id.pickpdf);
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

        Button btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FILE_UPLOAD != null){

                    KAlertDialog pDialog = new KAlertDialog(Tambahsurat.this, KAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Memproses..");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    upload_Pdf(FILE_UPLOAD, FILE_UPLOAD.getName(), new CustomCallback() {
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

    public void upload_Pdf(File fl, String filename, final CustomCallback customCallback){

        RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), fl);
        RequestBody submit = RequestBody.create(MediaType.parse("text/plain"), filename);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.WEB+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = api.uploadPdf(requestFile, submit);
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