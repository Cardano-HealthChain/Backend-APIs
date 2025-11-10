# HealthChain: API Service (Backend)

This repository contains the backend API service for the HealthChain ecosystem. It acts as the central gateway connecting the `frontend-pwa` to off-chain storage and the `plutus-contracts` on the blockchain.

Its primary responsibilities are:
* Acting as a REST API gateway for the frontend.
* Handling encryption and storage of data in an off-chain solution (like IPFS).
* Managing and sending push notifications via a service like Firebase.
* Providing simple, aggregated metrics for the Health Authority dashboard.
* Routing chat requests to the `ml-assistant` service.

➡️ **View the main organization page:** [Link to your GitHub Org]

---

### Technology Stack

* **Framework:** Node.js (with Express, NestJS, or similar)
* **Language:** TypeScript
* **Notifications:** Firebase Admin SDK
* **Storage:** IPFS (or other blob storage) integration
* **Database:** MongoDB / Postgres (for user metadata, notification tokens, etc.)

### Core MVP Endpoints

* `POST /api/v1/auth/did-init`: Triggers the DID creation process.
* `GET /api/v1/user/{did}/record-hash`: Retrieves the latest record hash from the blockchain.
* `POST /api/v1/data/upload`: Encrypts and uploads data to off-chain storage, returning a hash.
* `POST /api/v1/vc/issue-request`: Used by clinics to initiate a new record write.
* `POST /api/v1/alerts/send`: Used by the Authority to send geotargeted alerts.
* `GET /api/v1/dashboard/metrics`: Returns simple counts for Total DIDs and Total VCs Issued.
* `POST /api/v1/assistant/chat`: Forwards chat messages to the AI Assistant service.

### Getting Started

1.  **Clone the repository:**
    ```bash
    git clone [repository-url]
    cd api-service
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    ```

3.  **Set up environment variables:**
    Create a `.env` file in the root and add the required keys:
    ```
    # Service port
    PORT=3001

    # Database connection string
    DB_CONNECTION_STRING=...

    # Firebase Admin SDK service account key (as JSON)
    FIREBASE_SERVICE_ACCOUNT=...

    # URL for the ML-Assistant
    ML_ASSISTANT_URL=http://localhost:3002/api/v1
    
    # Off-chain storage/IPFS credentials
    IPFS_GATEWAY_URL=...
    ```

4.  **Run the development server:**
    ```bash
    npm run dev
    ```
