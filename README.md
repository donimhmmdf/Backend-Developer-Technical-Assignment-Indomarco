# Backend Developer Technical Assignment

REST API dibangun menggunakan **Spring Boot 3**, **Spring Security (JWT)**, dan **PostgreSQL** untuk mendukung fitur autentikasi, pencarian lokasi toko, serta manajemen cabang.

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security & JJWT**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**

---

## 📋 Fitur Utama & Aturan Bisnis

### 1. Security & Authentication

Seluruh endpoint API terproteksi dan hanya dapat diakses setelah pengguna melakukan **login** menggunakan JWT Token.

Token dikirim melalui HTTP Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

### 2. Global Soft Delete & Active Filter

Seluruh response API secara otomatis hanya mengembalikan data yang memenuhi kondisi:

```text
is_active = true
is_deleted = false
```

Dengan demikian, data yang tidak aktif atau sudah dihapus tidak akan ditampilkan pada response API.

---

### 3. Store Search & Whitelist Management

#### 🔍 Pencarian Toko

Pengguna dapat mencari daftar toko berdasarkan **nama provinsi**.

Contoh:

```http
GET /api/stores?provinceName=Jawa&page=0&size=10
```

#### ⭐ Whitelist Toko

Toko yang memiliki status:

```text
is_whitelisted = true
```

akan **selalu ditampilkan pada hasil pencarian**, terlepas dari lokasi provinsinya.

Pengguna juga dapat:

- Menambahkan toko ke whitelist.
- Menghapus toko dari whitelist.

---

### 4. Branch Management

API menyediakan fitur untuk melakukan:

- **Update Branch**
- **Delete Branch**

Operasi delete menggunakan mekanisme **soft delete**, sehingga data tidak benar-benar dihapus dari database.

# ⚙️ Cara Menjalankan Aplikasi

## 1. Prasyarat

Pastikan software berikut sudah ter-install:

- **Java 17** atau versi lebih baru
- **Maven**
- **PostgreSQL**

---

## 2. Konfigurasi Database

Sesuaikan konfigurasi koneksi PostgreSQL pada:

```text
src/main/resources/application.properties
```

Contoh konfigurasi:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/indomarco
spring.datasource.username=postgres_user
spring.datasource.password=postgres_pass

# Menjalankan data.sql secara otomatis saat startup
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

Pastikan database `indomarco` sudah dibuat terlebih dahulu.

---

## 3. Build & Run

Jalankan perintah berikut dari **root folder project**.

### Build

```bash
mvn clean install
```

### Menjalankan aplikasi

```bash
mvn spring-boot:run
```

Setelah aplikasi berhasil dijalankan, API dapat diakses melalui:

```text
http://localhost:8080
```

---

# 🧪 Cara Pengujian API dengan Postman

Seluruh endpoint API dapat diuji secara manual menggunakan **Postman**.

Postman collection telah disediakan di dalam repository:

```text
./postman/Indomarco.postman_collection.json
```

---

## 1. Import Collection ke Postman

1. Buka aplikasi **Postman**.
2. Klik **Import**.
3. Pilih atau drag & drop file:

```text
./postman/Indomarco.postman_collection.json
```

---

## 2. Authentication / Login

Jalankan request:

```http
POST /api/auth/login
```

### Request Body

Gunakan format JSON:

```json
{
  "username": "admin",
  "password": "password123"
}
```

Setelah berhasil login, salin **JWT Token** yang terdapat pada response.

---

## 3. Mengakses Endpoint Terproteksi

Untuk request berikutnya, tambahkan JWT Token pada HTTP Header:

| Key             | Value                |
| --------------- | -------------------- |
| `Authorization` | `Bearer <JWT_TOKEN>` |

Contoh:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# 🧪 Skenario Testing

## 1. Search Store by Province

Request:

```http
GET /api/stores?provinceName=Jawa&page=0&size=10
```

### Yang perlu diperiksa:

- Store dari provinsi yang sesuai muncul pada response.
- Store yang memiliki `is_whitelisted = true` dari provinsi lain tetap muncul.
- Store dengan `is_deleted = true` tidak muncul.
- Store dengan `is_active = false` tidak muncul.
- Pagination berjalan sesuai parameter `page` dan `size`.

---

## 2. Update Branch

Request:

```http
PUT /api/branches/{id}
```

Pastikan data branch berhasil diperbarui sesuai request.

---

## 3. Delete Branch

Request:

```http
DELETE /api/branches/{id}
```

Delete menggunakan mekanisme **soft delete**.

Setelah branch dihapus:

```text
is_deleted = true
```

Data tetap tersimpan di database tetapi tidak akan ditampilkan pada response API.

---

## 📌 API Endpoint Summary

| Method   | Endpoint                                 | Description                         |
| -------- | ---------------------------------------- | ----------------------------------- |
| `POST`   | `/api/auth/login`                        | Login dan mendapatkan JWT Token     |
| `POST`   | `/api/auth/register`                     | Registrasi user baru                |
| `PUT`    | `/api/stores/whitelist/{id}`             | Mengubah status whitelist store     |
| `GET`    | `/api/stores/search?province=Jawa Barat` | Mencari store berdasarkan provinsi  |
| `GET`    | `/api/branches`                          | Mendapatkan daftar branch           |
| `PUT`    | `/api/branches/{id}`                     | Memperbarui data branch             |
| `DELETE` | `/api/branches/{id}`                     | Menghapus branch secara soft delete |

---

## 📁 Project Structure

File Postman collection tersedia pada:

```text
postman/
└── Store_API_Collection.json
```

Konfigurasi aplikasi tersedia pada:

```text
src/
└── main/
    └── resources/
        └── application.properties
```
