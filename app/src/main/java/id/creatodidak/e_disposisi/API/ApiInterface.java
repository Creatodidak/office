package id.creatodidak.e_disposisi.API;

import java.util.List;

import id.creatodidak.e_disposisi.model.AuthLogin;
import id.creatodidak.e_disposisi.model.ResponseModel;
import id.creatodidak.e_disposisi.model.SuratKeluar;
import id.creatodidak.e_disposisi.model.SuratMasuk;
import id.creatodidak.e_disposisi.model.SuratRiwayat;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @GET("logindata/suratmasuk")
    Call<List<SuratMasuk>> getDaftarSurat();

    @GET("logindata/suratkeluar")
    Call<List<SuratKeluar>> getDaftarSuratKeluar();

    @GET("logindata/surat")
    Call<List<SuratRiwayat>> getDaftarSuratRiwayat();

    @FormUrlEncoded
    @POST("logindata/login")
    Call<AuthLogin> loginResponse(
            @Field("user") String user,
            @Field("pass") String pass
    );

    @Multipart
    @POST("upload.php")
    Call<ResponseModel> fileUpload(
            @Part("sender_information") RequestBody description,
            @Part MultipartBody.Part file);


}
