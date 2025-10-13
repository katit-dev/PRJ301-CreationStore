-- Tạo database
CREATE DATABASE CreationStore;
GO

USE CreationStore;
GO

-- 1. Bảng Users
CREATE TABLE Users (
    UserId INT IDENTITY(1,1) PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(256) NOT NULL,
    FullName NVARCHAR(100),
    Email VARCHAR(100),
    Role VARCHAR(20) NOT NULL DEFAULT 'Member' CHECK (Role IN ('Admin', 'Member')),
    CreatedAt DATETIME DEFAULT GETDATE()
);
GO

-- 2. Bảng Categories
CREATE TABLE Categories (
    CategoryId INT IDENTITY(1,1) PRIMARY KEY,
    CategoryName NVARCHAR(100) NOT NULL UNIQUE,
    Description TEXT
);
GO

-- 3. Bảng Products
CREATE TABLE Products (
    ProductId INT IDENTITY(1,1) PRIMARY KEY,
    ProductName VARCHAR(100) NOT NULL,
    Description TEXT,
    Price DECIMAL(10, 2) NOT NULL,
    Status BIT NOT NULL DEFAULT 1, -- 1 = còn bán, 0 = ngừng bán
    Image VARCHAR(255),
    Validity INT DEFAULT 30,
    CategoryId INT NOT NULL,
    FOREIGN KEY (CategoryId) REFERENCES Categories(CategoryId)
);
GO

-- 4. Bảng Cart
CREATE TABLE Cart (
    CartId INT IDENTITY(1,1) PRIMARY KEY,
    UserId INT NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    Status VARCHAR(20) DEFAULT 'pending' CHECK (Status IN ('pending', 'ordered', 'cancelled')),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);
GO

-- 5. Bảng CartDetail
CREATE TABLE CartDetail (
    CartId INT,
    ProductId INT,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    PriceAtTime DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (CartId, ProductId),
    FOREIGN KEY (CartId) REFERENCES Cart(CartId) ON DELETE CASCADE,
    FOREIGN KEY (ProductId) REFERENCES Products(ProductId)
);
GO

-- 6. Bảng Orders
CREATE TABLE Orders (
    OrderId INT IDENTITY(1,1) PRIMARY KEY,
    UserId INT NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    Status VARCHAR(20) DEFAULT 'processing' CHECK (Status IN ('processing', 'shipped', 'completed', 'cancelled')),
    OrderDate DATETIME DEFAULT GETDATE(),
    Note TEXT,
    CancelledAt DATETIME NULL,
    CancelReason TEXT,
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);
GO

-- 7. Bảng OrderDetails
CREATE TABLE OrderDetails (
    OrderId INT,
    ProductId INT,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    PriceAtTime DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (OrderId, ProductId),
    FOREIGN KEY (OrderId) REFERENCES Orders(OrderId) ON DELETE CASCADE,
    FOREIGN KEY (ProductId) REFERENCES Products(ProductId)
);
GO

ALTER TABLE Products
ALTER COLUMN Validity INT;


INSERT INTO Users (Username, Password, FullName, Email, Role)
VALUES 
('admin', '111', N'Trần Nguyễn Khánh Vy', 'admin1@gmail.com', 'Admin'),
('bichlth0506', '222', N'Lê Thị Bích', 'lethibich0506@gmail.com', 'Member'),
('cuongtv123', '333', N'Trần Văn Cường', 'trancuong@gmail.com', 'Member'),
('diempt', '444', N'Phạm Thu Diễm', 'diemthupham@gmail.com', 'Member'),
('lamhv567', '555', N'Hoàng Văn Lâm', 'hoangvanlam1977@gmail.com', 'Member');


INSERT INTO Categories (CategoryName, Description)
VALUES 
(N'Entertainment', N'Services related to entertainment, relaxation, such as movies, music, and events'),
(N'Work', N'Services supporting work activities such as office tasks, repair, and logistics'),
(N'Study', N'Learning resources and services such as courses, materials, and test preparation'),
(N'AI World', N'Technologies and services related to artificial intelligence and smart applications');

INSERT INTO Products (ProductName, Description, Price, Status, Image, Validity, CategoryId)
VALUES
-- CategoryId = 1: Entertainment
('Spotify Premium 3 Months', 'Access to Spotify Premium for 3 months', 350000, 1, 'assets/images/spotify02.jpg', 90, 1),
('YouTube Premium 6 Months', 'Enjoy YouTube Premium without ads for 6 months', 500000, 1, 'assets/images/logoyoutube.jpg', 180, 1),
('Netflix Premium 1 Month - Account', '1 month Netflix Premium access via shared account', 260000, 1, 'assets/images/netflix02.jpg', 30, 1),

-- CategoryId = 2: Work (phần mềm bản quyền)
('Windows 10 Professional - CD Key', 'Lifetime license for Windows 10 Professional', 400000, 1, 'assets/images/Windows 10 Professional CD Key-22736.png', NULL, 2),
('Windows 11 Professional - CD Key', 'Lifetime license for Windows 11 Professional', 400000, 1, 'assets/images/Windows 11 Professional CD Key-10256.png', NULL, 2),
('Microsoft Office 2019 Professional Plus for Windows', 'Permanent license for Office 2019 Pro Plus (Windows)', 989000, 1, 'assets/images/Microsoft Office 2019 Professional Plus-windows-11845.png', NULL, 2),
('Microsoft Office 2019 Home & Business for MAC', 'Permanent license for Office 2019 Home & Business (Mac)', 999000, 1, 'assets/images/Microsoft Office 2019 Home Business-mac-48555.png', NULL, 2),


-- CategoryId = 3: Study
('Coursera Course (7 Days)', 'Access to Coursera course for 7 days', 150000, 1, 'assets/images/coursera.webp', 7, 3),
('Quizlet Plus 1 Year', '1-year subscription to Quizlet Plus', 700000, 1, 'assets/images/quizlet.jpg', 365, 3),
('Duolingo Super ~1 Month', 'Access to Duolingo Super for about 1 month', 150000, 1, 'assets/images/Duolingo.jpg', 30, 3),
('Grammarly Premium (AI) 1 Month 1 Device', 'Grammarly Premium with AI for 1 device, 1 month', 400000, 1, 'assets/images/Grammarly.webp', 30, 3),

-- CategoryId = 4: AI World
('ChatGPT Plus 1 Month', 'ChatGPT Plus (GPT-4) access for 1 month', 500000, 1, 'assets/images/chatgpt-1024x623.jpg', 30, 4),
('Google AI Pro (Gemini) 1 Month', 'Google Gemini Pro AI access for 1 month', 300000, 1, 'assets/images/gemini.jpg', 30, 4),
('Curiosity AI Pro 14 Days', 'Premium access to Curiosity AI for 14 days', 240000, 1, 'assets/images/Curiosityai.avif', 14, 4),
('Super Grok AI 1 Month', 'Access to Grok AI for 1 month', 800000, 1, 'assets/images/Grok.jpg', 30, 4);

UPDATE Products SET image = 'assets/images/spotify02.jpg' WHERE ProductId = 1;
UPDATE Products SET image = 'assets/images/logoyoutube.jpg' WHERE ProductId = 2;
UPDATE Products SET image = 'assets/images/netflix02.jpg' WHERE ProductId = 3;
UPDATE Products SET image = 'assets/images/windows10_cdkey.png.png' WHERE ProductId = 4;
UPDATE Products SET image = 'assets/images/windows11_cdkey.png.png' WHERE ProductId = 5;
UPDATE Products SET image = 'assets/images/office2019_win.png.png' WHERE ProductId = 6;
UPDATE Products SET image = 'assets/images/office2019_mac.png.png' WHERE ProductId = 7;
UPDATE Products SET image = 'assets/images/quizlet.jpg' WHERE ProductId = 8;
UPDATE Products SET image = 'assets/images/coursera.png' WHERE ProductId = 9;
UPDATE Products SET image = 'assets/images/chatgpt-1024x623.jpg' WHERE ProductId = 10;
UPDATE Products SET image = 'assets/images/Duolingo.jpg' WHERE ProductId = 11;
UPDATE Products SET image = 'assets/images/gemini.jpg' WHERE ProductId = 12;
UPDATE Products SET image = 'assets/images/Grammarly.webp' WHERE ProductId = 13;
UPDATE Products SET image = 'assets/images/Grok.jpg' WHERE ProductId = 14;
UPDATE Products SET image = 'assets/images/Curiosityai.avif' WHERE ProductId = 15;





UPDATE Products
SET Validity = 0
WHERE Validity IS NULL;

SELECT dc.name AS DefaultConstraintName
FROM sys.default_constraints dc
JOIN sys.columns c ON dc.parent_object_id = c.object_id AND dc.parent_column_id = c.column_id
WHERE OBJECT_NAME(dc.parent_object_id) = 'Orders'
  AND c.name = 'Status';


ALTER TABLE Orders
DROP CONSTRAINT DF__Orders__Status__3C69FB99; -- thay bằng tên đúng bạn tìm được
