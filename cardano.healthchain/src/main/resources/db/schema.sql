CREATE TABLE dids (
    id SERIAL PRIMARY KEY,
    did VARCHAR(100) UNIQUE NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    region VARCHAR(50),
    public_key VARCHAR(512),
    created_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE record_hashes (
    id SERIAL PRIMARY KEY,
    did_id INT REFERENCES dids(id) ON DELETE CASCADE,
    latest_record_hash VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT NOW()
);
CREATE TABLE vaccination_credentials (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(255) UNIQUE NOT NULL,
    user_did_id INT REFERENCES dids(id) ON DELETE CASCADE,
    clinic_id VARCHAR(100),
    vaccine_type VARCHAR(50),
    dose INT,
    issued_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE alerts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    category VARCHAR(50),
    region VARCHAR(50),
    recipients INT DEFAULT 0,
    status VARCHAR(50),
    sent_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE dashboard_metrics (
    id SERIAL PRIMARY KEY,
    total_dids INT DEFAULT 0,
    total_vcs_issued INT DEFAULT 0,
    last_updated TIMESTAMP DEFAULT NOW()
);
CREATE INDEX idx_did_did ON dids(did);
CREATE INDEX idx_vc_user_did ON vaccination_credentials(user_did_id);
CREATE INDEX idx_alert_region ON alerts(region);
CREATE INDEX idx_record_did ON record_hashes(did_id);