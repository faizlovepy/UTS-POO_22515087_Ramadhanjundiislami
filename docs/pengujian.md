# Pengujian

Program dijalankan dari IntelliJ lewat `Main`. Kapasitas daftar menu 5. Sebelas skenario di bawah dijalankan berurutan dalam satu sesi, karena beberapa skenario memakai data dari skenario sebelumnya. Screenshot tiap skenario ada di folder `screenshots`.

## 1. Normal: tambah 3 menu dan tampilkan

- Input: Menu 1, pilih 1, tiga kali: M01 Nasi Goreng Makanan 15000 y; M02 Es Teh Minuman 5000 y; M03 Sate Makanan 20000 n. Lalu Menu 1, pilih 2.
- Hasil yang diharapkan: tiga pesan `[OK] Menu "..." ditambahkan.` Daftar tampil urut A-Z: Es Teh, Nasi Goreng, Sate (Sate berstatus Habis). Tertulis `Terisi 3 dari 5 slot.`
- Hasil aktual: Tiga menu masuk dengan pesan [OK]. Daftar tampil urut Es Teh, Nasi Goreng, Sate, dan Sate berstatus Habis. Di bawah daftar tertulis Terisi 3 dari 5 slot.
- Status: Lulus
- Screenshot: `screenshots/01-tambah-menu.png`

## 2. Normal: tambah pelanggan, buat pesanan, cetak struk

- Input: Menu 2, pilih 1: P01, Budi, 081234567890. Menu 3, pilih 1: P01, O01. Item `m01` jumlah 2 catatan `tanpa pedas`. Item `es teh` jumlah 3 catatan kosong. Lalu Enter kosong.
- Hasil yang diharapkan: `[OK] Pelanggan "Budi" ditambahkan.` Pesanan O01 dibuat dengan status DIPROSES. Struk memuat Nasi Goreng x2 Rp30.000, Es Teh x3 Rp15.000, Subtotal Rp45.000, Pajak 10% Rp4.500, Total Rp49.500.
- Hasil aktual: Pelanggan Budi masuk dengan pesan [OK], dan pesanan O01 dibuat dengan status DIPROSES. Struk menampilkan Nasi Goreng 2 porsi Rp30.000 dengan catatan tanpa pedas, Es Teh 3 porsi Rp15.000, subtotal Rp45.000, pajak 10% Rp4.500, dan total Rp49.500.
- Status: Lulus
- Screenshot: `screenshots/02-pesanan-struk.png`

## 3. Normal: cari menu lewat nama dan lewat kode

- Input: Menu 4, ketik `Nasi Goreng`. Menu 4, ketik `m02`.
- Hasil yang diharapkan: baris Nasi Goreng (M01) tampil, lalu baris Es Teh (M02) tampil.
- Hasil aktual: Pencarian Nasi Goreng menampilkan baris M01. Pencarian m02 menampilkan baris Es Teh, jadi pencarian jalan lewat nama maupun kode.
- Status: Lulus
- Screenshot: `screenshots/03-cari-menu.png`

## 4. Normal: ubah status ke SELESAI dan lihat laporan

- Input: Menu 3, pilih 2: O01, status `SELESAI`. Lalu Menu 6.
- Hasil yang diharapkan: `[OK] Status pesanan O01 sekarang SELESAI.` Laporan: menu 3 dari 5 slot, pelanggan 1, pesanan 1 (selesai: 1), total pendapatan Rp49.500.
- Hasil aktual: Status O01 berubah ke SELESAI dengan pesan [OK]. Laporan menampilkan 3 dari 5 slot menu, 1 pelanggan, 1 pesanan (selesai: 1), dan total pendapatan Rp49.500.
- Status: Lulus
- Screenshot: `screenshots/04-status-laporan.png`

## 5. Pelanggaran aturan: pesanan tanpa menu

- Input: Menu 3, pilih 1: P01, O02. Langsung Enter kosong pada "Kode atau nama menu".
- Hasil yang diharapkan: `[GAGAL] Pesanan harus berisi minimal 1 menu.` Pesanan O02 tidak tersimpan dan program kembali ke menu utama.
- Hasil aktual: Program menolak pesanan dengan pesan "Pesanan harus berisi minimal 1 menu." Pesanan O02 tidak tersimpan, dan program kembali ke menu utama.
- Status: Lulus
- Screenshot: `screenshots/05-pesanan-kosong.png`

## 6. Pelanggaran aturan: memesan menu yang habis

- Input: Menu 3, pilih 1: P01, O03. Item `sate` jumlah 1 catatan kosong. Lalu Enter kosong.
- Hasil yang diharapkan: `[GAGAL] Menu Sate sedang habis.` Karena pesanan belum punya item, muncul juga `[GAGAL] Pesanan harus berisi minimal 1 menu.`
- Hasil aktual: Item Sate ditolak dengan pesan "Menu Sate sedang habis." Setelah Enter kosong, pesanan ikut ditolak karena belum punya item, dengan pesan minimal 1 menu.
- Status: Lulus
- Screenshot: `screenshots/06-menu-habis.png`

## 7. Pelanggaran aturan: status mundur

- Input: Menu 3, pilih 2: O01, status `BARU`.
- Hasil yang diharapkan: `[GAGAL] Status tidak bisa diubah dari SELESAI ke BARU. Urutannya BARU, DIPROSES, SELESAI.`
- Hasil aktual: Program menolak perubahan dengan pesan "Status tidak bisa diubah dari SELESAI ke BARU. Urutannya BARU, DIPROSES, SELESAI." Status O01 tetap SELESAI.
- Status: Lulus
- Screenshot: `screenshots/07-status-mundur.png`

## 8. Input tidak valid: huruf di kolom angka

- Input: pada "Pilih menu (1-7)" ketik `x`.
- Hasil yang diharapkan: `[INPUT SALAH] "x" bukan angka bulat. Coba lagi.` Pertanyaan diulang dan program tidak berhenti.
- Hasil aktual: Muncul pesan [INPUT SALAH] bahwa "x" bukan angka bulat. Program menanyakan pilihan menu lagi dan tidak berhenti.
- Status: Lulus
- Screenshot: `screenshots/08-input-huruf.png`

## 9. Input tidak valid: nilai kosong

- Input: Menu 1, pilih 1: M09, nama dikosongkan (Enter), Makanan, 10000, y.
- Hasil yang diharapkan: `[GAGAL] Nama menu tidak boleh kosong.` Menu M09 tidak tersimpan.
- Hasil aktual: Muncul pesan "Nama menu tidak boleh kosong." Menu M09 tidak masuk daftar, dan program kembali ke menu utama.
- Status: Lulus
- Screenshot: `screenshots/09-input-kosong.png`

## 10. ADT penuh

- Input: Menu 5, pilih 1, tiga kali: M04 Kopi Minuman 7000 y; M05 Soto Makanan 12000 y; M06 Teh Minuman 4000 y.
- Hasil yang diharapkan: dua insert pertama `[OK]` dan daftar menjadi `Terisi 5 dari 5 slot.` Insert ketiga: `[GAGAL] Daftar menu penuh (kapasitas 5). Hapus menu dulu.`
- Hasil aktual: Insert Kopi dan Soto berhasil, dan daftar menjadi 5 dari 5 slot. Insert Teh ditolak dengan pesan "Daftar menu penuh (kapasitas 5). Hapus menu dulu."
- Status: Lulus
- Screenshot: `screenshots/10-adt-penuh.png`

## 11. ADT kosong

- Input: Menu 5, pilih 2, lima kali dengan nama: Es Teh, Kopi, Nasi Goreng, Sate, Soto. Lalu Menu 5, pilih 2, nama `Soto`. Lalu Menu 5, pilih 4, indeks 0.
- Hasil yang diharapkan: lima hapusan `[OK]`. Hapus saat kosong: `[GAGAL] Daftar menu kosong, tidak ada yang bisa dihapus.` Ambil data saat kosong: `[GAGAL] Daftar menu kosong.`
- Hasil aktual: Kelima menu terhapus satu per satu dengan pesan [OK]. Hapus Soto sekali lagi ditolak dengan pesan "Daftar menu kosong, tidak ada yang bisa dihapus." Pengambilan indeks 0 ditolak dengan pesan "Daftar menu kosong."
- Status: Lulus
- Screenshot: `screenshots/11-adt-kosong.png`
