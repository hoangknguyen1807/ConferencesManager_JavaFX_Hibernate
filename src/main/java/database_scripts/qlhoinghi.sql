CREATE DATABASE IF NOT EXISTS `qlhoinghi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `qlhoinghi`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: qlhoinghi
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conference`
(
    `confId`        int          NOT NULL AUTO_INCREMENT,
    `brief`         varchar(200)  DEFAULT NULL,
    `date`          datetime(6)   DEFAULT NULL,
    `detail`        varchar(1000) DEFAULT NULL,
    `image`         varchar(100)  DEFAULT NULL,
    `name`          varchar(100) NOT NULL,
    `venue_venueId` int           DEFAULT NULL,
    PRIMARY KEY (`confId`),
    KEY `FK5lralys56qbgyejtvwxwc8osw` (`venue_venueId`),
    CONSTRAINT `FK5lralys56qbgyejtvwxwc8osw` FOREIGN KEY (`venue_venueId`) REFERENCES `venue` (`venueId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference`
--

LOCK TABLES `conference` WRITE;
/*!40000 ALTER TABLE `conference`
    DISABLE KEYS */;
INSERT INTO `conference`
VALUES (1, 'Ngày hội dành cho sinh viên ngành Technology tìm hiểu về cơ hội việc làm', '2020-07-26 08:00:00.000000',
        'Nhằm đáp ứng nhu cầu tư vấn việc làm, định hướng nghề nghiệp cũng như tạo cầu nối giữa sinh viên và nhà tuyển dụng, #fithcmus phối hợp cùng Công ty Trần Vũ, Nhà Đồng tài trợ ELCA Việt Nam, FPT Software, Fujinet Systems, KMS Technology tổ chức Ngày hội việc làm 2020.',
        'ngayhoivieclam.png', 'Ngày hội việc làm', 1),
       (2, 'Ngưỡng cửa vào nghề: Những hành trang sinh viên cần chuẩn bị', '2020-07-04 09:00:00.000000',
        'Để hỗ trợ các bạn giải đáp những trăn trở, thắc mắc về định hướng nghề nghiệp, Talk Series trở lại với chủ đề đặc biệt dành cho các bạn sinh viên ngành Công nghệ Thông tin: NGƯỠNG CỬA VÀO NGHỀ: Những hành trang cần chuẩn bị',
        'talkseries_nguongcuavaonghe.jpg', 'TalkSeries: Ngưỡng cửa vào nghề', 2),
       (3, 'Microsoft trân trọng giới thiệu Hội nghị trực tuyến : Sales & Marketing trong Thời kỳ Số hoá',
        '2020-08-03 09:30:00.000000',
        'Tại sự kiện trực tuyến này, Quý Doanh nghiệp sẽ được tìm hiểu thêm về kinh nghiệm thực tế của các lãnh đạo cấp cao, các chuyên gia hàng đầu của Microsoft đến từ khắp nơi trên toàn thế giới, các kiến thức về giải pháp công nghệ thiết thực Microsoft Dynamics 365 bao gồm Dynamics 365 Marketing, Dynamics 365 Customer Insights, Dynamics 365 Sales',
        'digitalsalesmarketingsummit2020.jpg', 'Vietnam Digital Sales & Marketing Summit 2020', 3);
/*!40000 ALTER TABLE `conference`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference_user`
--

DROP TABLE IF EXISTS `conference_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conference_user`
(
    `id`       int    NOT NULL AUTO_INCREMENT,
    `approved` bit(1) NOT NULL,
    `confId`   int DEFAULT NULL,
    `userId`   int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKppqy82xar3o29pswtvwx3wsbb` (`confId`),
    KEY `FK7hguc56oj0xp1noblxus1qgjf` (`userId`),
    CONSTRAINT `FK7hguc56oj0xp1noblxus1qgjf` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
    CONSTRAINT `FKppqy82xar3o29pswtvwx3wsbb` FOREIGN KEY (`confId`) REFERENCES `conference` (`confId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_user`
--

LOCK TABLES `conference_user` WRITE;
/*!40000 ALTER TABLE `conference_user`
    DISABLE KEYS */;
INSERT INTO `conference_user`
VALUES (10, _binary '\0', 1, 3),
       (11, _binary '\0', 2, 3),
       (13, _binary '\0', 3, 4),
       (16, _binary '\0', 1, 2),
       (17, _binary '\0', 1, 1);
/*!40000 ALTER TABLE `conference_user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission`
(
    `permissionId` int         NOT NULL AUTO_INCREMENT,
    `name`         varchar(30) NOT NULL,
    PRIMARY KEY (`permissionId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission`
    DISABLE KEYS */;
INSERT INTO `permission`
VALUES (1, 'User'),
       (2, 'Admin');
/*!40000 ALTER TABLE `permission`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `userId`                  int          NOT NULL AUTO_INCREMENT,
    `email`                   varchar(50)  DEFAULT NULL,
    `fullName`                varchar(100) DEFAULT NULL,
    `isActive`                bit(1)       NOT NULL,
    `password`                varchar(100) NOT NULL,
    `username`                varchar(30)  NOT NULL,
    `permission_permissionId` int          DEFAULT NULL,
    PRIMARY KEY (`userId`),
    KEY `FK3xu3c9g9omqdn37amrdqn3btc` (`permission_permissionId`),
    CONSTRAINT `FK3xu3c9g9omqdn37amrdqn3btc` FOREIGN KEY (`permission_permissionId`) REFERENCES `permission` (`permissionId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES (1, 'nhoanganh@gmail.com', 'Nguyễn Hoàng Anh', _binary '',
        '$2a$10$6iBiNFS/SvQJssL8xutwJOZy2Mm8UIy3lkvSiX.L6.XNFt/0wsSRK', 'hoanganh1803', 1),
       (2, 'tqchien@gmail.com', 'Trần Quyết Chiến', _binary '',
        '$2a$10$Ibkjg1L/5JIG3jbJ.xiyTuyVdoIokP6pZVIpa3dGZE.v0ZpjYzauS', 'quyetchien571', 1),
       (3, 'vantoan789@gmail.com', 'Võ Văn Toàn', _binary '',
        '$2a$10$O85NgUWy/hUaEymaXa6vleUeJsiapsuDH3KEZR2bOwJ35u5VMK/O.', 'songtoan987', 1),
       (4, 'khanhhoang1910@gmail.com', 'Nguyễn Khánh Hoàng', _binary '',
        '$2a$10$qMOfi/wVzLKes8aFEyaYueDu4o/p7MeHggxT91sFP/YzrU7g5NYhy', 'nkhoang0191', 2),
       (5, 'hqthinh068@gmail.com', 'Huỳnh Quang Thịnh', _binary '',
        '$2a$10$5eIeaIaL6fV5wwMZpofyKeS6xYsQpTYmp2dui7lmU9oRk..tzGbVa', 'thinhq7970', 1),
       (6, 'hiendao046@gmail.com', 'Đào Minh Hiển', _binary '',
        '$2a$10$gtcGAT6G0kKK7K7BTdQmxezdi9MIh2rkk0Clg6ugUecAQ4YD5JoL6', 'minhhienbc10', 1),
       (7, 'sangdle55@gmail.com', 'Lê Đức Sang', _binary '',
        '$2a$10$/v52nynCcNOCH2Bfh4h/suE25MH4vflEhmOqga0oLupQ3Lj4hG5k6', 'ldsang1337', 1),
       (8, 'trunghieu3296@gmail.com', 'Nguyễn Trần Trung Hiếu', _binary '',
        '$2a$10$OxXw2HhqeRruqYE4LrubPu7AHTttWbmCB/UJqTiyVj82M7UPgi0pu', 'hieuttn369', 1),
       (9, 'hongnhung621@gmail.com', 'Phạm Thị Hồng Nhung', _binary '',
        '$2a$10$iyXahQ8Bj1eBYQwv7avTGuSVQgXodWYE5Wi6CCrq0LG.EKhWftaJa', 'pthnhung126', 1),
       (10, 'maituanhai56@gmail.com', 'Mai Tuấn Hải', _binary '',
        '$2a$10$39E2l7Pn4lmNk87/QIoAyutqeMeG2y3HJnPRX6VED6DwZey6UEIGO', 'mthai256', 1),
       (11, 'thanhlandang847@gmail.com', 'Đặng Thị Thanh Lan', _binary '',
        '$2a$10$4NzPMNLV90k4IeQqQZJCnOsOAjYUvOMpfQRAZaqLm2XMAMeszpxtG', 'dttlan303', 1);
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venue`
--

DROP TABLE IF EXISTS `venue`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venue`
(
    `venueId`  int         NOT NULL AUTO_INCREMENT,
    `capacity` int         NOT NULL,
    `location` varchar(100) DEFAULT NULL,
    `name`     varchar(50) NOT NULL,
    PRIMARY KEY (`venueId`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venue`
--

LOCK TABLES `venue` WRITE;
/*!40000 ALTER TABLE `venue`
    DISABLE KEYS */;
INSERT INTO `venue`
VALUES (1, 250, 'Đại học Khoa học Tự nhiên', 'Hội trường I'),
       (2, 500, 'Đại học Khoa học Tự nhiên', 'Giảng đường I'),
       (3, 1000, 'Quận Thủ Đức, Thành phố Hồ Chí Minh', 'Trung Tâm Hội Nghị White Palace');
/*!40000 ALTER TABLE `venue`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'qlhoinghi'
--
/*!50003 DROP PROCEDURE IF EXISTS `getAttendedConferencesByUser` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8mb4 */;
/*!50003 SET character_set_results = utf8mb4 */;
/*!50003 SET collation_connection = utf8mb4_0900_ai_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `getAttendedConferencesByUser`(userId int)
BEGIN
    SELECT *
    FROM `qlhoinghi`.conference as c
    WHERE exists(select *
                 from `qlhoinghi`.conference_user as cu
                 where c.confId = cu.confId
                   and cu.userId = userId);
END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `getParticipantsByConference` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8mb4 */;
/*!50003 SET character_set_results = utf8mb4 */;
/*!50003 SET collation_connection = utf8mb4_0900_ai_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `getParticipantsByConference`(confId int)
BEGIN
    SELECT *
    FROM `qlhoinghi`.user as u
    WHERE exists(select *
                 from `qlhoinghi`.conference_user as cu
                 where u.userId = cu.userId
                   and cu.confId = confId);
END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-07-31 21:10:38
