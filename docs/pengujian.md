# Pengujian

Program dijalankan dari IntelliJ lewat `Main`. Kapasitas daftar menu 5. Sebelas skenario di bawah dijalankan berurutan dalam satu sesi, karena beberapa skenario memakai data dari skenario sebelumnya. Screenshot tiap skenario ada di folder `screenshots`.

Kolom "Hasil aktual" dan "Status" diisi setelah skenario dijalankan.

| No | Skenario | Input | Hasil yang diharapkan | Hasil aktual | Status |
|----|----------|-------|-----------------------|--------------|--------|
| 1 | Normal: tambah 3 menu dan tampilkan | Menu 1, pilih 1, tiga kali:<br>M01, Nasi Goreng, Makanan, 15000, y<br>M02, Es Teh, Minuman, 5000, y<br>M03, Sate, Makanan, 20000, n<br>Lalu Menu 1, pilih 2 | Tiga pesan `[OK] Menu "..." ditambahkan.` Daftar tampil urut A-Z: Es Teh, Nasi Goreng, Sate (Sate berstatus Habis). Tertulis `Terisi 3 dari 5 slot.` | | |
| 2 | Normal: tambah pelanggan, buat pesanan, cetak struk | Menu 2, pilih 1: P01, Budi, 081234567890<br>Menu 3, pilih 1: P01, O01<br>Item `m01`, jumlah 2, catatan `tanpa pedas`<br>Item `es teh`, jumlah 3, catatan kosong<br>Lalu Enter kosong | `[OK] Pelanggan "Budi" ditambahkan.` Pesanan O01 dibuat dengan status DIPROSES. Struk memuat Nasi Goreng x2 Rp30.000, Es Teh x3 Rp15.000, Subtotal Rp45.000, Pajak 10% Rp4.500, Total Rp49.500 | | |
| 3 | Normal: cari menu lewat nama dan lewat kode | Menu 4, ketik `Nasi Goreng`<br>Menu 4, ketik `m02` | Baris Nasi Goreng (M01) tampil, lalu baris Es Teh (M02) tampil | | |
| 4 | Normal: ubah status ke SELESAI dan lihat laporan | Menu 3, pilih 2: O01, status `SELESAI`<br>Lalu Menu 6 | `[OK] Status pesanan O01 sekarang SELESAI.` Laporan: menu 3 dari 5 slot, pelanggan 1, pesanan 1 (selesai: 1), total pendapatan Rp49.500 | | |
| 5 | Pelanggaran aturan: pesanan tanpa menu | Menu 3, pilih 1: P01, O02<br>Langsung Enter kosong pada "Kode atau nama menu" | `[GAGAL] Pesanan harus berisi minimal 1 menu.` Pesanan O02 tidak tersimpan, program kembali ke menu utama | | |
| 6 | Pelanggaran aturan: memesan menu yang habis | Menu 3, pilih 1: P01, O03<br>Item `sate`, jumlah 1, catatan kosong<br>Lalu Enter kosong | `[GAGAL] Menu Sate sedang habis.` Karena pesanan belum punya item, muncul juga `[GAGAL] Pesanan harus berisi minimal 1 menu.` | | |
| 7 | Pelanggaran aturan: status mundur | Menu 3, pilih 2: O01, status `BARU` | `[GAGAL] Status tidak bisa diubah dari SELESAI ke BARU. Urutannya BARU, DIPROSES, SELESAI.` | | |
| 8 | Input tidak valid: huruf di kolom angka | Pada "Pilih menu (1-7)" ketik `x` | `[INPUT SALAH] "x" bukan angka bulat. Coba lagi.` Pertanyaan diulang, program tidak berhenti | | |
| 9 | Input tidak valid: nilai kosong | Menu 1, pilih 1: M09, nama dikosongkan (Enter), Makanan, 10000, y | `[GAGAL] Nama menu tidak boleh kosong.` Menu M09 tidak tersimpan | | |
| 10 | ADT penuh | Menu 5, pilih 1, tiga kali:<br>M04, Kopi, Minuman, 7000, y<br>M05, Soto, Makanan, 12000, y<br>M06, Teh, Minuman, 4000, y | Dua insert pertama `[OK]`, daftar menjadi `Terisi 5 dari 5 slot.` Insert ketiga: `[GAGAL] Daftar menu penuh (kapasitas 5). Hapus menu dulu.` | | |
| 11 | ADT kosong | Menu 5, pilih 2, lima kali dengan nama: Es Teh, Kopi, Nasi Goreng, Sate, Soto<br>Menu 5, pilih 2, nama `Soto`<br>Menu 5, pilih 4, indeks 0 | Lima hapusan `[OK]`. Hapus saat kosong: `[GAGAL] Daftar menu kosong, tidak ada yang bisa dihapus.` Get saat kosong: `[GAGAL] Daftar menu kosong.` | | |

## Screenshot

| No | Berkas |
|----|--------|
| 1 | `screenshots/01-tambah-menu.png` |
| 2 | `screenshots/02-pesanan-struk.png` |
| 3 | `screenshots/03-cari-menu.png` |
| 4 | `screenshots/04-status-laporan.png` |
| 5 | `screenshots/05-pesanan-kosong.png` |
| 6 | `screenshots/06-menu-habis.png` |
| 7 | `screenshots/07-status-mundur.png` |
| 8 | `screenshots/08-input-huruf.png` |
| 9 | `screenshots/09-input-kosong.png` |
| 10 | `screenshots/10-adt-penuh.png` |
| 11 | `screenshots/11-adt-kosong.png` |
