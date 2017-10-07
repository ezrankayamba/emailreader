SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `tbl_payment` (
  `txn_source` varchar(20) NOT NULL,
  `txn_id` varchar(20) NOT NULL,
  `amount` double NOT NULL,
  `msisdn` varchar(20) DEFAULT NULL,
  `record_date` datetime NOT NULL,
  `recorded_by` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `txn_date` datetime NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `txn_status` int(1) NOT NULL DEFAULT '0',
  `distributor` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
   `payee_msisdn` varchar(20) NOT NULL,
  PRIMARY KEY (`txn_id`),
  KEY `distributor` (`distributor`),
  KEY `recorded_by` (`recorded_by`),
  KEY `region_id` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--ALTER TABLE tbl_payment ADD  `txn_source` varchar(50) NOT NULL;

CREATE TABLE IF NOT EXISTS `tbl_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `msisdn` double NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `recorded_by` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `msisdn` (`msisdn`),
  KEY `recorded_by` (`recorded_by`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
--ALTER TABLE tbl_account ADD UNIQUE KEY `msisdn` (`msisdn`);

CREATE TABLE IF NOT EXISTS `tbl_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `price` double NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `recorded_by` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `recorded_by` (`recorded_by`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;
--ALTER TABLE tbl_region ADD `account_id` int(11) DEFAULT NULL;

CREATE TABLE IF NOT EXISTS `tbl_users` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(60) DEFAULT NULL,
  `isactive` tinyint(1) NOT NULL DEFAULT '0',
  `record_date` datetime NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userrole` int(1) NOT NULL DEFAULT '3',
  `region_id` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`),
  KEY `region_id` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*
ALTER TABLE `tbl_payment`
  ADD CONSTRAINT `ticket_pickup_region_id` FOREIGN KEY (`region_id`) REFERENCES `tbl_region` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `payment_recorder` FOREIGN KEY (`recorded_by`) REFERENCES `tbl_users` (`username`) ON UPDATE CASCADE,
  ADD CONSTRAINT `ticket_distributor` FOREIGN KEY (`distributor`) REFERENCES `tbl_users` (`username`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_region`
--
ALTER TABLE `tbl_region`
  ADD CONSTRAINT `tbl_region_ibfk_2` FOREIGN KEY (`recorded_by`) REFERENCES `tbl_users` (`username`) ON UPDATE CASCADE,
  ADD CONSTRAINT `region_account_fk` FOREIGN KEY (`account_id`) REFERENCES `tbl_account` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD CONSTRAINT `tbl_users_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `tbl_region` (`id`) ON UPDATE CASCADE;

*/
SET FOREIGN_KEY_CHECKS=1;