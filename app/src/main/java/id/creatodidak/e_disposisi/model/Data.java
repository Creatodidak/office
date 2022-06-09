package id.creatodidak.e_disposisi.model;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("nama")
	private String nama;

	@SerializedName("nip")
	private String nip;

	@SerializedName("foto")
	private String foto;

	@SerializedName("jabatan")
	private String jabatan;

	public String getNama(){
		return nama;
	}

	public String getNip(){
		return nip;
	}

	public String getFoto(){
		return foto;
	}

	public String getJabatan(){
		return jabatan;
	}
}