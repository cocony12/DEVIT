# DB 생성
CREATE DATABASE IF NOT EXISTS devit;



# DB 사용
USE devit;



# user 테이블 생성
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '유저 사용자 고유 id',
  `nickname` varchar(45) DEFAULT NULL COMMENT '사용자 닉네임',
  `email` varchar(45) DEFAULT NULL COMMENT '사용자 이메일',
  `password` varchar(500) DEFAULT NULL COMMENT '사용자 비밀번호',
  `active` varchar(1) DEFAULT 'Y' COMMENT ' 탈퇴여부',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `deleted` datetime DEFAULT NULL COMMENT '삭제일',
  `email_confirm` varchar(1) NOT NULL DEFAULT 'N' COMMENT '이메일 인증 확인 여부',
  `code` varchar(45) DEFAULT NULL,
  `profile` varchar(200) DEFAULT 'defaultUser.png',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# user_roles 테이블 생성
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `user_role` varchar(45) DEFAULT NULL,
  KEY `uid_idx` (`user_id`),
  CONSTRAINT `uid` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# temp data 삽입
INSERT INTO `user` (`nickname`, `email`, `password`) VALUES ('DEVIT-관리자', 'i3a101.devit.io@gmail.com', '{bcrypt}$2a$10$3WhBtFfHMLa2PLFby2uWMOGXXJ.NLLsqge9wJptlcvg1ZS.5xnwmK');
INSERT INTO `user_role` (`user_id`, `user_role`) VALUES((SELECT `user_id` FROM `user` WHERE nickname='DEVIT-관리자'), 'ROLE_ADMIN');



# board 테이블 삽입
CREATE TABLE `board` (
  `board_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(300) NOT NULL,
  `content` text,
  `content_html` text,
  `type` int(11) NOT NULL,
  `count` int(11) NOT NULL DEFAULT '0',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT NULL,
  `delete_yn` varchar(1) DEFAULT 'N',
  `lecture_id` int(11) DEFAULT NULL COMMENT '강의와 연관된 질문일 경우 키값',
  `sub_id` int(11) DEFAULT NULL COMMENT '강의와 연관된 질문일 경우 키값',
  `sub_his_id` int(11) DEFAULT NULL COMMENT '강의와 연관된 질문일 경우 키값',
  PRIMARY KEY (`board_id`),
  KEY `uidd_idx` (`user_id`),
  CONSTRAINT `board_uid_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# board_reply 테이블 삽입
CREATE TABLE `board_reply` (
  `board_reply_id` int(11) NOT NULL AUTO_INCREMENT,
  `board_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `reply_created` datetime DEFAULT CURRENT_TIMESTAMP,
  `reply_content` text,
  `parent_reply_id` int(11) DEFAULT NULL,
  `reply_modified` datetime DEFAULT CURRENT_TIMESTAMP,
  `delete_yn` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`board_reply_id`),
  KEY `board_reply_bid_fk_idx` (`board_id`),
  KEY `board_reply_uid_fk_idx` (`user_id`),
  CONSTRAINT `board_reply_bid_fk` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `board_reply_uid_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



# lecture 관련 테이블 삽입
CREATE TABLE `lecture_common` (
  `common_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`common_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `lecture` (
  `lecture_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `common_id` int(11) NOT NULL,
  `title` varchar(300) DEFAULT NULL,
  `content` text,
  `thumbnail_url` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT CURRENT_TIMESTAMP,
  `type` int(11) DEFAULT NULL,
  `deleted_flag` varchar(1) DEFAULT 'N',
  `view_count` int(11) DEFAULT '0',
  PRIMARY KEY (`lecture_id`),
  UNIQUE KEY `cid_UNIQUE` (`common_id`),
  KEY `lecture_cid_fk_idx` (`common_id`),
  KEY `lecture_uid_fk_idx` (`user_id`),
  CONSTRAINT `lecture_cid_fk` FOREIGN KEY (`common_id`) REFERENCES `lecture_common` (`common_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lecture_uid_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `lecture_auth` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '권한 id',
  `lecture_id` int(11) NOT NULL COMMENT '프로젝트 id',
  `user_id` int(11) NOT NULL COMMENT '사용자 id',
  `lecture_role` varchar(20) DEFAULT NULL COMMENT '권한 이름',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일자',
  `delete_yn` varchar(1) DEFAULT 'N' COMMENT '삭제 여부',
  PRIMARY KEY (`auth_id`),
  KEY `lecture_auth_uid_fk_idx` (`user_id`),
  KEY `lecture_auth_lid_fk` (`lecture_id`),
  CONSTRAINT `lecture_auth_lid_fk` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`lecture_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lecture_auth_uid_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `lecture_sub_index` (
  `sub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '소분류 테이블 자동 증가 id',
  `lecture_id` int(11) NOT NULL COMMENT '대표 프로젝트 id',
  `user_id` int(11) NOT NULL COMMENT '생성한 사람',
  `modifier` int(11) NOT NULL COMMENT '수정한 사람',
  `common_id` int(11) NOT NULL COMMENT '태그를 관리할 common id',
  `title` varchar(300) DEFAULT NULL COMMENT '소분류 제목',
  `order` int(11) DEFAULT '1' COMMENT '소분류 순서',
  `video_id` int(11) DEFAULT NULL COMMENT 'lecture_sub_history로 부터 반영되어 보여질 동영상을 포함하고 있는 lectrue_sub_history.sub_his_id',
  `wiki_id` int(11) DEFAULT NULL COMMENT 'lecture_sub_history로 부터 반영되어 보여질 wiki를 포함하고 있는 lectrue_sub_history.sub_his_id',
  `view_count` int(11) DEFAULT NULL COMMENT '조회수',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  PRIMARY KEY (`sub_id`),
  UNIQUE KEY `cid_UNIQUE` (`common_id`),
  KEY `lecture_sub_index_cid_fk_idx` (`common_id`),
  KEY `lecture_sub_index_lid_fk_idx` (`lecture_id`),
  KEY `lecture_sub_index_user_id_fk_idx` (`user_id`),
  KEY `lecture_sub_modifier_fk_idx` (`modifier`),
  CONSTRAINT `lecture_sub_index_cid_fk` FOREIGN KEY (`common_id`) REFERENCES `lecture_common` (`common_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lecture_sub_index_lecture_id_fk` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`lecture_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lecture_sub_index_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lecture_sub_modifier_fk` FOREIGN KEY (`modifier`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `lecture_sub_history` (
  `sub_his_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '강의 참여 요청 이력 테이블 PK',
  `lecture_id` int(11) NOT NULL COMMENT '프로젝트 단위',
  `sub_id` int(11) NOT NULL COMMENT '강의(목차) 단위',
  `user_id` int(11) NOT NULL COMMENT '요청한 사람',
  `modifier` int(11) NOT NULL COMMENT '수정한 사람',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  `title` varchar(300) DEFAULT NULL,
  `thumbnail_url` varchar(300) DEFAULT NULL,
  `play_time` varchar(45) DEFAULT NULL,
  `player_url` varchar(300) DEFAULT NULL COMMENT '동영상 강의 URL',
  `wiki_content` text COMMENT '강의 WIKI 내용 TEXT만',
  `wiki_content_html` text COMMENT '강의 WIKI 내용 HTML',
  `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '조회수 (요청타입이 동영상일 경우에만 count되는 컬럼)',
  `req_type` varchar(20) NOT NULL COMMENT '요청타입 (동영상인지 강의내용인지)\nex. video/wiki',
  `accept_yn` varchar(1) DEFAULT NULL COMMENT '요청을 받아들일지 거절할지 여부',
  `delete_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT 'accept_yn 여부와 상관 없이 부적절한 동영상일 경우 목록에서 보이지 않도록...',
  PRIMARY KEY (`sub_his_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='강의 참여 요청 이력 테이블';

CREATE TABLE `lecture_sub_his_like` (
  `sub_his_like_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'lecture_sub_his_like의 PK',
  `lecture_id` int(11) NOT NULL,
  `sub_id` int(11) NOT NULL,
  `sub_his_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_flag` varchar(1) DEFAULT 'Y' COMMENT '좋아요 여부',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`sub_his_like_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='lecture_sub_history에 존재하는 동영상들에 대한 좋아요 관리 테이블';

CREATE TABLE `lecture_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `common_id` int(11) NOT NULL,
  `tag_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  KEY `lecture_tag_cid_fk_idx` (`common_id`),
  CONSTRAINT `lecture_tag_cid_fk` FOREIGN KEY (`common_id`) REFERENCES `lecture_common` (`common_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `lecture_like` (
  `like_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  `like_flag` varchar(1) DEFAULT 'Y',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`like_id`),
  KEY `lecture_like_uid_fk_idx` (`user_id`),
  KEY `lecture_like_lecture_id_fk_idx` (`lecture_id`),
  CONSTRAINT `lecture_like_lecture_id_fk` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`lecture_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lecture_like_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



# notice 테이블 삽입
CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `notice_type` int(11) NOT NULL COMMENT '1 : 공지사항\\\\n2 : 내 게시글에 대한 댓글\\\\n3 : 내 강의에 들어온 요청\\\\n4 : 내 요청에 대한 응답',
  `user_id` int(11) NOT NULL COMMENT '알림을 보낼 user의 id',
  `notice_yn` varchar(1) DEFAULT 'N' COMMENT 'N : 알림 해줘야해\\nY : 알림 완료',
  `read_yn` varchar(1) DEFAULT 'N',
  `board_id` int(11) DEFAULT NULL COMMENT '댓글이나 공지사항이면, 그 게시글의 id\n아니면 0?',
  `board_reply_id` int(11) DEFAULT NULL COMMENT '내 게시글에 달린 댓글의 id',
  `sub_his_id` int(11) DEFAULT NULL COMMENT 'lecture_sub_history',
  `lecture_id` int(11) DEFAULT NULL COMMENT 'lecture_sub_history',
  `sub_id` int(11) DEFAULT NULL COMMENT 'lecture_sub_history',
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='about notice';

