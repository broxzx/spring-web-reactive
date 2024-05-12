create table users
(
    id       BIGSERIAL PRIMARY KEY,
    username varchar(32)  NOT NULL,
    password varchar(128) NOT NULL,
    role     varchar(32)
);