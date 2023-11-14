drop database if exists serisaraNetworks;
CREATE DATABASE if not exists serisaraNetworks;

USE serisaraNetworks;

CREATE TABLE user(
                     userId VARCHAR (25) PRIMARY KEY,
                     userName VARCHAR (100),
                     password VARCHAR (100),
                     email VARCHAR (100),
                     img longblob
);

CREATE TABLE employee(
                         empId VARCHAR (25) PRIMARY KEY,
                         name VARCHAR (100),
                         address VARCHAR (100),
                         position VARCHAR (50),
                         contact VARCHAR (11),
                         salary INT(15),
                         userId VARCHAR (25),
                         CONSTRAINT FOREIGN KEY(userId) REFERENCES user(userId) on Delete Cascade on Update Cascade
);

CREATE TABLE customer(
                         cId VARCHAR (25) PRIMARY KEY,
                         name VARCHAR (100),
                         email VARCHAR (100),
                         address VARCHAR (100),
                         contact VARCHAR (11),
                         userId VARCHAR (25),
                         CONSTRAINT FOREIGN KEY(userId) REFERENCES user(userId) on Delete Cascade on Update Cascade
);

CREATE TABLE handoverDevice(
                               deviceId VARCHAR (25) PRIMARY KEY,
                               dName VARCHAR (100),
                               problem VARCHAR (200),
                               status VARCHAR (100),
                               cost INT (25),
                               date DATE,
                               cId VARCHAR (25),
                               CONSTRAINT FOREIGN KEY(cId) REFERENCES customer(cId) on Delete Cascade on Update Cascade
);

CREATE TABLE item(
                     itemId VARCHAR (25) PRIMARY KEY,
                     itemName VARCHAR (150),
                     qtyOnHand INT (20),
                     cost INT (20),
                     unitPrice INT (20)
);

CREATE TABLE supplier(
                         supId VARCHAR (25) PRIMARY KEY,
                         sName VARCHAR (100),
                         address VARCHAR (150),
                         contact INT (10)
);

CREATE TABLE orders(
                       orderId VARCHAR (25) PRIMARY KEY,
                       date DATE,
                       cId VARCHAR (25),
                       CONSTRAINT FOREIGN KEY(cId) REFERENCES customer(cId) on Delete Cascade on Update Cascade
);

CREATE TABLE payment(
                        pId VARCHAR (25) PRIMARY KEY,
                        amount INT (25),
                        status VARCHAR (50),
                        date DATE,
                        orderId VARCHAR (25),
                        CONSTRAINT FOREIGN KEY(orderId) REFERENCES orders(orderId) on Delete Cascade on Update Cascade

);

CREATE TABLE orderItemDetail(
                                qty INT (25),
                                unitPrice DOUBLE,
                                itemId VARCHAR (25),
                                orderId VARCHAR (25),
                                CONSTRAINT FOREIGN KEY(itemId) REFERENCES item(itemId) on Delete Cascade on Update Cascade,
                                CONSTRAINT FOREIGN KEY(orderId) REFERENCES orders(orderId) on Delete Cascade on Update Cascade
);

CREATE TABLE itemSupplierDetail(
                                   itemId VARCHAR (25),
                                   supId VARCHAR (25),
                                   CONSTRAINT FOREIGN KEY(itemId) REFERENCES item(itemId) on Delete Cascade on Update Cascade,
                                   CONSTRAINT FOREIGN KEY(supId) REFERENCES supplier(supId) on Delete Cascade on Update Cascade
);

INSERT INTO user VALUES ("U001","Admin","1234","admin@gmail.com","C:\Users\ASUS\Downloads\image_processing20191016-29931-xs2onq.jpg");
