ALTER TABLE users ADD COLUMN first_access BOOLEAN DEFAULT TRUE NOT NULL;

UPDATE users SET first_access = FALSE WHERE is_active = TRUE;
