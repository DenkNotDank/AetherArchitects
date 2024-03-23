
CREATE TABLE  CONTENT (
    contentId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    contentBody TEXT
);
INSERT INTO CONTENT (contentBody) values ('Default text');

create table SEC_USER(
                        userId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         email VARCHAR(50) NOT NULL UNIQUE,
                         firstName VARCHAR(35) NOT NULL,
                         lastName VARCHAR(35) NOT NULL,
                         phone BIGINT NOT NULL,
                        secondaryEmail VARCHAR(50),
                        province VARCHAR(25) NOT NULL,
                        city VARCHAR (25) NOT NULL,
                        postalCode VARCHAR(25),
                         encryptedPassword VARCHAR(128) NOT NULL,
                         accountEnabled BIT NOT NULL
);

create table SEC_ROLE(
                         roleId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         roleName VARCHAR(30) NOT NULL UNIQUE
);

create table USER_ROLE(
                          ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          userId BIGINT NOT NULL,
                          roleId BIGINT NOT NULL
);

alter table USER_ROLE
    add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
    add constraint USER_ROLE_FK1 foreign key (userId)
        references SEC_USER(userId);

alter table USER_ROLE
    add constraint USER_ROLE_FK2 foreign key (roleId)
        references SEC_ROLE(roleId);

insert into SEC_USER (email,firstName, lastName, phone, province,city,encryptedPassword, accountEnabled)
values ('admin@email.com','Default','Admin' ,'4161231234','Ontario','Toronto',  'password',1);


insert into sec_role(roleName) values ('ROLE_ADMIN');
insert into sec_role(roleName) values ('ROLE_USER');
insert into user_role(userId, roleId) values (1,1);


