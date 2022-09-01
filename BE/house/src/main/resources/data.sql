
INSERT INTO users (user_id, account_id, display_name, oauth_client_type, profile_image_url, gender)
VALUES ( 1, 'street62',  'lee', 'KAKAO', 'lucas.com', 'MALE');

INSERT INTO facility (facility_id, name)
VALUES ( 1, '에어컨'), ( 2, '세탁기'), ( 3, '침대'), ( 4, '냉장고'), (5, 'TV');

INSERT INTO security_facility(security_facility_id, name)
VALUES (1, 'CCTV'), (2, '비디오픈'), (3, '공동현관');

INSERT INTO wanted_article (id, address, content, created_at, deposit_budget, is_completed, is_deleted, modified_at, move_in_date, move_out_date, rent_budget, title, view_count, user_user_id)
VALUES
(8, '서울특별시 성동구', '안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다.', '2022-08-19 02:57:51', 10000000, 0, 0, '2022-08-19 02:57:51.892033', '2022-08-01', '2023-02-02', 550000, '왕십리역 원룸 구합니다', 0, 1),
(9, '서울특별시 성동구', '사정이 생겨서 급하게 집 구해봅니다.', '2022-08-19 02:58:02.328856', 10000000, 0, 0, '2022-08-19 02:58:02.328862', '2022-08-01', '2023-02-02', 500000, '왕십리역 원룸 구함', 0, 1),
(10, '서울특별시 성동구', '왕십리역 근처 원룸 구합니다.', '2022-08-19 02:58:10.788609', 10000000, 0, 0, '2022-08-19 02:58:10.788615', '2022-08-01', '2023-02-02', 500000, '한양대 원룸 구합니다', 0, 1),
(11, '서울특별시 성동구', '한양대 근처 원룸 구합니다.', '2022-08-19 02:58:14.559943', 10000000, 0, 0, '2022-08-19 02:58:14.559949', '2022-08-01', '2023-02-02', 100000, '왕십리~상왕십리 원룸 구합니다', 0, 1),
(12, '서울특별시 성동구', '왕십리~상왕십리 원룸 구합니다!', '2022-08-19 02:58:30.050491', 10000000, 0, 0, '2022-08-19 02:58:30.050502', '2022-08-01', '2023-02-02', 750000, '왕십리 투룸 구합니다', 0, 1),
(13, '서울특별시 성동구', '왕십리 원룸 구합니다 연락주세요.', '2022-08-19 02:58:34.054144', 10000000, 0, 0, '2022-08-19 02:58:34.054150', '2022-08-01', '2023-02-02', 550000, '왕십리 근처 원룸 구해요', 0, 1),
(14, '서울특별시 성동구', '안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다.', '2022-08-19 02:57:51', 10000000, 0, 0, '2022-08-19 02:57:51.892033', '2022-08-01', '2023-02-02', 550000, '왕십리역 원룸 구합니다', 0, 1),
(15, '서울특별시 성동구', '사정이 생겨서 급하게 집 구해봅니다.', '2022-08-19 02:58:02.328856', 10000000, 0, 0, '2022-08-19 02:58:02.328862', '2022-08-01', '2023-02-02', 500000, '왕십리역 원룸 구함', 0, 1),
(16, '서울특별시 성동구', '왕십리역 근처 원룸 구합니다.', '2022-08-19 02:58:10.788609', 10000000, 0, 0, '2022-08-19 02:58:10.788615', '2022-08-01', '2023-02-02', 500000, '한양대 원룸 구합니다', 0, 1),
(17, '서울특별시 성동구', '한양대 근처 원룸 구합니다.', '2022-08-19 02:58:14.559943', 10000000, 0, 0, '2022-08-19 02:58:14.559949', '2022-08-01', '2023-02-02', 100000, '왕십리~상왕십리 원룸 구합니다', 0, 1),
(18, '서울특별시 성동구', '왕십리~상왕십리 원룸 구합니다!', '2022-08-19 02:58:30.050491', 10000000, 0, 0, '2022-08-19 02:58:30.050502', '2022-08-01', '2023-02-02', 750000, '왕십리 투룸 구합니다', 0, 1),
(19, '서울특별시 성동구', '왕십리 원룸 구합니다 연락주세요.', '2022-08-19 02:58:34.054144', 10000000, 0, 0, '2022-08-19 02:58:34.054150', '2022-08-01', '2023-02-02', 550000, '왕십리 근처 원룸 구해요', 0, 1);

INSERT INTO  rent_article (rent_article_id, address, address_description, address_detail, available_from, content, contract_expires_at, contract_type, created_at, deposit, has_balcony, has_elevator, has_parking_lot, house_type, is_completed, is_deleted, latitude, longitude, maintenance_fee, maintenance_fee_description, max_floor, modified_at, rent_fee, status, this_floor, title, view_count, user_id)
VALUES
('1', '서울특별시 성동구', '왕십리역 5분거리', '왕십리로 309', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:06.239433', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:55:06.239444', '500000', NULL, '2', '왕십리역 원룸(1000/50)', '0', '1'),
('2', '서울특별시 성동구', '왕십리역 코앞', '고산자로 269', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:16.979353', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:55:16.979359', '550000', NULL, '2', '왕십리역 초역세권 원룸(1000/55)', '0', '1'),
('3', '서울특별시 성동구', '왕십리역 10분거리', '고산자로 284', '2022-08-01', '채광좋은 원룸입니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:48.911475', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '100000', '수도, 가스 별도', '4', '2022-08-19 02:55:48.911481', '550000', NULL, '2', '성동구 원룸(500/55)', '0', '1'),
('4', '서울특별시 성동구', '왕십리역 7분거리', '왕십리로 336', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:53.205136', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '100000', '수도, 가스 별도', '4', '2022-08-19 02:55:53.205142', '600000', NULL, '2', '한양대 후문 원룸(1000/65)', '0', '1'),
('5', '서울특별시 성동구', '초역세권 왕십리역 도보3분', '왕십리로20길 30', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:06.257018', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '100000', '수도, 가스 별도', '4', '2022-08-19 02:56:06.257024', '600000', NULL, '2', '왕십리 원룸(1000/60)', '0', '1'),
('6', '서울특별시 성동구', '왕십리역 코앞', '왕십리로 340', '2022-08-01', '채광좋은 원룸입니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '650000', NULL, '2', '성동구 원룸(1000/65)', '0', '1'),
('7', '서울특별시 성동구', '왕십리역 코앞', '왕십리로 320', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '650000', NULL, '2', '왕십리역 원룸(1000/65)', '0', '1'),
('8', '서울특별시 성동구', '왕십리역 7분거리', '왕십리로 332', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '550000', NULL, '2', '왕십리역 초역세권 원룸(1000/55)', '0', '1'),
('9', '서울특별시 성동구', '왕십리역 바로앞', '무학봉28길 30', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '450000', NULL, '2', '성동구 원룸(1000/45)', '0', '1'),
('10', '서울특별시 성동구', '왕십리역 도보 4분거리', '왕십리로 241', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '550000', NULL, '2', '한양대 원룸(1000/55)', '0', '1'),
('11', '서울특별시 성동구', '왕십리역 앞', '왕십리로 322', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '600000', NULL, '2', '왕십리 원룸(1000/60)', '0', '1'),
('12', '서울특별시 성동구', '왕십리역 코앞', '왕십리로22길 22', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', null, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '650000', NULL, '2', '왕십리 원룸(1000/65)', '0', '1');

INSERT INTO  house_image (house_image_id, image_url, order_in_list, rent_article_id)
VALUES
('1', 'https://images.unsplash.com/photo-1553444836-bc6c8d340ba7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1738&q=80', '1', '1'),
('2', 'https://images.unsplash.com/flagged/photo-1556438758-8d49568ce18e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDF8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '1'),
('3', 'https://images.unsplash.com/photo-1543490791-db8323d8e5b2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDN8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '1', '2'),
('4', 'https://images.unsplash.com/photo-1557127275-f8b5ba93e24e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDV8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '2'),
('5', 'https://images.unsplash.com/flagged/photo-1573168710865-2e4c680d921a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEwfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '1', '3'),
('6', 'https://images.unsplash.com/photo-1578704311381-cc1226f2aa1f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEzfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '2', '3'),
('7', 'https://images.unsplash.com/photo-1553444836-bc6c8d340ba7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1738&q=80', '1', '4'),
('8', 'https://images.unsplash.com/flagged/photo-1556438758-8d49568ce18e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDF8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '4'),
('9', 'https://images.unsplash.com/photo-1543490791-db8323d8e5b2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDN8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '1', '5'),
('10', 'https://images.unsplash.com/photo-1557127275-f8b5ba93e24e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDV8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '5'),
('11', 'https://images.unsplash.com/flagged/photo-1573168710865-2e4c680d921a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEwfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '1', '6'),
('12', 'https://images.unsplash.com/photo-1578704311381-cc1226f2aa1f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEzfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '2', '6'),
('13', 'https://images.unsplash.com/photo-1553444836-bc6c8d340ba7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1738&q=80', '1', '7'),
('14', 'https://images.unsplash.com/flagged/photo-1556438758-8d49568ce18e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDF8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '7'),
('15', 'https://images.unsplash.com/photo-1543490791-db8323d8e5b2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDN8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '1', '8'),
('16', 'https://images.unsplash.com/photo-1557127275-f8b5ba93e24e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDV8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '8'),
('17', 'https://images.unsplash.com/flagged/photo-1573168710865-2e4c680d921a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEwfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '1', '9'),
('18', 'https://images.unsplash.com/photo-1578704311381-cc1226f2aa1f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEzfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '2', '9'),
('19', 'https://images.unsplash.com/flagged/photo-1573168710865-2e4c680d921a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEwfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '1', '10'),
('20', 'https://images.unsplash.com/photo-1578704311381-cc1226f2aa1f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEzfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60', '2', '10'),
('21', 'https://images.unsplash.com/photo-1553444836-bc6c8d340ba7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1738&q=80', '1', '11'),
('22', 'https://images.unsplash.com/flagged/photo-1556438758-8d49568ce18e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDF8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '11'),
('23', 'https://images.unsplash.com/photo-1543490791-db8323d8e5b2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDN8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '1', '12'),
('24', 'https://images.unsplash.com/photo-1557127275-f8b5ba93e24e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDV8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=800&q=60', '2', '12');
