-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: seminario
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `configuraciones`
--
LOCK TABLES configuraciones WRITE;
DROP TABLE IF EXISTS `configuraciones`;
UNLOCK TABLES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuraciones` (
  `idconfiguraciones` int NOT NULL AUTO_INCREMENT,
  `idusuarios` int NOT NULL,
  `minutos_riego` int NOT NULL,
  `humedad_max` int NOT NULL,
  `humedad_min` int NOT NULL,
  PRIMARY KEY (`idconfiguraciones`),
  KEY `usuarios_idx` (`idusuarios`),
  CONSTRAINT `usuarios` FOREIGN KEY (`idusuarios`) REFERENCES `usuarios` (`idusuarios`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cultivos`
--
LOCK TABLES cultivos WRITE;
DROP TABLE IF EXISTS `cultivos`;
UNLOCK TABLES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cultivos` (
  `idcultivos` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `idconfiguraciones` int DEFAULT NULL,
  PRIMARY KEY (`idcultivos`),
  KEY `configuracion_idx` (`idconfiguraciones`),
  CONSTRAINT `configuracion` FOREIGN KEY (`idconfiguraciones`) REFERENCES `configuraciones` (`idconfiguraciones`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cultivos_dispositivos`
--
LOCK TABLES cultivos_dispositivos WRITE;
DROP TABLE IF EXISTS `cultivos_dispositivos`;
UNLOCK TABLES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cultivos_dispositivos` (
  `idcultivos` int NOT NULL,
  `iddispositivos` int NOT NULL,
  PRIMARY KEY (`idcultivos`,`iddispositivos`),
  KEY `dispositivo_idx` (`iddispositivos`),
  CONSTRAINT `cultivo` FOREIGN KEY (`idcultivos`) REFERENCES `cultivos` (`idcultivos`),
  CONSTRAINT `dispositivo` FOREIGN KEY (`iddispositivos`) REFERENCES `dispositivos` (`iddispositivos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mensajes`
--
LOCK TABLES mensajes WRITE;
DROP TABLE IF EXISTS `mensajes`;
UNLOCK TABLES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensajes` (
  `idmensajes` int NOT NULL AUTO_INCREMENT,
  `topic` varchar(45) NOT NULL,
  `payload` varchar(45) NOT NULL,
  PRIMARY KEY (`idmensajes`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dispositivos`
--
LOCK TABLES dispositivos WRITE;
DROP TABLE IF EXISTS `dispositivos`;
UNLOCK TABLES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dispositivos` (
  `iddispositivos` int NOT NULL AUTO_INCREMENT,
  `mensaje_id` int NOT NULL,
  `estado` tinyint DEFAULT NULL,
  PRIMARY KEY (`iddispositivos`),
  KEY `mensaje_idx` (`mensaje_id`),
  CONSTRAINT `mensaje` FOREIGN KEY (`mensaje_id`) REFERENCES `mensajes` (`idmensajes`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notificaciones`
--
LOCK TABLES notificaciones WRITE;
DROP TABLE IF EXISTS `notificaciones`;
UNLOCK TABLES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificaciones` (
  `idnotificaciones` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) NOT NULL,
  `mensaje` varchar(45) NOT NULL,
  `idcultivos` int DEFAULT NULL,
  PRIMARY KEY (`idnotificaciones`),
  KEY `cultivo_idx` (`idcultivos`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--
LOCK TABLES usuarios WRITE;
DROP TABLE IF EXISTS `usuarios`;
UNLOCK TABLES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idusuarios` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `rol` varchar(45) NOT NULL,
  PRIMARY KEY (`idusuarios`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-06 21:39:09
