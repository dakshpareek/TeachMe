INSERT INTO USERS (ID, NAME, PASSWORD, Email,enabled) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','admin@localhost',true);
INSERT INTO USERS (ID, NAME, PASSWORD, Email,enabled) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','user@localhost',true);
INSERT INTO USERS (ID, NAME, PASSWORD, Email,enabled) VALUES (3, 'user2', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','user2@localhost',true);

INSERT INTO AUTHORITY (NAME) VALUES ('ROLE_STUDENT');
INSERT INTO AUTHORITY (NAME) VALUES ('ROLE_TEACHER');
INSERT INTO AUTHORITY (NAME) VALUES ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (2, 'ROLE_STUDENT');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_ADMIN');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) VALUES (3, 'ROLE_TEACHER');

INSERT INTO SKILL (ID,NAME,DESCRIPTION,ISDELETED,verificationstatus) VALUES (1,'JAVA','Programming Language',false,true);
INSERT INTO SKILL (ID,NAME,DESCRIPTION,ISDELETED,verificationstatus) VALUES (2,'PYTHON','Programming Language',false,true);
INSERT INTO SKILL (ID,NAME,DESCRIPTION,ISDELETED,verificationstatus) VALUES (3,'C++','Programming Language',false,true);

INSERT INTO USER_SKILL (USER_ID,SKILL_ID) VALUES (2,1);
INSERT INTO USER_SKILL (USER_ID,SKILL_ID) VALUES (2,3);
INSERT INTO USER_SKILL (USER_ID,SKILL_ID) VALUES (3,2);


INSERT INTO EXPERIENCE (ID,NAME,DESCRIPTION,DAO_USER_ID) VALUES (1,'GOOGLE','WORKED AT GOOGLE PAY',2);
INSERT INTO EXPERIENCE (ID,NAME,DESCRIPTION,DAO_USER_ID) VALUES (2,'FACEBOOK','WORKED AT MESSENGER',2);
INSERT INTO EXPERIENCE (ID,NAME,DESCRIPTION,DAO_USER_ID) VALUES (3,'AMAZON','WORKED AT ALEXA',3);



INSERT INTO USER_EXPERIENCE (USER_ID,EXPERIENCE_ID) VALUES (2,1);
INSERT INTO USER_EXPERIENCE (USER_ID,EXPERIENCE_ID) VALUES (2,3);
INSERT INTO USER_EXPERIENCE (USER_ID,EXPERIENCE_ID) VALUES (3,2);


INSERT INTO EXPERIENCE_SKILL (EXPERIENCE_ID,SKILL_ID) VALUES (1,1);
INSERT INTO EXPERIENCE_SKILL (EXPERIENCE_ID,SKILL_ID) VALUES (1,3);
INSERT INTO EXPERIENCE_SKILL (EXPERIENCE_ID,SKILL_ID) VALUES (2,3);
INSERT INTO EXPERIENCE_SKILL (EXPERIENCE_ID,SKILL_ID) VALUES (3,2);

INSERT INTO COURSES (ID,TITLE,DESCRIPTION,OFFERED_PRICE,COURSE_DURATION,USER_ID,IS_PUBLIC) VALUES (1,'Learn Spring','ADV. JAVA',99.99,25,2,true);
INSERT INTO COURSES (ID,TITLE,DESCRIPTION,OFFERED_PRICE,COURSE_DURATION,USER_ID,IS_PUBLIC) VALUES (2,'Learn PROGRAMMING','ADV. AWS',49.99,5,3,true);
INSERT INTO COURSES (ID,TITLE,DESCRIPTION,OFFERED_PRICE,COURSE_DURATION,USER_ID,IS_PUBLIC) VALUES (3,'Learn C++','ADV CPP',59.99,25,3,true);

INSERT INTO COURSE_SKILL(COURSE_ID,SKILL_ID) VALUES (1,1);
INSERT INTO COURSE_SKILL(COURSE_ID,SKILL_ID) VALUES (1,2);
INSERT INTO COURSE_SKILL(COURSE_ID,SKILL_ID) VALUES (2,3);
INSERT INTO COURSE_SKILL(COURSE_ID,SKILL_ID) VALUES (3,3);

INSERT INTO COURSE_RESPONSES(ID,MESSAGE,proposed_Price,status,USER_ID,course) VALUES (101,'Please Teach Me This',40.99,0,2,2);
INSERT INTO COURSE_RESPONSES(ID,MESSAGE,proposed_Price,status,USER_ID,course) VALUES (102,'Teach Me This',40.99,0,2,3);

INSERT INTO COURSE_RESPONSES(ID,MESSAGE,proposed_Price,status,USER_ID,course) VALUES (103,'Help Me With This Course',10.99,1,3,1);


INSERT INTO COURSE_CONTRACT(id,is_accepted,is_completed,is_hourly_pricing,price,total_time_in_mins,course_id,student_id,teacher_id) VALUES (401,true,false,true,55,0,1,3,2);

INSERT INTO CONTRACT_LOGS(ID,log_Message,created_Date,end_Date,lecture_Duration,is_verified,update_requested,course_contract) VALUES (501,'He taught me basics today','2020-02-13 01:30:00','2020-02-13 03:30:00',120,false,false,401);

INSERT INTO course_contract_contract_logs_set(course_contract_id,contract_logs_set_id) VALUES (401,501);






INSERT INTO REQUEST(id,title,description,offered_price,is_Hourly_Price,user_id,is_closed,is_public) VALUES (1001,'Help Me','Spring Boot',51,true,2,false,true);

INSERT INTO REQUEST_SKILL(REQUEST_ID,SKILL_ID) VALUES (1001,1);

INSERT INTO REQUEST_RESPONSE(id,is_accepted,is_hourly_price,message,proposed_price,request_id,user_id) VALUES (201,true,true,'I can help you out with spring',51,1001,3);

INSERT INTO REQUEST_CONTRACT(id,is_accepted,is_completed,is_hourly_pricing,price,total_time_in_mins,request_id,student_id,teacher_id) VALUES (301,true,false,true,51,0,1001,2,3);

INSERT INTO CONTRACT_LOGS(ID,log_Message,created_Date,end_Date,lecture_Duration,is_verified,update_requested,request_contract) VALUES (1,'This Log','2020-02-12 02:30:00','2020-02-12 03:20:00',50,false,0,301);

INSERT INTO request_contract_contract_logs_set(request_contract_id,contract_logs_set_id) VALUES (301,1);


