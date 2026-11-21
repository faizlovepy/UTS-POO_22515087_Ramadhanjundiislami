package restoran;

/**
 * Pelanggan adalah orang yang memesan di restoran.
 * Jumlah pesanan dicatat supaya bisa dikenali sebagai pelanggan langganan.
 */
public class Pelanggan {

    /** Batas jumlah pesanan agar pelanggan dihitung sebagai langganan. */
    private static final int BATAS_LANGGANAN = 3;

    private String idPelanggan;
    private String nama;
    private String noTelepon;
    private int jumlahPesanan;

    /** Konstruktor default: meneruskan nilai awal ke konstruktor berparameter. */
    public Pelanggan() {
        this("P000", "Belum diisi", "00000000");
    }

    /** Konstruktor berparameter: jumlah pesanan selalu mulai dari 0. */
    public Pelanggan(String idPelanggan, String nama, String noTelepon) {
        setIdPelanggan(idPelanggan);
        setNama(nama);
        setNoTelepon(noTelepon);
        this.jumlahPesanan = 0;
    }

    // ---------- getter ----------

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public int getJumlahPesanan() {
        return jumlahPesanan;
    }

    // ---------- setter (dengan validasi) ----------

    public void setIdPelanggan(String idPelanggan) {
        if (idPelanggan == null || idPelanggan.trim().isEmpty()) {
            throw new IllegalArgumentException("ID pelanggan tidak boleh kosong.");
        }
        this.idPelanggan = idPelanggan.trim().toUpperCase();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pelanggan tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setNoTelepon(String noTelepon) {
        // nomor telepon hanya angka, panjang 8 sampai 15 digit
        if (noTelepon == null || !noTelepon.trim().matches("\\d{8,15}")) {
            throw new IllegalArgumentException(
                    "No. telepon harus berupa angka sepanjang 8 sampai 15 digit.");
        }
        this.noTelepon = noTelepon.trim();
    }

    // ---------- method perilaku ----------

    /** Menampilkan data satu pelanggan ke layar. */
    public void tampilkanInfo() {
        System.out.printf("%-6s | %-20s | %-15s | %d pesanan%s%n",
                idPelanggan, nama, noTelepon, jumlahPesanan,
                apakahLangganan() ? " (langganan)" : "");
    }

    /** Menambah hitungan pesanan setiap kali pelanggan selesai memesan. */
    public void tambahJumlahPesanan() {
        jumlahPesanan++;
    }

    /** True kalau pelanggan sudah memesan minimal BATAS_LANGGANAN kali. */
    public boolean apakahLangganan() {
        return jumlahPesanan >= BATAS_LANGGANAN;
    }
}
