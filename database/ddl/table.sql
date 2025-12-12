SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


CREATE SCHEMA IF NOT EXISTS `management` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `management` ;

DROP TABLE IF EXISTS member_certificate;
DROP TABLE IF EXISTS certificate;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS department;

CREATE TABLE IF NOT EXISTS department (
  `dept_id` int NOT NULL,
  `dept_name` varchar(50) NOT NULL,
  PRIMARY KEY (`dept_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS certificate (
  `cert_id` INT AUTO_INCREMENT NOT NULL,
  `cert_name` VARCHAR(45) NOT NULL,
  `cert_job` text,
  `cert_summary` text,
  `cert_trend` text,
  PRIMARY KEY (`cert_id`),
  UNIQUE KEY `cert_name_UNIQUE` (`cert_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS member (
  `member_id` VARCHAR(45) NOT NULL,
  `member_password` VARCHAR(256) NOT NULL,
  `member_name` VARCHAR(45) NOT NULL,
  `member_email` VARCHAR(45) NOT NULL,
  `is_admin` boolean NOT NULL DEFAULT FALSE,
  `dept_id` INT NOT NULL,
  PRIMARY KEY (`member_id`),
  INDEX `fk_member_department1_idx` (`dept_id` ASC),
  CONSTRAINT `fk_member_department1`
    FOREIGN KEY (`dept_id`)
    REFERENCES department (`dept_id`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS member_certificate (
  `member_cert_id` int AUTO_INCREMENT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `cert_id` INT NOT NULL,
  `cert_date` DATE NOT NULL,
  PRIMARY KEY (`member_cert_id`),
  INDEX `fk_member_has_certificate_certificate1_idx` (`cert_id` ASC),
  INDEX `fk_member_has_certificate_member1_idx` (`member_id` ASC),
  CONSTRAINT `fk_member_has_certificate_certificate1`
    FOREIGN KEY (`cert_id`)
    REFERENCES `management`.`certificate` (`cert_id`)
    ON DELETE CASCADE 
    ON UPDATE CASCADE,
  CONSTRAINT `fk_member_has_certificate_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `management`.`member` (`member_id`)
    ON DELETE CASCADE 
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
