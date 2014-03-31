SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`member` (
  `idmember` INT NOT NULL,
  `name` VARCHAR(100) NULL,
  `lastname` VARCHAR(100) NULL,
  `email` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `telNum` VARCHAR(45) NULL,
  `address` VARCHAR(255) NULL,
  `memberType` VARCHAR(45) NULL,
  PRIMARY KEY (`idmember`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`bizdata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`bizdata` (
  `idbizdata` INT NOT NULL,
  `bizName` VARCHAR(45) NULL,
  `bizAddress` VARCHAR(45) NULL,
  `bizImg` VARCHAR(45) NULL,
  `bizURL` VARCHAR(255) NULL,
  `dateAdd` DATE NULL,
  `bizStatus` INT NULL,
  PRIMARY KEY (`idbizdata`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`member_has_bizdata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`member_has_bizdata` (
  `member_idmember` INT NOT NULL,
  `bizdata_idbizdata` INT NOT NULL,
  PRIMARY KEY (`member_idmember`, `bizdata_idbizdata`),
  INDEX `fk_member_has_bizdata_bizdata1_idx` (`bizdata_idbizdata` ASC),
  INDEX `fk_member_has_bizdata_member_idx` (`member_idmember` ASC),
  CONSTRAINT `fk_member_has_bizdata_member`
    FOREIGN KEY (`member_idmember`)
    REFERENCES `mydb`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_member_has_bizdata_bizdata1`
    FOREIGN KEY (`bizdata_idbizdata`)
    REFERENCES `mydb`.`bizdata` (`idbizdata`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
