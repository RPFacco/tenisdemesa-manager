## ADDED Requirements

### Requirement: Application connects to MySQL via environment variables
The system SHALL read database connection parameters from environment variables DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD with SSL required.

#### Scenario: Database connection configured
- **WHEN** application starts with DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD environment variables set
- **THEN** system connects to MySQL using those credentials with `sslMode=REQUIRED`

### Requirement: Health endpoint is available
The system SHALL expose an actuator health endpoint at `/actuator/health`.

#### Scenario: Health check returns UP
- **WHEN** a GET request is made to `/actuator/health`
- **THEN** system returns `{"status":"UP"}` without querying the database

### Requirement: Application can be deployed as Docker container
The system SHALL provide a multi-stage Dockerfile for building and running the application.

#### Scenario: Docker build succeeds
- **WHEN** `docker build` is executed from project root
- **THEN** a Maven build runs in the first stage and a JRE Alpine image runs the resulting jar in the second stage

### Requirement: Port is configurable via environment
The system SHALL use the PORT environment variable when available, falling back to 8080.

#### Scenario: Port configuration
- **WHEN** PORT environment variable is set
- **THEN** server listens on that port; otherwise it listens on 8080
