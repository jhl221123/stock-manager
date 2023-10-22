CREATE TABLE `product`
(
    `id`             bigint PRIMARY KEY AUTO_INCREMENT,
    `name`           varchar(30)  NOT NULL,
    `price`          integer,
    `stock_quantity` integer
);

CREATE TABLE `purchase`
(
    `id`             bigint PRIMARY KEY AUTO_INCREMENT,
    `created_date`   datetime(6)
);