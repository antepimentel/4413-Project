/** bid: unique identifier of Book (like ISBN)
* title: title of Book
* price: unit price WHEN ordered
* author: name of authors
* category: as specified
*/
DROP TABLE Book;
CREATE TABLE Book (
bid VARCHAR(20) NOT NULL,
title VARCHAR(60) NOT NULL,
price INT NOT NULL,
category VARCHAR(32) CONSTRAINT categories CHECK  (category IN ('Science','Fiction','Engineering')),
/**category ENUM('Science','Fiction','Engineering') NOT NULL, **/
PRIMARY KEY(bid)
);
/**#
# Adding data for table 'Book'
#**/
INSERT INTO Book (bid, title, price, category) VALUES ('b001', 'Little Prince', 20, 'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b002','Physics', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b003','Mechanics' ,100,'Engineering');

/* Address
* id: address id
*
*/
CREATE TABLE Address (
id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
street VARCHAR(100) NOT NULL,
province VARCHAR(20) NOT NULL,
country VARCHAR(20) NOT NULL,
zip VARCHAR(20) NOT NULL,
phone VARCHAR(20),
PRIMARY KEY(id)
);
/**
# Inserting data for table 'address'
#**/
INSERT INTO Address (street, province, country, zip, phone) VALUES ('123 Yonge St', 'ON',
'Canada', 'K1E 6T5' ,'647-123-4567');
INSERT INTO Address (street, province, country, zip, phone) VALUES ('445 Avenue rd', 'ON',
'Canada', 'M1C 6K5' ,'416-123-8569');
INSERT INTO Address (street, province, country, zip, phone) VALUES ('789 Keele St.', 'ON',
'Canada', 'K3C 9T5' ,'416-123-9568');

/* Purchase Order
* lname: last name
* fname: first name
* id: purchase order id
* status:status of purchase
*/
CREATE TABLE PO (
id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
lname VARCHAR(20) NOT NULL,
fname VARCHAR(20) NOT NULL,
/**status ENUM('ORDERED','PROCESSED','DENIED') NOT NULL,**/
status VARCHAR(32) CONSTRAINT statusValues CHECK  (status IN ('ORDERED','PROCESSED','DENIED')),

address INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE
);

/**#
# Inserting data for table 'PO'
#**/
INSERT INTO PO (lname, fname, status, address) VALUES ('John', 'White', 'PROCESSED', 1);
INSERT INTO PO (lname, fname, status, address) VALUES ('Peter', 'Black', 'DENIED', 2);
INSERT INTO PO (lname, fname, status, address) VALUES ('Andy', 'Green', 'ORDERED', 3);

/* Items on order
* id : purchase order id
* bid: unique identifier of Book
* price: unit price
*/
CREATE TABLE POItem (
id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
bid VARCHAR(20) NOT NULL,
price INT NOT NULL,
PRIMARY KEY(id,bid),
FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);
CREATE INDEX IDINDEX ON POItem (id);
CREATE INDEX bidIndex ON  POItem (bid);

/**#
# Inserting data for table 'POitem'
#**/
INSERT INTO POItem (bid, price) VALUES ('b001', 20);
INSERT INTO POItem (bid, price) VALUES ('b002', 201);
INSERT INTO POItem (bid, price) VALUES ('b003', 100);

/* visit to website
* day: date
* bid: unique identifier of Book
* eventtype: status of purchase
*/
DROP TABLE VisitEvent;
CREATE TABLE VisitEvent (
day varchar(8) NOT NULL,
bid varchar(20) not null,
/**eventtype ENUM('VIEW','CART','PURCHASE') NOT NULL,**/
eventtype VARCHAR(32) NOT NULL CONSTRAINT types CHECK  (eventtype IN ('VIEW','CART','PURCHASE')),

FOREIGN KEY(bid) REFERENCES Book(bid)
);

/**#
# Dumping data for table 'VisitEvent'
#**/
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12202015', 'b001', 'VIEW');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12242015', 'b001', 'CART');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12252015', 'b001', 'PURCHASE');
