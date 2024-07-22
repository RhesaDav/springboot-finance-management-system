# Personal Finance Management System

## Deskripsi Proyek
Aplikasi Personal Finance Management System adalah alat yang dirancang untuk membantu pengguna mengelola keuangan pribadi mereka. Aplikasi ini memungkinkan pengguna untuk mencatat dan melacak pengeluaran, mengelompokkan pengeluaran berdasarkan kategori, dan menghasilkan laporan bulanan atau tahunan. Fitur autentikasi dan otorisasi memastikan bahwa data pribadi pengguna tetap aman, sementara manajemen peran mengatur hak akses pengguna.

## Fitur Utama
- **Autentikasi & Otorisasi**: Pengguna dapat mendaftar, login, dan mengelola kata sandi. Autentikasi token berbasis JWT digunakan untuk memastikan akses data yang aman.
- **Manajemen Pengeluaran**: Pengguna dapat menambah, mengedit, dan menghapus pengeluaran. Pengeluaran dapat dikategorikan ke dalam satu atau lebih kategori.
- **Manajemen Kategori**: Pengguna dapat membuat, mengedit, dan menghapus kategori pengeluaran.
- **Laporan Keuangan**: Pengguna dapat melihat laporan pengeluaran bulanan atau tahunan. Laporan dapat di-filter berdasarkan kategori atau rentang waktu tertentu.
- **Manajemen Peran**: Admin dapat mengelola pengguna dan peran. Pengguna dengan peran tertentu memiliki akses ke fitur-fitur tertentu dalam aplikasi.

## Entity-Entity

### Users
- `id`: Bigint, primary key, auto-generated.
- `username`: Varchar(20), nama pengguna unik.
- `password`: Varchar(255), kata sandi terenkripsi.
- `email`: Varchar(255), alamat email pengguna.
- `name`: Varchar(255), nama lengkap pengguna.
- `created_at`: Timestamp, waktu pembuatan akun.
- `updated_at`: Timestamp, waktu terakhir akun diperbarui.

### Expenses
- `id`: Bigint, primary key, auto-generated.
- `amount`: Double precision, jumlah uang yang dikeluarkan.
- `date`: Timestamp, tanggal pengeluaran.
- `description`: Varchar(255), deskripsi pengeluaran.
- `user_id`: Bigint, foreign key yang menghubungkan pengeluaran dengan pengguna.
- `created_at`: Timestamp, waktu pembuatan pengeluaran.
- `updated_at`: Timestamp, waktu terakhir pengeluaran diperbarui.

### Categories
- `id`: Bigint, primary key, auto-generated.
- `name`: Varchar(100), nama kategori (misal: makanan, transportasi, hiburan).
- `user_id`: Bigint, foreign key yang menghubungkan kategori dengan pengguna.

### ExpenseCategories
- `id`: Bigint, primary key, auto-generated.
- `expense_id`: Bigint, foreign key yang menghubungkan dengan tabel Expenses.
- `category_id`: Bigint, foreign key yang menghubungkan dengan tabel Categories.

### Roles
- `id`: Bigint, primary key, auto-generated.
- `role_name`: Varchar(50), nama peran (misal: Admin, User).

### UserRoles
- `id`: Bigint, primary key, auto-generated.
- `user_id`: Bigint, foreign key yang menghubungkan dengan tabel Users.
- `role_id`: Bigint, foreign key yang menghubungkan dengan tabel Roles.

## Hubungan Antar Entity
- **Users**: Satu pengguna dapat memiliki banyak pengeluaran dan kategori (one-to-many).
- **Expenses**: Satu pengeluaran milik satu pengguna (many-to-one). Satu pengeluaran dapat memiliki banyak kategori (many-to-many).
- **Categories**: Satu kategori dapat dimiliki oleh banyak pengguna (one-to-many). Satu kategori dapat digunakan untuk banyak pengeluaran (many-to-many).
- **Roles**: Satu peran dapat dimiliki oleh banyak pengguna (one-to-many).
- **UserRoles**: Menyimpan informasi tentang peran apa yang dimiliki oleh pengguna.

## Alur Fungsional

### Autentikasi dan Otorisasi
- Pengguna mendaftar dan login menggunakan email dan kata sandi.
- Autentikasi menggunakan JWT (JSON Web Tokens) untuk memastikan bahwa hanya pengguna yang sah yang dapat mengakses data.

### Manajemen Pengeluaran
- Pengguna dapat menambah, mengedit, dan menghapus pengeluaran.
- Pengeluaran dapat dikategorikan ke dalam satu atau lebih kategori.

### Manajemen Kategori
- Pengguna dapat membuat, mengedit, dan menghapus kategori pengeluaran.
- Kategori digunakan untuk mengelompokkan pengeluaran.

### Laporan Keuangan
- Pengguna dapat melihat laporan pengeluaran bulanan atau tahunan.
- Laporan dapat di-filter berdasarkan kategori atau rentang waktu tertentu.

### Manajemen Peran
- Admin dapat mengelola pengguna dan peran.
- Pengguna dengan peran tertentu memiliki akses ke fitur-fitur tertentu dalam aplikasi.
