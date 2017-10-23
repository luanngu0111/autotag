-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: recondb
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `trn_hdr`
--

DROP TABLE IF EXISTS `trn_hdr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trn_hdr` (
  `trn_fmly` char(5) NOT NULL,
  `trn_grp` char(5) NOT NULL,
  `trn_type` char(5) NOT NULL DEFAULT '',
  `description` char(100) NOT NULL,
  PRIMARY KEY (`trn_fmly`,`trn_type`,`trn_grp`),
  KEY `trade_header_index` (`trn_fmly`,`trn_grp`,`trn_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trn_hdr`
--

LOCK TABLES `trn_hdr` WRITE;
/*!40000 ALTER TABLE `trn_hdr` DISABLE KEYS */;
INSERT INTO `trn_hdr` VALUES ('COM','ASIAN','','COM Asian'),('COM','EFS','','COM Exchange Future for Swap'),('COM','FUT','','COM Future'),('COM','FWD','','COM Forward'),('COM','SPOT','','COM Spot/Forward'),('COM','SWAP','','COM Swap'),('COM','SWAP','CAPA','COM Capacity'),('COM','ASIAN','CLR','COM Cleared asian'),('COM','SWAP','CLR','COM Cleared swap'),('COM','OPT','CMP','COM Compound Option'),('COM','OFUT','LST','COM Option on Future - Listed'),('COM','OFUT','OTC','COM Option on Future - OTC'),('COM','SWAP','PHYS','COM Physical Forward'),('COM','OPT','SMP','COM Simple option'),('COM','OPT','SWAP','COM Swaption'),('COM','SWAP','TRS','COM transport'),('CURR','OPT','ASN','Asian Option'),('CURR','OPT','BAR','Simple Barrier'),('CURR','OPT','BAR2','Double Barrier'),('CURR','OPT','FLEX','Flexible Deal'),('CURR','FUT','FUT','Futures'),('CURR','FXD','FXD','Spot-Forward'),('CURR','OPT','KIKO','Kiko Barrier'),('CURR','OPT','LST','Listed Option'),('CURR','OPT','RBT','Touch rebate'),('CURR','OPT','RBTS','Strip of Touch rebate'),('CURR','OPT','SMP','Simple Option'),('CURR','OPT','SMPS','Strip of Simple Options'),('CURR','FXD','SWLEG','Forex-Swap Leg'),('CURR','FXD','XSW','Forex-Swap'),('IRD','ASWP','','Asset swaps'),('IRD','BOND','','Bonds'),('IRD','CD','','Call deposits'),('IRD','CF','','Caps/floors'),('IRD','CS','','Currency swaps'),('IRD','FRA','','FRA\'s'),('IRD','IRS','','Interest rate swaps'),('IRD','LFUT','','Long futures'),('IRD','LN_BR','','Loans/Deposits'),('IRD','OSWP','','Swaptions'),('IRD','REPO','','Old Bond Buy/Sell Back'),('IRD','SFUT','','Short futures'),('IRD','REPO','BSK','Basket Repos'),('IRD','BOND','CALL','Callable Bonds'),('IRD','BOND','CPCD','CP/CD'),('IRD','BOND','FWD','Bond Forwards'),('IRD','OPT','ORG','Interest rate future options'),('IRD','OPT','OTC','Bond options'),('IRD','REPO','REPO','Bond Repos'),('SCF','SCF','SCF','Simple cash flow');
/*!40000 ALTER TABLE `trn_hdr` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-23 13:55:02
