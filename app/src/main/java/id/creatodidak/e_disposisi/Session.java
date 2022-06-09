package id.creatodidak.e_disposisi;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

import id.creatodidak.e_disposisi.model.Data;

public class Session {

    public static final String NAMA = "nama";
    public static final String JABATAN = "jabatan";
    public static final String NIP = "nip";
    public static final String FOTO = "foto";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private final Context _context;
    private final SharedPreferences sharedPreference;
    private final SharedPreferences.Editor editor;

    public Session(Context context) {
        this._context = context;
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreference.edit();
    }

    public void createLoginSession(Data user) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(NAMA, user.getNama());
        editor.putString(JABATAN, user.getJabatan());
        editor.putString(NIP, user.getNip());
        editor.putString(FOTO, user.getFoto());
        editor.commit();
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NAMA, sharedPreference.getString(NAMA, null));
        user.put(JABATAN, sharedPreference.getString(JABATAN, null));
        user.put(NIP, sharedPreference.getString(NIP, null));
        user.put(FOTO, sharedPreference.getString(FOTO, null));
        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreference.getBoolean(IS_LOGGED_IN, false);
    }
}
