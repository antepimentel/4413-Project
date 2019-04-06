SET FOREIGN_KEY_CHECKS = 0;

/**
 * Drop all tables first
 */
DROP TABLE if exists Customer;
DROP TABLE if exists Address;
DROP TABLE if exists Book;
DROP TABLE if exists BookReview;
DROP TABLE if exists PO;
DROP TABLE if exists POItem;
DROP TABLE if exists VisitEvent;
DROP TABLE if exists ShoppingCart;

/** id: unique identifier of customer
* username:
* password:
* fname:
* lname:
* address: address id (link to Address)
*/
CREATE TABLE Customer (
	username VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	password varchar(20) NOT NULL,
	fname VARCHAR(20) NOT NULL,
	lname VARCHAR(20) NOT NULL,
	c_type ENUM('CUSTOMER','ADMIN','PARTNER') NOT NULL,
	PRIMARY KEY(username)
);

INSERT INTO Customer  (username, email, password, fname, lname, c_type) VALUES ('antep', 'email@123.com', 'password', 'Ante', 'Pimentel', 'CUSTOMER');
INSERT INTO Customer  (username, email, password, fname, lname, c_type) VALUES ('rajan','email@123.com', 'password', 'Sukhrajan', 'Johal', 'CUSTOMER');
INSERT INTO Customer  (username, email, password, fname, lname, c_type) VALUES ('sara1','email@123.com', 'password', 'Sara', 'Attalla', 'CUSTOMER');
INSERT INTO Customer  (username, email, password, fname, lname, c_type) VALUES ('sarah2','email@123.com', 'password', 'Sarah', 'Feroz', 'CUSTOMER');
INSERT INTO Customer  (username, email, password, fname, lname, c_type) VALUES ('admin','email@123.com', 'password', 'Sarah', 'Feroz', 'ADMIN');
INSERT INTO Customer  (username, email, password, fname, lname, c_type) VALUES ('partner','email@123.com', 'password', 'Sarah', 'Feroz', 'PARTNER');

/* Address
* id: address id
*
*/
CREATE TABLE Address (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	cid VARCHAR(20) NOT NULL,
	street VARCHAR(100) NOT NULL,
	city VARCHAR(20) NOT NULL,
	province VARCHAR(20) NOT NULL,
	country VARCHAR(20) NOT NULL,
	zip VARCHAR(20) NOT NULL,
	phone VARCHAR(20),
	PRIMARY KEY(id),
	FOREIGN KEY(cid) REFERENCES Customer (username)
);

INSERT INTO Address (cid, street, city, province, country, zip, phone) 
VALUES ('antep', '123 Yonge St', 'Toronto','ON', 'Canada', 'K1E 6T5' ,'647-123-4567');

INSERT INTO Address (cid, street, city, province, country, zip, phone) 
VALUES ('antep', '123 Yonge St', 'Toronto', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567');

INSERT INTO Address (cid, street, city, province, country, zip, phone) 
VALUES ('antep', '45 Fake Ave', 'Toronto', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567');

INSERT INTO Address (cid, street, city, province, country, zip, phone) 
VALUES ('sarah2', '445 Avenue rd', 'Toronto', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569');

INSERT INTO Address (cid, street, city, province, country, zip, phone) 
VALUES ('sara1', '789 Keele St.', 'Toronto', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');

/** bid: unique identifier of Book (like ISBN)
* title: title of Book
* price: unit price WHEN ordered
* author: name of authors
* category: as specified
*/
CREATE TABLE Book (
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(60) NOT NULL,
	price INT NOT NULL,
	category ENUM('Science','Fiction','Engineering') NOT NULL,
	PRIMARY KEY(bid)
);

INSERT INTO Book (bid, title, price, category) VALUES ('b001', 'Little Prince', 20, 'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b002','Physics', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b003','Mechanics' ,100,'Engineering');
INSERT INTO Book (bid, title, price, category) VALUES ('b004','Chemistry', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b005','Biology', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b006','Chemistry II', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b007','Calculus I' ,100,'Engineering');
INSERT INTO Book (bid, title, price, category) VALUES ('b008','Calculus II' ,100,'Engineering');
INSERT INTO Book (bid, title, price, category) VALUES ('b009', 'Little Prince 2: Revengeance', 20, 'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b010', 'Harry Potter', 20, 'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b011', 'Lord of the Rings', 20, 'Fiction');

/** bid: links to Book
* cid: links to customer who left rating
* rating: 1-5 rating
* review: 200 char review 
*/
CREATE TABLE BookReview (
	bid VARCHAR(20) NOT NULL,
	cid VARCHAR(20) NOT NULL,
	rating INT UNSIGNED NOT NULL,
	review VARCHAR(200),
	FOREIGN KEY (bid) REFERENCES Book (bid) ON DELETE CASCADE,
	FOREIGN KEY(cid) REFERENCES Customer (username)
);


/* Purchase Order
* lname: last name
* fname: first name
* id: purchase order id
* status:status of purchase
*/
CREATE TABLE PO (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	cid VARCHAR(20) NOT NULL,
	status ENUM('ORDERED','PROCESSED','DENIED') NOT NULL,
	PRIMARY KEY(id),
	INDEX (cid),
	FOREIGN KEY (cid) REFERENCES Customer (username) ON DELETE CASCADE
);

INSERT INTO PO (id, cid, status) VALUES (1, 'antep', 'PROCESSED');
INSERT INTO PO (id, cid, status) VALUES (2, 'sarah1', 'DENIED');
INSERT INTO PO (id, cid, status) VALUES (3, 'sara2', 'ORDERED');

/* Items on order
* id : purchase order id
* bid: unique identifier of Book
* price: unit price
*/
CREATE TABLE POItem (
	id INT UNSIGNED NOT NULL,
	bid VARCHAR(20) NOT NULL,
	price INT UNSIGNED NOT NULL,
	quantity INT UNSIGNED NOT NULL,
	PRIMARY KEY(id,bid),
	INDEX (id),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
	INDEX (bid),
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);

INSERT INTO POItem (id, bid, price, quantity) VALUES (1, 'b001', '20', '1');
INSERT INTO POItem (id, bid, price, quantity) VALUES (2, 'b002', '201', '2');
INSERT INTO POItem (id, bid, price, quantity) VALUES (3, 'b003', '100', '2');


/* visit to website
* day: date
* bid: unique identifier of Book
* eventtype: status of purchase
*/ 
CREATE TABLE VisitEvent (
	day varchar(8) NOT NULL,
	bid varchar(20) not null REFERENCES Book.bid,
	eventtype ENUM('VIEW','CART','PURCHASE') NOT NULL,
	FOREIGN KEY(bid) REFERENCES Book(bid)
);

INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12202015', 'b001', 'VIEW');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12242015', 'b001', 'CART');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12252015', 'b001', 'PURCHASE');

/* Shopping cart items for all shopping carts
* username: customer of cid
* price: date
* bid: unique identifier of Book
* quantity: number of items
*/ 
CREATE TABLE ShoppingCart (
	username varchar(20) NOT NULL REFERENCES CUSTOMER.username,
	bid varchar(20) not null REFERENCES Book.bid,
	unitPrice int not NULL REFERENCES Book.price,
	quantity int not NULL,
	FOREIGN KEY(username) REFERENCES Customer(username),
	FOREIGN KEY(bid) REFERENCES Book(bid)
);

SET FOREIGN_KEY_CHECKS = 1;