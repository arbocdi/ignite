DROP TABLE IF EXISTS POSTS;
CREATE TABLE POSTS (
id  VARCHAR(150) NOT NULL PRIMARY KEY,
title VARCHAR(255),
description TEXT,
creationDate DATE,
author VARCHAR(150)
);