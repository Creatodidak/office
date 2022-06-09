package id.creatodidak.e_disposisi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.creatodidak.e_disposisi.API.ApiClient;
import id.creatodidak.e_disposisi.API.ApiInterface;
import id.creatodidak.e_disposisi.model.AuthLogin;
import id.creatodidak.e_disposisi.model.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button loginbtn;
    ApiInterface apiInterface;
    Session session;
    EditText username, password;

    String user;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(this);

    }

    private void getlogin(String user, String pass) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AuthLogin> loginCall = apiInterface.loginResponse(user, pass);
        loginCall.enqueue(new Callback<AuthLogin>() {
            @Override
            public void onResponse(Call<AuthLogin> call, Response<AuthLogin> response) {
                if (response.isSuccessful() && response.body().isNext()) {

                    session = new id.creatodidak.e_disposisi.Session(Login.this);
                    Data data = response.body().getData();
                    session.createLoginSession(data);


                    Toast.makeText(Login.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<AuthLogin> call, Throwable t) {
               // warning = findViewById(R.id.warning);
               // warning.setText("");
                //new Handler().postDelayed(() -> warning.setVisibility(View.GONE), 2000);
                Toast.makeText(Login.this, "Gagal Login", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loginbtn:
                user = username.getText().toString();
                pass = password.getText().toString();
                getlogin(user,pass);
                break;
        }
    }


}