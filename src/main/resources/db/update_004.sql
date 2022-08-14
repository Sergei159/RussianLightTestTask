CREATE TABLE IF NOT EXISTS categories(
	id SERIAL PRIMARY KEY,
	name VARCHAR(100),
	description TEXT
);

CREATE TABLE IF NOT EXISTS products(
	id SERIAL PRIMARY KEY,
	name VARCHAR(50),
	description TEXT,
	price INT,
	photo BYTEA,
	created TIMESTAMP,
	status BOOLEAN,
	category_id INT REFERENCES categories(id)
);


CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS persons (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role_id INT REFERENCES roles(id)
);

INSERT INTO roles (name) VALUES ('USER'), ('ADMIN');


INSERT INTO categories(name, description) VALUES
('кабельная продукция', 'кабельная продукция ГОСТ 31565-2012');
INSERT INTO categories(name, description) VALUES
('освещение', 'товары для наружного и внутреннего освещения' );
INSERT INTO categories(name, description) VALUES
('кабеленесущие системы', 'оборудование для прокладки кабеля' );


INSERT INTO persons (name, email, password, role_id) VALUES ('Sergei', 'Serega@com', '$2a$12$0JOV0/MTQuYW7JhIPXy3tua73YoUa4.VDQEPDjM9lpwfWqAue7Yli', 1);
INSERT INTO persons (name, email, password, role_id) VALUES ('SergeiAdmin', 'Admin@com', '$2a$12$15bw0zNjbw3yU3dXPKwpR.yXBPPsCX/1pdU3mcaVj.SMD0i00XJOy', 2);


INSERT INTO products(name, description, price, photo, created, status, category_id) VALUES
('Кабель ВВГнг-LS 3 x 1.5', 'медный кабель ВВГнг-LS 3 x 1.5 ГОСТ 31565-2012', 7240, null, current_timestamp, true, 1);

INSERT INTO products(name, description, price, photo, created, status, category_id) VALUES
('Кабель ВВГнг-LS 3 x 4', 'медный кабель ВВГнг-LS 3 x 1.5 ГОСТ 31565-2012', 15620, null, current_timestamp, true, 1);

INSERT INTO products(name, description, price, photo, created, status, category_id) VALUES
('Светильник светодиодный PHILIPS RC048B ', 'Светильник светодиодный RC048B LED32S/840 PSU W60L60 NOCCFW панель PHILIPS 911401801480', 238520, null, current_timestamp, true, 2);

INSERT INTO products(name, description, price, photo, created, status, category_id) VALUES
('Светильник светодиодный PHILIPS CL200 ', 'Светильник светодиодный CL200 EC RD 17W 65K W HV 02 PHILIPS 915005773907', 146020, null, current_timestamp, true, 2);

INSERT INTO products(name, description, price, photo, created, status, category_id) VALUES
('Кабель-канал 20х10', 'Кабель-канал 20х10 L2000 пластик ЭЛЕКОР IEK CKK10-020-010-1-K01', 9650, null, current_timestamp, true, 3);

INSERT INTO products(name, description, price, photo, created, status, category_id) VALUES
('Кабель-канал 40х25', 'Кабель-канал 40х25 L2000 пластик ЭЛЕКОР IEK CKK10-040-025-1-K01', 20880, null, current_timestamp, true, 3);


