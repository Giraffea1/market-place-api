INSERT INTO users (
        username,
        email,
        password,
        first_name,
        last_name,
        phone_number,
        role
    )
VALUES (
        'john_doe',
        'john@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'John',
        'Doe',
        '123-456-7890',
        'USER'
    ),
    (
        'jane_smith',
        'jane@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Jane',
        'Smith',
        '234-567-8901',
        'USER'
    ),
    (
        'admin_user',
        'admin@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Admin',
        'User',
        '345-678-9012',
        'ADMIN'
    ),
    (
        'seller_bob',
        'bob@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Bob',
        'Seller',
        '456-789-0123',
        'USER'
    ),
    (
        'buyer_alice',
        'alice@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Alice',
        'Buyer',
        '567-890-1234',
        'USER'
    ),
    (
        'tech_guru',
        'tech@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Mike',
        'TechGuru',
        '678-901-2345',
        'USER'
    ),
    (
        'fashion_lover',
        'fashion@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Sarah',
        'Fashion',
        '789-012-3456',
        'USER'
    ),
    (
        'bookworm',
        'books@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'David',
        'Reader',
        '890-123-4567',
        'USER'
    ),
    (
        'fitness_fan',
        'fitness@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Emma',
        'Fitness',
        '901-234-5678',
        'USER'
    ),
    (
        'art_collector',
        'art@example.com',
        '$2a$10$2/GucNJ9xgYOW4iQYwpL1eSRf6KW2SjjwubX1fpBREAPr7/GNc86O',
        'Lisa',
        'Artist',
        '012-345-6789',
        'USER'
    );
-- Insert test posts
INSERT INTO posts (
        title,
        description,
        asking_price,
        status,
        user_id
    )
VALUES (
        'iPhone 13 Pro Max',
        'Excellent condition iPhone 13 Pro Max, 256GB, Pacific Blue. Includes original box and charger.',
        899.99,
        'AVAILABLE',
        1
    ),
    (
        'MacBook Air M2',
        'Like new MacBook Air with M2 chip, 8GB RAM, 256GB SSD. Perfect for work or school.',
        1099.99,
        'AVAILABLE',
        1
    ),
    (
        'Gaming Laptop',
        'High-performance gaming laptop with RTX 3070, 16GB RAM, 1TB SSD. Great for gaming and streaming.',
        1299.99,
        'AVAILABLE',
        2
    ),
    (
        'Wireless Headphones',
        'Sony WH-1000XM4 wireless noise-canceling headphones. Excellent sound quality.',
        249.99,
        'AVAILABLE',
        2
    ),
    (
        'Coffee Table',
        'Modern wooden coffee table, perfect condition. Dimensions: 48" x 24" x 18".',
        150.00,
        'AVAILABLE',
        4
    ),
    (
        'Bicycle',
        'Mountain bike, barely used. Great for trails and city riding.',
        299.99,
        'SOLD',
        4
    ),
    (
        'iPad Pro 12.9"',
        'iPad Pro 12.9-inch with M2 chip, 256GB, Space Gray. Perfect for artists and professionals.',
        799.99,
        'AVAILABLE',
        6
    ),
    (
        'Designer Handbag',
        'Authentic Louis Vuitton Neverfull MM bag. Excellent condition, includes dust bag.',
        899.99,
        'AVAILABLE',
        7
    ),
    (
        'Vintage Camera',
        'Canon AE-1 film camera with 50mm lens. Great condition, perfect for photography enthusiasts.',
        199.99,
        'AVAILABLE',
        7
    ),
    (
        'Book Collection',
        'Complete Harry Potter series, hardcover editions. All books in excellent condition.',
        89.99,
        'AVAILABLE',
        8
    ),
    (
        'Treadmill',
        'ProForm treadmill with incline, barely used. Perfect for home workouts.',
        599.99,
        'AVAILABLE',
        9
    ),
    (
        'Yoga Mat Set',
        'Premium yoga mat with blocks and straps. Great for beginners and advanced practitioners.',
        45.99,
        'AVAILABLE',
        9
    ),
    (
        'Oil Painting',
        'Original oil painting by local artist. Abstract landscape, 24" x 36".',
        299.99,
        'AVAILABLE',
        10
    ),
    (
        'Guitar',
        'Acoustic guitar, Yamaha FG800. Great sound, perfect for beginners.',
        179.99,
        'AVAILABLE',
        10
    ),
    (
        'Smart Watch',
        'Apple Watch Series 7, 45mm, GPS. Excellent condition with extra bands.',
        349.99,
        'AVAILABLE',
        6
    ),
    (
        'Dining Table Set',
        '6-person dining table with chairs. Solid wood construction, modern design.',
        450.00,
        'AVAILABLE',
        4
    );
-- Insert tags for posts
INSERT INTO post_tags (post_id, tag)
VALUES (1, 'electronics'),
    (1, 'phone'),
    (1, 'apple'),
    (1, 'iphone'),
    (2, 'electronics'),
    (2, 'laptop'),
    (2, 'apple'),
    (2, 'macbook'),
    (3, 'electronics'),
    (3, 'laptop'),
    (3, 'gaming'),
    (3, 'computer'),
    (4, 'electronics'),
    (4, 'headphones'),
    (4, 'audio'),
    (4, 'wireless'),
    (5, 'furniture'),
    (5, 'table'),
    (5, 'wooden'),
    (5, 'home'),
    (6, 'sports'),
    (6, 'bicycle'),
    (6, 'mountain'),
    (6, 'outdoor'),
    (7, 'electronics'),
    (7, 'tablet'),
    (7, 'apple'),
    (7, 'ipad'),
    (8, 'fashion'),
    (8, 'handbag'),
    (8, 'luxury'),
    (8, 'designer'),
    (9, 'electronics'),
    (9, 'camera'),
    (9, 'vintage'),
    (9, 'photography'),
    (10, 'books'),
    (10, 'collection'),
    (10, 'harry-potter'),
    (10, 'fantasy'),
    (11, 'fitness'),
    (11, 'treadmill'),
    (11, 'exercise'),
    (11, 'cardio'),
    (12, 'fitness'),
    (12, 'yoga'),
    (12, 'exercise'),
    (12, 'wellness'),
    (13, 'art'),
    (13, 'painting'),
    (13, 'original'),
    (13, 'abstract'),
    (14, 'music'),
    (14, 'guitar'),
    (14, 'acoustic'),
    (14, 'instrument'),
    (15, 'electronics'),
    (15, 'smartwatch'),
    (15, 'apple'),
    (15, 'fitness'),
    (16, 'furniture'),
    (16, 'dining'),
    (16, 'table'),
    (16, 'wooden');
-- Insert test messages
INSERT INTO messages (sender_id, receiver_id, post_id, content)
VALUES (5, 4, 1, 'Hi! Is the iPhone still available?'),
    (
        4,
        5,
        1,
        'Yes, it is! Would you like to meet up?'
    ),
    (5, 4, 1, 'Great! Can we meet tomorrow at 2 PM?'),
    (3, 2, 3, 'Is the gaming laptop still for sale?'),
    (
        2,
        3,
        3,
        'Yes, it is available. Are you interested?'
    ),
    (
        6,
        1,
        2,
        'Is the MacBook still available? I can offer $1000.'
    ),
    (
        1,
        6,
        2,
        'Thanks for the offer, but I''m firm on the price.'
    ),
    (
        7,
        6,
        7,
        'Hi! I''m interested in the iPad Pro. Is it still available?'
    ),
    (
        6,
        7,
        7,
        'Yes, it is! Would you like to see it in person?'
    ),
    (
        8,
        7,
        8,
        'Is the designer handbag authentic? Do you have proof of purchase?'
    ),
    (
        7,
        8,
        8,
        'Yes, it''s authentic. I have the receipt and dust bag.'
    ),
    (
        9,
        8,
        10,
        'Hi! I''m interested in the Harry Potter collection. Are all books in good condition?'
    ),
    (
        8,
        9,
        10,
        'Yes, all books are in excellent condition. No tears or missing pages.'
    ),
    (
        10,
        9,
        11,
        'Is the treadmill still available? What''s the condition?'
    ),
    (
        9,
        10,
        11,
        'Yes, it''s in great condition. Barely used, like new.'
    ),
    (
        5,
        10,
        13,
        'I love the oil painting! Is it still available?'
    ),
    (
        10,
        5,
        13,
        'Yes, it is! It''s an original piece by a local artist.'
    ),
    (
        6,
        10,
        14,
        'Hi! I''m interested in the guitar. Is it good for beginners?'
    ),
    (
        10,
        6,
        14,
        'Yes, it''s perfect for beginners. Great sound quality too.'
    ),
    (
        7,
        6,
        15,
        'Is the Apple Watch still available? What size is it?'
    ),
    (
        6,
        7,
        15,
        'Yes, it''s 45mm. Includes extra bands and is in excellent condition.'
    ),
    (
        8,
        4,
        16,
        'Hi! I''m interested in the dining table set. Can you deliver?'
    ),
    (
        4,
        8,
        16,
        'Sorry, no delivery. You''ll need to pick it up.'
    ),
    (
        9,
        7,
        9,
        'Is the vintage camera still available? Does it work properly?'
    ),
    (
        7,
        9,
        9,
        'Yes, it works perfectly. Great condition for its age.'
    ),
    (
        10,
        9,
        12,
        'Hi! I''m interested in the yoga mat set. Is it still available?'
    ),
    (
        9,
        10,
        12,
        'Yes, it''s still available. Perfect for yoga practice.'
    );