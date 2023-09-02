CREATE DATABASE  IF NOT EXISTS `uci_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `uci_db`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: uci_db
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `seq` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hostNickname` varchar(255) DEFAULT NULL,
  `joinNumber` int NOT NULL,
  `startTime` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roomhistory`
--

DROP TABLE IF EXISTS `roomhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roomhistory` (
  `seq` bigint NOT NULL AUTO_INCREMENT,
  `action` int NOT NULL,
  `insertTime` datetime(6) DEFAULT NULL,
  `userSeq` bigint DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKgkfsbok85lvuadta4qnawu75c` (`userSeq`),
  CONSTRAINT `FKgkfsbok85lvuadta4qnawu75c` FOREIGN KEY (`userSeq`) REFERENCES `user` (`seq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roomhistory`
--

LOCK TABLES `roomhistory` WRITE;
/*!40000 ALTER TABLE `roomhistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `seq` bigint NOT NULL AUTO_INCREMENT,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `imgurl` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  UNIQUE KEY `UK_e6gkqunxajvyxl5uctpl2vl2p` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userimg`
--

DROP TABLE IF EXISTS `userimg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userimg` (
  `seq` bigint NOT NULL AUTO_INCREMENT,
  `originFile` varchar(255) DEFAULT NULL,
  `saveFile` varchar(255) DEFAULT NULL,
  `saveFolder` varchar(255) DEFAULT NULL,
  `userSeq` bigint DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKixqcq16f5iawrojes7wxvjvmv` (`userSeq`),
  CONSTRAINT `FKixqcq16f5iawrojes7wxvjvmv` FOREIGN KEY (`userSeq`) REFERENCES `user` (`seq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userimg`
--

LOCK TABLES `userimg` WRITE;
/*!40000 ALTER TABLE `userimg` DISABLE KEYS */;
/*!40000 ALTER TABLE `userimg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroom`
--

DROP TABLE IF EXISTS `userroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userroom` (
  `seq` bigint NOT NULL AUTO_INCREMENT,
  `joinTime` time DEFAULT NULL,
  `state` bit(1) DEFAULT NULL,
  `sumTime` bigint NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `roomSeq` bigint DEFAULT NULL,
  `userSeq` bigint DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKq5qf1byo7tes151lpq75qpyl` (`roomSeq`),
  KEY `FKqxcyqmp6b4nvxq3d2vtb59exu` (`userSeq`),
  CONSTRAINT `FKq5qf1byo7tes151lpq75qpyl` FOREIGN KEY (`roomSeq`) REFERENCES `room` (`seq`) ON DELETE CASCADE,
  CONSTRAINT `FKqxcyqmp6b4nvxq3d2vtb59exu` FOREIGN KEY (`userSeq`) REFERENCES `user` (`seq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroom`
--

LOCK TABLES `userroom` WRITE;
/*!40000 ALTER TABLE `userroom` DISABLE KEYS */;
/*!40000 ALTER TABLE `userroom` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-17 15:52:08
