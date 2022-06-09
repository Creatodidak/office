package id.creatodidak.e_disposisi.model;

import com.google.gson.annotations.SerializedName;

public class AuthLogin{

	@SerializedName("next")
	private boolean next;

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private int status;

	public boolean isNext(){
		return next;
	}

	public Data getData(){
		return data;
	}

	public int getStatus(){
		return status;
	}
}