package restoran;

import java.util.ArrayList;

/**
 * ADT Sorted List untuk menyimpan Menu, selalu terurut A-Z berdasarkan nama menu.
 * Atribut internal private, dan pemakai class ini hanya lewat operasi publik.
 * Kondisi penuh, kosong, dan data tidak ditemukan ditangani dengan pesan yang jelas,
 * jadi program tidak berhenti mendadak.
 */
public class SortedListMenu {

    private ArrayList<Menu> data;
    private int kapasitas;

    /** Membuat daftar kosong dengan kapasitas tetap. */
    public SortedListMenu(int kapasitas) {
        if (kapasitas <= 0) {
            throw new IllegalArgumentException("Kapasitas daftar menu harus lebih dari 0.");
        }
        this.kapasitas = kapasitas;
        this.data = new ArrayList<>();
    }

    // ---------- operasi ADT ----------

    /**
     * Menyisipkan menu pada posisi yang tepat supaya daftar tetap terurut.
     * Prasyarat: daftar belum penuh dan nama menu belum dipakai.
     */
    public boolean insert(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu yang disisipkan tidak boleh kosong.");
        }
        if (isFull()) {
            throw new IllegalStateException(
                    "Daftar menu penuh (kapasitas " + kapasitas + "). Hapus menu dulu.");
        }
        int hasil = cariPosisi(menu.getNama());
        if (hasil >= 0) {
            throw new IllegalArgumentException(
                    "Menu dengan nama \"" + menu.getNama() + "\" sudah ada.");
        }
        int titikSisip = -(hasil + 1);
        data.add(titikSisip, menu); // elemen sesudahnya bergeser satu ke kanan
        return true;
    }

    /** Menghapus menu berdasarkan nama. Mengembalikan false kalau tidak ditemukan. */
    public boolean remove(String nama) {
        if (isEmpty()) {
            throw new IllegalStateException("Daftar menu kosong, tidak ada yang bisa dihapus.");
        }
        int indeks = search(nama);
        if (indeks < 0) {
            return false;
        }
        data.remove(indeks);
        return true;
    }

    /** Mencari menu berdasarkan nama. Mengembalikan indeks, atau -1 kalau tidak ada. */
    public int search(String nama) {
        if (nama == null || isEmpty()) {
            return -1;
        }
        int hasil = cariPosisi(nama.trim());
        return (hasil >= 0) ? hasil : -1;
    }

    /** Mengambil menu pada indeks tertentu (mulai dari 0). */
    public Menu get(int index) {
        if (isEmpty()) {
            throw new IllegalStateException("Daftar menu kosong.");
        }
        if (index < 0 || index >= data.size()) {
            throw new IllegalArgumentException(
                    "Indeks " + index + " di luar jangkauan (0 sampai " + (data.size() - 1) + ").");
        }
        return data.get(index);
    }

    /** Menampilkan seluruh isi daftar menu, urut berdasarkan nama. */
    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("(daftar menu masih kosong)");
            return;
        }
        System.out.printf("%-6s | %-20s | %-10s | %12s | %s%n",
                "KODE", "NAMA", "KATEGORI", "HARGA", "STATUS");
        for (Menu menu : data) {
            menu.tampilkanInfo();
        }
        System.out.println("Terisi " + data.size() + " dari " + kapasitas + " slot.");
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean isFull() {
        return data.size() >= kapasitas;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    // ---------- bantuan internal ----------

    /**
     * Binary search pada nama menu (huruf besar kecil tidak dibedakan).
     * Kalau ketemu, hasilnya indeks (0 atau lebih). Kalau tidak, hasilnya
     * -(titik sisip + 1), jadi titik sisip = -(hasil + 1).
     */
    private int cariPosisi(String nama) {
        int kiri = 0;
        int kanan = data.size() - 1;
        while (kiri <= kanan) {
            int tengah = (kiri + kanan) / 2;
            int banding = data.get(tengah).getNama().compareToIgnoreCase(nama);
            if (banding == 0) {
                return tengah;
            } else if (banding < 0) {
                kiri = tengah + 1;
            } else {
                kanan = tengah - 1;
            }
        }
        return -(kiri + 1);
    }
}
