CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);
CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company (id, name) VALUES (1, 'Yandex'), (2, 'Sber'), (3, 'Mail.ru'),
(4, 'Inopolis'), (5, 'Telegram');

INSERT INTO person (id, name, company_id) VALUES (1, 'Ivan', 1),
(2, 'Petr', 1), (3, 'Dmitry', 2), (4, 'Alex', 2), (5, 'Stas', 3), (6, 'Vlad', 4), (7, 'Pavel', 5);

SELECT * FROM company;
SELECT * FROM person;

SELECT p.name, c.name FROM person p JOIN company c ON p.company_id = c.id AND p.company_id <> 5;


SELECT c.name company, count(p.id) n_personnel FROM person p  
JOIN company c 
ON p.company_id = c.id 
GROUP BY c.name
HAVING count(p.id) = (SELECT count(p.id) FROM person p
GROUP BY p.company_id
ORDER BY count(p.id) DESC
LIMIT 1
) AS max_person;

