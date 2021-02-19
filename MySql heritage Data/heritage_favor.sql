-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: heritage
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `favor`
--

DROP TABLE IF EXISTS `favor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favor` (
  `favor_no` int NOT NULL AUTO_INCREMENT,
  `user_no` int NOT NULL,
  `ccba_ctcd_nm1` varchar(100) DEFAULT NULL,
  `ccba_ctcd_nm2` varchar(100) DEFAULT NULL,
  `ccba_ctcd_nm3` varchar(100) DEFAULT NULL,
  `bcode_name1` varchar(100) DEFAULT NULL,
  `bcode_name2` varchar(100) DEFAULT NULL,
  `bcode_name3` varchar(100) DEFAULT NULL,
  `ccce_name1` varchar(100) DEFAULT NULL,
  `ccce_name2` varchar(100) DEFAULT NULL,
  `ccce_name3` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`favor_no`),
  KEY `user_no` (`user_no`),
  CONSTRAINT `favor_ibfk_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favor`
--

LOCK TABLES `favor` WRITE;
/*!40000 ALTER TABLE `favor` DISABLE KEYS */;
INSERT INTO `favor` VALUES (1,6,'서울','전남','경북','정치국방','주거생활','서각류','조선시대','19세기','삼국시대'),(2,6,'서울','강원','제주','정치국방','주거생활','서각류','조선시대','19세기','삼국시대'),(3,13,'경북','전남','서울','전적류','주거생활','불교조각','조선시대','고려시대','일제강점기'),(4,13,'경북','전남','서울','전적류','주거생활','불교조각','조선시대','고려시대','일제강점기'),(5,10,'경북','충북','제주','주거생활','천연기념물','전적류','고려시대','조선시대 후기 & 조선 후기','신라시대'),(6,12,'경북','경기','충남','정치국방','인물사건','서각류','조선시대','삼국시대','조선시대 후기'),(7,12,'경북','경기','충남','정치국방','인물사건','서각류','조선시대','삼국시대','조선시대 후기'),(8,12,'경북','경기','충남',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `favor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-19 15:10:36
