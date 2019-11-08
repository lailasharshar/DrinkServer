CREATE TABLE `drinkserver`.`machine` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`nane` VARCHAR(200) NOT NULL,
`location` VARCHAR(200) NULL,
`volume` DOUBLE NULL,
`remaining` DOUBLE NULL,
PRIMARY KEY (`id`));

CREATE TABLE `drinkserver`.`card` (
`id` VARCHAR(255) NOT NULL,
`amount` BIGINT NOT NULL,
`userId` BIGINT NULL,
PRIMARY KEY (`id`));

CREATE TABLE `drinkserver`.`user` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`firstName` VARCHAR(255) NULL,
`middleName` VARCHAR(255) NULL,
`lastName` VARCHAR(255) NULL,
`email` VARCHAR(255) NULL,
`password` VARCHAR(255) NULL,
PRIMARY KEY (`id`));


