create table bank_accounts (
    number char(26),
    name varchar,
    country_code char(2),
    CONSTRAINT bank_accounts_pk PRIMARY KEY (country_code, number)
)