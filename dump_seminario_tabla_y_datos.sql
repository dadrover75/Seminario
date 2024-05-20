CREATE DATABASE  IF NOT EXISTS `seminario` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `seminario`;
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

DROP TABLE IF EXISTS `configuraciones`;
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
-- Dumping data for table `configuraciones`
--

LOCK TABLES `configuraciones` WRITE;
/*!40000 ALTER TABLE `configuraciones` DISABLE KEYS */;
INSERT INTO `configuraciones` VALUES (1,1,30,80,40),(2,2,20,75,35),(3,3,25,85,45),(4,1,30,80,40),(5,2,20,75,35),(6,3,25,85,45);
/*!40000 ALTER TABLE `configuraciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cultivos`
--

DROP TABLE IF EXISTS `cultivos`;
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
-- Dumping data for table `cultivos`
--

LOCK TABLES `cultivos` WRITE;
/*!40000 ALTER TABLE `cultivos` DISABLE KEYS */;
INSERT INTO `cultivos` VALUES (1,'Blue One',1),(2,'Roxy Blue',2),(3,'Calipso',3),(4,'Blue One',1),(5,'Roxy Blue',2),(6,'Calipso',3);
/*!40000 ALTER TABLE `cultivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cultivos_dispositivos`
--

DROP TABLE IF EXISTS `cultivos_dispositivos`;
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
-- Dumping data for table `cultivos_dispositivos`
--

LOCK TABLES `cultivos_dispositivos` WRITE;
/*!40000 ALTER TABLE `cultivos_dispositivos` DISABLE KEYS */;
INSERT INTO `cultivos_dispositivos` VALUES (1,1),(1,2),(2,3),(3,4),(3,5),(4,6),(1,7),(3,8);
/*!40000 ALTER TABLE `cultivos_dispositivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dispositivos`
--

DROP TABLE IF EXISTS `dispositivos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dispositivos` (
  `iddispositivos` int NOT NULL AUTO_INCREMENT,
  `message` varchar(100) NOT NULL,
  `estado` tinyint DEFAULT NULL,
  PRIMARY KEY (`iddispositivos`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dispositivos`
--

LOCK TABLES `dispositivos` WRITE;
/*!40000 ALTER TABLE `dispositivos` DISABLE KEYS */;
INSERT INTO `dispositivos` VALUES (1,'{\"topic\":\"/sensor/humidity/1\", \"value\":\"75\"}',0),(2,'{\"topic\":\"/sensor/humidity/2\", \"value\":\"55\"}',0),(3,'{\"topic\":\"/actuator/waterpump/1\", \"value\":\"on\"}',1),(4,'{\"topic\":\"/sensor/humidity/3\", \"value\":\"60\"}',0),(5,'{\"topic\":\"/sensor/humidity/4\", \"value\":\"70\"}',0),(6,'{\"topic\":\"/sensor/humidity/5\", \"value\":\"65\"}',0),(7,'{\"topic\":\"/actuator/waterpump/2\", \"value\":\"on\"}',1),(8,'{\"topic\":\"/actuator/waterpump/3\", \"value\":\"off\"}',0),(9,'{\"topic\":\"/sensor/humidity/6\", \"value\":\"60\"}',0);
/*!40000 ALTER TABLE `dispositivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
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
-- Dumping data for table `notificaciones`
--

LOCK TABLES `notificaciones` WRITE;
/*!40000 ALTER TABLE `notificaciones` DISABLE KEYS */;
INSERT INTO `notificaciones` VALUES (1,'info','Inicio de riego para Blue One',NULL),(2,'info','Fin de riego para Blue One',NULL),(3,'warning','Sensor de humedad 2 fuera de línea',NULL),(4,'info','Bomba de agua 1 activada',NULL);
/*!40000 ALTER TABLE `notificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idusuarios` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `rol` varchar(45) NOT NULL,
  PRIMARY KEY (`idusuarios`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Juan Perez','Administrador'),(2,'Maria Lopez','Usuario'),(3,'Carlos Gonzalez','Usuario'),(4,'Juan Perez','Administrador'),(5,'Maria Lopez','Usuario'),(6,'Carlos Gonzalez','Usuario');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-19 22:16:18
