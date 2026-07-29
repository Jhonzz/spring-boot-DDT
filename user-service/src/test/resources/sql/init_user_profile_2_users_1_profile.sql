insert into devdojo_user (id, email, first_name, last_name, roles, password) values (1,'gon.freecss@HXH.com','Gon','Freecss','USER','{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS');
insert into devdojo_user (id, email, first_name, last_name, roles, password) values (2,'Hisoka.Morow@HXH.com','Hisoka','Morow','USER','{bcrypt}$2a$10$GuB62zrrf2dwNbd8UeERsu04s4xdQgiEs56ZurI3BU.28Z.eb3YoS');
insert into profile (id, name, description) values (1, 'admin','admins everything');
insert into user_profile (id, profile_id, user_id) values (1, 1, 1);
insert into user_profile (id, profile_id, user_id) values (2, 1, 2);