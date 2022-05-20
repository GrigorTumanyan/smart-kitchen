USE smart_kitchen;
DROP TABLE warehouse_product_list;
alter table warehouse
    add column product_id  varchar(255),
    change column name description varchar(255),
    add constraint FK96sdfdfsg57892dfgdf85fds foreign key (product_id) references product (id);