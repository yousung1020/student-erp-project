-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema management
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema management
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `management` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `management` ;

-- -----------------------------------------------------
-- Table `management`.`certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `management`.`certificate` (
  `cert_id` INT NOT NULL,
  `cert_name` VARCHAR(45) NOT NULL,
  `agency` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`cert_id`),
  UNIQUE INDEX `cert_name_UNIQUE` (`cert_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `management`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `management`.`department` (
  `dept_id` TEXT NULL DEFAULT NULL,
  `dept_name` TEXT NULL DEFAULT NULL,
  `school_id` TEXT NULL DEFAULT NULL,
  `series_code` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `management`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `management`.`member` (
  `member_id` VARCHAR(45) NOT NULL,
  `member_name` VARCHAR(45) NOT NULL,
  `member_email` VARCHAR(45) NOT NULL,
  `member_password` VARCHAR(45) NOT NULL,
  `dept_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  INDEX `fk_member_department1_idx` (`dept_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `management`.`member_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `management`.`member_certificate` (
  `member_id` VARCHAR(45) NOT NULL,
  `cert_id` INT NOT NULL,
  `acquisition_date` DATE NOT NULL,
  `grade` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`member_id`, `cert_id`),
  INDEX `fk_member_has_certificate_certificate1_idx` (`cert_id` ASC) VISIBLE,
  INDEX `fk_member_has_certificate_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_has_certificate_certificate1`
    FOREIGN KEY (`cert_id`)
    REFERENCES `management`.`certificate` (`cert_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `management`.`school`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `management`.`school` (
  `school_id` INT NOT NULL,
  `school_name` VARCHAR(50) NOT NULL,
  `homepage` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`school_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
