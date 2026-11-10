-- ============================================================================
-- SCRIPT SQL PGADMIN UNTUK DATABASE: pbo_aritmatika
-- ============================================================================
-- Petunjuk Penggunaan di pgAdmin 4:
-- 1. Buat database baru bernama: pbo_aritmatika
-- 2. Buka Query Tool pada database pbo_aritmatika, lalu jalankan script di bawah ini.
-- ============================================================================

-- Membuat tabel operasi_aritmatika dengan 3 atribut utama: bilangan_1, bilangan_2, dan operator
-- (Beserta id sebagai Primary Key untuk kebutuhan CRUD Spring Data JPA)
-- Kolom result TIDAK disimpan di dalam tabel database sesuai ketentuan.

CREATE TABLE IF NOT EXISTS operasi_aritmatika (
    id BIGSERIAL PRIMARY KEY,
    bilangan_1 DOUBLE PRECISION NOT NULL,
    bilangan_2 DOUBLE PRECISION NOT NULL,
    operator VARCHAR(5) NOT NULL CHECK (operator IN ('+', '-', 'x', '/'))
);

-- Contoh query untuk melihat isi tabel (Hanya menyimpan bilangan_1, bilangan_2, dan operator):
SELECT * FROM operasi_aritmatika ORDER BY id ASC;
