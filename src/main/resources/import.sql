# noinspection SqlNoDataSourceInspectionForFile
INSERT INTO USERS (ID, NAME, PASSWORD, Email) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','admin@localhost');
INSERT INTO USERS (ID, NAME, PASSWORD, Email) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','user@localhost');
INSERT INTO USERS (ID, NAME, PASSWORD, Email) VALUES (3, 'user2', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','user2@localhost');

INSERT INTO AUTHORITY (NAME) VALUES ('ROLE_STUDENT');
INSERT INTO AUTHORITY (NAME) VALUES ('ROLE_TEACHER');
INSERT INTO AUTHORITY (NAME) VALUES ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (2, 'ROLE_STUDENT');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_ADMIN');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (3, 'ROLE_TEACHER');

INSERT INTO SKILL (ID,NAME,DESCRIPTION,ISDELETED,verificationstatus) VALUES (1,'JAVA','Programming Language',0,1);
INSERT INTO SKILL (ID,NAME,DESCRIPTION,ISDELETED,verificationstatus) VALUES (2,'PYTHON','Programming Language',0,1);
INSERT INTO SKILL (ID,NAME,DESCRIPTION,ISDELETED,verificationstatus) VALUES (3,'C++','Programming Language',0,1);

INSERT INTO USER_SKILL (USER_ID,SKILL_ID) VALUES (2,1);
INSERT INTO USER_SKILL (USER_ID,SKILL_ID) VALUES (2,3);
INSERT INTO USER_SKILL (USER_ID,SKILL_ID) VALUES (3,2);