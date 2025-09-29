DROP TABLE IF EXISTS panic_status CASCADE;

CREATE TABLE panic_status
(
    id             UUID        NOT NULL,
    status         VARCHAR(64) NOT NULL,
    metadata       JSONB,
    timestamp      TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    declared_by    VARCHAR(64) NOT NULL,
    declared_by_id VARCHAR(64),
    CONSTRAINT PK_panic_status PRIMARY KEY (id)
);

CREATE INDEX IDX_panic_status_timestamp ON panic_status (timestamp);

INSERT INTO panic_status
VALUES (gen_random_uuid(), 'OK', NULL, NOW(), 'Init');
