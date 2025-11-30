CREATE TABLE users (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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
);

CREATE TABLE clinics (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    clinic_name         VARCHAR(255) NOT NULL,
    address             TEXT,
    contact_email       VARCHAR(255),
    created_at          TIMESTAMP DEFAULT NOW()
    verified            BOOLEAN
    license_no          TEXT
);
    CREATE TABLE medical_records (
        id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

        record_type         VARCHAR(100),         -- e.g., Lab Result, Immunization
        record_data         TEXT NOT NULL,
        hash_local          TEXT NOT NULL,        -- Hash of record stored in DB
        blockchain_tx_id    TEXT,
        created_at          TIMESTAMP DEFAULT NOW()
    );
CREATE TABLE permissions (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    clinic_id           UUID NOT NULL REFERENCES clinics(id) ON DELETE CASCADE,

    access_type         VARCHAR(50) NOT NULL,
    granted_at          TIMESTAMP DEFAULT NOW(),
    revoked             BOOLEAN DEFAULT FALSE,
    revoked_at          TIMESTAMP
);
CREATE TABLE notifications (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

    title               VARCHAR(255) NOT NULL,
    message             TEXT NOT NULL,
    category            VARCHAR(100),

    sent_at             TIMESTAMP DEFAULT NOW(),
    read_status         BOOLEAN DEFAULT FALSE
);
CREATE TABLE firebase_device_tokens (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    device_token        TEXT NOT NULL,
    created_at          TIMESTAMP DEFAULT NOW(),
    UNIQUE (device_token)
);
CREATE TABLE notification_fanout_log (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    notification_id     UUID REFERENCES notifications(id) ON DELETE CASCADE,
    device_token        TEXT,
    status              VARCHAR(50),         -- success / failed
    error_message       TEXT,
    sent_at             TIMESTAMP DEFAULT NOW()
);
CREATE TABLE audit_logs (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id             UUID REFERENCES users(id),
    action              VARCHAR(255) NOT NULL,
    details             JSONB,
    created_at          TIMESTAMP DEFAULT NOW()
);