CREATE DATABASE IF NOT EXISTS adserver;

CREATE TABLE IF NOT EXISTS adserver.agencies(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS adserver.users(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `email_address` VARCHAR(255) NOT NULL,
    `user_id` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` INT NOT NULL,
    `agency_id` BIGINT(20) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(agency_id) REFERENCES adserver.agencies(id)
);

CREATE TABLE IF NOT EXISTS adserver.advertisers(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `domain` VARCHAR(255) NOT NULL,
    `agency_id` BIGINT(20) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(agency_id) REFERENCES adserver.agencies(id)
);

CREATE TABLE IF NOT EXISTS adserver.campaigns(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `delivery_status` INT NOT NULL,
    `monthly_budget_limit` INT,
    `daily_budget_upper_limit` INT,
    `charge` INT,
    `advertiser_id` BIGINT(20) NOT NULL,
    `delivery_start_at` TIMESTAMP NULL DEFAULT NULL,
    `delivery_end_at` TIMESTAMP NULL DEFAULT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(advertiser_id) REFERENCES adserver.advertisers(id)
);

CREATE TABLE IF NOT EXISTS adserver.ad_groups(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `delivery_status` INT NOT NULL,
    `monthly_budget_limit` INT,
    `daily_budget_upper_limit` INT,
    `charge` INT,
    `campaign_id` BIGINT(20) NOT NULL,
    `delivery_start_at` TIMESTAMP NULL DEFAULT NULL,
    `delivery_end_at` TIMESTAMP NULL DEFAULT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(campaign_id) REFERENCES adserver.campaigns(id)
);

CREATE TABLE IF NOT EXISTS adserver.segments(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `ad_group_id` BIGINT(20) NOT NULL UNIQUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(ad_group_id) REFERENCES adserver.ad_groups(id)
);

CREATE TABLE IF NOT EXISTS adserver.devices(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `segment_id` BIGINT(20) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(segment_id) REFERENCES adserver.segments(id)
);

CREATE TABLE IF NOT EXISTS adserver.ads(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `landing_page_url` TEXT,
    `delivery_switch` BOOLEAN DEFAULT false,
    `ad_group_id` BIGINT(20) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(ad_group_id) REFERENCES adserver.ad_groups(id)
);

CREATE TABLE IF NOT EXISTS adserver.creatives(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `url` TEXT NOT NULL,
    `ad_id` BIGINT(20) NOT NULL UNIQUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(ad_id) REFERENCES adserver.ads(id)
);

CREATE TABLE IF NOT EXISTS adserver.creative_types(
    `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    `width` INT NOT NULL,
    `height` INT NOT NULL,
    `file_size` INT NOT NULL,
    `mime` VARCHAR(255),
    `creative_id` BIGINT(20) NOT NULL UNIQUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY(creative_id) REFERENCES adserver.creatives(id)
);

