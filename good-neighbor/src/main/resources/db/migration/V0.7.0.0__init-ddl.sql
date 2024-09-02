-- # GLOBAL # --

CREATE TABLE global_parameter (
    name varchar(256) PRIMARY KEY,
    value varchar(1024) NOT NULL,
    client_view boolean DEFAULT false
);

CREATE TABLE global_like (
    id bigserial PRIMARY KEY,
    insert_date timestamp NOT NULL,
    client_ip varchar(256)
);

-- # POST IT # --

CREATE TABLE postit_board (
    id bigserial PRIMARY KEY,
    creation_date timestamp NOT NULL,
    update_date timestamp,
    name varchar(128) NOT NULL
);

CREATE TABLE postit_note (
    id bigserial PRIMARY KEY,
    creation_date timestamp NOT NULL,
    update_date timestamp,
    name varchar(256) NOT NULL,
    text_value varchar(2048),
    postit_board_id bigint NOT NULL references postit_board(id),
    update_text_date timestamp,
    color varchar(128),
    order_num integer
);

-- # GLOBAL # --

CREATE TABLE global_user (
    id bigserial PRIMARY KEY,
    username varchar(256) NOT NULL UNIQUE,
    password varchar(256),
    enabled boolean NOT NULL DEFAULT false
);

CREATE TABLE global_user_authority (
    id bigserial PRIMARY KEY,
    authority varchar(256) NOT NULL
);

CREATE TABLE global_link_user_authority (
    user_id bigint NOT NULL references postit_board(id),
    user_authority_id bigint NOT NULL references postit_board(id),
    PRIMARY KEY(user_id, user_authority_id)
);