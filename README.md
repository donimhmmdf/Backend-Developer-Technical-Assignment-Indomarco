# Backend Developer Technical Assignment

## 🛠️ Tech Stack

- **Java 17** & **Spring Boot 3.x**
- **Spring Security & JJWT**
- **Spring Data JPA** & **PostgreSQL**
- **Maven**

---

## 📋 Fitur Utama & Aturan Bisnis

### 1. Security & Authentication

- Seluruh endpoint API terproteksi dan hanya bisa diakses setelah pengguna **login** menggunakan JWT Token (`Authorization: Bearer <token>`).

### 2. Global Soft-Delete & Active Filter

- Semua response API secara otomatis hanya mengembalikan data yang **aktif (`is_active = true`)** dan **belum dihapus (`is_deleted = false`)**.

### 3. Store Search & Whitelist Management

- **Pencarian Toko:** Mencari daftar toko berdasarkan nama provinsi.
- **Whitelist Toko:** Toko yang ditandai sebagai _whitelisted_ (`is_whitelisted = true`) akan **selalu ditampilkan di semua hasil pencarian provinsi**, terlepas dari lokasi provinsinya.
- Pengguna dapat mengelola, memperbarui status, atau menghapus toko dari daftar whitelist kapan saja.

### 4. Management Branch

- Fitur untuk memperbarui (_update_) dan menghapus (_delete_) data cabang/branch.

## ⚙️ Cara Menjalankan Aplikasi

### 1. Prasyarat

- Java 17 atau versi lebih baru
- Maven
- PostgreSQL

### 2. Konfigurasi Database

Sesuaikan koneksi database PostgreSQL di file `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/indomarco
spring.datasource.username=postgres_user
spring.datasource.password=postgres_pass

# Menjalankan data.sql secara otomatis saat startup
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
3. Build & Run
Jalankan perintah berikut pada terminal di root folder proyek:

Bash
mvn clean install
mvn spring-boot:run
Aplikasi akan berjalan di http://localhost:8080.

🧪 Cara Pengujian API (Via Postman)
Seluruh endpoint API dapat ditest secara manual menggunakan Postman. File collection telah disediakan di dalam repositori:

File Path: ./postman/Store_API_Collection.json

Langkah-Langkah Testing:
Import Collection ke Postman:

Buka aplikasi Postman.

Klik tombol Import di kiri atas.

Pilih atau drag-and-drop file ./postman/Store_API_Collection.json.

Langkah 1: Otentikasi (Login)

Jalankan request POST /api/auth/login.

Body Request (JSON):

JSON
{
  "username": "admin",
  "password": "password123"
}
Salin nilai JWT Token yang ada pada response.

Langkah 2: Mengakses Endpoint Terproteksi

Untuk setiap request selanjutnya (Search Store, Update/Delete Branch), tambahkan token ke dalam Header:

Key: Authorization

Value: Bearer <TOKEN_JWT_KAMU>

Skenario Testing yang Dapat Dicoba:

Search Store by Province: GET /api/stores?provinceName=Jawa&page=0&size=10

Pastikan toko di provinsi yang dicari dan toko bertipe whitelisted dari luar provinsi ikut muncul.

Pastikan toko yang is_deleted = true atau is_active = false tidak muncul di response.

Update Branch: PUT /api/branches/{id}

Delete Branch: DELETE /api/branches/{id} (Soft delete).
```
