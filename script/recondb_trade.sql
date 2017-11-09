CREATE DATABASE  IF NOT EXISTS `recondb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `recondb`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: recondb
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade` (
  `NB` int(10) NOT NULL,
  `instrument` char(20) DEFAULT NULL,
  `currency` char(3) DEFAULT NULL,
  `portfolio` char(50) DEFAULT NULL,
  `trn_fmly` char(5) DEFAULT NULL,
  `trn_grp` char(5) DEFAULT NULL,
  `trn_type` char(5) DEFAULT NULL,
  `trn_status` char(15) DEFAULT NULL,
  `field` char(15) NOT NULL,
  `issue_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`field`,`NB`),
  KEY `issue_id` (`issue_id`),
  KEY `idx_trade_NB` (`NB`),
  CONSTRAINT `trade_ibfk_1` FOREIGN KEY (`issue_id`) REFERENCES `issue` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade`
--

LOCK TABLES `trade` WRITE;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` VALUES (12351,'FUT1','EUR','FUT PTF','EQD','FUT','','LIVE','MV',2),(12352,'FUT2','USD','FUT PTF','EQD','FUT','','LIVE','MV',2),(12353,'FUT1','EUR','FUT PTF','EQD','FUT','','LIVE','MV',2),(12354,'FUT2','USD','FUT PTF','EQD','FUT','','LIVE','MV',2),(12356,'FUT3','USD','FUT PTF','EQD','FUT','','LIVE','MV',2),(12357,'FUT1','USD','FUT PTF','EQD','FUT','','LIVE','MV',2),(12345,'SEC1','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1),(12347,'SEC1','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1),(12348,'SEC2','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1),(12350,'SEC2','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1),(12351,'FUT1','EUR','FUT PTF','EQD','FUT','','LIVE','PC',3),(12353,'FUT1','EUR','FUT PTF','EQD','FUT','','LIVE','PC',3);
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'recondb'
--

--
-- Dumping routines for database 'recondb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-09 13:52:50
