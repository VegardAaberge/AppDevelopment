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