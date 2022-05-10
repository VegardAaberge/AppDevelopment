create table person (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(150),
	gender VARCHAR(7) NOT NULL,
	date_of_birth DATE NOT NULL,
	country_of_birth VARCHAR(50)
);

INSERT INTO person(
	first_name,
	last_name,
	gender,
	date_of_birth
)
VALUES ('Anne', 'Smith', 'Female', DATE '1988-01-09')

SELECT country_of_birth FROM person ORDER BY country_of_birth

SELECT DISTINCT country_of_birth FROM person ORDER BY country_of_birth DESC

SELECT * FROM person WHERE gender = 'Male' AND (country_of_birth = 'Poland' OR country_of_birth = 'China') AND last_name = 'Reynoldson'

SELECT 1 <> 1;

SELECT * FROM person OFFSET 5 LIMIT 10

SELECT * FROM person OFFSET 5 FETCH FIRST 5 ROW ONLY

SELECT * FROM person WHERE country_of_birth IN ('China', 'Brazil', 'France', 'Portugal', 'Nigeria') ORDER BY country_of_birth

SELECT * FROM person WHERE date_of_birth BETWEEN DATE '2021-06-01' AND '2022-01-01'

SELECT * FROM person WHERE email LIKE '%google.com'
SELECT * FROM person WHERE email LIKE '%google.%';

SELECT * FROM person WHERE email LIKE '_________@%';

SELECT * FROM person WHERE country_of_birth ILIKE 'p%';

SELECT country_of_birth, COUNT(*) FROM person GROUP BY country_of_birth ORDER BY country_of_birth

SELECT country_of_birth, COUNT(*) FROM person GROUP BY country_of_birth HAVING COUNT(*) > 5 ORDER BY country_of_birth

SELECT COALESCE(email, 'Email not provided') from person

// CARS
ALTER TABLE car
ADD COLUMN pricedecimal numeric(19,2) 
GENERATED ALWAYS AS (CAST(RIGHT(price, length(price) - 1) as DECIMAL(19,2))) STORED;

SELECT ROUND(avg(pricedecimal)) FROM car

SELECT make, model, MIN(price) FROM car GROUP BY make, model
SELECT make, SUM(pricedecimal) FROM car GROUP by make

SELECT id, make, model, 
    price as original_price, 
    ROUND(pricedecimal*.10, 2) AS ten_percent_value, 
    ROUND(pricedecimal - (pricedecimal*0.1), 2) AS discount_after_10_percent
FROM car

SELECT COALESCE(10 / NULLIF(0, 0), 0)

// Dates
SELECT (now() + interval '10 years')::date
SELECT extract(YEAR FROM now())

SELECT first_name, last_name, gender, country_of_birth, date_of_birth, 
    age(now(), date_of_birth) AS age
FROM person

// Constraints
ALTER TABLE person DROP CONSTRAINT person_pkey;
ALTER TABLE person ADD PRIMARY KEY person_pkey;
ALTER TABLE person ADD CONSTRAINT unique_email_address UNIQUE (email)

ALTER TABLE person
ADD CONSTRAINT gender_constraint 
CHECK (gender = 'Female' OR gender = 'Male' OR gender = 'Agender');

// Update person
UPDATE person 
SET first_name = 'Omar', last_name = 'Montana', email = 'ommar.montana@hotmail.com' 
WHERE id = 11;

INSERT INTO person (id, first_name, last_name, gender, email, date_of_birth, country_of_birth)
VALUES (11,	'Omar',	'Montana', 'Female', 'ommar.montana@hotmail.com','2021-09-11', 'China')
ON CONFLICT (id) DO NOTHING

