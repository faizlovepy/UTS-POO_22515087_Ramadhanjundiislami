package restoran;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Pesanan mencatat apa saja yang dipesan satu pelanggan.
 * Aturan bisnis: pesanan harus berisi minimal 1 menu sebelum diproses,
 * dan pajak 10 persen dihitung otomatis dari subtotal.
 */
public class Pesanan {

    private static final double TARIF_PAJAK = 0.10;

    // status berjalan berurutan: BARU -> DIPROSES -> SELESAI
    private static final String BARU = "BARU";
    private static final String DIPROSES = "DIPROSES";
    private static final String SELESAI = "SELESAI";

    private String idPesanan;
    private Pelanggan pelanggan;
    private ArrayList<ItemPesanan> daftarItem;
    private String status;

    /** Konstruktor default: pesanan kosong atas nama pelanggan bawaan. */
    public Pesanan() {
        this("O000", new Pelanggan());
    }

    /** Konstruktor berparameter: pesanan baru selalu kosong dan berstatus BARU. */
    public Pesanan(String idPesanan, Pelanggan pelanggan) {
        setIdPesanan(idPesanan);
        setPelanggan(pelanggan);
        this.daftarItem = new ArrayList<>();
        this.status = BARU;
    }

    // ---------- getter ----------

    public String getIdPesanan() {
        return idPesanan;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    /** Mengembalikan salinan daftar, supaya isi asli tidak bisa diubah dari luar. */
    public ArrayList<ItemPesanan> getDaftarItem() {
        return new ArrayList<>(daftarItem);
    }

    public String getStatus() {
        return status;
    }

    // ---------- setter (dengan validasi) ----------

    public void setIdPesanan(String idPesanan) {
        if (idPesanan == null || idPesanan.trim().isEmpty()) {
            throw new IllegalArgumentException("ID pesanan tidak boleh kosong.");
        }
        this.idPesanan = idPesanan.trim().toUpperCase();
    }

    public void setPelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) {
            throw new IllegalArgumentException("Pesanan harus punya pelanggan.");
        }
        this.pelanggan = pelanggan;
    }

    // ---------- method perilaku ----------

    /** Menambah satu item ke pesanan. Hanya bisa selama status masih BARU. */
    public boolean tambahItem(Menu menu, int jumlah, String catatan) {
        if (!status.equals(BARU)) {
            throw new IllegalArgumentException(
                    "Pesanan sudah " + status + ", item tidak bisa ditambah lagi.");
        }
        if (menu == null) {
            throw new IllegalArgumentException("Menu tidak ditemukan.");
        }
        if (!menu.isTersedia()) {
            throw new IllegalArgumentException("Menu " + menu.getNama() + " sedang habis.");
        }
        daftarItem.add(new ItemPesanan(menu, jumlah, catatan));
        return true;
    }

    /** Jumlah harga semua item sebelum pajak. */
    public double hitungSubtotal() {
        double total = 0;
        for (ItemPesanan item : daftarItem) {
            total += item.hitungSubtotal();
        }
        return total;
    }

    /** Pajak 10 persen dari subtotal, dihitung otomatis. */
    public double hitungPajak() {
        return hitungSubtotal() * TARIF_PAJAK;
    }

    /** Total bayar = subtotal + pajak. */
    public double hitungTotal() {
        return hitungSubtotal() + hitungPajak();
    }

    /**
     * Mengubah status pesanan. Urutannya harus BARU, DIPROSES, lalu SELESAI.
     * Pesanan kosong tidak boleh diproses.
     */
    public boolean ubahStatus(String statusBaru) {
        if (statusBaru == null) {
            throw new IllegalArgumentException("Status baru tidak boleh kosong.");
        }
        String target = statusBaru.trim().toUpperCase();

        if (target.equals(DIPROSES) && status.equals(BARU)) {
            // aturan bisnis: minimal 1 menu
            if (daftarItem.isEmpty()) {
                throw new IllegalArgumentException("Pesanan harus berisi minimal 1 menu.");
            }
            status = DIPROSES;
        } else if (target.equals(SELESAI) && status.equals(DIPROSES)) {
            status = SELESAI;
            pelanggan.tambahJumlahPesanan(); // pesanan selesai, catat di riwayat pelanggan
        } else {
            throw new IllegalArgumentException(
                    "Status tidak bisa diubah dari " + status + " ke " + target
                    + ". Urutannya BARU, DIPROSES, SELESAI.");
        }
        return true;
    }

    /** Mencetak struk lengkap dengan pajak dan total. */
    public void tampilkanStruk() {
        Locale id = Locale.forLanguageTag("id-ID");
        System.out.println("=== STRUK PESANAN " + idPesanan + " ===");
        System.out.println("Pelanggan : " + pelanggan.getNama());
        System.out.println("Status    : " + status);
        System.out.println("--------------------------------------------");
        if (daftarItem.isEmpty()) {
            System.out.println("(belum ada item)");
        }
        for (ItemPesanan item : daftarItem) {
            item.tampilkanInfo();
        }
        System.out.println("--------------------------------------------");
        System.out.println(String.format(id, "Subtotal  : Rp%,.0f", hitungSubtotal()));
        System.out.println(String.format(id, "Pajak %d%% : Rp%,.0f",
                (int) Math.round(TARIF_PAJAK * 100), hitungPajak()));
        System.out.println(String.format(id, "Total     : Rp%,.0f", hitungTotal()));
    }
}
