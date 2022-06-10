package id.creatodidak.e_disposisi.Callback;

import com.google.gson.JsonObject;

public interface CustomCallback {

    String onSucess(JsonObject value);
    void onFailure(String s);

}