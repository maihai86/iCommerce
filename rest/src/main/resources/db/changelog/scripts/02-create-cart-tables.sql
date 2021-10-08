-- liquibase formatted sql
-- changeset haimt:02

create table cart
(
    id         serial  not null primary key,
    product_id bigint  not null,
    amount     integer not null,
    CONSTRAINT cart_product_fk FOREIGN KEY (product_id) REFERENCES product (id)
);
