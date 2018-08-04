create database blog character set utf8;
use blog;

create table authority
(
  id          bigint auto_increment
    primary key,
  create_time datetime     null,
  last_modify datetime     null,
  name        varchar(255) null,
  permission  int          null
)
  engine = InnoDB
  charset = utf8;

create table category
(
  id          bigint auto_increment
    primary key,
  create_time datetime     null,
  last_modify datetime     null,
  name        varchar(255) not null,
  constraint UK_46ccwnsi9409t36lurvtyljak
  unique (name)
)
  engine = InnoDB
  charset = utf8;

create table comment
(
  id           bigint auto_increment
    primary key,
  create_time  datetime     null,
  last_modify  datetime     null,
  email        varchar(255) null,
  name         varchar(255) null,
  site_url     varchar(255) null,
  post_id      bigint       null,
  content_html text         null
)
  engine = InnoDB;

create index FKs1slvnkuemjsq2kj4h3vhx7i1
  on comment (post_id);

create table comment_replies
(
  comment_id bigint not null,
  replies_id bigint not null,
  primary key (comment_id, replies_id),
  constraint UK_107vee447mhtsxghw5bigtv9l
  unique (replies_id)
)
  engine = InnoDB;

create table friend
(
  id          bigint auto_increment
    primary key,
  create_time datetime     null,
  last_modify datetime     null,
  about       varchar(255) null,
  img_url     varchar(255) null,
  name        varchar(255) null,
  site_url    varchar(255) null,
  username    varchar(255) null
)
  engine = InnoDB;

create table post
(
  id           bigint auto_increment
    primary key,
  create_time  datetime                        null,
  last_modify  datetime                        null,
  content      text                            null,
  content_html text collate utf8mb4_unicode_ci null,
  img          varchar(255)                    null,
  summary      text                            null,
  title        varchar(255)                    not null,
  category_id  bigint                          null,
  user_id      bigint                          null,
  visiable     bit                             not null,
  visible      bit                             not null,
  constraint UK_2jm25hjrq6iv4w8y1dhi0d9p4
  unique (title)
)
  engine = InnoDB
  charset = utf8mb4;
-- 采用utf8mb4为了存储Emoji

create index FK72mt33dhhs48hf9gcqrq4fxte
  on post (user_id);

create index FKg6l1ydp1pwkmyj166teiuov1b
  on post (category_id);

create table post_section
(
  post_id    bigint not null,
  section_id bigint not null,
  primary key (post_id, section_id)
)
  engine = InnoDB
  charset = utf8;

create index FKmt8xgeqfhqovsf6h0vq6v46a6
  on post_section (section_id);

create table post_tag
(
  post_id bigint not null,
  tag_id  bigint not null,
  primary key (post_id, tag_id)
)
  engine = InnoDB
  charset = utf8;

create index FKac1wdchd2pnur3fl225obmlg0
  on post_tag (tag_id);

create table section
(
  id          bigint auto_increment
    primary key,
  create_time datetime     null,
  last_modify datetime     null,
  name        varchar(255) not null,
  user_id     bigint       null,
  constraint UK_rwqtt5x96oahjdtqt20vxu4um
  unique (name)
)
  engine = InnoDB
  charset = utf8;

create index FKpeiuk0lt4fbfnk64nkypnmrn0
  on section (user_id);

create table tag
(
  id          bigint auto_increment
    primary key,
  create_time datetime     null,
  last_modify datetime     null,
  name        varchar(255) not null,
  constraint UK_1wdpsed5kna2y38hnbgrnhi5b
  unique (name)
)
  engine = InnoDB
  charset = utf8;

create table user
(
  id                       bigint auto_increment
    primary key,
  create_time              datetime     null,
  last_modify              datetime     null,
  about                    varchar(255) null,
  address                  varchar(255) null,
  age                      int          null,
  avatar_img               varchar(255) null,
  background_img           varchar(255) null,
  email                    varchar(255) null,
  enabled                  bit          not null,
  last_password_reset_date datetime     not null,
  last_seen                datetime     null,
  name                     varchar(255) null,
  password                 varchar(255) not null,
  signature                varchar(255) null,
  username                 varchar(255) not null,
  constraint UK_sb8bbouer5wak8vyiiy4pf2bx
  unique (username)
)
  engine = InnoDB
  charset = utf8;

create table user_authority
(
  user_id      bigint not null,
  authority_id bigint not null,
  primary key (user_id, authority_id)
)
  engine = InnoDB
  charset = utf8;

create index FKgvxjs381k6f48d5d2yi11uh89
  on user_authority (authority_id);


INSERT INTO authority (name, permission, create_time, last_modify)
values ('ROLE_VISITOR', 0, now(), now());
INSERT INTO authority (name, permission, create_time, last_modify)
values ('ROLE_WRITE_ARTICLES', 1, now(), now());
INSERT INTO authority (name, permission, create_time, last_modify)
values ('ROLE_ADMINISTER', 2, now(), now());
commit ;

INSERT INTO user (username, password, enabled, last_password_reset_date)
values ('admin', 'admin', 1, now());
INSERT INTO user_authority(user_id, authority_id) values (1, 3);
commit ;