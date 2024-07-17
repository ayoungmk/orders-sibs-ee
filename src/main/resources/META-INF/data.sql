INSERT INTO TB_ITEM (name) VALUES ('arroz');
INSERT INTO TB_ITEM (name) VALUES ('feijão');
INSERT INTO TB_ITEM (name) VALUES ('batata');
INSERT INTO TB_ITEM (name) VALUES ('macarrão');

INSERT INTO TB_USER (name, email) VALUES ('adrian', 'adriankimura@gmail.com');
INSERT INTO TB_USER (name, email) VALUES ('barbara', 'adriankimura@gmail.com');
INSERT INTO TB_USER (name, email) VALUES ('melissa', 'adriankimura@gmail.com');
INSERT INTO TB_USER (name, email) VALUES ('jorge', 'adriankimura@gmail.com');

INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'arroz'), 50);
INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'feijão'), 50);
INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'batata'), 50);
INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'macarrão'), 50);

INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-12 13:23:44', 6, (SELECT ID FROM TB_ITEM WHERE NAME = 'arroz'));
INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-13 13:23:44', 5, (SELECT ID FROM TB_ITEM WHERE NAME = 'feijão'));
INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-14 13:23:44', 9, (SELECT ID FROM TB_ITEM WHERE NAME = 'batata'));
INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-15 13:23:44', 3, (SELECT ID FROM TB_ITEM WHERE NAME = 'macarrão'));

INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-12 13:23:44', 6, (SELECT ID FROM TB_ITEM WHERE NAME = 'arroz'), 1, 'INCOMPLETE');
INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-14 13:23:44', 5, (SELECT ID FROM TB_ITEM WHERE NAME = 'feijão'), 2, 'INCOMPLETE');
INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-15 13:23:44', 9, (SELECT ID FROM TB_ITEM WHERE NAME = 'batata'), 4, 'INCOMPLETE');
INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-16 13:23:44', 3, (SELECT ID FROM TB_ITEM WHERE NAME = 'macarrão'), 3, 'INCOMPLETE');
