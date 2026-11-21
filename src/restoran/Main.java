package restoran;

import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main berisi menu console. Semua pelanggaran aturan ditangkap di sini
 * dan ditampilkan sebagai pesan, jadi program tidak berhenti mendadak.
 */
public class Main {

    // Kapasitas sengaja kecil supaya skenario "ADT penuh" gampang diuji. Boleh diubah.
    private static final int KAPASITAS_MENU = 5;

    private static Restoran restoran = new Restoran("Restoran Nusantara", "Kendari", KAPASITAS_MENU);
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean jalan = true;
        try {
            while (jalan) {
                tampilkanMenu();
                int pilihan = bacaInt("Pilih menu (1-7): ");
                try {
                    switch (pilihan) {
                        case 1:
                            menuDataMenu();
                            break;
                        case 2:
                            menuDataPelanggan();
                            break;
                        case 3:
                            menuPesanan();
                            break;
                        case 4:
                            cariMenu();
                            break;
                        case 5:
                            menuAdt();
                            break;
                        case 6:
                            System.out.println();
                            restoran.tampilkanLaporan();
                            break;
                        case 7:
                            jalan = false;
                            System.out.println("Terima kasih, program selesai.");
                            break;
                        default:
                            System.out.println("[INPUT SALAH] Pilihan harus angka 1 sampai 7.");
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    // pelanggaran aturan bisnis atau validasi: tampilkan pesannya, lanjut ke menu
                    System.out.println("[GAGAL] " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nInput berhenti, program ditutup.");
        }
    }

    private static void tampilkanMenu() {
        System.out.println();
        System.out.println("===== " + restoran.getNama().toUpperCase() + " =====");
        System.out.println("1. Data menu (tambah dan tampilkan)");
        System.out.println("2. Data pelanggan (tambah dan tampilkan)");
        System.out.println("3. Pesanan");
        System.out.println("4. Cari menu");
        System.out.println("5. Operasi ADT daftar menu");
        System.out.println("6. Laporan");
        System.out.println("7. Keluar");
    }

    // ---------- menu 1 dan 2 ----------

    private static void menuDataMenu() {
        System.out.println("\n-- Data Menu --");
        System.out.println("1. Tambah menu");
        System.out.println("2. Tampilkan semua menu");
        int pilihan = bacaInt("Pilih: ");
        if (pilihan == 1) {
            tambahMenu();
        } else if (pilihan == 2) {
            restoran.getDaftarMenu().tampilkan();
        } else {
            System.out.println("[INPUT SALAH] Pilihan tidak dikenal.");
        }
    }

    private static void tambahMenu() {
        String kode = bacaString("Kode menu: ");
        String nama = bacaString("Nama menu: ");
        String kategori = bacaString("Kategori (Makanan/Minuman/dll): ");
        int harga = bacaInt("Harga dalam rupiah, angka saja (contoh 15000): ");
        boolean tersedia = bacaYaTidak("Menu tersedia? (y/n): ");
        Menu menu = new Menu(kode, nama, kategori, harga, tersedia);
        restoran.tambahMenu(menu);
        System.out.println("[OK] Menu \"" + menu.getNama() + "\" ditambahkan.");
    }

    private static void menuDataPelanggan() {
        System.out.println("\n-- Data Pelanggan --");
        System.out.println("1. Tambah pelanggan");
        System.out.println("2. Tampilkan semua pelanggan");
        int pilihan = bacaInt("Pilih: ");
        if (pilihan == 1) {
            String id = bacaString("ID pelanggan: ");
            String nama = bacaString("Nama pelanggan: ");
            String telepon = bacaString("No. telepon (angka, 8-15 digit): ");
            Pelanggan pelanggan = new Pelanggan(id, nama, telepon);
            restoran.tambahPelanggan(pelanggan);
            System.out.println("[OK] Pelanggan \"" + pelanggan.getNama() + "\" ditambahkan.");
        } else if (pilihan == 2) {
            ArrayList<Pelanggan> semua = restoran.getDaftarPelanggan();
            if (semua.isEmpty()) {
                System.out.println("(belum ada pelanggan)");
            }
            for (Pelanggan p : semua) {
                p.tampilkanInfo();
            }
        } else {
            System.out.println("[INPUT SALAH] Pilihan tidak dikenal.");
        }
    }

    // ---------- menu 3: pesanan ----------

    private static void menuPesanan() {
        System.out.println("\n-- Pesanan --");
        System.out.println("1. Buat pesanan baru");
        System.out.println("2. Ubah status pesanan");
        System.out.println("3. Tampilkan struk");
        System.out.println("4. Tampilkan semua pesanan");
        int pilihan = bacaInt("Pilih: ");
        switch (pilihan) {
            case 1:
                buatPesanan();
                break;
            case 2:
                ubahStatusPesanan();
                break;
            case 3:
                tampilkanStruk();
                break;
            case 4:
                tampilkanSemuaPesanan();
                break;
            default:
                System.out.println("[INPUT SALAH] Pilihan tidak dikenal.");
        }
    }

    private static void buatPesanan() {
        String idPelanggan = bacaString("ID pelanggan: ");
        Pelanggan pelanggan = restoran.cariPelanggan(idPelanggan);
        if (pelanggan == null) {
            System.out.println("[GAGAL] Pelanggan \"" + idPelanggan
                    + "\" belum terdaftar. Tambahkan dulu lewat menu 2.");
            return;
        }
        String idPesanan = bacaString("ID pesanan: ");
        if (restoran.cariPesanan(idPesanan) != null) {
            System.out.println("[GAGAL] ID pesanan " + idPesanan + " sudah dipakai.");
            return;
        }
        Pesanan pesanan = new Pesanan(idPesanan, pelanggan);

        System.out.println("Tambah item pesanan. Kosongkan kode/nama menu lalu Enter kalau sudah selesai.");
        while (true) {
            String kunci = bacaString("Kode atau nama menu: ");
            if (kunci.isEmpty()) {
                break;
            }
            Menu menu = restoran.cariMenu(kunci);
            if (menu == null) {
                System.out.println("[GAGAL] Menu \"" + kunci + "\" tidak ditemukan.");
                continue;
            }
            int jumlah = bacaInt("Jumlah porsi: ");
            String catatan = bacaString("Catatan (boleh kosong): ");
            try {
                pesanan.tambahItem(menu, jumlah, catatan);
                System.out.println("[OK] " + jumlah + " x " + menu.getNama() + " masuk ke pesanan.");
            } catch (IllegalArgumentException e) {
                System.out.println("[GAGAL] " + e.getMessage());
            }
        }

        // aturan bisnis: pesanan kosong ditolak di sini, dan pesanannya tidak disimpan
        pesanan.ubahStatus("DIPROSES");
        restoran.buatPesanan(pesanan);
        System.out.println("[OK] Pesanan " + pesanan.getIdPesanan() + " dibuat, status DIPROSES.");
        System.out.println();
        pesanan.tampilkanStruk();
    }

    private static void ubahStatusPesanan() {
        String id = bacaString("ID pesanan: ");
        Pesanan pesanan = restoran.cariPesanan(id);
        if (pesanan == null) {
            System.out.println("[GAGAL] Pesanan \"" + id + "\" tidak ditemukan.");
            return;
        }
        System.out.println("Status sekarang: " + pesanan.getStatus());
        String statusBaru = bacaString("Status baru (DIPROSES/SELESAI): ");
        pesanan.ubahStatus(statusBaru);
        System.out.println("[OK] Status pesanan " + pesanan.getIdPesanan() + " sekarang "
                + pesanan.getStatus() + ".");
    }

    private static void tampilkanStruk() {
        String id = bacaString("ID pesanan: ");
        Pesanan pesanan = restoran.cariPesanan(id);
        if (pesanan == null) {
            System.out.println("[GAGAL] Pesanan \"" + id + "\" tidak ditemukan.");
            return;
        }
        System.out.println();
        pesanan.tampilkanStruk();
    }

    private static void tampilkanSemuaPesanan() {
        ArrayList<Pesanan> semua = restoran.getDaftarPesanan();
        if (semua.isEmpty()) {
            System.out.println("(belum ada pesanan)");
            return;
        }
        System.out.printf("%-6s | %-20s | %-9s | %s%n", "ID", "PELANGGAN", "STATUS", "TOTAL");
        for (Pesanan p : semua) {
            System.out.printf("%-6s | %-20s | %-9s | %s%n",
                    p.getIdPesanan(), p.getPelanggan().getNama(), p.getStatus(),
                    String.format(Locale.forLanguageTag("id-ID"), "Rp%,.0f", p.hitungTotal()));
        }
    }

    // ---------- menu 4: cari ----------

    private static void cariMenu() {
        String kunci = bacaString("Cari menu (kode atau nama): ");
        Menu menu = restoran.cariMenu(kunci);
        if (menu == null) {
            System.out.println("[GAGAL] Menu \"" + kunci + "\" tidak ditemukan.");
            return;
        }
        System.out.printf("%-6s | %-20s | %-10s | %12s | %s%n",
                "KODE", "NAMA", "KATEGORI", "HARGA", "STATUS");
        menu.tampilkanInfo();
    }

    // ---------- menu 5: operasi ADT ----------

    private static void menuAdt() {
        SortedListMenu adt = restoran.getDaftarMenu();
        System.out.println("\n-- Operasi ADT Sorted List (daftar menu) --");
        System.out.println("1. insert (sisipkan menu, tetap terurut)");
        System.out.println("2. remove (hapus menu berdasarkan nama)");
        System.out.println("3. search (cari indeks berdasarkan nama)");
        System.out.println("4. get (ambil menu pada indeks)");
        System.out.println("5. tampilkan isi");
        System.out.println("6. size, isEmpty, isFull");
        int pilihan = bacaInt("Pilih: ");
        switch (pilihan) {
            case 1:
                tambahMenu();
                adt.tampilkan();
                break;
            case 2:
                String namaHapus = bacaString("Nama menu yang dihapus: ");
                if (adt.remove(namaHapus)) {
                    System.out.println("[OK] Menu \"" + namaHapus + "\" dihapus.");
                } else {
                    System.out.println("[GAGAL] Menu \"" + namaHapus + "\" tidak ditemukan.");
                }
                adt.tampilkan();
                break;
            case 3:
                String namaCari = bacaString("Nama menu yang dicari: ");
                int indeks = adt.search(namaCari);
                if (indeks >= 0) {
                    System.out.println("[OK] \"" + namaCari + "\" ada di indeks " + indeks + ".");
                } else {
                    System.out.println("[GAGAL] Menu \"" + namaCari + "\" tidak ditemukan (hasil -1).");
                }
                break;
            case 4:
                int index = bacaInt("Indeks (mulai dari 0): ");
                Menu menu = adt.get(index);
                System.out.printf("%-6s | %-20s | %-10s | %12s | %s%n",
                        "KODE", "NAMA", "KATEGORI", "HARGA", "STATUS");
                menu.tampilkanInfo();
                break;
            case 5:
                adt.tampilkan();
                break;
            case 6:
                System.out.println("size    : " + adt.size());
                System.out.println("isEmpty : " + adt.isEmpty());
                System.out.println("isFull  : " + adt.isFull() + " (kapasitas " + adt.getKapasitas() + ")");
                break;
            default:
                System.out.println("[INPUT SALAH] Pilihan tidak dikenal.");
        }
    }

    // ---------- bantuan input ----------

    /** Membaca satu baris teks. Spasi di awal dan akhir dibuang. */
    private static String bacaString(String pesan) {
        System.out.print(pesan);
        return input.nextLine().trim();
    }

    /** Membaca angka bulat. Kalau input salah atau kosong, pertanyaan diulang. */
    private static int bacaInt(String pesan) {
        while (true) {
            String teks = bacaString(pesan);
            if (teks.isEmpty()) {
                System.out.println("[INPUT SALAH] Input tidak boleh kosong.");
                continue;
            }
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("[INPUT SALAH] \"" + teks + "\" bukan angka bulat. Coba lagi.");
            }
        }
    }

    /** Membaca jawaban y atau n. Jawaban lain diulang. */
    private static boolean bacaYaTidak(String pesan) {
        while (true) {
            String teks = bacaString(pesan).toLowerCase();
            if (teks.equals("y")) {
                return true;
            }
            if (teks.equals("n")) {
                return false;
            }
            System.out.println("[INPUT SALAH] Jawab dengan y atau n.");
        }
    }
}
