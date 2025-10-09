-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: emailcomment
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `author_id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (3,'2024-01-25 09:30:00.000000',1,1,'Excelente introdução! Muito claro e didático.'),(4,'2024-01-25 10:45:00.000000',2,1,'Tenho uma dúvida sobre configuração de profiles. Pode ajudar?'),(5,'2024-01-25 11:20:00.000000',3,2,'Kafka é realmente poderoso para sistemas distribuídos.'),(1,'2024-01-25 12:15:00.000000',4,2,'Poderia falar mais sobre particionamento?'),(6,'2024-01-25 12:45:00.000000',5,3,'Docker mudou minha vida como desenvolvedor!'),(7,'2024-01-25 13:30:00.000000',6,3,'Qual a diferença entre Docker Compose e Kubernetes?'),(2,'2024-01-25 15:10:00.000000',7,4,'Microservices são ótimos mas aumentam a complexidade.'),(8,'2024-01-25 16:20:00.000000',8,4,'Como gerenciar transações distribuídas?'),(9,'2024-01-26 09:30:00.000000',9,5,'Essas dicas de performance são ouro! Obrigado.'),(10,'2024-01-26 10:15:00.000000',10,5,'Tenho usado essas otimizações e funcionam muito bem.'),(1,'2024-01-26 11:45:00.000000',11,6,'TDD mudou minha forma de programar completamente.'),(3,'2024-01-26 12:30:00.000000',12,6,'Qual framework de testes vocês recomendam?'),(4,'2024-01-26 14:20:00.000000',13,7,'Clean Code deveria ser obrigatório na faculdade.'),(6,'2024-01-26 15:10:00.000000',14,7,'Tem algum livro específico que recomenda?'),(8,'2024-01-26 16:45:00.000000',15,8,'REST APIs bem feitas facilitam muito a vida.'),(9,'2024-01-26 17:30:00.000000',16,8,'Como vocês documentam suas APIs?'),(2,'2024-01-27 10:15:00.000000',17,9,'Git Flow vs GitHub Flow, qual preferem?'),(5,'2024-01-27 11:00:00.000000',18,9,'Uso GitLab Flow e funciona bem para nosso time.'),(7,'2024-01-27 12:45:00.000000',19,10,'DevOps não é só ferramentas, é mudança cultural.'),(10,'2024-01-27 13:30:00.000000',20,10,'Como convencer o time a adotar práticas DevOps?');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email` (
  `comment_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sent_at` datetime(6) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `subject` varchar(500) DEFAULT NULL,
  `error_message` text,
  `recipient_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` VALUES (1,1,'2024-01-25 09:31:00.000000','SENT','Novo comentário no seu post: Introdução ao Spring Boot',NULL,'joao.silva@email.com'),(2,2,'2024-01-25 10:46:00.000000','SENT','Novo comentário no seu post: Introdução ao Spring Boot',NULL,'joao.silva@email.com'),(3,3,'2024-01-25 11:21:00.000000','SENT','Novo comentário no seu post: Kafka para Iniciantes',NULL,'maria.oliveira@email.com'),(4,4,'2024-01-25 12:16:00.000000','FAILED','Novo comentário no seu post: Kafka para Iniciantes','SMTP connection timeout','maria.oliveira@email.com'),(5,5,'2024-01-25 12:46:00.000000','SENT','Novo comentário no seu post: Docker em Produção',NULL,'joao.silva@email.com'),(6,6,'2024-01-25 13:31:00.000000','SENT','Novo comentário no seu post: Docker em Produção',NULL,'joao.silva@email.com'),(7,7,'2024-01-25 15:11:00.000000','PENDING','Novo comentário no seu post: Microservices com Java',NULL,'pedro.santos@email.com'),(8,8,'2024-01-25 16:21:00.000000','SENT','Novo comentário no seu post: Microservices com Java',NULL,'pedro.santos@email.com'),(9,9,'2024-01-26 09:31:00.000000','SENT','Novo comentário no seu post: MySQL Performance Tips',NULL,'ana.costa@email.com'),(10,10,'2024-01-26 10:16:00.000000','SENT','Novo comentário no seu post: MySQL Performance Tips',NULL,'ana.costa@email.com');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `author_id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'2024-01-25 09:00:00.000000',1,'2024-01-25 09:00:00.000000','Introdução ao Spring Boot','Spring Boot é um framework que facilita o desenvolvimento de aplicações Java...'),(2,'2024-01-25 10:15:00.000000',2,'2024-01-25 10:15:00.000000','Kafka para Iniciantes','Apache Kafka é uma plataforma de streaming distribuída que permite...'),(1,'2024-01-25 11:30:00.000000',3,'2024-01-25 11:30:00.000000','Docker em Produção','Usar Docker em produção requer alguns cuidados especiais...'),(3,'2024-01-25 14:20:00.000000',4,'2024-01-25 14:20:00.000000','Microservices com Java','Arquitetura de microservices traz vantagens mas também desafios...'),(4,'2024-01-26 08:45:00.000000',5,'2024-01-26 08:45:00.000000','MySQL Performance Tips','Dicas para otimizar consultas e melhorar performance do MySQL...'),(2,'2024-01-26 10:30:00.000000',6,'2024-01-26 10:30:00.000000','Testes Automatizados','A importância de testes automatizados no desenvolvimento...'),(5,'2024-01-26 13:15:00.000000',7,'2024-01-26 13:15:00.000000','Clean Code Principles','Princípios de código limpo que todo desenvolvedor deve conhecer...'),(1,'2024-01-26 15:40:00.000000',8,'2024-01-26 15:40:00.000000','API REST Best Practices','Melhores práticas para desenvolvimento de APIs REST...'),(6,'2024-01-27 09:20:00.000000',9,'2024-01-27 09:20:00.000000','Git Workflow Strategies','Diferentes estratégias de workflow com Git para equipes...'),(3,'2024-01-27 11:50:00.000000',10,'2024-01-27 11:50:00.000000','DevOps Culture','Como implementar cultura DevOps na sua empresa...');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processed_events`
--

DROP TABLE IF EXISTS `processed_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `processed_events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `event_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` text COLLATE utf8mb4_unicode_ci,
  `processed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `retry_count` int DEFAULT '0',
  `error_message` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `event_id` (`event_id`),
  KEY `idx_event_id` (`event_id`),
  KEY `idx_processed_at` (`processed_at`),
  KEY `idx_event_type` (`event_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processed_events`
--

LOCK TABLES `processed_events` WRITE;
/*!40000 ALTER TABLE `processed_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `processed_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `email_notifications` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary '','2024-01-15 10:30:00.000000',1,'joaosilva','joao.silva@email.com','João Silva'),(_binary '','2024-01-16 11:45:00.000000',2,'mariaoliveira','maria.oliveira@email.com','Maria Oliveira'),(_binary '\0','2024-01-17 09:20:00.000000',3,'pedrosantos','pedro.santos@email.com','Pedro Santos'),(_binary '','2024-01-18 14:15:00.000000',4,'anacosta','ana.costa@email.com','Ana Costa'),(_binary '','2024-01-19 16:30:00.000000',5,'carlospereira','carlos.pereira@email.com','Carlos Pereira'),(_binary '','2024-01-20 08:45:00.000000',6,'fernandalima','fernanda.lima@email.com','Fernanda Lima'),(_binary '\0','2024-01-21 13:25:00.000000',7,'rafaelsouza','rafael.souza@email.com','Rafael Souza'),(_binary '','2024-01-22 10:10:00.000000',8,'julianarocha','juliana.rocha@email.com','Juliana Rocha'),(_binary '','2024-01-23 15:20:00.000000',9,'brunoalves','bruno.alves@email.com','Bruno Alves'),(_binary '','2024-01-24 12:35:00.000000',10,'camilaferreira','camila.ferreira@email.com','Camila Ferreira');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-09 15:16:39
