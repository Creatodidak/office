package id.creatodidak.e_disposisi.model;

public class SuratRiwayat {

    private String id;
    private String nomor_surat;
    private String asal_surat;
    private String tanggal_surat;
    private String proses;
    private String diteruskan_kepada;
    private String disposisi_surat;
    private String file;
    private String prioritas;
    private String privasi;
    private String user_input;
    private String jenis_surat;
    private String tentang;
    public SuratRiwayat(String tentang, String id, String nomor_surat, String asal_surat, String tanggal_surat, String proses, String diteruskan_kepada, String disposisi_surat, String file, String prioritas, String privasi, String user_input, String jenis_surat) {

        this.id = id;
        this.nomor_surat = nomor_surat;
        this.asal_surat = asal_surat;
        this.tanggal_surat = tanggal_surat;
        this.proses = proses;
        this.diteruskan_kepada = diteruskan_kepada;
        this.disposisi_surat = disposisi_surat;
        this.prioritas = prioritas;
        this.privasi = privasi;
        this.user_input = user_input;
        this.jenis_surat = jenis_surat;
        this.file = file;
        this.tentang = tentang;
    }

    public String getid() {return id;}
    public String getnomor_surat() {return nomor_surat;}
    public String getasal_surat() {return asal_surat;}
    public String gettanggal_surat() {return tanggal_surat;}
    public String getproses() {return proses;}
    public String getditeruskan_kepada() {return diteruskan_kepada;}
    public String getdisposisi_surat() {return disposisi_surat;}
    public String getfile() {return file;}
    public String getprioritas() {return prioritas;}
    public String getprivasi() {return privasi;}
    public String getuser_input() {return user_input;}
    public String getjenis_surat() {return jenis_surat;}
    public String getTentang() {return tentang;}
}