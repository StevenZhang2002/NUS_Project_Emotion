-- 插入用户数据
INSERT INTO tb_User (Username, Password, gender, email, status, createdAt, updatedAt)
VALUES
    ('JohnDoe', 'password123', 'male', 'john.doe@example.com', 'Public', NOW(), NOW()),
    ('JaneDoe', 'password456', 'female', 'jane.doe@example.com', 'Protected', NOW(), NOW()),
    ('AliceSmith', 'password789', 'female', 'alice.smith@example.com', 'Public', NOW(), NOW());

-- 插入记录数据
INSERT INTO Record (userId, Title, Content, Mood, createdAt, updatedAt)
VALUES
    (44, 'My First Record', 'This is my first record content.', 5, NOW(), NOW()),
    (45, 'Another Day', 'Had a great day today!', 8, NOW(), NOW());

-- 插入帖子数据
INSERT INTO Post (recordId, userId, createdAt, updatedAt)
VALUES
    (1, 1, NOW(), NOW()),
    (2, 2, NOW(), NOW());

-- 插入点赞数据
INSERT INTO Likes (postId, userId, createdAt, likeOrNot)
VALUES
    (1, 2, NOW(), TRUE),
    (2, 1, NOW(), TRUE);

-- 插入好友关系数据
INSERT INTO Friendship (userId1, userId2, createdAt)
VALUES
    (1, 2, NOW()),
    (2, 3, NOW());

-- 插入心情历史数据
INSERT INTO MoodHistory (userId, mood, createdAt)
VALUES
    (1, 7, NOW()),
    (2, 6, NOW());

-- 插入照片数据
INSERT INTO photo (record_id, photo_data, created_at)
VALUES
    (1, LOAD_FILE('/path/to/photo1.jpg'), NOW()),
    (2, LOAD_FILE('/path/to/photo2.jpg'), NOW());

-- 插入积分数据
INSERT INTO Points (userId, pointsBalance, updatedAt)
VALUES
    (1, 500, NOW()),
    (2, 750, NOW());

-- 插入积分交易数据
INSERT INTO PointsTransaction (userId, changeAmount, transactionType, description, createdAt)
VALUES
    (1, 100, 'Earn', 'Posted a new record', NOW()),
    (2, -300, 'Spend', 'Redeemed product', NOW());

-- 插入订单数据
INSERT INTO Orders (userId, productId, quantity, totalPoints, createdAt)
VALUES
    (1, 1, 1, 500, NOW()),
    (2, 3, 2, 600, NOW());

-- 插入地址数据
INSERT INTO Address (userId, street, city, state, postalCode, country, createdAt, updatedAt)
VALUES
    (1, '123 Main St', 'New York', 'NY', '10001', 'USA', NOW(), NOW()),
    (2, '456 Elm St', 'Los Angeles', 'CA', '90001', 'USA', NOW(), NOW());
