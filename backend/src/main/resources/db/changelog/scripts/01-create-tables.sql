-- liquibase formatted sql
-- changeset haimt:01

create table product_category
(
    id   serial       not null primary key,
    name varchar(255) not null
);

create table product_brand
(
    id   serial       not null primary key,
    name varchar(255) not null
);

create table product_color
(
    id   serial       not null primary key,
    name varchar(255) not null
);

create table product
(
    id          serial         not null primary key,
    category_id bigint         not null,
    brand_id    bigint         not null,
    color_id    bigint         not null,
    name        varchar(255)   not null,
    price       decimal(16, 0) not null,
    CONSTRAINT product_cat_fk FOREIGN KEY (category_id) REFERENCES product_category (id),
    CONSTRAINT product_brand_fk FOREIGN KEY (brand_id) REFERENCES product_brand (id),
    CONSTRAINT product_color_fk FOREIGN KEY (color_id) REFERENCES product_color (id)
);