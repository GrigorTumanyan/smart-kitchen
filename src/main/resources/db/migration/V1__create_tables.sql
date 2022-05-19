USE smart_kitchen;
create table category
(
    id          varchar(255) not null,
    created_by  varchar(255),
    created_on  datetime(6),
    modified_on datetime(6),
    name        varchar(255),
    deleted     bit default false,
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table log
(
    id          varchar(255) not null,
    created_by  varchar(255),
    created_on  datetime(6),
    modified_on datetime(6),
    event       varchar(255),
    info        varchar(255),
    log_type    varchar(255),
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table menu_item
(
    id          varchar(255) not null,
    created_by  varchar(255),
    created_on  datetime(6),
    modified_on datetime(6),
    image       varchar(255),
    measurement varchar(255),
    name        varchar(255),
    price       double precision,
    weight      double precision,
    deleted     bit default false,
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table menu_item_products
(
    menu_item_id varchar(255) not null,
    products_id  varchar(255) not null
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table product
(
    id          varchar(255) not null,
    created_by  varchar(255),
    created_on  datetime(6),
    modified_on datetime(6),
    name        varchar(255),
    deleted     bit default false,
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table product_category
(
    product_id  varchar(255) not null,
    category_id varchar(255) not null
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table restaurant_order
(
    id          varchar(255) not null,
    created_by  varchar(255),
    created_on  datetime(6),
    modified_on datetime(6),
    state       integer,
    total_price double precision,
    cook_id     varchar(255),
    waiter_id   varchar(255),
    deleted     bit default false,
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table restaurant_order_items_list
(
    order_id      varchar(255) not null,
    items_list_id varchar(255) not null
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table user
(
    id          varchar(255) not null,
    created_by  varchar(255),
    created_on  datetime(6),
    modified_on datetime(6),
    active      bit default false,
    address     varchar(255),
    email       varchar(255) not null unique,
    image       varchar(255),
    name        varchar(255),
    password    varchar(255),
    phone       varchar(255),
    surname     varchar(255),
    user_type   varchar(255),
    deleted     bit default false,
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
create table warehouse
(
    id          varchar(255) not null,
    created_by  varchar(255),
    created_on  datetime(6),
    modified_on datetime(6),
    count       integer,
    name        varchar(255),
    price       double precision,
    measurement varchar(255),
    deleted     bit default false,
    product_id  varchar(255),
    primary key (id)
) engine = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci;
alter table warehouse
    add constraint FK96sdfdfsg57892dfgdf85fds foreign key (product_id) references product (id);
alter table menu_item_products
    add constraint FK10mtljwyghld4u2hjt8c9mhk foreign key (products_id) references product (id);
alter table menu_item_products
    add constraint FKeovss0aim3jn22q8j7w8wyyss foreign key (menu_item_id) references menu_item (id);
alter table product_category
    add constraint FKkud35ls1d40wpjb5htpp14q4e foreign key (category_id) references category (id);
alter table product_category
    add constraint FK2k3smhbruedlcrvu6clued06x foreign key (product_id) references product (id);
alter table restaurant_order
    add constraint FKqcugi35h9wj8xy27pfky33rdb foreign key (cook_id) references user (id);
alter table restaurant_order
    add constraint FKnaj8utb27vlvd0xrk89nw8xqh foreign key (waiter_id) references user (id);
alter table restaurant_order_items_list
    add constraint FKi78wa9ko3mf3aky4rjkscfd8q foreign key (items_list_id) references menu_item (id);
alter table restaurant_order_items_list
    add constraint FKpyyrsve3a70hkikj9mbau1pld foreign key (order_id) references restaurant_order (id);