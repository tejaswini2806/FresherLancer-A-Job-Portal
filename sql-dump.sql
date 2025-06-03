/*
SQLyog Community v13.1.5  (32 bit)
MySQL - 5.7.41-log : Database - fresherlancer
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fresherlancer` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `fresherlancer`;

/*Table structure for table `candidate` */

DROP TABLE IF EXISTS `candidate`;

CREATE TABLE `candidate` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `status` enum('ACTIVE','BOOKED','AVAILABLE') NOT NULL DEFAULT 'AVAILABLE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `candidate` */

insert  into `candidate`(`id`,`first_name`,`last_name`,`email`,`status`) values 
(1,'Amol','Zambre','amol@yopmail.com','ACTIVE'),
(2,'Abhishek','Bhirud','abhi@yopmail.com','AVAILABLE'),
(3,'Vijay','Shrewastav','vijay@yopmail.com','BOOKED'),
(4,'Tine','Mihic','tina@yopmail.com','AVAILABLE'),
(5,'Simone','Arora','simone@yopmail.com','AVAILABLE'),
(6,'Summit','trivedi','sumit@yopmail.com','AVAILABLE'),
(8,'Anushka','Deshmukh','anushka@yopmail.com','ACTIVE');

/*Table structure for table `candidate_education` */

DROP TABLE IF EXISTS `candidate_education`;

CREATE TABLE `candidate_education` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `institution` text,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `grade` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `candidate_education_candidate_id_fk` (`candidate_id`),
  CONSTRAINT `candidate_education_candidate_id_fk` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `candidate_education` */

insert  into `candidate_education`(`id`,`candidate_id`,`degree`,`institution`,`start_date`,`end_date`,`grade`) values 
(1,8,'BE','GSM','2019-01-01','2022-06-01','90');

/*Table structure for table `candidate_experiences` */

DROP TABLE IF EXISTS `candidate_experiences`;

CREATE TABLE `candidate_experiences` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `job_title` varchar(255) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `candidate_experiences_candidate_id_fk` (`candidate_id`),
  CONSTRAINT `candidate_experiences_candidate_id_fk` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `candidate_experiences` */

insert  into `candidate_experiences`(`id`,`candidate_id`,`company_name`,`job_title`,`start_date`,`end_date`,`description`) values 
(1,8,'E-zest Solutions','Java Fullstack Developer','2022-08-01','2025-08-31',NULL);

/*Table structure for table `candidate_skills` */

DROP TABLE IF EXISTS `candidate_skills`;

CREATE TABLE `candidate_skills` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) NOT NULL,
  `skill` varchar(255) NOT NULL,
  `proficiency` enum('BEGINNER','INTERMEDIATE','EXPERT') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `candidate_skills_candidate_id_fk` (`candidate_id`),
  CONSTRAINT `candidate_skills_candidate_id_fk` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `candidate_skills` */

insert  into `candidate_skills`(`id`,`candidate_id`,`skill`,`proficiency`) values 
(1,8,'Java','BEGINNER'),
(2,8,'Spring Boot','EXPERT');

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `location` varchar(255) NOT NULL,
  `employment_type` enum('FULL_TIME','PART_TIME','CONTRACT') NOT NULL,
  `experience_level` varchar(50) NOT NULL,
  `salary_min` decimal(10,2) DEFAULT NULL,
  `salary_max` decimal(10,2) DEFAULT NULL,
  `posted_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `openings` int(5) DEFAULT '1',
  `job_status` enum('OPEN','BOOKED','ACTIVE','REOPEN') NOT NULL DEFAULT 'OPEN',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `job` */

insert  into `job`(`id`,`title`,`description`,`location`,`employment_type`,`experience_level`,`salary_min`,`salary_max`,`posted_at`,`openings`,`job_status`) values 
(2,'Java Fullstack Developer','Java Fullstack Developer\n','Mumbai','FULL_TIME','INTERMEDIATE',200000.00,1000000.00,'2025-04-20 17:33:18',1,'BOOKED'),
(3,'Python Developer','Python Developer\n','Pune','FULL_TIME','INTERMEDIATE',200000.00,1000000.00,'2025-04-20 01:04:00',1,'OPEN');

/*Table structure for table `job_applications` */

DROP TABLE IF EXISTS `job_applications`;

CREATE TABLE `job_applications` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `job_id` int(10) NOT NULL,
  `candidate_id` int(10) NOT NULL,
  `applied_date` date NOT NULL,
  `status` enum('APPLIED','SHORTLIST','REJECTED','ACTIVE','CLOSED') NOT NULL DEFAULT 'APPLIED',
  PRIMARY KEY (`id`),
  KEY `job_applications_job_id_fk` (`job_id`),
  KEY `job_applications_candidate_id_fk` (`candidate_id`),
  CONSTRAINT `job_applications_candidate_id_fk` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`),
  CONSTRAINT `job_applications_job_id_fk` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `job_applications` */

insert  into `job_applications`(`id`,`job_id`,`candidate_id`,`applied_date`,`status`) values 
(1,2,8,'2025-04-26','ACTIVE');

/*Table structure for table `job_skills` */

DROP TABLE IF EXISTS `job_skills`;

CREATE TABLE `job_skills` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `job_id` int(10) NOT NULL,
  `skill` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `job_skills_job_id_fk` (`job_id`),
  CONSTRAINT `job_skills_job_id_fk` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `job_skills` */

insert  into `job_skills`(`id`,`job_id`,`skill`) values 
(1,2,'SpringBoot'),
(2,2,'Java'),
(3,3,'Pandas'),
(4,3,'Numpy'),
(5,3,'Python'),
(6,2,'Advance java');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `candidate_id` int(10) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` text,
  `user_type` enum('ADMIN','AGENCY','CANDIDATE') NOT NULL DEFAULT 'CANDIDATE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_candidate_id_fk` (`candidate_id`),
  CONSTRAINT `user_candidate_id_fk` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`candidate_id`,`first_name`,`last_name`,`email`,`username`,`password`,`user_type`) values 
(1,NULL,'Dhiraj','Deshmukh','admin@yopmail.com','admin@yopmail.com','$2a$10$kbL49f8U29Mw5CvomqRe7uQlyTzsNW2NYRinY4P/jkBfN4LgXPCni','ADMIN'),
(2,1,'Amol','Zambre','amol@yopmail.com','amol@yopmail.com','$2a$10$sKILf3GJdNH0QN7VYanDCOmSWGobw1dFdRgIL99pt182y4RDn2lwG','CANDIDATE'),
(3,2,'Abhishek','Bhirud','abhi@yopmail.com','abhi@yopmail.com','$2a$10$jtPAnJWyii12CZkSy/ketukbJzyloiOzz9FJ7rlDw6uatMXdT5HiC','CANDIDATE'),
(4,3,'Vijay','Shrewastav','vijay@yopmail.com','vijay@yopmail.com','$2a$10$Fcox40eom.ilk.dLBBrRA.jiLlYZcz2V1.gfwSRnXMhKxfYCOu/JO','CANDIDATE'),
(5,4,'Tine','Mihic','tina@yopmail.com','tina@yopmail.com','$2a$10$aYGevjL4ajxjSE.lrUQiv.7vKCPLX2SN0.Lt3Zjn7t0p1Q5phE/M2','CANDIDATE'),
(6,5,'Simone','Arora','simone@yopmail.com','simone@yopmail.com','$2a$10$AG44RZNqNOEa1qp5XU.TJObF5ASMhQRIwV7NU4y4kWQfU.u1okKQW','CANDIDATE'),
(12,8,'Anushka','Deshmukh','anushka@yopmail.com','anushka@yopmail.com','$2a$10$gsMi7YbtK6f2Okk1wtb4t.05Vzg9YwxFNTeCubSn2OizgPQshygJO','CANDIDATE');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
