INSERT INTO teacher(name,email) VALUES('長男','tyounan@example.com'); 

INSERT INTO roles(id, name) VALUES(1, 'ROLE_GENERAL');
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN');

-- password = "general"
--INSERT INTO login_user(id, name, email, password) VALUES(1, '一般太郎', 'general@example.com', '$2a$10$6fPXYK.C9rCWUBifuqBIB.GRNU.nQtBpdzkkKis8ETaKVKxNo/ltO');
INSERT INTO login_user(id, name, email, password) VALUES(1, '一般', 'general@example.com', '$2a$10$UpPVd2/oe4k7Pn.vCTuIKeuzpT8rZS2D1Df7/b1FlF/vyCry6gRui');
-- password = "admin"
INSERT INTO login_user(id, name, email, password) VALUES(2, '管理太郎', 'admin2@example.com', '$2a$10$SJTWvNl16fCU7DaXtWC0DeN/A8IOakpCkWWNZ/FKRV2CHvWElQwMS');
INSERT INTO login_user(id, name, email, password) VALUES(3, '管理', 'admin@example.com', '4dff4ea340f0a823f15d3f4f01ab62eae0e5da579ccb851f8db9dfe84c58b2b37b89903a740e1ee172da793a6e79d560e5f7f9bd058a12a280433ed6fa46510a');

INSERT INTO user_role(user_id, role_id) VALUES(1, 1);
INSERT INTO user_role(user_id, role_id) VALUES(2, 1);
INSERT INTO user_role(user_id, role_id) VALUES(2, 2);
--INSERT INTO user_role(user_id, role_id) VALUES(3, 1);
INSERT INTO user_role(user_id, role_id) VALUES(3, 2);