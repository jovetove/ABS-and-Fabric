CREATE  DATABASE IF NOT EXISTS test;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS `user`(
   `id` INT UNSIGNED AUTO_INCREMENT COMMENT '用户id,自增型',
   `name` VARCHAR(20) NOT NULL DEFAULT '默认用户' COMMENT '用户名,昵称',
   `password` VARCHAR(20) COMMENT '用户密码',
   `email` VARCHAR(40) NOT NULL COMMENT '用户邮箱 非空 需通过邮箱激活',
   `age` INT UNSIGNED COMMENT '年龄',
   `active_status` BIT(1)  DEFAULT false COMMENT '激活状态，默认未激活',
   `timestamp` timestamp DEFAULT NOW() COMMENT '创建时间',
   PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `user` ADD UNIQUE (`email`);

insert into user(name,  password, email, age)
 values ("zjianfa", "123456", "zjianfa3@foxmail.com", 12);

insert into user(name,  password, email, age)
values ("zjianfa", "123456", "zjianfa2@foxmail.com", 12);


insert into user(name,  password, email, age,active_status)
values ("zjianfa", "123456", "zjianfa4@foxmail.com", 12,true);

delete from user
where id =1;
select * from user;

