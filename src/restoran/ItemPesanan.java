package restoran;

import java.util.Locale;

/**
 * ItemPesanan adalah satu baris dalam pesanan: menu apa, berapa porsi,
 * dan catatan khusus (misalnya "tanpa pedas").
 */
public class ItemPesanan {

    private Menu menu;
    private int jumlah;
    private String catatan;
    private double subtotal;

    /** Konstruktor default: satu porsi menu bawaan tanpa catatan. */
    public ItemPesanan() {
        this(new Menu(), 1, "");
    }

    /** Konstruktor berparameter: nilai dicek lewat setter, subtotal langsung dihitung. */
    public ItemPesanan(Menu menu, int jumlah, String catatan) {
        setMenu(menu);
        setJumlah(jumlah);
        setCatatan(catatan);
        hitungSubtotal();
    }

    // ---------- getter ----------

    public Menu getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getCatatan() {
        return catatan;
    }

    public double getSubtotal() {
        // dihitung ulang supaya tidak basi kalau jumlah atau menu berubah
        return hitungSubtotal();
    }

    // ---------- setter (dengan validasi) ----------

    public void setMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu pada item pesanan tidak boleh kosong.");
        }
        this.menu = menu;
    }

    public void setJumlah(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah porsi harus lebih dari 0.");
        }
        this.jumlah = jumlah;
    }

    public void setCatatan(String catatan) {
        // catatan boleh kosong, jadi null diganti string kosong
        this.catatan = (catatan == null) ? "" : catatan.trim();
    }

    // ---------- method perilaku ----------

    /** Menghitung subtotal = harga menu x jumlah porsi, lalu menyimpannya. */
    public double hitungSubtotal() {
        this.subtotal = menu.hitungSubtotal(jumlah);
        return subtotal;
    }

    /** Mengubah jumlah porsi dan memperbarui subtotal. */
    public void ubahJumlah(int jumlah) {
        setJumlah(jumlah);
        hitungSubtotal();
    }

    /** Menampilkan satu baris item ke layar. */
    public void tampilkanInfo() {
        String baris = String.format(Locale.forLanguageTag("id-ID"),
                "%-20s x%-3d %12s", menu.getNama(), jumlah,
                String.format(Locale.forLanguageTag("id-ID"), "Rp%,.0f", hitungSubtotal()));
        if (!catatan.isEmpty()) {
            baris += "  (" + catatan + ")";
        }
        System.out.println(baris);
    }
}
