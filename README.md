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

## 2. Register User

Sebelum melakukan login, buat akun terlebih dahulu menggunakan endpoint:

```http
POST /api/auth/register
```

### Request Body

Gunakan format JSON:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Setelah registrasi berhasil, user dapat digunakan untuk proses login.

---

## 3. Login

Setelah berhasil melakukan registrasi, jalankan request:

```http
POST /api/auth/login
```

### Request Body

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Setelah login berhasil, salin **JWT Token** yang terdapat pada response.

---

## 4. Mengakses Endpoint Terproteksi

Endpoint selain register dan login membutuhkan JWT Token.

Tambahkan token pada HTTP Header:

| Key             | Value                |
| --------------- | -------------------- |
| `Authorization` | `Bearer <JWT_TOKEN>` |

Contoh:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

Token tersebut digunakan untuk mengakses endpoint Store dan Branch.

---

# 🧪 Skenario Testing

## 1. Register User

```http
POST /api/auth/register
```

Pastikan user berhasil dibuat dan dapat digunakan untuk login.

---

## 2. Login

```http
POST /api/auth/login
```

Pastikan login berhasil dan JWT Token berhasil diperoleh.

---

## 3. Search Store by Province

```http
GET /api/stores/search?province=Jawa Barat
```

### Yang perlu diperiksa:

- Store dari provinsi yang dicari muncul pada response.
- Store dengan `is_whitelisted = true` dari provinsi lain tetap muncul.
- Store dengan `is_deleted = true` tidak muncul.
- Store dengan `is_active = false` tidak muncul.

---

## 4. Update Store Whitelist

```http
PUT /api/stores/whitelist/{id}
```

Contoh:

```http
PUT /api/stores/whitelist/8
```

Digunakan untuk mengubah status whitelist pada store.

Pastikan perubahan status whitelist berhasil dan memengaruhi hasil pencarian store.

---

## 5. Get Branches

```http
GET /api/branches
```

Digunakan untuk mendapatkan daftar branch yang masih aktif dan belum dihapus.

---

## 6. Update Branch

```http
PUT /api/branches/{id}
```

Contoh:

```http
PUT /api/branches/1
```

Pastikan data branch berhasil diperbarui sesuai request.

---

## 7. Delete Branch

```http
DELETE /api/branches/{id}
```

Contoh:

```http
DELETE /api/branches/1
```

Delete menggunakan mekanisme **soft delete**.

Setelah branch dihapus:

```text
is_deleted = true
```

Data tetap tersimpan di database, tetapi tidak akan ditampilkan pada response API.

---

## 📌 API Endpoint Summary

| Method   | Endpoint                                 | Description                         |
| -------- | ---------------------------------------- | ----------------------------------- |
| `POST`   | `/api/auth/register`                     | Registrasi user baru                |
| `POST`   | `/api/auth/login`                        | Login dan mendapatkan JWT Token     |
| `GET`    | `/api/stores/search?province=Jawa Barat` | Mencari store berdasarkan provinsi  |
| `PUT`    | `/api/stores/whitelist/{id}`             | Mengubah status whitelist store     |
| `GET`    | `/api/branches`                          | Mendapatkan daftar branch           |
| `PUT`    | `/api/branches/{id}`                     | Memperbarui data branch             |
| `DELETE` | `/api/branches/{id}`                     | Menghapus branch secara soft delete |

---

## 📁 Project Structure

File Postman collection tersedia pada:

```text
postman/
└── Indomarco.postman_collection.json
```

Konfigurasi aplikasi tersedia pada:

```text
src/
└── main/
    └── resources/
        └── application.properties
```
