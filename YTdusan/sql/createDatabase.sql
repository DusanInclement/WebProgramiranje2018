DROP SCHEMA IF EXISTS YTdusan;
CREATE SCHEMA YTdusan DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE YTdusan;

CREATE TABLE users(
userName VARCHAR(15) NOT NULL,
password VARCHAR(15) NOT NULL,
firstName VARCHAR(15),
lastName VARCHAR(15),
email VARCHAR(30) NOT NULL,
channelDescription VARCHAR(50),
userType ENUM ('USER','ADMIN') NOT NULL DEFAULT 'USER',
registrationDate DATE NOT NULL,
blocked BOOLEAN NOT NULL DEFAULT FALSE,
deleted BOOLEAN NOT NULL DEFAULT FALSE,
profileUrl VARCHAR(300),
lol BOOLEAN NOT NULL DEFAULT FALSE,
PRIMARY KEY (userName)
);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('dusan','123','Dusan','Vukovic','dusan@gmail.com',null,'ADMIN','2018-6-1','https://i.pinimg.com/originals/58/f0/33/58f0335ef6f5545b78151b265ef25824.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('miki','123','Miroljub','Marjanovic','miki@gmail.com',null,'USER','2018-6-3','https://www.sideshowtoy.com/wp-content/uploads/2015/06/voldemort.jpg',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('pera','123','Petar','Djuric','pera@gmail.com',null,'USER','2018-6-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('nesa','123','Nenad','Djanis','nesa@gmail.com',null,'USER','2018-6-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);


CREATE TABLE subscribe(
mainUser VARCHAR(15),
subscriber VARCHAR(15),
FOREIGN KEY (mainUser) REFERENCES users(userName) ON DELETE RESTRICT,
FOREIGN KEY (subscriber) REFERENCES users(userName) ON DELETE RESTRICT
);

CREATE TABLE video(
id BIGINT AUTO_INCREMENT,
videoUrl VARCHAR(100) NOT NULL,
pictureUrl VARCHAR(200) NOT NULL,
videoName VARCHAR(80) NOT NULL,
description VARCHAR(500),
visibility ENUM ('PRIVATE','PUBLIC','UNLISTED') NOT NULL,
blocked BOOLEAN NOT NULL DEFAULT FALSE,
commentsEnabled BOOLEAN NOT NULL DEFAULT TRUE,
ratingEnabled BOOLEAN DEFAULT TRUE,
numberOfLikes BIGINT NOT NULL ,
numberOfDislikes BIGINT NOT NULL,
views BIGINT NOT NULL,
datePosted  DATE NOT NULL,
owner VARCHAR(10) NOT NULL,
deleted BOOLEAN NOT NULL DEFAULT FALSE,
PRIMARY KEY (id),
FOREIGN KEY (owner) REFERENCES users(userName) ON DELETE RESTRICT
);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/tAGnKpE4NCI','https://mozartheroes.com/en/wp-content/uploads/sites/2/2017/09/mozart-heroes-metallica-nothing-else-matters-unplugged.jpg','Metallica - Nothing Else Matters',
'Nothing Else Matters [Official Music Video] From the album "Metallica"','PUBLIC',false,true,true,132,1,200,'2018-6-6','dusan',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/XsEMu5UCy0g','https://i.ytimg.com/vi/0OiXPkvZK2Y/maxresdefault.jpg','Irish Stew of Sindidun - Take Me High',
'Irish Stew of Sindidun - Take Me High','PUBLIC',false,true,true,100,2,300,'2018-6-7','pera',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/wGugCsfT6po','https://orig00.deviantart.net/509f/f/2013/217/0/3/the_five_wizards_by_jjulie98-d6gqf7n.png','The Wizards of Middle-Earth',
'A grand thank you to the online artists who have created some of the pieces used in this video! ','PUBLIC',false,true,true,423543,11,123,'2018-6-7','dusan',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/NdPqPxL5rXQ','https://i.ytimg.com/vi/NdPqPxL5rXQ/hqdefault.jpg','Aragorn- Epic Character History ',
'Aragorn was a vital character in J.R.R. Tolkiens The Lord of the Rings who went from ranger to king.','PRIVATE',false,true,true,234,2,1234,'2018-7-7','nesa',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/ITqYqMNF4R8','http://fr.web.img3.acsta.net/videothumbnails/14/03/05/12/41/079559.jpg','Harry vs Voldemort Final Battle',
'Harry and Voldemort battle for the last time.. ','PUBLIC',false,true,true,111,1,1111,'2018-7-7','miki',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/2m04j8rRkHw','https://i.ytimg.com/vi/2m04j8rRkHw/maxresdefault.jpg','The Hobbit:The Battle Of The Five Armies',
'Extended Edition','UNLISTED',false,true,true,1234,1,1244,'2013-3-4','pera',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/H1_0qnSNBZ0','https://i.ytimg.com/vi/H1_0qnSNBZ0/maxresdefault.jpg','Sparta vs Persia Battle ',
'Sparta vs Persia Battle Hd Triler (Warner Bros)','PUBLIC',false,true,true,233,11,232131,'2018-9-9','nesa',false);



CREATE TABLE comment(
id BIGINT AUTO_INCREMENT,
text VARCHAR(500) NOT NULL,
owner VARCHAR(10) NOT NULL,
videoId BIGINT NOT NULL,
datePosted DATE NOT NULL,
likeNumber BIGINT NOT NULL,
dislikeNumber BIGINT NOT NULL,
deleted BOOLEAN NOT NULL DEFAULT FALSE,
PRIMARY KEY (id),
FOREIGN KEY (owner) REFERENCES users(userName) ON DELETE RESTRICT,
FOREIGN KEY (videoId) REFERENCES video(id) ON DELETE RESTRICT
);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Nice','dusan',1,'2018-8-6',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Heej','miki',1,'2018-8-8',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Klasika','nesa',1,'2018-8-7',5,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Nice','dusan',2,'2018-8-7',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Heej','miki',2,'2018-8-8',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Klasika','nesa',2,'2018-8-9',5,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Nice','dusan',3,'2018-8-3',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Heej','miki',3,'2018-8-2',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Klasika','nesa',3,'2018-8-1',5,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Nice','dusan',4,'2018-8-3',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Heej','miki',4,'2018-8-4',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Klasika','nesa',4,'2018-8-5',5,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Nice','dusan',5,'2018-8-8',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Heej','miki',5,'2018-8-8',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Klasika','nesa',5,'2018-8-8',5,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Nice','dusan',6,'2018-8-8',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Heej','miki',6,'2018-8-8',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Klasika','nesa',6,'2018-8-8',5,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Nice','dusan',7,'2018-8-8',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Heej','miki',7,'2018-8-8',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Klasika','nesa',7,'2018-8-8',5,2,false);



CREATE TABLE likedislike(
id BIGINT AUTO_INCREMENT,
liked BOOLEAN NOT NULL,
likeDate DATE NOT NULL,
owner VARCHAR(10),
deleted BOOLEAN,
PRIMARY KEY (id),
FOREIGN KEY (owner) REFERENCES users(userName) ON DELETE RESTRICT
);

---------------------------------------------
CREATE TABLE likedislikevideo(
likeId BIGINT,
videoId BIGINT,
deleted BOOLEAN,
FOREIGN KEY (likeId) REFERENCES likedislike (id) ON DELETE RESTRICT,
FOREIGN KEY (videoId) REFERENCES video(id) ON DELETE RESTRICT
);


CREATE TABLE likedislikecomment(
likeId BIGINT,
commentId BIGINT,
deleted BOOLEAN,
FOREIGN KEY (likeId) REFERENCES likedislike (id) ON DELETE RESTRICT,
FOREIGN KEY (commentId) REFERENCES comment (id) ON DELETE RESTRICT
);
