
CREATE TABLE SEND_MONEY(
   token varchar (255),
   user_id varchar (255),
   room_id varchar (255),
   send_money long,
   send_count integer,
   send_date timestamp
);

CREATE TABLE RCV_MONEY(
   seq bigserial,
   token varchar (255),
   room_id varchar (255),
   user_id varchar (255),
   rcv_money long
);


