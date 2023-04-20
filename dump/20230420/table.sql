-- Adminer 4.8.1 MySQL 5.5.5-10.4.21-MariaDB-1:10.4.21+maria~focal dump

SET NAMES utf8;
SET
time_zone = '+00:00';
SET
foreign_key_checks = 0;
SET
sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`
(
  `id`         bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime    NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `name`       varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`
(
  `id`         bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime     NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `name`       varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `place`;
CREATE TABLE `place`
(
  `id`          bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at`  datetime     NOT NULL,
  `deleted_at`  datetime DEFAULT NULL,
  `address`     varchar(255) NOT NULL,
  `description` longtext     NOT NULL,
  `images`      longtext     NOT NULL,
  `name`        varchar(100) NOT NULL,
  `point`       geometry     NOT NULL,
  `rating`      double       NOT NULL,
  `region_id`   bigint(20) NOT NULL,
  `title`       varchar(200) NOT NULL,
  `visit_date`  char(8)      NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `place_activity`;
CREATE TABLE `place_activity`
(
  `activity_id` bigint(20) NOT NULL,
  `place_id`    bigint(20) NOT NULL,
  `created_at`  datetime NOT NULL,
  `deleted_at`  datetime DEFAULT NULL,
  PRIMARY KEY (`activity_id`, `place_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `place_like`;
CREATE TABLE `place_like`
(
  `place_id`   bigint(20) NOT NULL,
  `user_id`    bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`place_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `place_type`;
CREATE TABLE `place_type`
(
  `id`         bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime    NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `name`       varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `place_type_group`;
CREATE TABLE `place_type_group`
(
  `place_id`      bigint(20) NOT NULL,
  `place_type_id` bigint(20) NOT NULL,
  `created_at`    datetime NOT NULL,
  `deleted_at`    datetime DEFAULT NULL,
  PRIMARY KEY (`place_id`, `place_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `place_unit`;
CREATE TABLE `place_unit`
(
  `place_id`   bigint(20) NOT NULL,
  `unit_id`    bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`place_id`, `unit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`
(
  `id`              bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at`      datetime    NOT NULL,
  `deleted_at`      datetime DEFAULT NULL,
  `name`            varchar(20) NOT NULL,
  `upper_region_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY               `IDX14mc3fyj8i1tdouyxij2faqbv` (`name`,`upper_region_id`,`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`
(
  `id`         bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime     NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `name`       varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `tag_place`;
CREATE TABLE `tag_place`
(
  `place_id`   bigint(20) NOT NULL,
  `tag_id`     bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`place_id`, `tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `tour`;
CREATE TABLE `tour`
(
  `id`         bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime    NOT NULL,
  `deleted_at` datetime     DEFAULT NULL,
  `favorites`  int(11) NOT NULL,
  `from`       datetime    NOT NULL,
  `is_done`    bit(1)      NOT NULL,
  `name`       varchar(50) NOT NULL,
  `recommends` int(11) NOT NULL,
  `region_id`  bigint(20) NOT NULL,
  `scope`      varchar(255) DEFAULT NULL,
  `to`         datetime    NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `tourist`;
CREATE TABLE `tourist`
(
  `tour_id`    bigint(20) NOT NULL,
  `user_id`    binary(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`tour_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `tour_notification`;
CREATE TABLE `tour_notification`
(
  `id`           bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at`   datetime NOT NULL,
  `deleted_at`   datetime DEFAULT NULL,
  `scheduled_at` datetime NOT NULL,
  `sended_at`    datetime DEFAULT NULL,
  `tour_id`      bigint(20) NOT NULL,
  `user_id`      binary(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `tour_place`;
CREATE TABLE `tour_place`
(
  `tour_id`     bigint(20) NOT NULL,
  `created_at`  datetime     NOT NULL,
  `deleted_at`  datetime DEFAULT NULL,
  `budget`      int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `from`        datetime     NOT NULL,
  `to`          datetime     NOT NULL,
  `place_id`    bigint(20) NOT NULL,
  PRIMARY KEY (`place_id`, `tour_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit`
(
  `id`         bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime    NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `name`       varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `unit_member`;
CREATE TABLE `unit_member`
(
  `member_id`  bigint(20) NOT NULL,
  `unit_id`    bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`member_id`, `unit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
  `id`           varchar(255) NOT NULL,
  `created_at`   datetime     NOT NULL,
  `deleted_at`   datetime     DEFAULT NULL,
  `display_name` varchar(30)  NOT NULL,
  `email`        varchar(100) NOT NULL,
  `fcm_token`    varchar(200) DEFAULT NULL,
  `role`         varchar(255) DEFAULT NULL,
  `twitter_id`   varchar(30)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pnrrew2o71m330w38p0ypwjvn` (`display_name`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_gb09nma0ng5y12omp5d8jbvfg` (`twitter_id`),
  KEY            `IDXk506v42k6ayrumfp4k1w5f0x3` (`email`,`deleted_at`),
  KEY            `IDX15smhe6hvs6x6ja3vgw95sulv` (`fcm_token`,`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 2023-04-20 13:17:24
