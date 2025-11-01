CREATE TABLE character_comment (
    id SERIAL PRIMARY KEY,
    anilist_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    comment TEXT NOT NULL
);
