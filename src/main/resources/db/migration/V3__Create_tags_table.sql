CREATE TABLE post_tags (
    post_id BIGINT NOT NULL,
    tag VARCHAR(255),
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);