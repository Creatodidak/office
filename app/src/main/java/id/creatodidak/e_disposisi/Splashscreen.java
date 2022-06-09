package id.creatodidak.e_disposisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        int durasi = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent home = new Intent(Splashscreen.this, Login.class);
                startActivity(home);
                finish();

            }
        }, durasi);
    }
}