-- MySQL dump 10.11
--
-- Host: localhost    Database: signos
-- ------------------------------------------------------
-- Server version	5.0.51b-community-nt

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
-- Table structure for table `adjunto_mensaje`
--

DROP TABLE IF EXISTS `adjunto_mensaje`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `adjunto_mensaje` (
  `id` bigint(15) NOT NULL auto_increment,
  `nombre` varchar(200) default NULL,
  `content_type` varchar(200) default NULL,
  `size` int(15) default NULL,
  `id_mensaje` bigint(15) default NULL,
  `fecha_creacion` datetime default NULL,
  `titulo` varchar(200) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_mensaje_adjunto` (`id_mensaje`),
  CONSTRAINT `fk_mensaje_adjunto` FOREIGN KEY (`id_mensaje`) REFERENCES `mensaje` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `adjunto_mensaje`
--

LOCK TABLES `adjunto_mensaje` WRITE;
/*!40000 ALTER TABLE `adjunto_mensaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `adjunto_mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adjunto_ticket`
--

DROP TABLE IF EXISTS `adjunto_ticket`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `adjunto_ticket` (
  `id` bigint(15) NOT NULL auto_increment,
  `nombre` varchar(200) default NULL,
  `content_type` varchar(200) default NULL,
  `size` int(15) default NULL,
  `id_ticket` bigint(15) default NULL,
  `fecha_creacion` datetime default NULL,
  `titulo` varchar(200) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_ticket_adjunto` (`id_ticket`),
  CONSTRAINT `fk_ticket_adjunto` FOREIGN KEY (`id_ticket`) REFERENCES `ticket` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `adjunto_ticket`
--

LOCK TABLES `adjunto_ticket` WRITE;
/*!40000 ALTER TABLE `adjunto_ticket` DISABLE KEYS */;
INSERT INTO `adjunto_ticket` VALUES (42,'1369234518.alizee-137-3-wallpapers-gallery-97.jpg','image/jpeg',189387,50,'2013-05-22 09:55:18','alizee-137-3-wallpapers-gallery-97.jpg'),(43,'1369234707.alizee-137-3-wallpapers-gallery-97.jpg','image/jpeg',189387,51,'2013-05-22 09:58:27','alizee-137-3-wallpapers-gallery-97.jpg'),(44,'1369235181.alizee-137-3-wallpapers-gallery-97.jpg','image/jpeg',189387,52,'2013-05-22 10:06:21','alizee-137-3-wallpapers-gallery-97.jpg'),(45,'1369235490.alizee-137-3-wallpapers-gallery-97.jpg','image/jpeg',189387,53,'2013-05-22 10:11:30','alizee-137-3-wallpapers-gallery-97.jpg'),(46,'1369236364.alizee-137-3-wallpapers-gallery-97.jpg','image/jpeg',189387,54,'2013-05-22 10:26:04','alizee-137-3-wallpapers-gallery-97.jpg'),(47,'1369236972.alizee-137-3-wallpapers-gallery-97.jpg','image/jpeg',189387,55,'2013-05-22 10:36:12','alizee-137-3-wallpapers-gallery-97.jpg'),(48,'1369238553.alizee-137-3-wallpapers-gallery-97.jpg','image/jpeg',189387,56,'2013-05-22 11:02:33','alizee-137-3-wallpapers-gallery-97.jpg');
/*!40000 ALTER TABLE `adjunto_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `cliente` (
  `id` bigint(15) NOT NULL auto_increment,
  `nombre` varchar(200) default NULL,
  `ruc` varchar(11) default NULL,
  `direccion` varchar(200) default NULL,
  `nombre_contacto` varchar(200) default NULL,
  `email_contacto` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (5,'INNOVA SCHOOLS','20510713363','Edificio Interbank Piso 7 - La Victoria','Eduardo Ibarra','eibarra@colegiosperuanos.edu.pe'),(6,'CENTRUM CATOLICA','20155945860','AV. AVENIDA UNIVERSITARIA #1801','Hugo Vertiz','hvertiz@pucp.edu.pe'),(7,'ARENA VERDE','20519314836','','Romulo Alva','ralva@arenaverde.com.pe'),(8,'KAZUO CONSULTING','66666666666','999','José','cvidal@kazuo.pe'),(9,'STARK LOGISTICS','98989898988','','Jeannette','jeane@gmail.com'),(10,'CLINICA ANGLO','12345678876','','Javier Menepa','jmenepa@anglo.com'),(14,'CLINICA ANGLO','12345567898','as12','ww','s@d.c'),(15,'hola','12345678989','12345','q','qq@a.hot'),(16,'a','12345678989','123','1','q@h.com');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `mensaje` (
  `id` bigint(15) NOT NULL auto_increment,
  `mensaje` text,
  `fecha_creacion` datetime default NULL,
  `id_persona` bigint(15) default NULL,
  `id_ticket` bigint(15) default NULL,
  `tipo_persona` tinyint(4) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_persona_mensaje` (`id_persona`),
  KEY `fk_ticket_mensaje` (`id_ticket`),
  CONSTRAINT `fk_persona_mensaje` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`),
  CONSTRAINT `fk_ticket_mensaje` FOREIGN KEY (`id_ticket`) REFERENCES `ticket` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES (65,'respuesta','2013-05-22 10:16:34',20,53,1),(66,'respuesta','2013-05-22 10:18:43',20,52,1),(67,'respuesta','2013-05-22 10:18:56',20,51,1),(68,'respuesta','2013-05-22 10:19:09',20,50,1),(69,'mensaje','2013-05-22 10:21:21',33,53,2),(70,'respuesta de blad','2013-05-22 10:44:29',33,55,2),(71,'respuesta de soporte','2013-05-22 10:55:26',33,55,2),(72,'respuesta de soporte','2013-05-22 10:56:27',20,55,1),(73,'respuesta al nuevo ticket creado por soporte a nombre de blad','2013-05-22 11:06:25',33,56,2),(74,'ssssssssssssssss','2013-07-12 13:02:48',33,56,2),(75,'respuesta de soporte','2013-07-12 13:11:38',33,55,2);
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `persona` (
  `id` bigint(15) NOT NULL auto_increment,
  `nombres` varchar(200) default NULL,
  `apellidos` varchar(200) default NULL,
  `email` varchar(50) default NULL,
  `celular` int(15) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (11,'Bladymir','Capcha','bladymir@albatross.pe',NULL),(12,'José Luis','Eusebio Alderete','jose@albatross.pe',971117877),(20,'Soporte','Albatross','soporte@albatross.pe',971117877),(21,'Admin','Albatross','admin',NULL),(33,'blad','capcha','bladymircch@outlook.com',123456789),(34,'Christian','Vidal','cvidal@kazuo.pe',123456789),(35,'apariciocch','apariciocch','apariciocch@hotmail.com',123456);
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `producto` (
  `id` bigint(15) NOT NULL auto_increment,
  `nombre` varchar(200) default NULL,
  `descripcion` text,
  `id_cliente` bigint(15) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_cliente_producto` (`id_cliente`),
  CONSTRAINT `fk_cliente_producto` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (4,'SIAE','Sistema de Apoyo al Estudiante',6),(6,'CRM','CRM KAZUO',8),(7,'MAIL HOSTING','',7),(8,'SISTEMA CONTABLE','',7),(9,'FIREWALL','',7),(10,'ASTERISK','',7),(11,'SISTEMA TICKETS','',8),(12,'WEBSITE','',9),(13,'Sistema de Mantenimiento','',9),(14,'LPC','',5),(15,'Formulario Actualización','',5),(16,'HORARIOS','',5),(17,'ERP FARMACIA','',10);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stakeholder`
--

DROP TABLE IF EXISTS `stakeholder`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `stakeholder` (
  `id` bigint(15) NOT NULL auto_increment,
  `id_persona` bigint(15) default NULL,
  `id_cliente` bigint(15) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_cliente_stakeholder` (`id_cliente`),
  KEY `fk_persona_stakeholder` (`id_persona`),
  CONSTRAINT `fk_cliente_stakeholder` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_persona_stakeholder` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `stakeholder`
--

LOCK TABLES `stakeholder` WRITE;
/*!40000 ALTER TABLE `stakeholder` DISABLE KEYS */;
INSERT INTO `stakeholder` VALUES (21,33,5),(22,34,8);
/*!40000 ALTER TABLE `stakeholder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stakeholder_producto`
--

DROP TABLE IF EXISTS `stakeholder_producto`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `stakeholder_producto` (
  `id` bigint(15) NOT NULL auto_increment,
  `id_stakeholder` bigint(15) default NULL,
  `id_producto` bigint(15) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_producto_ps` (`id_producto`),
  KEY `fk_stakeholder_ps` (`id_stakeholder`),
  CONSTRAINT `fk_producto_ps` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`),
  CONSTRAINT `fk_stakeholder_ps` FOREIGN KEY (`id_stakeholder`) REFERENCES `stakeholder` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `stakeholder_producto`
--

LOCK TABLES `stakeholder_producto` WRITE;
/*!40000 ALTER TABLE `stakeholder_producto` DISABLE KEYS */;
INSERT INTO `stakeholder_producto` VALUES (50,21,14),(51,21,15),(52,21,16),(54,22,6);
/*!40000 ALTER TABLE `stakeholder_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ticket` (
  `id` bigint(15) NOT NULL auto_increment,
  `codigo` varchar(15) default NULL,
  `asunto` varchar(200) default NULL,
  `mensaje` text,
  `estado` tinyint(15) default NULL,
  `prioridad` tinyint(15) default NULL,
  `fecha_creacion` datetime default NULL,
  `id_persona` bigint(15) default NULL,
  `id_producto` bigint(15) default NULL,
  `id_encargado` bigint(20) default NULL,
  `fecha_actualizacion` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_persona_ticket` (`id_persona`),
  KEY `fk_producto_ticket` (`id_producto`),
  CONSTRAINT `fk_persona_ticket` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`),
  CONSTRAINT `fk_producto_ticket` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (50,'680033','hola ','es mi primer mensaje con datos adjuntos',2,0,'2013-05-22 09:55:20',33,15,20,'2013-05-22 10:19:09'),(51,'666000','hola','mi primer mensaje con datos adjuntos.',2,0,'2013-05-22 09:58:42',33,15,20,'2013-05-22 10:18:56'),(52,'734562','hola ','primer mensaje con datos adjuntos',2,0,'2013-05-22 10:06:23',33,14,20,'2013-05-22 10:18:43'),(53,'432504','hola','hola ',2,0,'2013-05-22 10:11:31',33,14,20,'2013-05-22 10:21:21'),(54,'664704','prueba','ticket creado por soporte.',0,1,'2013-05-22 10:26:50',33,14,20,'2013-05-22 10:26:50'),(55,'581220','hola ','mensaje creado por soporte en nombre de blad',0,2,'2013-05-22 10:36:14',33,14,20,'2013-07-12 13:11:38'),(56,'972544','nuevo ticket','nuevo ticket creado por soporte a nombre de blad',0,0,'2013-05-22 11:03:18',33,14,20,'2013-07-12 13:02:48'),(57,'738217','Ampliación de Caracteristicas de Servidor','Buen día\n\nDebido al crecimiento de nuestra empresa tenemos más usuarios utilizando la aplicación, requerimos amplien las caracteristicas del servidor dado que sentimos cierta lentitud en el funcionamiento. Saludos',2,0,'2013-05-22 15:00:02',34,6,20,'2013-05-22 15:00:02'),(58,'784549','Ampliación de Caracteristicas de Servidor','Buen día\n\nDebido al crecimiento de nuestra empresa tenemos más usuarios utilizando la aplicación, requerimos amplien las caracteristicas del servidor dado que sentimos cierta lentitud en el funcionamiento. \n\nSaludos\n',0,0,'2013-05-22 15:24:54',34,6,20,'2013-05-22 15:24:54');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `usuario` (
  `id` bigint(15) NOT NULL auto_increment,
  `id_persona` bigint(15) default NULL,
  `password` varchar(200) default NULL,
  `tipo_usuario` tinyint(15) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_persona_usuario` (`id_persona`),
  CONSTRAINT `fk_persona_usuario` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,11,'fae0b27c451c728867a567e8c1bb4e53',1),(2,12,'fae0b27c451c728867a567e8c1bb4e53',1),(3,20,'fae0b27c451c728867a567e8c1bb4e53',1),(4,21,'fae0b27c451c728867a567e8c1bb4e53',0),(5,35,'ef6299c9e7fdae6d775819ce1e2620b8',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-12-15 14:19:33
