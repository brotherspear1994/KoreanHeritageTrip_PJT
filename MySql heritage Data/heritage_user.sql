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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_password` varchar(150) DEFAULT NULL,
  `user_phone` varchar(15) NOT NULL,
  `auth_key` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (6,'test@naver.com','테스트','dc564e8e5eb4c5f6c58481eec29937da5091ad884b49176c9aa90190671318db6dd930d07da6e3bd0461beecfb12ce48de30e10c4b536c59417ed46cc0d65d42','01012345678','Y'),(7,'test123@naver.com','이평창','d540eed07d0377b1f9acf987eb3562a3412110c9d92062a319bbba0077962e208776e5c47dc4ed25eaac21de7367c1b99464aaf656f44356aceb4189389f748b','01012345678','Y'),(8,'test123@naver.com','이평창','d540eed07d0377b1f9acf987eb3562a3412110c9d92062a319bbba0077962e208776e5c47dc4ed25eaac21de7367c1b99464aaf656f44356aceb4189389f748b','01012345678','Y'),(9,'test123@naver.com','이평창','d540eed07d0377b1f9acf987eb3562a3412110c9d92062a319bbba0077962e208776e5c47dc4ed25eaac21de7367c1b99464aaf656f44356aceb4189389f748b','01012345678','Y'),(10,'test1234@naver.com','이평창','868867d77efffb52d32b9590d1133c8c33a7a29bae30584f524d57ed573699f14669132e7291891c5041af7e7e4bd232410dbc59af51c1e618285ebe7c46e3dc','01012345678','Y'),(11,'test1@naver.com','이평창','f228f2f3cf988c0300dc65756ae85e46cf4262def026adde76fdf7b6e5bc91d0237e37302ce496a676f070b0d1c40c04ab2523d22def194e08572ab4e9506925','01012345678','Y'),(12,'test2','이형창','045c03011de1eb76bfb46e83f6532d4e2086debb7714a2ec2a7559fb4a0494ef32891389bbb7579c47bd0d38c34952d7114fe70dd0f4803974d4182f1ee7d3f7','01012345678','Y'),(13,'test2@naver.com','이평창','f05044836f2ffa13f7dac2df7e5e8be0ba2a0d299081cef7742faa164fdac0a0f80f7d9bfe1a65fb54dddc2d86b7287f0374bd1358b471ade7018bf65594e010','01012345678','Y');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
