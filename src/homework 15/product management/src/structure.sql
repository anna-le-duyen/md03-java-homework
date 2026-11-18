CREATE DATABASE IF NOT EXISTS ProductManagement;
USE ProductManagement;

DROP TABLE IF EXISTS Product;
CREATE TABLE Product (
                         Product_Id INT PRIMARY KEY AUTO_INCREMENT,
                         Product_Name VARCHAR(100) NOT NULL UNIQUE,
                         Product_Price FLOAT NOT NULL CHECK (Product_Price > 0),
                         Product_Title VARCHAR(200) NOT NULL,
                         Product_created DATE NOT NULL,
                         Product_catalog VARCHAR(100) NOT NULL,
                         Product_Status BIT DEFAULT 1
);

-- Xóa các procedure cũ nếu tồn tại
DROP PROCEDURE IF EXISTS sp_get_all_products;
DROP PROCEDURE IF EXISTS sp_check_catalog_exists;
DROP PROCEDURE IF EXISTS sp_add_product;
DROP PROCEDURE IF EXISTS sp_update_product;
DROP PROCEDURE IF EXISTS sp_delete_product;
DROP PROCEDURE IF EXISTS sp_get_product_by_id;
DROP PROCEDURE IF EXISTS sp_search_product_by_name;
DROP PROCEDURE IF EXISTS sp_statistic_by_catalog;

DELIMITER $$

-- 1. Lấy tất cả thông tin sản phẩm
CREATE PROCEDURE sp_get_all_products()
BEGIN
    SELECT Product_Id, Product_Name, Product_Price, Product_Title, Product_created, Product_catalog, Product_Status
    FROM Product;
END $$

-- 2. Kiểm tra sự tồn tại của danh mục (Trả về số lượng bản ghi qua tham số OUT)
CREATE PROCEDURE sp_check_catalog_exists(
    IN in_catalog VARCHAR(100),
    OUT out_count INT
)
BEGIN
    SELECT COUNT(*) INTO out_count
    FROM Product
    WHERE Product_catalog = in_catalog;
END $$

-- 3. Thêm mới một sản phẩm
CREATE PROCEDURE sp_add_product(
    IN in_name VARCHAR(100),
    IN in_price FLOAT,
    IN in_title VARCHAR(200),
    IN in_created DATE,
    IN in_catalog VARCHAR(100),
    IN in_status BIT
)
BEGIN
    INSERT INTO Product (Product_Name, Product_Price, Product_Title, Product_created, Product_catalog, Product_Status)
    VALUES (in_name, in_price, in_title, in_created, in_catalog, in_status);
END $$

-- 4. Cập nhật một sản phẩm theo mã sản phẩm
CREATE PROCEDURE sp_update_product(
    IN in_id INT,
    IN in_name VARCHAR(100),
    IN in_price FLOAT,
    IN in_title VARCHAR(200),
    IN in_created DATE,
    IN in_catalog VARCHAR(100),
    IN in_status BIT
)
BEGIN
    UPDATE Product
    SET Product_Name = in_name,
        Product_Price = in_price,
        Product_Title = in_title,
        Product_created = in_created,
        Product_catalog = in_catalog,
        Product_Status = in_status
    WHERE Product_Id = in_id;
END $$

-- 5. Xóa một sản phẩm theo mã sản phẩm
CREATE PROCEDURE sp_delete_product(
    IN in_id INT
)
BEGIN
    DELETE FROM Product
    WHERE Product_Id = in_id;
END $$

-- 6. Lấy thông tin sản phẩm theo mã sản phẩm
CREATE PROCEDURE sp_get_product_by_id(
    IN in_id INT
)
BEGIN
    SELECT Product_Id, Product_Name, Product_Price, Product_Title, Product_created, Product_catalog, Product_Status
    FROM Product
    WHERE Product_Id = in_id;
END $$

-- 7. Tìm kiếm sản phẩm theo tên sản phẩm (tương đối)
CREATE PROCEDURE sp_search_product_by_name(
    IN in_keyword VARCHAR(100)
)
BEGIN
    SELECT Product_Id, Product_Name, Product_Price, Product_Title, Product_created, Product_catalog, Product_Status
    FROM Product
    WHERE Product_Name LIKE CONCAT('%', in_keyword, '%');
END $$

-- 8. Thống kê số lượng sản phẩm theo danh mục
CREATE PROCEDURE sp_statistic_by_catalog()
BEGIN
    SELECT Product_catalog, COUNT(Product_Id) AS total_products
    FROM Product
    GROUP BY Product_catalog;
END $$

DELIMITER ;