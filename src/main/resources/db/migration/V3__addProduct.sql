insert into product (id, code ,name,description,type) values (1, 0001, 'Pizza Санмария' , 'Вкусная пицца', 'PIZZA');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (1, 140 , '17x17');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (1, 210 , '21x21');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (1, 240 , '25x25');

insert into product (id, code ,name,description,type) values (2, 0002, 'Pizza Франческо' , 'Бесподобная пицца', 'PIZZA');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (2, 190 , '17x17');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (2, 230 , '21x21');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (2, 290 , '25x25');

insert into product (id, code ,name,description,type) values (3, 0003, 'Pepsi' , 'Сладкий напиток', 'BEVERAGES');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (3, 40 , '0.25 л');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (3, 60 , '0.5 л');
insert into product_price_from_size (product_id, price_from_size,price_from_size_key) values (3, 100 , '1 л');