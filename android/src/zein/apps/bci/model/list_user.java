package zein.apps.bci.model;

public class list_user {
	
	public String email = "";
	public String display_name = "";
	public String nomor_polisi = "";
	public String rayon = "";
	public String id_anggota = "";
	public String jenis_blazer = "";
	public String status_id = "";
	public String qr_code = "";

	public list_user (
			String email,
			String display_name, 
			String nomor_polisi, 
			String rayon, 
			String id_anggota, 
			String jenis_blazer,
			String status_id,
			String qr_code
			){
		this.email = email;
		this.display_name = display_name;
		this.nomor_polisi = nomor_polisi;
		this.rayon = rayon;
		this.id_anggota = id_anggota;
		this.jenis_blazer = jenis_blazer;
		this.status_id = status_id;
		this.qr_code = qr_code;
	}
}
