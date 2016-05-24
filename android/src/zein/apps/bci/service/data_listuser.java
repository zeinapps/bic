package zein.apps.bci.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import zein.apps.bci.model.list_user;

import android.util.Log;

public class data_listuser {
	ArrayList<list_user> list_user;
	
	public data_listuser(String stringjson){
		Log.d("Json", stringjson);
		JSONArray data = null;
		list_user = new ArrayList<list_user>();
        
		try {
			JSONObject json = new JSONObject(stringjson);
			data = json.getJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				JSONObject item_barang = data.getJSONObject(i);
				String email = item_barang.getString("email");
				String display_name = item_barang.getString("display_name");
				String nomor_polisi = item_barang.getString("nomor_polisi");
				String rayon = item_barang.getString("rayon");
				String id_anggota = item_barang.getString("id_anggota");
				String jenis_blazer = item_barang.getString("jenis_blazer");
				String status_id = item_barang.getString("status_id");
				String qr_code = item_barang.getString("qr_code");
				
				list_user.add(new list_user(email, display_name, nomor_polisi, rayon, id_anggota, jenis_blazer, status_id,qr_code));
			}
		} catch (Exception e) {
			Log.d("EROR",e.getMessage());
		}
		

	}
	
	
	public ArrayList<list_user> getlist_user(){
		return this.list_user;
	}
	
	
}
