CREATE TABLE IF NOT EXISTS users (
    user_id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    -- Signup Captured Details
    email                       VARCHAR(255) UNIQUE NOT NULL,
    hashed_password             TEXT NOT NULL,
    first_name                  VARCHAR(255),
    last_name                   VARCHAR(255),
    phone_number                VARCHAR(50),
    dob                         DATE,
    -- Profile completion basic health information
    gender                      VARCHAR(20),
    address                     TEXT,
    blood_type                  TEXT,
    genotype                    TEXT,
    known_allergies             TEXT,
    pre_existing_conditions     TEXT,
    -- Emergency contact information
    emergency_contact_name      TEXT,
    emergency_contact_phone     TEXT,
    emergency_contact_rel       TEXT,
    -- Profile completion location details
    nationality                 VARCHAR(100),
    state_of_origin             VARCHAR(100),

    created_at                  TIMESTAMP DEFAULT NOW(),
    -- Changed after using otp to verify account
    verified                    BOOLEAN DEFAULT false
    role                        text default RESIDENT
);
CREATE TABLE IF NOT EXISTS user_sessions (
    session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_email VARCHAR(255) NOT NULL,
    refresh_token TEXT NOT NULL,
    ip_address VARCHAR(100),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    expires_at TIMESTAMP,
    revoked BOOLEAN DEFAULT FALSE
);
CREATE TABLE IF NOT EXISTS otp_codes (
    otp_id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email               VARCHAR(255) NOT NULL,
    otp_code            VARCHAR(10),
    created_at          TIMESTAMP DEFAULT NOW()
    expires_at          TIMESTAMP NOT NULL
    used                BOOLEAN DEFAULT FALSE
);
CREATE TABLE IF NOT EXISTS clinics (
    clinic_id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    clinic_name         VARCHAR(255) NOT NULL,
    address             TEXT,
    contact_email       VARCHAR(255),
    created_at          TIMESTAMP DEFAULT NOW()
    verified            BOOLEAN
    license_no          TEXT
);
CREATE TABLE IF NOT EXISTS medical_records (
    record_id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_email          UUID NOT NULL REFERENCES users(email) ON DELETE CASCADE,
    record_type         VARCHAR(100),         -- e.g., Lab Result, Immunization
    record_data         TEXT NOT NULL,
    clinic_name         VARCHAR(100),         -- e.g., Lab Result, Immunization
    hash_local          TEXT NOT NULL,        -- Hash of record stored in DB
    blockchain_tx_id    TEXT,
    blockchain_tx_hash  TEXT,
    block_number        TEXT,
    verified            boolean,
    created_at          TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS medical_records_shared_with(
    shared_with_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    record_id           UUID NOT NULL,
    clinic_id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
)
CREATE TABLE IF NOT EXISTS permissions(
    permissions_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email               UUID NOT NULL REFERENCES users(email) ON DELETE CASCADE,
    clinic_id           UUID NOT NULL REFERENCES clinics(id) ON DELETE CASCADE,

    access_type         VARCHAR(50) NOT NULL,
    granted_at          TIMESTAMP,
    expires_at          TIMESTAMP
    granted             BOOLEAN DEFAULT FALSE,
    revoked             BOOLEAN DEFAULT FALSE,
    revoked_at          TIMESTAMP
    expires_at          TIMESTAMP
);
CREATE TABLE IF NOT EXISTS notifications (
    notification_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_email          TEXT NOT NULL REFERENCES users(email) ON DELETE CASCADE,

    title               VARCHAR(255) NOT NULL,
    message             TEXT NOT NULL,
    notification_level  VARCHAR(100),
    notification_type   VARCHAR(100),

    sent_at             TIMESTAMP DEFAULT NOW(),
    read_status         BOOLEAN DEFAULT FALSE
);
CREATE TABLE IF NOT EXISTS firebase_device_tokens (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    device_token        TEXT NOT NULL,
    created_at          TIMESTAMP DEFAULT NOW(),
    UNIQUE (device_token)
);
CREATE TABLE IF NOT EXISTS notification_fanout_log (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    notification_id     UUID REFERENCES notifications(id) ON DELETE CASCADE,
    device_token        TEXT,
    status              VARCHAR(50),         -- success / failed
    error_message       TEXT,
    sent_at             TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS audit_logs (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id             UUID REFERENCES users(id),
    action              VARCHAR(255) NOT NULL,
    details             JSONB,
    created_at          TIMESTAMP DEFAULT NOW()
);