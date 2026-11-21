package restoran;

import java.util.Locale;

/**
 * Menu mewakili satu item makanan atau minuman yang dijual restoran.
 * Semua atribut private, perubahan nilai lewat setter yang sudah divalidasi.
 */
public class Menu {

    private String kode;
    private String nama;
    private String kategori;
    private double harga;
    private boolean tersedia;

    /** Konstruktor default: meneruskan nilai awal ke konstruktor berparameter. */
    public Menu() {
        this("-", "Belum diisi", "Umum", 1.0, true);
    }

    /** Konstruktor berparameter: semua nilai dicek lewat setter sebelum disimpan. */
    public Menu(String kode, String nama, String kategori, double harga, boolean tersedia) {
        setKode(kode);
        setNama(nama);
        setKategori(kategori);
        setHarga(harga);
        this.tersedia = tersedia;
    }

    // ---------- getter ----------

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public double getHarga() {
        return harga;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    // ---------- setter (dengan validasi) ----------

    public void setKode(String kode) {
        if (kode == null || kode.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode menu tidak boleh kosong.");
        }
        this.kode = kode.trim().toUpperCase();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama menu tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setKategori(String kategori) {
        if (kategori == null || kategori.trim().isEmpty()) {
            throw new IllegalArgumentException("Kategori menu tidak boleh kosong.");
        }
        this.kategori = kategori.trim();
    }

    public void setHarga(double harga) {
        // aturan tambahan: harga harus positif
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga menu harus lebih dari 0.");
        }
        this.harga = harga;
    }

    // ---------- method perilaku ----------

    /** Menampilkan detail satu menu ke layar. */
    public void tampilkanInfo() {
        String status = tersedia ? "Tersedia" : "Habis";
        System.out.printf("%-6s | %-20s | %-10s | %12s | %s%n",
                kode, nama, kategori,
                String.format(Locale.forLanguageTag("id-ID"), "Rp%,.0f", harga),
                status);
    }

    /** Menghitung harga untuk sejumlah porsi menu ini. */
    public double hitungSubtotal(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah porsi harus lebih dari 0.");
        }
        return harga * jumlah;
    }

    /** Mengubah status ketersediaan menu (true = tersedia, false = habis). */
    public void ubahKetersediaan(boolean tersedia) {
        this.tersedia = tersedia;
    }
}
