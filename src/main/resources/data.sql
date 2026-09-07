-- 1. Insert Data Dummy ke tabel provinces
INSERT INTO provinces (id, name, is_active, is_deleted, created_at, updated_at, created_by, updated_by) 
VALUES 
(1, 'DKI Jakarta', true, false, NOW(), NOW(), 'system', 'system'),
(2, 'Jawa Barat', true, false, NOW(), NOW(), 'system', 'system'),
(3, 'Jawa Tengah', true, false, NOW(), NOW(), 'system', 'system');

-- 2. Insert Data Dummy ke tabel branches
INSERT INTO branches (id, province_id, name, is_active, is_deleted, created_at, updated_at, created_by, updated_by) 
VALUES 
(1, 1, 'Branch Jakarta Selatan', true, false, NOW(), NOW(), 'system', 'system'),
(2, 1, 'Branch Jakarta Pusat', true, false, NOW(), NOW(), 'system', 'system'),
(3, 2, 'Branch Bandung', true, false, NOW(), NOW(), 'system', 'system'),
(4, 2, 'Branch Bekasi', true, false, NOW(), NOW(), 'system', 'system'),
(5, 3, 'Branch Semarang', true, false, NOW(), NOW(), 'system', 'system'),
(6, 3, 'Branch Solo', true, false, NOW(), NOW(), 'system', 'system');

-- 3. Insert Data Dummy ke tabel stores
INSERT INTO stores (id, branch_id, name, is_active, is_deleted, is_whitelisted, created_at, updated_at, created_by, updated_by) 
VALUES 
(1, 1, 'Toko Kebayoran', true, false, true, NOW(), NOW(), 'system', 'system'),
(2, 1, 'Toko Blok M', true, false, true, NOW(), NOW(), 'system', 'system'),
(3, 2, 'Toko Tanah Abang', true, false, false, NOW(), NOW(), 'system', 'system'),
(4, 3, 'Toko Dago', true, false, true, NOW(), NOW(), 'system', 'system'),
(5, 3, 'Toko Buah Batu', true, false, true, NOW(), NOW(), 'system', 'system'),
(6, 4, 'Toko Summarecon Bekasi', true, false, false, NOW(), NOW(), 'system', 'system'),
(7, 5, 'Toko Simpang Lima', true, false, true, NOW(), NOW(), 'system', 'system'),
(8, 6, 'Toko Slamet Riyadi', true, false, true, NOW(), NOW(), 'system', 'system');