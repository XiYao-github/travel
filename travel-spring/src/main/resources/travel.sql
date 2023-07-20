/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 5.7.24 : Database - travel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`travel` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `travel`;

/*Table structure for table `travel_admin` */

DROP TABLE IF EXISTS `travel_admin`;

CREATE TABLE `travel_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '管理员名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '管理员状态，0禁用，1正常',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='管理员';

/*Data for the table `travel_admin` */

insert  into `travel_admin`(`id`,`username`,`password`,`status`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,'admin','admin',1,0,NULL,0,NULL,0),
(2,'张三','d1b129656359e35e95ebd56a63d7b9e0',1,0,NULL,0,NULL,0);

/*Table structure for table `travel_comment` */

DROP TABLE IF EXISTS `travel_comment`;

CREATE TABLE `travel_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `item_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '景点/套餐/游记id',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '评价类型，1景点，2套餐，3游记',
  `content` text COMMENT '评价内容',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '评分',
  `image` json DEFAULT NULL COMMENT '评价图片',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='评价';

/*Data for the table `travel_comment` */

insert  into `travel_comment`(`id`,`user_id`,`item_id`,`type`,`content`,`score`,`image`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,1,6,2,'美丽小岛，让人心神向往。',4,'[\"http://localhost:8080/travel/image/download?name=小岛1.jpg\"]',1,'2023-05-05 17:19:23',1,'2023-05-05 17:19:23',0),
(2,1,6,2,'评论一下',5,'[]',1,'2023-05-06 10:42:21',1,'2023-05-06 10:42:21',0);

/*Table structure for table `travel_dict` */

DROP TABLE IF EXISTS `travel_dict`;

CREATE TABLE `travel_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '编码',
  `value` varchar(100) NOT NULL DEFAULT '' COMMENT '值',
  `label` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`),
  KEY `idx_key` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='数据字典';

/*Data for the table `travel_dict` */

insert  into `travel_dict`(`id`,`code`,`value`,`label`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,'yes_no','1','正常',0,NULL,0,NULL,0),
(2,'yes_no','0','禁用',0,NULL,0,NULL,0),
(3,'comment_type','1','景点',0,NULL,0,NULL,0),
(4,'comment_type','2','套餐',0,NULL,0,NULL,0),
(5,'comment_type','3','游记',0,NULL,0,NULL,0),
(6,'order_type','1','景点',0,NULL,0,NULL,0),
(7,'order_type','2','套餐',0,NULL,0,NULL,0),
(8,'order_status','1','买家下单',0,NULL,0,NULL,0),
(9,'order_status','2','卖家确认',0,NULL,0,NULL,0),
(10,'order_status','3','买家签收',0,NULL,0,NULL,0),
(11,'order_status','4','买家评价',0,NULL,0,NULL,0),
(12,'order_status','5','交易完成',0,NULL,0,NULL,0),
(13,'order_status','0','交易取消',0,NULL,0,NULL,0),
(14,'gender','1','男',0,NULL,0,NULL,0),
(15,'gender','2','女',0,NULL,0,NULL,0),
(16,'gender','0','未知',0,NULL,0,NULL,0),
(17,'notes_status','1','未审核',0,NULL,0,NULL,0),
(18,'notes_status','2','已审核',0,NULL,0,NULL,0);

/*Table structure for table `travel_notes` */

DROP TABLE IF EXISTS `travel_notes`;

CREATE TABLE `travel_notes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '游记标题',
  `content` json DEFAULT NULL COMMENT '游记内容',
  `image` json DEFAULT NULL COMMENT '游记图片',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '游记状态，1未审核，2已审核',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='游记';

/*Data for the table `travel_notes` */

insert  into `travel_notes`(`id`,`user_id`,`title`,`content`,`image`,`status`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,1,'美丽的大自然','[{\"id\": 64369, \"title\": \"林间\", \"content\": \"一走进树林，仿佛进入了一片绿色的海洋，这里的树木高大笔直，树冠茂盛，太阳透过树叶洒落在地面上，形成了一道道斑驳的光线，让人不由自主地想起了童话故事。在这里漫步，仿佛闻到了春天的气息，看到了大自然的神奇之处。\"}, {\"id\": 58868, \"title\": \"草原\", \"content\": \"在草原上，我们驾着马儿奔驰，追逐自由和梦想。这是一场关于自然、人类和生命的奇妙之旅，让我们用心去感受大自然的磅礴力量和美丽景色。\"}, {\"id\": 82132, \"title\": \"雪地\", \"content\": \"山脉的上方挂满了浓重的云雾，看起来宛如仙女的裙裾，令人陶醉，仿佛自己是一只雪怪在雪地中自由翱翔。在这片纯白的雪地上，感受到了大自然的伟大和神奇之处。\"}]','[\"http://localhost:8080/travel/image/download?name=1.jpeg\", \"http://localhost:8080/travel/image/download?name=2.jpeg\", \"http://localhost:8080/travel/image/download?name=6.jpeg\"]',1,1,'2023-05-04 15:21:00',1,'2023-05-04 15:21:00',0),
(2,1,'美丽的大自然','[{\"id\": 39680, \"title\": \"草原\", \"content\": \"在草原上，我们驾着马儿奔驰，追逐自由和梦想。这是一场关于自然、人类和生命的奇妙之旅，让我们用心去感受大自然的磅礴力量和美丽景色。\"}]','[\"http://localhost:8080/travel/image/download?name=2.jpeg\"]',2,1,'2023-05-05 17:10:58',1,'2023-05-05 17:10:58',0),
(3,1,'美丽的大自然','[{\"id\": 61096, \"title\": \"林间\", \"content\": \"一走进树林，仿佛进入了一片绿色的海洋，这里的树木高大笔直，树冠茂盛，太阳透过树叶洒落在地面上，形成了一道道斑驳的光线，让人不由自主地想起了童话故事。在这里漫步，仿佛闻到了春天的气息，看到了大自然的神奇之处。\"}]','[\"http://localhost:8080/travel/image/download?name=1.jpeg\"]',2,1,'2023-05-05 17:11:36',1,'2023-05-05 17:11:36',0),
(4,1,'美丽的大自然','[{\"id\": 4071, \"title\": \"雪地\", \"content\": \"山脉的上方挂满了浓重的云雾，看起来宛如仙女的裙裾，令人陶醉，仿佛自己是一只雪怪在雪地中自由翱翔。在这片纯白的雪地上，感受到了大自然的伟大和神奇之处。\"}]','[\"http://localhost:8080/travel/image/download?name=6.jpeg\"]',2,1,'2023-05-05 17:12:00',1,'2023-05-05 17:12:00',0);

/*Table structure for table `travel_order` */

DROP TABLE IF EXISTS `travel_order`;

CREATE TABLE `travel_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `item_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '景点/套餐id',
  `num` varchar(100) NOT NULL DEFAULT '' COMMENT '订单编号',
  `price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '订单价格',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '订单类型，1景点，2套餐',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '订单状态，1买家下单，2卖家确认，3买家签收，4买家评价，5交易完成，0交易取消',
  `status_list` json DEFAULT NULL COMMENT '订单状态详情',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='订单';

/*Data for the table `travel_order` */

insert  into `travel_order`(`id`,`user_id`,`item_id`,`num`,`price`,`type`,`status`,`status_list`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,1,2,'DD202305058617',12.12,1,2,'[{\"desc\": \"2023-05-05 17:17:38\", \"title\": \"买家下单\", \"status\": 1}, {\"desc\": \"2023-05-05 17:17:38\", \"title\": \"卖家确认\", \"status\": 2}]',1,'2023-05-05 17:17:39',1,'2023-05-05 17:17:39',0),
(2,1,4,'DD202305052042',12.12,1,3,'[{\"desc\": \"2023-05-05 17:17:42\", \"title\": \"买家下单\", \"status\": 1}, {\"desc\": \"2023-05-05 17:17:42\", \"title\": \"卖家确认\", \"status\": 2}, {\"desc\": \"2023-05-05 17:18:10\", \"title\": \"买家签收\", \"status\": 3}]',1,'2023-05-05 17:17:42',1,'2023-05-05 17:17:42',0),
(3,1,6,'DD202305054954',13.00,2,5,'[{\"desc\": \"2023-05-05 17:18:04\", \"title\": \"买家下单\", \"status\": 1}, {\"desc\": \"2023-05-05 17:18:04\", \"title\": \"卖家确认\", \"status\": 2}, {\"desc\": \"2023-05-05 17:18:13\", \"title\": \"买家签收\", \"status\": 3}, {\"desc\": \"2023-05-06 10:42:20\", \"title\": \"买家评价\", \"status\": 4}, {\"desc\": \"2023-05-06 10:42:20\", \"title\": \"交易完成\", \"status\": 5}]',1,'2023-05-05 17:18:05',1,'2023-05-05 17:18:05',0);

/*Table structure for table `travel_packages` */

DROP TABLE IF EXISTS `travel_packages`;

CREATE TABLE `travel_packages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '套餐名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '套餐描述',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '套餐区域',
  `price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '套餐价格',
  `image` json DEFAULT NULL COMMENT '套餐图片',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '套餐状态，0禁用，1正常',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='套餐';

/*Data for the table `travel_packages` */

insert  into `travel_packages`(`id`,`name`,`description`,`address`,`price`,`image`,`status`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,'河流','美丽的济南泉城著名的泉水就来源于紫霄泉，紫霄泉是有名的泉城“七十二泉”之一，有着绵绵不绝的泉水流动，整个山庄不用人工喝水。搭乘泉城篷车，游客可以欣赏沿途风景，还可以饱览泉城的美景，河流在这里格外清澈迷人。','济南',20.00,'[\"http://localhost:8080/travel/image/download?name=河流1.jpg\", \"http://localhost:8080/travel/image/download?name=河流2.jpg\"]',1,1,'2023-04-23 13:45:11',1,'2023-04-23 13:45:11',0),
(2,'公园','天津的五大道风景区是天津的著名旅游景点，以其历史悠久、欧式别墅和花园的美丽闻名于世。五大道的公园都是极为托兴趣的。在这里，“老豆菜园”是许多人最受欢迎的地方，可以看到各种各样的植物和中国传统建筑的优美景观。','天津',50.00,'[\"http://localhost:8080/travel/image/download?name=公园1.jpg\", \"http://localhost:8080/travel/image/download?name=公园2.jpg\"]',1,1,'2023-04-23 13:45:40',1,'2023-04-23 13:45:40',0),
(3,'松树','旅游胜地湖南的张家界是一个兼具自然景观和人文特色的地区，其独特的地形、自然景观和灵秀的开花，勾勒出了一个神奇的大自然世界。其中，松桥溪是游客不能错过的景区之一。这个富含众多松树的景区以其原始而亲近的特点吸引了许多游客。','湖南',13.00,'[\"http://localhost:8080/travel/image/download?name=松树1.jpg\", \"http://localhost:8080/travel/image/download?name=松树2.jpg\"]',1,1,'2023-04-27 13:06:25',1,'2023-04-27 13:06:25',0),
(4,'峡谷','秀美的三峡景区是中国著名的景点之一，主要由三个峡谷——瞿塘峡、巫峡和西陵峡组成。三峡峡谷是中国最壮丽的自然景观之一，它以壮观的自然景观和神秘的文化历史闻名于世。','三峡',20.00,'[\"http://localhost:8080/travel/image/download?name=峡谷1.jpg\", \"http://localhost:8080/travel/image/download?name=峡谷2.jpg\"]',1,1,'2023-05-04 10:57:54',1,'2023-05-04 10:58:00',0),
(5,'山河','新疆的天山山脉是世界著名的山脉之一，其瑰丽的山河风景让人心醉。天山是中国重要的旅游城市，每年吸引了许多游客前来观赏天山美景、独特的民俗和美味的新疆美食。','新疆',50.00,'[\"http://localhost:8080/travel/image/download?name=山河1.jpg\", \"http://localhost:8080/travel/image/download?name=山河2.jpg\"]',1,1,'2023-05-04 10:57:55',1,'2023-05-04 10:58:01',0),
(6,'小岛','浙江的普陀山是中国佛教文化名山，也是浙江著名的旅游胜地之一。普陀山是一座位于海滨的小山岛，这里有壮观的海洋景观、丰富多彩的历史、人文景观和瑰丽的寺庙建筑，极富吸引力。','浙江',13.00,'[\"http://localhost:8080/travel/image/download?name=小岛1.jpg\", \"http://localhost:8080/travel/image/download?name=小岛2.jpg\"]',1,1,'2023-05-04 10:57:56',1,'2023-05-04 10:58:01',0),
(7,'树丛','位于四川的九寨沟是中国最美的自然景观之一，以其特有的大树和草原而闻名。在这里，游客可以看到松、柏、榆、栎、枫等各种林木在溪谷之上纵横交错，在金色的秋天，冰清玉洁的湍急的山泉在树丛中流淌，让人不禁心驰神往。','区域',20.00,'[\"http://localhost:8080/travel/image/download?name=树丛1.jpg\", \"http://localhost:8080/travel/image/download?name=树丛2.jpg\"]',1,1,'2023-05-04 10:57:57',1,'2023-05-04 10:58:02',0),
(8,'冰川','新疆天然的玛纳斯冰川是一个家喻户晓的旅游胜地，it在天山山区的克孜勒苏州，是中国第二大冰川。这个神秘的冰川区域吸引了许多爱好自然环境和自然风光的游客前来观光。这里有壮观的冰川、神秘的山谷、丰富多彩的草原、苍翠绿色的山丘、清凉的高山氧吧。','新疆',50.00,'[\"http://localhost:8080/travel/image/download?name=冰川1.jpg\", \"http://localhost:8080/travel/image/download?name=冰川2.jpg\"]',1,1,'2023-05-04 10:57:57',1,'2023-05-04 10:58:02',0);

/*Table structure for table `travel_packages_detailed` */

DROP TABLE IF EXISTS `travel_packages_detailed`;

CREATE TABLE `travel_packages_detailed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `packages_id` bigint(20) NOT NULL COMMENT '套餐id',
  `scenic_id` bigint(20) NOT NULL COMMENT '景点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='套餐景点明细';

/*Data for the table `travel_packages_detailed` */

insert  into `travel_packages_detailed`(`id`,`packages_id`,`scenic_id`) values 
(1,1,17),
(2,1,9),
(3,2,10),
(4,2,18),
(5,3,19),
(6,3,11),
(7,4,20),
(8,4,12),
(9,5,13),
(10,5,21),
(11,6,14),
(12,6,22),
(13,7,15),
(14,7,23),
(15,8,16),
(16,8,24);

/*Table structure for table `travel_scenic` */

DROP TABLE IF EXISTS `travel_scenic`;

CREATE TABLE `travel_scenic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '景点名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '景点描述',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '景点地址',
  `price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '景点价格',
  `image` json DEFAULT NULL COMMENT '景点图片',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '景点状态，0禁用，1正常',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='景点';

/*Data for the table `travel_scenic` */

insert  into `travel_scenic`(`id`,`name`,`description`,`address`,`price`,`image`,`status`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,'古城','位于江西的庐山古城是中国文化历史悠久的古城之一，也是老祖宗留给我们的一份珍贵的文化遗产。庐山古城有着高峻的城墙、巍峨的古楼和精美的府第等多种建筑风格，是游客来庐山的必去之地。','江西',12.33,'[\"http://localhost:8080/travel/image/download?name=古城1.jpg\"]',1,1,'2023-04-23 13:44:20',1,'2023-04-23 13:44:20',0),
(2,'城市','香港是一个充满活力和生机的现代化城市，也是中国的主要城市之一。香港是东亚区域的商业、旅游和文化中心，拥有一系列独特的景点和地标建筑，吸引了数以百万计的游客前来参观。','香港',12.12,'[\"http://localhost:8080/travel/image/download?name=城市1.jpg\"]',1,1,'2023-04-23 13:44:43',1,'2023-04-23 13:44:43',0),
(3,'海滨','青岛是一个著名的海滨城市，有“海上明珠”之称。位于黄海之滨的青岛有许多极具吸引力的海滨景观，如栈桥、海水浴场、海边公园等等。每年夏季，青岛吸引了来自各地的众多游客前来享受海滨之旅。','青岛',12.00,'[\"http://localhost:8080/travel/image/download?name=海滨1.jpg\"]',1,1,'2023-04-27 13:06:01',1,'2023-04-27 13:06:01',0),
(4,'山水','广西的桂林山水甲天下，其美丽秀丽的山水环境让人心醉。游客可以在桂林的山水之间游览，在著名的象鼻山、福临门、三星岩等景点领略壮美的山水美景。','广西',12.12,'[\"http://localhost:8080/travel/image/download?name=山水1.jpg\"]',1,1,'2023-05-04 11:00:11',1,'2023-05-04 11:00:15',0),
(5,'凤凰古城','湖南的凤凰古城是中国历史文化名城之一，被列入UNESCO世界遗产名录。凤凰古城以其古香古色的民居和独特的人文景观闻名于世，游客可以在这里漫步古镇的街道、探访古建筑和风景名胜。','湖南',12.33,'[\"http://localhost:8080/travel/image/download?name=古城2.jpg\"]',1,1,'2023-04-23 13:44:20',1,'2023-04-23 13:44:20',0),
(6,'城市夜景','上海是一个充满现代化气息的城市，其璀璨夺目的夜景更为其增色不少。来到上海，不妨在南京路步行街、外滩、陆家嘴等地欣赏城市夜景，体验一种与众不同的魅力。','上海',12.12,'[\"http://localhost:8080/travel/image/download?name=城市2.jpg\"]',1,1,'2023-04-23 13:44:43',1,'2023-04-23 13:44:43',0),
(7,'海滨景观','厦门是一个著名的海滨城市，其独特的自然风光和人文景观吸引了无数游客前来。在厦门，游客可以欣赏壮观的海滨景观，如南普陀寺、鼓浪屿等著名景点。','厦门',12.00,'[\"http://localhost:8080/travel/image/download?name=海滨2.jpg\"]',1,1,'2023-04-27 13:06:01',1,'2023-04-27 13:06:01',0),
(8,'山水瀑布','云南的泸沽湖是中国西南地区最美丽的山水湖泊之一，拥有壮丽的山峦、神秘的湖泊和瀑布等自然景观。在这里，游客可以欣赏到独特的山水之美，感受大自然的神奇。','云南',12.12,'[\"http://localhost:8080/travel/image/download?name=山水2.jpg\"]',1,1,'2023-05-04 11:00:11',1,'2023-05-04 11:00:15',0),
(9,'树林河流','贵州的黄果树瀑布是中国著名的瀑布之一，被誉为“世界五大瀑布之首”。黄果树瀑布不仅拥有壮观的瀑布景观，还有树林河流和奇特的岩石景观，让游客深深被大自然所吸引。','贵州',20.00,'[\"http://localhost:8080/travel/image/download?name=河流1.jpg\"]',1,1,'2023-04-23 13:45:11',1,'2023-04-23 13:45:11',0),
(10,'黄石公园','美国黄石公园是全球知名的自然保护区，被列入UNESCO世界遗产名录并被誉为世界七大自然奇观之一。黄石公园拥有美丽的山水景观、壮观的峡谷河流和独特的自然景观，是国际旅游者的必游之地。','美国',50.00,'[\"http://localhost:8080/travel/image/download?name=公园1.jpg\"]',1,1,'2023-04-23 13:45:40',1,'2023-04-23 13:45:40',0),
(11,'寒雪松树','哈尔滨的松花江是一个美丽的自然景区，名列中国最美丽的河流之一。在寒冷的冬季，松花江河畔的松树变得格外凝重，树枝上挂着飞扬的雪花，形成了一幅幅壮美的冬景，让人陶醉。','哈尔滨',13.00,'[\"http://localhost:8080/travel/image/download?name=松树1.jpg\"]',1,1,'2023-04-27 13:06:25',1,'2023-04-27 13:06:25',0),
(12,'峡谷河流','四川的金沙江大峡谷是中国最为壮美的河流峡谷之一。峡谷河流穿梭于巨石间，形成了一条壮美的景观线路，其雄奇壮观的风景让游客流连忘返。','四川',20.00,'[\"http://localhost:8080/travel/image/download?name=峡谷2.jpg\"]',1,1,'2023-05-04 10:57:54',1,'2023-05-04 10:58:00',0),
(13,'高山长河','中国的四川、云南、贵州等省份有着许多高山长河的景点，其中最著名的要数中国的第一条长江，它的源头位于西藏青藏高原，从那里流淌全长6300公里，经过19个省市区并最终注入东海。在长江流域，有着许多令人惊叹的景观，如三峡大坝、峨眉山等。此外，还有黄河、长白山等高山长河景点值得一去。','西藏',50.00,'[\"http://localhost:8080/travel/image/download?name=山河1.jpg\"]',1,1,'2023-05-04 10:57:55',1,'2023-05-04 10:58:01',0),
(14,'小岛晚霞','小岛和晚霞的相遇总是让人感到无比浪漫，可以考虑前往希腊的圣托里尼岛，它被誉为世界上最美的岛屿之一，这里的蓝色白色房屋和风车，与黄金色的晚霞交相辉映。另外，日本的小樽运河、美国的夏威夷群岛也是不错的选择。','希腊',13.00,'[\"http://localhost:8080/travel/image/download?name=小岛1.jpg\"]',1,1,'2023-05-04 10:57:56',1,'2023-05-04 10:58:01',0),
(15,'红树丛林','红树丛林一般生长在潮间带，如中国海南的西岛、洛阳岛上都有非常广阔的红树林。这里的红树林呈现在千姿百态的颜色，不仅可以观赏到其独特的景色还可以感受到它带来的新鲜空气。','海南',20.00,'[\"http://localhost:8080/travel/image/download?name=树丛1.jpg\"]',1,1,'2023-05-04 10:57:57',1,'2023-05-04 10:58:02',0),
(16,'冰川小岛','冰川小岛可以前往南极和格陵兰岛。南极圈内的雷蒙德冰瀑布是南极洲上最著名的景点之一，它的落差高达340米，是世界上最高的冰瀑布之一。在格陵兰岛，可以欣赏到冰川蓝色的光彩，以及冰山碰撞时发出的巨大声响。','格陵兰',50.00,'[\"http://localhost:8080/travel/image/download?name=冰川1.jpg\"]',1,1,'2023-05-04 10:57:57',1,'2023-05-04 10:58:02',0),
(17,'河流','美丽的济南泉城著名的泉水就来源于紫霄泉，紫霄泉是有名的泉城“七十二泉”之一，有着绵绵不绝的泉水流动，整个山庄不用人工喝水。搭乘泉城篷车，游客可以欣赏沿途风景，还可以饱览泉城的美景，河流在这里格外清澈迷人。','济南',20.00,'[\"http://localhost:8080/travel/image/download?name=河流2.jpg\"]',1,1,'2023-04-23 13:45:11',1,'2023-04-23 13:45:11',0),
(18,'公园','天津的五大道风景区是天津的著名旅游景点，以其历史悠久、欧式别墅和花园的美丽闻名于世。五大道的公园都是极为托兴趣的。在这里，“老豆菜园”是许多人最受欢迎的地方，可以看到各种各样的植物和中国传统建筑的优美景观。','天津',50.00,'[\"http://localhost:8080/travel/image/download?name=公园2.jpg\"]',1,1,'2023-04-23 13:45:40',1,'2023-04-23 13:45:40',0),
(19,'松树','旅游胜地湖南的张家界是一个兼具自然景观和人文特色的地区，其独特的地形、自然景观和灵秀的开花，勾勒出了一个神奇的大自然世界。其中，松桥溪是游客不能错过的景区之一。这个富含众多松树的景区以其原始而亲近的特点吸引了许多游客。','湖南',13.00,'[\"http://localhost:8080/travel/image/download?name=松树2.jpg\"]',1,1,'2023-04-27 13:06:25',1,'2023-04-27 13:06:25',0),
(20,'峡谷','秀美的三峡景区是中国著名的景点之一，主要由三个峡谷——瞿塘峡、巫峡和西陵峡组成。三峡峡谷是中国最壮丽的自然景观之一，它以壮观的自然景观和神秘的文化历史闻名于世。','三峡',20.00,'[\"http://localhost:8080/travel/image/download?name=峡谷1.jpg\"]',1,1,'2023-05-04 10:57:54',1,'2023-05-04 10:58:00',0),
(21,'山河','新疆的天山山脉是世界著名的山脉之一，其瑰丽的山河风景让人心醉。天山是中国重要的旅游城市，每年吸引了许多游客前来观赏天山美景、独特的民俗和美味的新疆美食。','新疆',50.00,'[\"http://localhost:8080/travel/image/download?name=山河2.jpg\"]',1,1,'2023-05-04 10:57:55',1,'2023-05-04 10:58:01',0),
(22,'小岛','浙江的普陀山是中国佛教文化名山，也是浙江著名的旅游胜地之一。普陀山是一座位于海滨的小山岛，这里有壮观的海洋景观、丰富多彩的历史、人文景观和瑰丽的寺庙建筑，极富吸引力。','浙江',13.00,'[\"http://localhost:8080/travel/image/download?name=小岛2.jpg\"]',1,1,'2023-05-04 10:57:56',1,'2023-05-04 10:58:01',0),
(23,'树丛','位于四川的九寨沟是中国最美的自然景观之一，以其特有的大树和草原而闻名。在这里，游客可以看到松、柏、榆、栎、枫等各种林木在溪谷之上纵横交错，在金色的秋天，冰清玉洁的湍急的山泉在树丛中流淌，让人不禁心驰神往。','四川',20.00,'[\"http://localhost:8080/travel/image/download?name=树丛2.jpg\"]',1,1,'2023-05-04 10:57:57',1,'2023-05-04 10:58:02',0),
(24,'冰川','新疆天然的玛纳斯冰川是一个家喻户晓的旅游胜地，it在天山山区的克孜勒苏州，是中国第二大冰川。这个神秘的冰川区域吸引了许多爱好自然环境和自然风光的游客前来观光。这里有壮观的冰川、神秘的山谷、丰富多彩的草原、苍翠绿色的山丘、清凉的高山氧吧。','新区域',50.00,'[\"http://localhost:8080/travel/image/download?name=冰川2.jpg\"]',1,1,'2023-05-04 10:57:57',1,'2023-05-04 10:58:02',0);

/*Table structure for table `travel_shop` */

DROP TABLE IF EXISTS `travel_shop`;

CREATE TABLE `travel_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `item_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '景点/套餐id',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '订单类型，1景点，2套餐',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='购物车';

/*Data for the table `travel_shop` */

insert  into `travel_shop`(`id`,`user_id`,`item_id`,`type`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,1,1,1,1,'2023-05-05 17:17:12',1,'2023-05-05 17:17:12',0),
(2,1,1,2,1,'2023-05-05 17:17:16',1,'2023-05-05 17:17:16',0),
(3,1,8,2,1,'2023-05-05 17:17:23',1,'2023-05-05 17:17:23',0),
(4,1,4,1,1,'2023-05-05 17:17:28',1,'2023-05-05 17:17:28',1),
(5,1,2,1,1,'2023-05-05 17:17:33',1,'2023-05-05 17:17:33',1),
(6,1,6,2,1,'2023-05-05 17:18:01',1,'2023-05-05 17:18:01',1);

/*Table structure for table `travel_user` */

DROP TABLE IF EXISTS `travel_user`;

CREATE TABLE `travel_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `phone` varchar(100) NOT NULL DEFAULT '' COMMENT '手机号码',
  `address` varchar(100) DEFAULT '' COMMENT '地址',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `gender` int(11) NOT NULL DEFAULT '0' COMMENT '性别，1男，2女，0未知',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态，0禁用，1正常',
  `image` json DEFAULT NULL COMMENT '用户头像',
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

/*Data for the table `travel_user` */

insert  into `travel_user`(`id`,`username`,`password`,`phone`,`address`,`birthday`,`gender`,`status`,`image`,`created_by`,`created_time`,`updated_by`,`updated_time`,`del_flag`) values 
(1,'user','user','17720202177','系统','1999-02-04',0,1,'[\"http://localhost:8080/travel/image/download?name=img.png\"]',1,'2023-05-04 14:46:30',1,'2023-05-04 14:46:31',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
