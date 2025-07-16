-- MariaDB dump 10.17  Distrib 10.4.14-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: bucketstore
-- ------------------------------------------------------
-- Server version	10.4.14-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ORDERS`
--

DROP TABLE IF EXISTS `ORDERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDERS` (
  `DELIVERY_FEE` int(11) DEFAULT NULL,
  `TOTAL_PRICE` int(11) DEFAULT NULL,
  `CREATED_AT` datetime(6) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `ORDER_DATE` datetime(6) DEFAULT NULL,
  `ORDER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UPDATED_AT` datetime(6) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `ORDER_CODE` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ORDER_STATUS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`),
  KEY `FK6jhqv9srg8s7x7ycrce7oxuur` (`USER_ID`),
  CONSTRAINT `FK6jhqv9srg8s7x7ycrce7oxuur` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDERS`
--

LOCK TABLES `ORDERS` WRITE;
/*!40000 ALTER TABLE `ORDERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ORDERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDER_DELIVERY`
--

DROP TABLE IF EXISTS `ORDER_DELIVERY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDER_DELIVERY` (
  `CREATED_AT` datetime(6) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `ORDER_ORDER_ID` bigint(20) NOT NULL,
  `UPDATED_AT` datetime(6) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `ADDRESS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DELIVERY_MESSAGE` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `PHONE_NUMBER` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RECEIVER_NAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ZIP_CODE` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ORDER_ORDER_ID`),
  CONSTRAINT `FKhn24povl6l5vkikw7f4uqycps` FOREIGN KEY (`ORDER_ORDER_ID`) REFERENCES `ORDERS` (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDER_DELIVERY`
--

LOCK TABLES `ORDER_DELIVERY` WRITE;
/*!40000 ALTER TABLE `ORDER_DELIVERY` DISABLE KEYS */;
/*!40000 ALTER TABLE `ORDER_DELIVERY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDER_ITEM`
--

DROP TABLE IF EXISTS `ORDER_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDER_ITEM` (
  `DELIVERY_FEE` int(11) DEFAULT NULL,
  `ITEM_PRICE` int(11) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `CREATED_AT` datetime(6) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `OPTION_ID` bigint(20) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  `ORDER_ITEM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `UPDATED_AT` datetime(6) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `ITEM_STATUS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ORDER_ITEM_ID`),
  KEY `FKay3bm111uroflmvmbhpp2q549` (`OPTION_ID`),
  KEY `FKn3ojvpgwyoherqkqy7ls4q46w` (`ORDER_ID`),
  KEY `FKljipjqamrx5ic92xjgn7rgh2c` (`PRODUCT_ID`),
  CONSTRAINT `FKay3bm111uroflmvmbhpp2q549` FOREIGN KEY (`OPTION_ID`) REFERENCES `PRODUCT_OPTION` (`OPTION_ID`),
  CONSTRAINT `FKljipjqamrx5ic92xjgn7rgh2c` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`PRODUCT_ID`),
  CONSTRAINT `FKn3ojvpgwyoherqkqy7ls4q46w` FOREIGN KEY (`ORDER_ID`) REFERENCES `ORDERS` (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDER_ITEM`
--

LOCK TABLES `ORDER_ITEM` WRITE;
/*!40000 ALTER TABLE `ORDER_ITEM` DISABLE KEYS */;
/*!40000 ALTER TABLE `ORDER_ITEM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCT`
--

DROP TABLE IF EXISTS `PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCT` (
  `BASE_PRICE` int(11) DEFAULT NULL,
  `CREATED_AT` datetime(6) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `PRODUCT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UPDATED_AT` datetime(6) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `BRAND` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CATEGORY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `PRODUCT_CODE` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `PRODUCT_NAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCT`
--

LOCK TABLES `PRODUCT` WRITE;
/*!40000 ALTER TABLE `PRODUCT` DISABLE KEYS */;
/*!40000 ALTER TABLE `PRODUCT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCT_DELIVERY`
--

DROP TABLE IF EXISTS `PRODUCT_DELIVERY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCT_DELIVERY` (
  `DELIVERY_FEE` int(11) DEFAULT NULL,
  `FREE_DELIVERY_THRESHOLD` int(11) DEFAULT NULL,
  `CREATED_AT` datetime(6) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `PRODUCT_ID` bigint(20) NOT NULL,
  `UPDATED_AT` datetime(6) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `DELIVERY_METHOD` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_ID`),
  CONSTRAINT `FKgyi7n0ael3n0dyefuwoaevi7o` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCT_DELIVERY`
--

LOCK TABLES `PRODUCT_DELIVERY` WRITE;
/*!40000 ALTER TABLE `PRODUCT_DELIVERY` DISABLE KEYS */;
/*!40000 ALTER TABLE `PRODUCT_DELIVERY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCT_OPTION`
--

DROP TABLE IF EXISTS `PRODUCT_OPTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCT_OPTION` (
  `ADDITIONAL_PRICE` int(11) DEFAULT NULL,
  `STOCK_QTY` int(11) DEFAULT NULL,
  `CREATED_AT` datetime(6) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `OPTION_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `UPDATED_AT` datetime(6) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `BARCODE` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `COLOR` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIZE` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`OPTION_ID`),
  KEY `FKce5s9kcidsu22c94cepxr6hco` (`PRODUCT_ID`),
  CONSTRAINT `FKce5s9kcidsu22c94cepxr6hco` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCT_OPTION`
--

LOCK TABLES `PRODUCT_OPTION` WRITE;
/*!40000 ALTER TABLE `PRODUCT_OPTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `PRODUCT_OPTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `CREATED_AT` datetime(6) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_AT` datetime(6) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `PASSWORD` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `PHONE_NUMBER` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ZIP_CODE` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-16  9:53:03
