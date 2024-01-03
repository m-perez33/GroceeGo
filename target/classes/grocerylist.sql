/**************************************************************************
-- Step 2: Drop all database objects to start with an empty database
**************************************************************************/
DROP TABLE IF EXISTS list_entry;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS grocery_list;



/**************************************************************************
-- Step 1: Create the customer table
**************************************************************************/
-- Table customer
CREATE TABLE grocery_list(
	grocery_list_id serial NOT NULL,
    created_date DATE NOT NULL DEFAULT CURRENT_DATE
	CONSTRAINT PK_grocery_list PRIMARY KEY (grocery_list_id)
);



/**************************************************************************
-- Step 3: Create the service table
**************************************************************************/
-- Table service
CREATE TABLE product(
	product_id serial NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT PK_product PRIMARY KEY (service_id)
);


/**************************************************************************
-- Step 4: Create the pet table
**************************************************************************/
-- Table pet
CREATE TABLE list_entry(
	list_entry_id serial NOT NULL,
	quantity numeric(7,2) NOT NULL,
	cost numeric(7,2) NOT NULL,
	grocery_list_id int NOT NULL,
	product_id int NOT NULL,
	CONSTRAINT PK_list_entry PRIMARY KEY (list_entry_id),
	CONSTRAINT FK_grocery_list FOREIGN KEY(grocery_list_id) REFERENCES grocery_list (grocery_list_id)
	CONSTRAINT FK_product FOREIGN KEY(product_id) REFERENCES product (product_id)

);



/**************************************************************************
-- Step 5: Add a check constraint to the pet table
**************************************************************************/
--ALTER TABLE pet
	--ADD CONSTRAINT CHK_type CHECK (type IN ('Dog', 'Bird', 'Cat', 'Reptile', 'Fish', 'Primate'));


/**************************************************************************
-- Step 6: Create the pet_service table
**************************************************************************/
-- Table pet_service




/***********************************************************************************************************
 Insert test data into the tables. Un-comment these INSERT and SELECT statements as you create new tables above.
***********************************************************************************************************/

/**************************************************************************
-- Step 1a: Insert test data into the customer table and select it back out.
**************************************************************************/
INSERT INTO grocery_list (created_date) VALUES
	('2023-12-22'),
SELECT * FROM grocery_list;


/**************************************************************************
-- Step 3a: Insert test data into the service table and select it back out.
**************************************************************************/
INSERT INTO product (name) VALUES
	('Banana'),
	('Cereal'),
	('Pork loin'),
	('Toilet paper'),

SELECT * FROM product;


/**************************************************************************
-- Step 4a: Insert test data into the pet table and select it back out.
**************************************************************************/
INSERT INTO list_entry (quantity, cost, grocery_list_id, product_id) VALUES
	('3', '2.5', (Select grocery_list_id from grocery_list), (Select product_id from product where name = 'Banana')),
	('2', '3', (Select grocery_list_id from grocery_list), (Select product_id from product where name = 'Cereal')),
	('10', '30', (Select grocery_list_id from grocery_list), (Select product_id from product where name = 'Toilet paper')),
	('1', '4', (Select grocery_list_id from grocery_list), (Select product_id from product where name = 'Pork loin')),

SELECT * FROM list_entry;



/**************************************************************************
-- Step 6a: Insert test data into the pet_service table and select it back out.
**************************************************************************/
-- INSERT INTO pet_service (service_date, pet_id, service_id) VALUES
-- 	( '2021-01-22', (Select pet_id from pet where name = 'Sarah'),       (Select service_id from service where name = 'Checkup')),
-- 	( '2021-01-22', (Select pet_id from pet where name = 'Mr. Jenkins'), (Select service_id from service where name = 'Checkup')),
-- 	( '2021-01-22', (Select pet_id from pet where name = 'Sarah'),       (Select service_id from service where name = 'Rabies vaccination')),
-- 	( '2021-02-04', (Select pet_id from pet where name = 'Sarah'),       (Select service_id from service where name = 'Bath')),
-- 	( '2021-02-04', (Select pet_id from pet where name = 'Mr. Jenkins'), (Select service_id from service where name = 'Bath')),
-- 	( '2021-02-04', (Select pet_id from pet where name = 'Stanley'),     (Select service_id from service where name = 'Bath')),
-- 	( '2021-02-04', (Select pet_id from pet where name = 'Lump'),        (Select service_id from service where name = 'Flea and tick treatment')),
-- 	( '2021-02-04', (Select pet_id from pet where name = 'Minou'),       (Select service_id from service where name = 'Flea and tick treatment')),
-- 	( '2021-02-04', (Select pet_id from pet where name = 'Lump'),        (Select service_id from service where name = 'Rabies vaccination')),
-- 	( '2021-02-04', (Select pet_id from pet where name = 'Minou'),       (Select service_id from service where name = 'Rabies vaccination')),
-- 	( '2021-02-10', (Select pet_id from pet where name = 'OG Malley'),   (Select service_id from service where name = 'Flea and tick treatment')),
-- 	( '2021-02-11', (Select pet_id from pet where name = 'Bubbles'),     (Select service_id from service where name = 'Flea and tick treatment')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Martha'),      (Select service_id from service where name = 'Bath')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Martha'),      (Select service_id from service where name = 'Clip nails')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Martha'),      (Select service_id from service where name = 'Flea and tick treatment')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Martha'),      (Select service_id from service where name = 'Heart worm test')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Thisbe'),      (Select service_id from service where name = 'Parvo vaccination')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Thisbe'),      (Select service_id from service where name = 'Bath')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Thisbe'),      (Select service_id from service where name = 'Clip nails')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Thisbe'),      (Select service_id from service where name = 'Flea and tick treatment')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Thisbe'),      (Select service_id from service where name = 'Heart worm test')),
-- 	( '2021-02-12', (Select pet_id from pet where name = 'Thisbe'),      (Select service_id from service where name = 'Rabies vaccination'));
-- SELECT * FROM pet_service;

/**************************************************************************
-- Step 7: Health History Report: by customer, pet, and date
**************************************************************************/




/**************************************************************************
-- Step 8: Invoice for McCartney visit of 2/12
**************************************************************************/



