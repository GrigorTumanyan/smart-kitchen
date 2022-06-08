USE smart_kitchen;
drop table if exists restaurant_order_items_list;
alter table restaurant_order
    add column order_menu_item_id varchar(255);
create table order_menu_item
(
    id           varchar(255),
    menu_item_id varchar(255),
    count        int,
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
alter table restaurant_order
    add constraint FK96sdfdfsgsdsdfs57892dfgdf85fds foreign key (order_menu_item_id) references order_menu_item (id);

