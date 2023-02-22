create table article(
  aid   int    NOT NULL auto_increment,
  title   VARCHAR(50),
  createtime DATETIME,
  content VARCHAR(100),
  pid int,
  PRIMARY KEY (aid)
);
create table photo(
  pid   int    NOT NULL auto_increment,
  panme   VARCHAR(50),
  PRIMARY KEY (pid)
);
ALTER TABLE article
ADD FOREIGN KEY (pid) REFERENCES photo (pid);
create table target(
  tid   int    NOT NULL auto_increment,
  tname  VARCHAR(50),
  PRIMARY KEY (tid)
);
create table message(
  mid   int    NOT NULL auto_increment,
  mname   VARCHAR(10),
  createtime DATETIME,
  mcontent VARCHAR(100),
  aid int,
  PRIMARY KEY (mid),
  FOREIGN KEY (aid) REFERENCES article (aid)
);
create table article_target(
  aid   int,
  tid   int
);

select * from message;
INSERT INTO photo(panme) VALUES ('photo001');
INSERT INTO article(title,createtime,content,pid)  VALUES ('第一個標題',NOW(),'第一個內容',1);
INSERT INTO target (tname) VALUES ('生活');
INSERT INTO target (tname) VALUES ('音樂');
INSERT INTO target (tname) VALUES ('搖滾');
INSERT INTO arttar(aid,tid) VALUES (1,1);
INSERT INTO arttar(aid,tid) VALUES (2,2);

create table systemadmin(
aid int NOT NULL auto_increment,
adminaccount VARCHAR(10),
adminpassword VARCHAR(10),
adminname VARCHAR(5),
level VARCHAR(5),
regdate DATETIME,
lastlogin DATETIME,
PRIMARY KEY(aid)
);
alter table systemadmin add unique(adminaccount);
alter table systemadmin add unique(adminname);
INSERT INTO systemadmin(adminaccount,adminpassword,adminname,level,regdate)
VALUES ('admin0214','2023201401','admin','大總管',NOW());
INSERT INTO systemadmin(adminaccount,adminpassword,adminname,level,regdate)
VALUES ('admin2023','2023201401','ad01','大總管',NOW());

INSERT INTO systemadmin(adminaccount,adminpassword,adminname,level,regdate)
VALUES ('admin0216','2023021616','流星雲','版務',NOW());
INSERT INTO systemadmin(adminaccount,adminpassword,adminname,level,regdate)
VALUES ('admin1611','2023021616','解凍豬腳','版務',NOW());
INSERT INTO systemadmin(adminaccount,adminpassword,adminname,level,regdate)
VALUES ('noname0216','2023021616','小明','韭菜',NOW());
ALTER TABLE message
ADD column adminid int;
ALTER TABLE message
ADD foreign key (adminid) references systemadmin(aid);

INSERT INTO message(mname,createtime,mcontent,aid,adminid)
VALUES ('test',NOW(),'這是一個回覆測試',47,5);
ALTER TABLE message DROP FOREIGN KEY message_ibfk_2;
ALTER TABLE message
DROP column adminid;
select * from message;
ALTER TABLE message
ADD column replyid int;

INSERT INTO message(mname,createtime,mcontent,aid,replyid)
VALUES ('test',NOW(),'這是二個回覆測試',47,17);

#新增文章、留言trigger
#將相關紀錄載入資料表blog_trigger
#blog_trigger(id順序、tablename資料表名稱、action動作、time時間、content內容)
create table blog_trigger(
id int NOT NULL auto_increment,
tablename VARCHAR(15),
action VARCHAR(10),
time DATETIME,
content VARCHAR(20),
PRIMARY KEY(id)
);
select * from blog_trigger;
select * from article;
select * from message;
#新增文章trigger
DELIMITER $$
CREATE TRIGGER article_insert after insert
ON article FOR EACH ROW
BEGIN
  declare message varchar(20);
  SET message = NEW.title;
  INSERT INTO blog_trigger(tablename,action,time,content) 
  VALUES ('article','insert',now(),message);
END $$
DELIMITER ;
#新增留言trigger
DELIMITER $$
CREATE TRIGGER message_insert after insert
ON message FOR EACH ROW
BEGIN
  declare message varchar(20); 
  declare gettitle VARCHAR(50);
  CALL getArticle (43);
  SET message = CONCAT(NEW.mname,'在',NEW.aid,'進行留言');
  INSERT INTO blog_trigger(tablename,action,time,content) 
  VALUES ('message','insert',now(),message);
END $$
DELIMITER ;

select * from article;

#測試sproc
DELIMITER $$
CREATE PROCEDURE getArticle(`articleID` int)
BEGIN
	select title from article where aid = articleID;
END$$
DELIMITER ;

CALL getArticle (43);
