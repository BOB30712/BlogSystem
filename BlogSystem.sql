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

select * from arttar;
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