CREATE TABLE BANK_ACCOUNT(
	ACCOUNT_ID INT AUTO_INCREMENT PRIMARY KEY,
	ACCOUNT_NUMBER NUMBER NOT NULL UNIQUE,
	PIN VARCHAR2(500) NOT NULL,
	OPENING_BALANCE NUMBER,
	OVERDRAFT NUMBER
);

CREATE TABLE ATM_MACHINE_BALANCE(
	ID INT AUTO_INCREMENT PRIMARY KEY,
	BILL_DENOMINATION VARCHAR(10) NOT NULL UNIQUE,
	NUMBER_OF_BILLS BIGINT NOT NULL
);