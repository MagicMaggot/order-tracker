drop database if exists `order_tracker`;
create database `order_tracker`;
use `order_tracker`;

create table `orders` (
	`id` bigint not null auto_increment,
    `customer_name` varchar(50) not null,
    `customer_address` varchar(300) not null,
    `total` double not null,
    `placed` date not null,
    primary key(`id`));
    
create table `products` (
	`serial_no` varchar(100) not null,
    `name` varchar(100) not null,
    `description` text,
    `production_date` date,
    primary key(`serial_no`));
    
create table `order_details` (
	`item_id` bigint not null auto_increment,
    `serial_no` varchar(100) not null,
    `qty` int not null,
    `order_id` bigint not null,
    primary key(`item_id`),
    constraint `order_details_serial_fk`
    foreign key (`serial_no`) references `products` (`serial_no`),
    constraint `order_details_order_id_fk`
    foreign key (`order_id`) references `orders` (`id`),
    constraint `qty_chk`
    check (`qty` > 0),
    constraint `serial_order_id_uq`
    unique (`serial_no`, `order_id`));