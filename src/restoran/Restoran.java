package restoran;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Restoran menyimpan semua data: menu (di ADT SortedListMenu), pelanggan, dan pesanan.
 * Main hanya berbicara dengan class ini, jadi aturan penyimpanan ada di satu tempat.
 */
public class Restoran {

    private static final String STATUS_SELESAI = "SELESAI";

    private String nama;
    private String alamat;
    private SortedListMenu daftarMenu;
    private ArrayList<Pelanggan> daftarPelanggan;
    private ArrayList<Pesanan> daftarPesanan;

    /** Konstruktor default: restoran dengan nama bawaan dan kapasitas menu 10. */
    public Restoran() {
        this("Restoran Baru", "-", 10);
    }

    /** Konstruktor berparameter: kapasitas menu ditentukan saat restoran dibuat. */
    public Restoran(String nama, String alamat, int kapasitasMenu) {
        setNama(nama);
        setAlamat(alamat);
        this.daftarMenu = new SortedListMenu(kapasitasMenu);
        this.daftarPelanggan = new ArrayList<>();
        this.daftarPesanan = new ArrayList<>();
    }

    // ---------- getter ----------

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public SortedListMenu getDaftarMenu() {
        return daftarMenu;
    }

    /** Mengembalikan salinan, supaya daftar asli tidak bisa diubah dari luar. */
    public ArrayList<Pelanggan> getDaftarPelanggan() {
        return new ArrayList<>(daftarPelanggan);
    }

    /** Mengembalikan salinan, supaya daftar asli tidak bisa diubah dari luar. */
    public ArrayList<Pesanan> getDaftarPesanan() {
        return new ArrayList<>(daftarPesanan);
    }

    // ---------- setter (dengan validasi) ----------

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama restoran tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat restoran tidak boleh kosong.");
        }
        this.alamat = alamat.trim();
    }

    // ---------- method perilaku ----------

    /** Menambah menu ke daftar terurut. Kode dan nama menu tidak boleh kembar. */
    public boolean tambahMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu yang ditambahkan tidak boleh kosong.");
        }
        if (cariMenuBerdasarkanKode(menu.getKode()) != null) {
            throw new IllegalArgumentException("Kode menu " + menu.getKode() + " sudah dipakai.");
        }
        return daftarMenu.insert(menu); // ADT yang mengecek penuh dan nama kembar
    }

    /** Mencari menu berdasarkan nama atau kode. Mengembalikan null kalau tidak ada. */
    public Menu cariMenu(String kunci) {
        if (kunci == null || kunci.trim().isEmpty()) {
            return null;
        }
        int indeks = daftarMenu.search(kunci); // binary search lewat nama
        if (indeks >= 0) {
            return daftarMenu.get(indeks);
        }
        return cariMenuBerdasarkanKode(kunci);
    }

    /** Menambah pelanggan baru. ID pelanggan tidak boleh kembar. */
    public boolean tambahPelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) {
            throw new IllegalArgumentException("Pelanggan yang ditambahkan tidak boleh kosong.");
        }
        if (cariPelanggan(pelanggan.getIdPelanggan()) != null) {
            throw new IllegalArgumentException(
                    "ID pelanggan " + pelanggan.getIdPelanggan() + " sudah terdaftar.");
        }
        daftarPelanggan.add(pelanggan);
        return true;
    }

    /** Mencari pelanggan berdasarkan ID. Mengembalikan null kalau tidak ada. */
    public Pelanggan cariPelanggan(String id) {
        if (id == null) {
            return null;
        }
        for (Pelanggan p : daftarPelanggan) {
            if (p.getIdPelanggan().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    /** Mencatat pesanan. Pelanggannya harus sudah terdaftar dan ID pesanan belum dipakai. */
    public boolean buatPesanan(Pesanan pesanan) {
        if (pesanan == null) {
            throw new IllegalArgumentException("Pesanan tidak boleh kosong.");
        }
        if (cariPelanggan(pesanan.getPelanggan().getIdPelanggan()) == null) {
            throw new IllegalArgumentException("Pelanggan belum terdaftar di restoran ini.");
        }
        if (cariPesanan(pesanan.getIdPesanan()) != null) {
            throw new IllegalArgumentException(
                    "ID pesanan " + pesanan.getIdPesanan() + " sudah dipakai.");
        }
        daftarPesanan.add(pesanan);
        return true;
    }

    /** Mencari pesanan berdasarkan ID. Mengembalikan null kalau tidak ada. */
    public Pesanan cariPesanan(String id) {
        if (id == null) {
            return null;
        }
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    /** Pendapatan dihitung dari total pesanan yang sudah SELESAI. */
    public double hitungPendapatan() {
        double total = 0;
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus().equals(STATUS_SELESAI)) {
                total += p.hitungTotal();
            }
        }
        return total;
    }

    /** Mencetak ringkasan jumlah data dan pendapatan. */
    public void tampilkanLaporan() {
        int selesai = 0;
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus().equals(STATUS_SELESAI)) {
                selesai++;
            }
        }
        System.out.println("=== LAPORAN " + nama.toUpperCase() + " ===");
        System.out.println("Alamat           : " + alamat);
        System.out.println("Jumlah menu      : " + daftarMenu.size() + " dari "
                + daftarMenu.getKapasitas() + " slot");
        System.out.println("Jumlah pelanggan : " + daftarPelanggan.size());
        System.out.println("Jumlah pesanan   : " + daftarPesanan.size() + " (selesai: " + selesai + ")");
        System.out.println(String.format(Locale.forLanguageTag("id-ID"),
                "Total pendapatan : Rp%,.0f", hitungPendapatan()));
    }

    // ---------- bantuan internal ----------

    /** Pencarian linear, karena ADT hanya terurut berdasarkan nama. */
    private Menu cariMenuBerdasarkanKode(String kode) {
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            if (menu.getKode().equalsIgnoreCase(kode.trim())) {
                return menu;
            }
        }
        return null;
    }
}
