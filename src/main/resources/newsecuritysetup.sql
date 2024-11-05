
CREATE TABLE  CONTENT (
                          contentId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          contentBody TEXT,
                          pageHidden BIT NOT NULL DEFAULT 0
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


insert into sec_role(roleName) values ('ROLE_ADMIN');
insert into sec_role(roleName) values ('ROLE_USER');

create table USER_MEMBERSHIPS(
        userID BIGINT NOT NULL PRIMARY KEY,
        membershipID INT DEFAULT 0,
        paid BOOLEAN DEFAULT FALSE,
        paidDate DATE DEFAULT NULL
);

alter table USER_MEMBERSHIPS
    add constraint USER_MEMBERSHIPS_FK1 foreign key (userID)
        references SEC_USER(userId);



-- password is 3xT6E4;x`AKj



insert into SEC_USER (email,firstName, lastName, phone, province,city,encryptedPassword, accountEnabled) values ('admin@email.com','Default','Admin' ,'4161231234','Ontario','Toronto',  '$2a$10$DnNxZ0MNTMgs/m7QUWZu0u1jULd2Ltsl/tlUzONoUG7mxSZ..lvN2',1);
insert into SEC_USER (email,firstName, lastName, phone, province,city,encryptedPassword, accountEnabled)values ('user@email.com','Default','User' ,'4161231234','Ontario','Toronto',  '$2a$10$DnNxZ0MNTMgs/m7QUWZu0u1jULd2Ltsl/tlUzONoUG7mxSZ..lvN2',1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('rabelwhite0@devhub.com', 'Reece', 'Abelwhite', '3056561781',  'Florida', 'Miami', '90b695ff3cb781eb53cbbe1f774a241fe4a0a3d8', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('sclixby1@indiegogo.com', 'Selig', 'Clixby', '2812242019', 'Texas', 'Houston', 'faa546069344f50806dee5259d0686956deb5f59', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('pcotterel2@fda.gov', 'Porter', 'Cotterel', '6502784025', 'California', 'Sunnyvale', 'a41b0b99ef74010a03a45681304de2f9a7c1e607', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('cbettlestone3@hatena.ne.jp', 'Cyrill', 'Bettlestone', '5718960149', 'Virginia', 'Arlington', '448af77165e0701e552aeb91fe2cbc9ba0b22206', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('dbernadzki4@wix.com', 'Danella', 'Bernadzki', '8647721043', 'South Carolina', 'Greenville', '69da1123f93cd780ac4562f82df661084420a7ad', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('zduckitt5@wordpress.com', 'Zelig', 'Duckitt', '5093675928', 'Washington', 'Spokane', '47b832dee10fae0b13eb7bd8dd5688996db31e7f', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('hpennycord6@newyorker.com', 'Hubie', 'Pennycord', '8156062773', 'Illinois', 'Rockford', '6e5d4b5a158ec113681651c69ec4653671f67d5e', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mjermin7@nature.com', 'Maisey', 'Jermin', '5155053970', 'Iowa', 'Des Moines', '70f97ff29f2657fc6871e97ad9e30c9c266a7d61', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('cdanielis8@unesco.org', 'Corenda', 'Danielis', '5034636815', 'Oregon', 'Portland', '076661871f9e4d74a563169e51b5446f9e5ca103', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('meakley9@mozilla.org', 'Maressa', 'Eakley', '9043443763', 'Florida', 'Jacksonville', '9f743a49a743021ca7f6f3901e1187b79a230a5c', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('ipharea@hugedomains.com', 'Idaline', 'Phare', '2028885236', 'District of Columbia', 'Washington', 'f3332b2f07bcde6bf92daa430ff886a53d595392', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('rmusprattb@accuweather.com', 'Rosemarie', 'Muspratt', '8123117107', 'Indiana', 'Evansville', '7b7b537c8b9ff05cc1893a5ad67bfc21e9d413d9', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bmcnaec@guardian.co.uk', 'Batsheva', 'McNae', '8067327623', 'Texas', 'Amarillo', 'df8595a87423ed5db9b1a0882bf617a34dbb8bf2', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jbrownd@google.com', 'Jaine', 'Brown', '2029499355', 'Virginia', 'Alexandria', '691f47dc10bfd9fd56bb0330116aa22f0fe3505e', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('kdoldone@yahoo.co.jp', 'Kalinda', 'Doldon', '2058020044', 'Alabama', 'Tuscaloosa', '6feffdced8c7ba3ae01495fc10b7cc667721a520', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bsqueersf@wired.com', 'Bevvy', 'Squeers', '8329005170', 'Texas', 'Houston', '025d9f4af40f2a3aee92690d7b59687ddb0b8b3c', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('cmassenhoveg@yelp.com', 'Cherlyn', 'Massenhove', '2025831098', 'District of Columbia', 'Washington', '12167168504ab0c45bd7f8130cd10026619ad970', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bcelloh@sciencedaily.com', 'Bax', 'Cello', '2693867579', 'Michigan', 'Battle Creek', '65ea3a2570b28ad959c60b5fea0e9b97c66c8da7', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jparemani@photobucket.com', 'Jessika', 'Pareman', '7145286884', 'California', 'Anaheim', 'c0aec2878147a53f996067123bbbbcbf92c4fde5', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('tgarrochj@instagram.com', 'Thomasin', 'Garroch', '4028781298', 'Nebraska', 'Omaha', '7545ec7b5b3565ae4d06b1762cb93c5626fdc8e5', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('acluleek@reddit.com', 'Audre', 'Clulee', '4158887438', 'California', 'San Francisco', 'e0f2c8fb5aed11c2b2b8557e8dee34a3e2c6c9d2', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('zpefferl@addthis.com', 'Zuzana', 'Peffer', '3038364241', 'Colorado', 'Denver', '1863097f2aee28c4b4fb01007d81ce9013fe1a4e', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('theavysidem@tripod.com', 'Tessy', 'Heavyside', '4324457920', 'Texas', 'Midland', '3efc8a360dbea0155a56735749f2ca045e75d5ff', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bkisarn@deviantart.com', 'Bea', 'Kisar', '5715009227', 'Virginia', 'Arlington', 'fa626a4b5b7cf0e4076333e2b77dfc24e39a6f06', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('gandrellio@fda.gov', 'Garrik', 'Andrelli', '8142509456', 'Pennsylvania', 'Erie', '0770fd2b1d5583fe1466d76d0cc84117d4c3554d', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('kmethvenp@samsung.com', 'Kassey', 'Methven', '7198312466', 'Colorado', 'Colorado Springs', '9bfbe4ccb1ef6f536b830f3dee7dc8afb53bce96', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('gwilseq@overblog.com', 'Gard', 'Wilse', '2025960134', 'District of Columbia', 'Washington', '991c3227e52d2a1f3ba3a87921593fd6df2a7a4a', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('lhenkenr@nsw.gov.au', 'Lazare', 'Henken', '7573438908', 'Virginia', 'Norfolk', '0252d5eba18dd0e4911df14f0ecee4c44b467760', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('gpirazzis@cocolognifty.com', 'Gilligan', 'Pirazzi', '9198148593', 'North Carolina', 'Durham', '249d8f4dad1295d3ece1a86410b7fd363122c773', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('kovernellt@dailymail.co.uk', 'Karlyn', 'Overnell', '5027949609', 'Kentucky', 'Louisville', '40926e2735aff089ed5bcf9ce190611dd5ab2aed', 1);
insert into SEC_USER (email, firstName, lastName, phone, secondaryEmail, province, city, encryptedPassword, accountEnabled) values ('sdubberu@hc360.com', 'Sonnie', 'Dubber', '5044433831', 'Josh@cisco.com',  'Louisiana', 'New Orleans', 'ec31fd40d2227f75f507e1845ca9afcafdbf3d25', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('arosettiniv@wordpress.org', 'Aggie', 'Rosettini', '7131795262', 'Texas', 'Houston', '621bc478cb363219c8908419c53f3340eed6d3dd', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('wolahyw@barnesandnoble.com', 'Windham', 'O''Lahy', '3347405823', 'Alabama', 'Montgomery', '09b7cc4d1e4635406bceaee160e7c0725584f9b3', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('lroofx@stumbleupon.com', 'Louisa', 'Roof', '7042731876', 'North Carolina', 'Charlotte', 'd7f8238578e749292524268c5cec77bf6e2c1cd0', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('pgraylandy@ted.com', 'Pia', 'Grayland', '3187117597', 'Louisiana', 'Shreveport', '51f0303baa4270d0170568a95b6f54b64b5f172e', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jgettinsz@ocn.ne.jp', 'Joanne', 'Gettins', '9417190972', 'Florida', 'Punta Gorda', '51452aab2d987b44cea4aca081bccd7d9292d6d0', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('santusch10@gnu.org', 'Sonia', 'Antusch', '4024346774', 'Nebraska', 'Lincoln', 'd7f59f279a7d6e7cc9871284b5c7235d5280c8ab', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('vmoorton11@smh.com.au', 'Vivianne', 'Moorton', '5043839479', 'Louisiana', 'New Orleans', 'c8b7cd6d6928bdce14adada0f9d437ae807a6cf8', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('sbeedham12@forbes.com', 'Sibella', 'Beedham', '2064271138', 'Washington', 'Seattle', 'baaf13af5fce34e291cad70894e7a3dda182fad3', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('sbruni13@google.com.hk', 'Steffi', 'Bruni', '2031426039', 'Connecticut', 'New Haven', 'efb7ac6e87f2d766cc9847b19597d4b1d3d50d9e', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('aoglesbee14@toplist.cz', 'Aurie', 'Oglesbee', '8313371360', 'California', 'Santa Cruz', 'aa2ef8fd01624d181dd9eb42bab9ef18c9bde7cd', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('ghucknall15@squidoo.com', 'Gabbi', 'Hucknall', '2629238253', 'Wisconsin', 'Racine', '6ecae283db64682d0eef96e55db4d5e7e8ee272e', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('cjezard16@globo.com', 'Carlye', 'Jezard', '8156857530', 'Illinois', 'Joliet', 'da0619034242b88ea729db3073a500657edfdb5c', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('amangeney17@narod.ru', 'Adaline', 'Mangeney', '9528875897', 'Minnesota', 'Maple Plain', '3e16df4f621eae20ca09a5e7d10bf37a0e1c44a6', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jsegoe18@diigo.com', 'Jessika', 'Segoe', '6148278730', 'Ohio', 'Columbus', '21933bdf4075b1736fad816ce1257e28b263d0f6', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mmegany19@yale.edu', 'Marnia', 'Megany', '2535088597', 'Washington', 'Tacoma', '76474481d6047dd1c1f550bb062c42b4e7a7691a', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('fwaterdrinker1a@wsj.com', 'Faber', 'Waterdrinker', '4154393233', 'California', 'San Francisco', '04d481f6d44e3c286cf2fd389a708a7b3f8575b1', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('nstolworthy1b@umn.edu', 'Niall', 'Stolworthy', '7196462411', 'Colorado', 'Colorado Springs', '67b0d2eaa5d1b75dc8f1d3a1b2d8fb3583985daa', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('lantoinet1c@scribd.com', 'Lucia', 'Antoinet', '8325403498', 'Texas', 'Houston', '90f4b9f02172824657d48fb8eaf1a077622b4471', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('chaysham1d@illinois.edu', 'Charla', 'Haysham', '7166947745', 'New York', 'Buffalo', 'c2233d517ee14c58341296bef529f6d1f36006fb', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bhankinson1e@msu.edu', 'Bethina', 'Hankinson', '2151353565', 'Pennsylvania', 'Philadelphia', 'a4cccbca452d54440c80fc3e49e6768b87e9f63a', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('sraper1f@paypal.com', 'Shawn', 'Raper', '7049727129', 'North Carolina', 'Charlotte', '221663e80154276677ce0b167d3fa4e59b282fd0', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mpidcock1g@simplemachines.org', 'Marice', 'Pidcock', '9126301121', 'Georgia', 'Savannah', '6fdd204f7a98e619f48c5b8c9cc47b60e14a4116', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mtylor1h@sakura.ne.jp', 'Margareta', 'Tylor', '9154883443', 'Texas', 'El Paso', '9de96700d4f980ff5c6bac03e08b1c3833b46dbe', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('djeffes1i@sun.com', 'Dory', 'Jeffes', '8327773010', 'Texas', 'Houston', '52886fee474d7f23f940653f9298ed10583b8d5c', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('rrenshall1j@xrea.com', 'Randy', 'Renshall', '7544993362', 'Florida', 'Fort Lauderdale', '11f0878cf5b0a2315de7e9947d1aa47456e9bbb7', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('dmaffulli1k@acquirethisname.com', 'Donni', 'Maffulli', '4234324226', 'Tennessee', 'Chattanooga', '348ecafa288790cf9c0332924518b543178d560d', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('fsames1l@hatena.ne.jp', 'Ferdie', 'Sames', '4045322573', 'Georgia', 'Atlanta', '45c1e5c8dbd32f0fac238e6738e0c36b1690a26d', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('hcalifornia1m@hp.com', 'Hollis', 'California', '9378293594', 'Ohio', 'Dayton', '618c73317f53fe9d7275af9e8a1b1ad6ffe34532', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jiacovo1n@pen.io', 'Jacquie', 'Iacovo', '7869309954', 'Florida', 'Miami', '3df13ea38ea98687939440d41d64dc62344b7369', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('ftowler1o@nytimes.com', 'Filide', 'Towler', '7867440882', 'Florida', 'Miami', '95a57bec489b9f4a485a57387939b065a05bafcd', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('creedman1p@jimdo.com', 'Chase', 'Reedman', '4154068691', 'California', 'San Francisco', '3b2684eacd2eb8221d1a8b92e51448b5e67cfa37', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('wbarwack1q@moonfruit.com', 'Waylen', 'Barwack', '3135949936', 'Michigan', 'Detroit', 'f611ad70b23e78e8bd784753f2cbb40bf18be7d3', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('ibye1r@geocities.jp', 'Irina', 'Bye', '6518568774', 'Minnesota', 'Saint Paul', 'ea9c39fa8b63905bc3924c14b848a941f986e861', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mdixey1s@engadget.com', 'Mair', 'Dixey', '3609459157', 'Washington', 'Vancouver', '440b08c0dd87333b8b83677c2fa837896c1625b4', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jsandeson1t@seesaa.net', 'Jody', 'Sandeson', '3349294439', 'Alabama', 'Montgomery', '398303e1e3879d5e8abb8728e2266bd971f7c305', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('wcarlick1u@google.com', 'Waylon', 'Carlick', '3149068616', 'Missouri', 'Saint Louis', '31625eebcd370f3542526c9a249cf3d95c7c8628', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jtunno1v@nbcnews.com', 'Jamaal', 'Tunno', '7025603299', 'Nevada', 'Las Vegas', '76490e05aa0c06f565f9968213b9ef0025d215af', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('rbirdsall1w@seesaa.net', 'Robbie', 'Birdsall', '6088163260', 'Wisconsin', 'Madison', '1b224be7b1b97d7a5e5f8c6bbd4c16b2c8932188', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('trackstraw1x@sina.com.cn', 'Terrance', 'Rackstraw', '5628980269', 'California', 'Long Beach', 'c0b519a8e3e6ea4e5fc96f0a05e231945b046bc4', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('ftugman1y@guardian.co.uk', 'Friedrick', 'Tugman', '4342084100', 'Virginia', 'Lynchburg', '97cc4ad57a616678a906b684e1df95f2c8f9e25d', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mducarne1z@is.gd', 'Michal', 'Ducarne', '7708347244', 'Georgia', 'Duluth', '29ac8d6af94690bf99c834961d8e378b0850adf7', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('dguilleton20@blog.com', 'Deedee', 'Guilleton', '3053288011', 'Florida', 'Miami', '303f74f77f71e0ef2a9f16b9621efcf5872512b1', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('elepoidevin21@tinypic.com', 'Estele', 'Lepoidevin', '9376445434', 'Ohio', 'Dayton', '28ab6cf65e3032aa1f58710e0f4212f7448b67ef', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mbowes22@irs.gov', 'Marleah', 'Bowes', '8431665423', 'South Carolina', 'Florence', '6bb0a4a54fd130fb0198af839a8aa856a6d03427', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bdignon23@behance.net', 'Brnaba', 'Dignon', '2028407207', 'District of Columbia', 'Washington', '7ea5f810ac6fa73ad8ebb61062e112cf5fbc1673', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('pcorrison24@google.com.br', 'Pepita', 'Corrison', '6123014735', 'Minnesota', 'Minneapolis', '957c93bbe4fe2cd97939ee3cb6c359a8e1cffee4', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mmckirdy25@nyu.edu', 'Massimo', 'McKirdy', '2027943397', 'District of Columbia', 'Washington', 'b1b657e068591fc23385f0f4583c2e6f4ddfb946', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('psotworth26@typepad.com', 'Penelope', 'Sotworth', '8591182494', 'Kentucky', 'Lexington', 'cbcff983039795f47d2bf4882bc8a34719bb69b1', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('hpatridge27@usgs.gov', 'Hildagarde', 'Patridge', '6052414361', 'South Dakota', 'Sioux Falls', '7cac28935bcf46e4d9c7a3930e62b51b2730aec4', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('kgoode28@weebly.com', 'Karlis', 'Goode', '9405437762', 'Texas', 'Wichita Falls', 'eb0dafa945e7831c2cc44627b942ec41efa61de3', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('ssimenet29@techcrunch.com', 'Stace', 'Simenet', '3131304158', 'Michigan', 'Southfield', '8f27a560eec3e1dea45ca6f64a010b5350147c29', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('qbolzmann2a@rambler.ru', 'Quinn', 'Bolzmann', '3047474456', 'West Virginia', 'Morgantown', '67cbdfea07f4f4e514547753e13e20bffff1ba18', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bplaydon2b@i2i.jp', 'Brad', 'Playdon', '4342570735', 'Virginia', 'Charlottesville', 'a5dec0e6961f266be25decbb6e242ac165e81dbf', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('gjarvis2c@dailymail.co.uk', 'Gene', 'Jarvis', '3038834700', 'Colorado', 'Denver', '53104be73459b9666363a34b294c7bb9b0aa83b5', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('bbonas2d@mail.ru', 'Betsey', 'Bonas', '2133819261', 'California', 'Van Nuys', '5b3912eb498b28d63b82e68c1c1c5fc43ad89929', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('aohenery2e@pcworld.com', 'Adelheid', 'O''Henery', '6021308111', 'Arizona', 'Phoenix', '36c6d387b11edd1a298f1bd70227e501fc45bec4', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jhebbron2f@sohu.com', 'Jordon', 'Hebbron', '2022244663', 'District of Columbia', 'Washington', 'c663181876bc82b37a9ac51218f2ee436ffbb830', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('tgrumble2g@nationalgeographic.com', 'Tamra', 'Grumble', '2602671367', 'Indiana', 'Fort Wayne', 'cc028410077bee2f84a57b4feb6deccc26e8cb9d', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('nhosten2h@cyberchimps.com', 'Netty', 'Hosten', '7025773233', 'Nevada', 'Henderson', 'a32f80246d893d5b572b0b2a7ca36be6a579e2c8', 1);
insert into SEC_USER (email, firstName, lastName, phone, secondaryEmail, province,  city, encryptedPassword, accountEnabled) values ('loverbury2i@gnu.org', 'Lurline', 'Overbury', '4143120195', 'Test@gmail.com', 'Wisconsin', 'Milwaukee', '62513dba17f4a85b1f9093dcc18d92c44fa46b4c', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('mohare2j@washington.edu', 'Margaretta', 'O''Hare', '4143401383', 'Wisconsin', 'Milwaukee', '254bf58ae9fda48f5ddbf46a6c9a6c6b095b4817', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('jcribbott2k@irs.gov', 'Jan', 'Cribbott', '9521158400', 'Minnesota', 'Young America', 'a8198234358056a8109d18d2ba1af539e40b9c53', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('clamblot2l@google.es', 'Corrina', 'Lamblot', '9133408822', 'Kansas', 'Shawnee Mission', '4fb3a5e427c448fa503c50d835f165c662a9c3db', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('xalfonzo2m@infoseek.co.jp', 'Xenos', 'Alfonzo', '4193263900', 'Ohio', 'Toledo', 'df7aed0825919d6b9f6516878d3e52df20ecbadb', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('ctulleth2n@flavors.me', 'Craggie', 'Tulleth', '8125479500', 'Indiana', 'Terre Haute', 'e9e7b7269226aa6faa9dbd60ddbd24fbaedbffa3', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('eveschi2o@craigslist.org', 'Evin', 'Veschi', '3617658438', 'Texas', 'Austin', '68fa078c875f75fcd70a3b22d0c208a5aebb8590', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('gsandwich2p@prweb.com', 'Grant', 'Sandwich', '2021967475', 'District of Columbia', 'Washington', 'a4b29eaa96712c7b2235737620cf588aec4eda8e', 1);
insert into SEC_USER (email, firstName, lastName, phone, province, city, encryptedPassword, accountEnabled) values ('atenney2q@lycos.com', 'Annice', 'Tenney', '3184474406', 'Louisiana', 'Shreveport', '1036d409b92dd823f398fabf04ef07d1dfb90a81', 1);
insert into SEC_USER (email, firstName, lastName, phone, secondaryEmail, province, city, encryptedPassword, accountEnabled) values ('jdanter2r@msu.edu', 'Jillayne', 'Danter', '8143134139', 'achane@yahoo.com', 'Pennsylvania', 'Erie', '6d3dafb8cd9281a490e714ebfb5eb6dbe9e6401c', 1);

insert into user_role (userId, roleId) values (1,1); -- Default Admin
insert into user_role (userId, roleId) values (2,2); -- Default User
insert into user_role (userId, roleId) values (3, 2);
insert into user_role (userId, roleId) values (4, 2);
insert into user_role (userId, roleId) values (5, 2);
insert into user_role (userId, roleId) values (6, 2);
insert into user_role (userId, roleId) values (7, 2);
insert into user_role (userId, roleId) values (8, 2);
insert into user_role (userId, roleId) values (9, 2);
insert into user_role (userId, roleId) values (10, 2);
insert into user_role (userId, roleId) values (11, 2);
insert into user_role (userId, roleId) values (12, 2);
insert into user_role (userId, roleId) values (13, 2);
insert into user_role (userId, roleId) values (14, 2);
insert into user_role (userId, roleId) values (15, 2);
insert into user_role (userId, roleId) values (16, 2);
insert into user_role (userId, roleId) values (17, 2);
insert into user_role (userId, roleId) values (18, 2);
insert into user_role (userId, roleId) values (19, 2);
insert into user_role (userId, roleId) values (20, 2);
insert into user_role (userId, roleId) values (21, 2);
insert into user_role (userId, roleId) values (22, 2);
insert into user_role (userId, roleId) values (23, 2);
insert into user_role (userId, roleId) values (24, 2);
insert into user_role (userId, roleId) values (25, 2);
insert into user_role (userId, roleId) values (26, 2);
insert into user_role (userId, roleId) values (27, 2);
insert into user_role (userId, roleId) values (28, 2);
insert into user_role (userId, roleId) values (29, 2);
insert into user_role (userId, roleId) values (30, 2);
insert into user_role (userId, roleId) values (31, 2);
insert into user_role (userId, roleId) values (32, 2);
insert into user_role (userId, roleId) values (33, 2);
insert into user_role (userId, roleId) values (34, 2);
insert into user_role (userId, roleId) values (35, 2);
insert into user_role (userId, roleId) values (36, 2);
insert into user_role (userId, roleId) values (37, 2);
insert into user_role (userId, roleId) values (38, 2);
insert into user_role (userId, roleId) values (39, 2);
insert into user_role (userId, roleId) values (40, 2);
insert into user_role (userId, roleId) values (41, 2);
insert into user_role (userId, roleId) values (42, 2);
insert into user_role (userId, roleId) values (43, 2);
insert into user_role (userId, roleId) values (44, 2);
insert into user_role (userId, roleId) values (45, 2);
insert into user_role (userId, roleId) values (46, 2);
insert into user_role (userId, roleId) values (47, 2);
insert into user_role (userId, roleId) values (48, 2);
insert into user_role (userId, roleId) values (49, 2);
insert into user_role (userId, roleId) values (50, 2);
insert into user_role (userId, roleId) values (51, 2);
insert into user_role (userId, roleId) values (52, 2);
insert into user_role (userId, roleId) values (53, 2);
insert into user_role (userId, roleId) values (54, 2);
insert into user_role (userId, roleId) values (55, 2);
insert into user_role (userId, roleId) values (56, 2);
insert into user_role (userId, roleId) values (57, 2);
insert into user_role (userId, roleId) values (58, 2);
insert into user_role (userId, roleId) values (59, 2);
insert into user_role (userId, roleId) values (60, 2);
insert into user_role (userId, roleId) values (61, 2);
insert into user_role (userId, roleId) values (62, 2);
insert into user_role (userId, roleId) values (63, 2);
insert into user_role (userId, roleId) values (64, 2);
insert into user_role (userId, roleId) values (65, 2);
insert into user_role (userId, roleId) values (66, 2);
insert into user_role (userId, roleId) values (67, 2);
insert into user_role (userId, roleId) values (68, 2);
insert into user_role (userId, roleId) values (69, 2);
insert into user_role (userId, roleId) values (70, 2);
insert into user_role (userId, roleId) values (71, 2);
insert into user_role (userId, roleId) values (72, 2);
insert into user_role (userId, roleId) values (73, 2);
insert into user_role (userId, roleId) values (74, 2);
insert into user_role (userId, roleId) values (75, 2);
insert into user_role (userId, roleId) values (76, 2);
insert into user_role (userId, roleId) values (77, 2);
insert into user_role (userId, roleId) values (78, 2);
insert into user_role (userId, roleId) values (79, 2);
insert into user_role (userId, roleId) values (80, 2);
insert into user_role (userId, roleId) values (81, 2);
insert into user_role (userId, roleId) values (82, 2);
insert into user_role (userId, roleId) values (83, 2);
insert into user_role (userId, roleId) values (84, 2);
insert into user_role (userId, roleId) values (85, 2);
insert into user_role (userId, roleId) values (86, 2);
insert into user_role (userId, roleId) values (87, 2);
insert into user_role (userId, roleId) values (88, 2);
insert into user_role (userId, roleId) values (89, 2);
insert into user_role (userId, roleId) values (90, 2);
insert into user_role (userId, roleId) values (91, 2);
insert into user_role (userId, roleId) values (92, 2);
insert into user_role (userId, roleId) values (93, 2);
insert into user_role (userId, roleId) values (94, 2);
insert into user_role (userId, roleId) values (95, 2);
insert into user_role (userId, roleId) values (96, 2);
insert into user_role (userId, roleId) values (97, 2);
insert into user_role (userId, roleId) values (98, 2);
insert into user_role (userId, roleId) values (99, 2);
insert into user_role (userId, roleId) values (100, 2);
insert into user_role (userId, roleId) values (101, 2);
insert into user_role (userId, roleId) values (102, 2);



INSERT INTO USER_MEMBERSHIPS(userID, membershipID, paid, paidDate) VALUES (1, 1, FALSE, NULL);
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (2, 2, false, '2024-09-19');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (3, 1, false, '2024-02-13');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (4, 3, false, '2024-05-30');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (5, 3, true, '2024-06-05');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (6, 2, false, '2023-10-24');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (7, 1, true, '2023-12-25');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (8, 1, false, '2024-06-22');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (9, 2, false, '2024-01-23');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (10, 2, true, '2024-04-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (11, 3, true, '2024-04-19');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (12, 3, false, '2024-05-01');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (13, 3, false, '2024-01-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (14, 2, false, '2024-07-05');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (15, 3, false, '2024-10-01');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (16, 2, true, '2024-05-11');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (17, 1, false, '2024-02-06');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (18, 1, true, '2024-07-31');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (19, 2, true, '2024-10-04');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (20, 2, true, '2023-10-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (21, 2, false, '2024-02-16');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (22, 1, false, '2024-03-14');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (23, 2, true, '2024-09-06');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (24, 1, true, '2024-07-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (25, 2, false, '2024-03-03');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (26, 1, false, '2024-04-24');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (27, 1, true, '2024-09-30');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (28, 3, true, '2024-06-15');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (29, 3, true, '2024-06-24');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (30, 2, true, '2023-11-15');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (31, 3, true, '2024-09-23');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (32, 2, true, '2023-12-12');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (33, 2, true, '2023-11-01');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (34, 1, true, '2023-11-02');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (35, 1, true, '2024-01-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (36, 2, true, '2024-04-23');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (37, 3, false, '2024-04-06');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (38, 1, true, '2024-04-22');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (39, 2, true, '2023-11-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (40, 1, true, '2024-03-02');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (41, 1, false, '2024-08-12');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (42, 1, true, '2023-11-12');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (43, 1, false, '2024-05-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (44, 1, false, '2024-08-26');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (45, 3, true, '2024-07-08');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (46, 3, true, '2024-02-21');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (47, 2, true, '2023-11-07');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (48, 1, false, '2024-09-07');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (49, 2, true, '2024-03-06');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (50, 1, true, '2023-12-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (51, 3, true, '2024-08-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (52, 2, false, '2024-04-04');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (53, 1, true, '2024-06-19');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (54, 3, true, '2023-11-02');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (55, 2, false, '2024-02-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (56, 1, true, '2024-10-02');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (57, 2, false, '2024-04-27');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (58, 3, false, '2024-03-29');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (59, 3, true, '2024-05-02');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (60, 3, true, '2024-05-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (61, 3, false, '2024-07-04');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (62, 2, true, '2023-11-14');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (63, 3, true, '2024-01-27');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (64, 3, true, '2024-05-29');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (65, 1, true, '2024-03-06');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (66, 3, false, '2024-06-16');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (67, 1, true, '2024-06-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (68, 1, false, '2024-01-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (69, 2, true, '2023-12-15');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (70, 3, true, '2024-07-16');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (71, 3, true, '2023-12-27');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (72, 1, true, '2024-05-14');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (73, 1, true, '2023-10-19');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (74, 1, false, '2024-09-25');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (75, 2, true, '2023-11-27');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (76, 1, false, '2023-11-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (77, 1, false, '2024-01-22');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (78, 1, true, '2024-03-08');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (79, 3, false, '2024-07-18');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (80, 3, false, '2024-10-01');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (81, 2, false, '2023-12-30');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (82, 2, true, '2023-11-29');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (83, 2, true, '2024-04-29');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (84, 1, true, '2024-03-06');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (85, 1, false, '2024-04-20');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (86, 3, false, '2024-08-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (87, 1, false, '2024-05-08');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (88, 1, false, '2023-12-24');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (89, 1, true, '2024-09-09');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (90, 3, true, '2024-01-31');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (91, 1, true, '2023-12-20');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (92, 2, true, '2024-02-29');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (93, 1, false, '2024-03-05');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (94, 1, false, '2024-05-13');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (95, 2, false, '2024-04-22');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (96, 2, true, '2023-10-28');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (97, 2, false, '2024-05-21');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (98, 2, true, '2024-07-13');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (99, 2, false, '2024-05-26');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (100, 2, true, '2024-04-19');
insert into USER_MEMBERSHIPS (userId, membershipId, paid, paidDate) values (101, 2, true, '2024-01-30');
