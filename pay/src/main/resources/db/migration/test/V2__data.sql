-- test data save

-- sned money
INSERT INTO SEND_MONEY VALUES('testtoken01', 'senduser01', '1', 10000, 3, '2020-06-26 10:00:00');
INSERT INTO SEND_MONEY VALUES('testtoken02', 'senduser02', '2', 10000, 6, '2020-06-27 10:00:00');


INSERT INTO RCV_MONEY VALUES(1, 'testtoken01', '1', 'rcvuser01', 3000);
INSERT INTO RCV_MONEY VALUES(2, 'testtoken01', '1','rcvuser02', 5000);
INSERT INTO RCV_MONEY (seq, token,room_id, rcv_money) VALUES(3, 'testtoken01', '1', 2000);
