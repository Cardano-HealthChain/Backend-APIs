-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id                     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email                       VARCHAR(255) UNIQUE,
    hashed_password             TEXT,
    first_name                  VARCHAR(255),
    last_name                   VARCHAR(255),
    phone_number                VARCHAR(50),
    dob                         DATE,
    gender                      VARCHAR(20),
    address                     TEXT,
    blood_type                  TEXT,
    genotype                    TEXT,
    known_allergies             TEXT,
    pre_existing_conditions     TEXT,
    emergency_contact_name      TEXT,
    emergency_contact_phone     TEXT,
    emergency_contact_rel       TEXT,
    nationality                 VARCHAR(100),
    state_of_origin             VARCHAR(100),
    created_at                  TIMESTAMP DEFAULT NOW(),
    verified                    BOOLEAN DEFAULT FALSE,
    role                        TEXT,
-- wallet information
    wallet_address              TEXT UNIQUE,
    stake_address               TEXT UNIQUE,
    public_key                  TEXT,
    wallet_verified_at          TIMESTAMP,
    wallet_network              VARCHAR(20),
    last_wallet_login           TIMESTAMP
);
-- User Sessions Table
CREATE TABLE IF NOT EXISTS user_sessions (
    session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_email VARCHAR(255) NOT NULL REFERENCES users(email) ON DELETE CASCADE,
    refresh_token TEXT NOT NULL,
    ip_address VARCHAR(100),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    expires_at TIMESTAMP,
    revoked BOOLEAN DEFAULT FALSE
);

-- OTP Codes Table
CREATE TABLE IF NOT EXISTS otp_codes (
    otp_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL REFERENCES users(email) ON DELETE CASCADE,
    otp_code VARCHAR(10),
    created_at TIMESTAMP DEFAULT NOW(),
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE
);

-- Clinics Table
CREATE TABLE IF NOT EXISTS clinics (
    clinic_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    clinic_name VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    address TEXT,
    clinic_email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    verified BOOLEAN DEFAULT FALSE,
    license_no TEXT
);
-- Permissions Table
CREATE TABLE IF NOT EXISTS permissions (
    permissions_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             VARCHAR(255) NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    clinic_id UUID      NOT NULL REFERENCES clinics(clinic_id) ON DELETE CASCADE,
    clinic_email        VARCHAR(50) NOT NULL,
    access_type         VARCHAR(50) NOT NULL,
    granted_at          TIMESTAMP,
    expires_at          TIMESTAMP,
    status              TEXT,
    granted             BOOLEAN DEFAULT FALSE,
    revoked             BOOLEAN DEFAULT FALSE,
    revoked_at          TIMESTAMP
);

-- Medical Records Table
CREATE TABLE IF NOT EXISTS medical_records (
    record_id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             VARCHAR(255) NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    record_type         VARCHAR(100),
    record_data         TEXT NOT NULL,
    clinic_name         VARCHAR(100),
    hash_local          TEXT NOT NULL,
    blockchain_tx_id    TEXT,
    blockchain_tx_hash  TEXT,
    block_number        TEXT,
    verified            BOOLEAN,
    diagnosis           TEXT,
    created_at          TIMESTAMP DEFAULT NOW()
);

-- Shared Medical Records Table
CREATE TABLE IF NOT EXISTS medical_records_shared_with (
    shared_with_id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                 TEXT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    clinic_id               UUID NOT NULL REFERENCES clinics(clinic_id) ON DELETE CASCADE
);

-- Notifications Table
CREATE TABLE IF NOT EXISTS notifications (
    notification_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_email VARCHAR(255) NOT NULL REFERENCES users(email) ON DELETE CASCADE,
    clinic_email VARCHAR(255) NOT NULL REFERENCES clinics(clinic_email) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    notification_level VARCHAR(100),
    notification_type VARCHAR(100),
    sent_at TIMESTAMP DEFAULT NOW(),
    read_status BOOLEAN DEFAULT FALSE
);

-- Firebase Device Tokens Table
CREATE TABLE IF NOT EXISTS firebase_device_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    device_token TEXT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Notification Fanout Log Table
CREATE TABLE IF NOT EXISTS notification_fanout_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    notification_id UUID REFERENCES notifications(notification_id) ON DELETE CASCADE,
    device_token TEXT,
    status VARCHAR(50),  -- success / failed
    error_message TEXT,
    sent_at TIMESTAMP DEFAULT NOW()
);

-- Audit Logs Table
CREATE TABLE IF NOT EXISTS audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    actor_type TEXT NOT NULL,
    actor_reference TEXT NOT NULL,
    action_performed VARCHAR(255),
    details TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);