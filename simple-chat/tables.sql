CREATE SCHEMA `simple_chat` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

use simple_chat;

CREATE TABLE IF NOT EXISTS `users` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(128) COLLATE utf8mb4_unicode_520_ci NOT NULL,
    `display_name` varchar(250) COLLATE utf8mb4_unicode_520_ci,
    `password` varchar(255) COLLATE utf8mb4_unicode_520_ci NOT NULL,
    `email` varchar(120) COLLATE utf8mb4_unicode_520_ci,
    `status` varchar(25) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT 'INACTIVATED',
    `created_at` datetime NOT NULL DEFAULT '2021-01-01 00:00:00',
    `updated_at` datetime NOT NULL DEFAULT '2021-01-01 00:00:00',
    PRIMARY KEY (`id`),
    UNIQUE KEY `key_username` (`username`),
    UNIQUE KEY `key_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

CREATE TABLE IF NOT EXISTS `user_access_tokens` (
    `id` varchar(256) NOT NULL,
    `user_id` bigint NOT NULL,
    `created_at` datetime NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `index_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

CREATE TABLE IF NOT EXISTS `messages` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `from_user_id` bigint unsigned NOT NULL,
    `to_channel_id` bigint unsigned NOT NULL DEFAULT 0,
    `message` varchar(1200) COLLATE utf8mb4_unicode_520_ci NOT NULL,
    `created_at` datetime NOT NULL DEFAULT '2021-01-01 00:00:00',
    `updated_at` datetime NOT NULL DEFAULT '2021-01-01 00:00:00',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;


CREATE TABLE IF NOT EXISTS `channels` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `display_name` bigint unsigned NOT NULL,
    `created_by_user_id` bigint unsigned NOT NULL DEFAULT 0,
    `created_at` datetime NOT NULL DEFAULT '2021-01-01 00:00:00',
    `updated_at` datetime NOT NULL DEFAULT '2021-01-01 00:00:00',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
