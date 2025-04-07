```sql
CREATE TABLE account
(
    uuid              UUID PRIMARY KEY,
    email             VARCHAR(255) NOT NULL,
    hash_password     VARCHAR(255) NOT NULL,
    token             VARCHAR(255),
    confirmation_code VARCHAR(255),
    is_confirmed      BOOLEAN DEFAULT FALSE,
    CONSTRAINT uk_account_email UNIQUE (email)
);
```