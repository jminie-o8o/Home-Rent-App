INSERT INTO users (user_id, account_id, display_name, oauth_client_type, profile_image_url, gender)
VALUES
( 1, 'street62',  'lee', 'KAKAO', 'lucas.com', 'MALE'),
( 2, 'honux77',  'honux', 'KAKAO', 'lucas.com', 'MALE');

INSERT INTO facility (facility_id, name)
VALUES ( 1, '에어컨'), ( 2, '세탁기'), ( 3, '침대'), ( 4, '냉장고'), (5, 'TV');

INSERT INTO security_facility(security_facility_id, name)
VALUES (1, 'CCTV'), (2, '비디오폰'), (3, '공동현관');

INSERT INTO house_facility(id, has_parking_lot,has_balcony, has_elevator,has_aircon, has_laundry, has_bed, has_fridge, has_tv, has_cctv, has_video_phone, has_lobby)
VALUES (1, true, true, true, true, false, false, false, false, true, true, false);

INSERT INTO wanted_article (id, address, content, created_at, deposit_budget, is_completed, is_deleted, modified_at, move_in_date, move_out_date, rent_budget, title, view_count, user_user_id)
VALUES
(1, '서울특별시 성동구', '안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다.', '2022-08-19 02:57:51', 10000000, 0, 0, '2022-08-19 02:57:51.892033', '2022-08-01', '2023-02-02', 550000, '왕십리역 원룸 구합니다', 0, 1),
(2, '서울특별시 성동구', '사정이 생겨서 급하게 집 구해봅니다.', '2022-08-19 02:58:02.328856', 10000000, 0, 0, '2022-08-19 02:58:02.328862', '2022-08-01', '2023-02-02', 500000, '왕십리역 원룸 구함', 0, 1),
(3, '서울특별시 성동구', '왕십리역 근처 원룸 구합니다.', '2022-08-19 02:58:10.788609', 10000000, 0, 0, '2022-08-19 02:58:10.788615', '2022-08-01', '2023-02-02', 500000, '한양대 원룸 구합니다', 0, 1),
(4, '서울특별시 성동구', '한양대 근처 원룸 구합니다.', '2022-08-19 02:58:14.559943', 10000000, 0, 0, '2022-08-19 02:58:14.559949', '2022-08-01', '2023-02-02', 100000, '왕십리~상왕십리 원룸 구합니다', 0, 1),
(5, '서울특별시 성동구', '왕십리~상왕십리 원룸 구합니다!', '2022-08-19 02:58:30.050491', 10000000, 0, 0, '2022-08-19 02:58:30.050502', '2022-08-01', '2023-02-02', 750000, '왕십리 투룸 구합니다', 0, 1),
(6, '서울특별시 성동구', '왕십리 원룸 구합니다 연락주세요.', '2022-08-19 02:58:34.054144', 10000000, 0, 0, '2022-08-19 02:58:34.054150', '2022-08-01', '2023-02-02', 550000, '왕십리 근처 원룸 구해요', 0, 1),
(7, '서울특별시 성동구', '안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다.', '2022-08-19 02:58:51', 10000000, 0, 0, '2022-08-19 02:57:51.892033', '2022-08-01', '2023-02-02', 550000, '왕십리역 원룸 구합니다', 0, 1),
(8, '서울특별시 성동구', '사정이 생겨서 급하게 집 구해봅니다.', '2022-08-19 02:59:02.328856', 10000000, 0, 0, '2022-08-19 02:58:02.328862', '2022-08-01', '2023-02-02', 500000, '왕십리역 원룸 구함', 0, 1),
(9, '서울특별시 성동구', '왕십리역 근처 원룸 구합니다.', '2022-08-19 02:59:10.788609', 10000000, 0, 0, '2022-08-19 02:58:10.788615', '2022-08-01', '2023-02-02', 500000, '한양대 원룸 구합니다', 0, 1),
(10, '서울특별시 성동구', '한양대 근처 원룸 구합니다.', '2022-08-19 02:59:14.559943', 10000000, 0, 0, '2022-08-19 02:58:14.559949', '2022-08-01', '2023-02-02', 100000, '왕십리~상왕십리 원룸 구합니다', 0, 1),
(11, '서울특별시 성동구', '왕십리~상왕십리 원룸 구합니다!', '2022-08-19 02:59:30.050491', 10000000, 0, 0, '2022-08-19 02:58:30.050502', '2022-08-01', '2023-02-02', 750000, '왕십리 투룸 구합니다', 0, 1),
(12, '서울특별시 성동구', '왕십리 원룸 구합니다 연락주세요.', '2022-08-19 02:59:34.054144', 10000000, 0, 0, '2022-08-19 02:58:34.054150', '2022-08-01', '2023-02-02', 550000, '왕십리 근처 원룸 구해요', 0, 1),
(13, '서울특별시 성동구', '안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다.', '2022-08-19 02:59:51', 10000000, 0, 0, '2022-08-19 02:57:51.892033', '2022-08-01', '2023-02-02', 550000, '왕십리역 원룸 구합니다', 0, 2),
(14, '서울특별시 성동구', '안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다.', '2022-08-19 02:59:52', 10000000, 0, 0, '2022-08-19 02:57:51.892033', '2022-08-01', '2023-02-02', 550000, '왕십리역 원룸 구합니다', 0, 2);


-- INSERT INTO  rent_article (rent_article_id, address, address_description, address_detail, available_from, content, contract_expires_at, contract_type, created_at, deposit, has_balcony, has_elevator, has_parking_lot, house_type, is_completed, is_deleted, latitude, longitude, maintenance_fee, maintenance_fee_description, max_floor, modified_at, rent_fee, status, this_floor, title, view_count, user_id)
-- VALUES
-- ('1', '서울특별시 성동구', '왕십리역 5분거리', '왕십리로 309', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:06.239433', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:55:06.239444', '500000', NULL, '2', '왕십리역 원룸(1000/50)', '0', '1'),
-- ('2', '서울특별시 성동구', '왕십리역 코앞', '고산자로 269', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:16.979353', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:55:16.979359', '550000', NULL, '2', '왕십리역 초역세권 원룸(1000/55)', '0', '1'),
-- ('3', '서울특별시 성동구', '왕십리역 10분거리', '고산자로 284', '2022-08-01', '채광좋은 원룸입니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:48.911475', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '100000', '수도, 가스 별도', '4', '2022-08-19 02:55:48.911481', '550000', NULL, '2', '성동구 원룸(500/55)', '0', '1'),
-- ('4', '서울특별시 성동구', '왕십리역 7분거리', '왕십리로 336', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:53.205136', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '100000', '수도, 가스 별도', '4', '2022-08-19 02:55:53.205142', '600000', NULL, '2', '한양대 후문 원룸(1000/65)', '0', '1'),
-- ('5', '서울특별시 성동구', '초역세권 왕십리역 도보3분', '왕십리로20길 30', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:06.257018', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '100000', '수도, 가스 별도', '4', '2022-08-19 02:56:06.257024', '600000', NULL, '2', '왕십리 원룸(1000/60)', '0', '1'),
-- ('6', '서울특별시 성동구', '왕십리역 코앞', '왕십리로 340', '2022-08-01', '채광좋은 원룸입니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:10.736606', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '650000', NULL, '2', '성동구 원룸(1000/65)', '0', '1'),
-- ('7', '서울특별시 성동구', '왕십리역 코앞', '왕십리로 320', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:20.736606', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '650000', NULL, '2', '왕십리역 원룸(1000/65)', '0', '1'),
-- ('8', '서울특별시 성동구', '왕십리역 7분거리', '왕십리로 332', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:30.736606', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '550000', NULL, '2', '왕십리역 초역세권 원룸(1000/55)', '0', '1'),
-- ('9', '서울특별시 성동구', '왕십리역 바로앞', '무학봉28길 30', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:40.736606', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '450000', NULL, '2', '성동구 원룸(1000/45)', '0', '1'),
-- ('10', '서울특별시 성동구', '왕십리역 도보 4분거리', '왕십리로 241', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:56:50.736606', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '550000', NULL, '2', '한양대 원룸(1000/55)', '0', '1'),
-- ('11', '서울특별시 성동구', '왕십리역 앞', '왕십리로 322', '2022-08-01', '초역세권 원룸 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:57:10.736606', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '600000', NULL, '2', '왕십리 원룸(1000/60)', '0', '1'),
-- ('12', '서울특별시 성동구', '왕십리역 코앞', '왕십리로22길 22', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:57:20.736606', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:56:10.736613', '650000', NULL, '2', '왕십리 원룸(1000/65)', '0', '1'),
-- ('13', '서울특별시 성동구', '왕십리역 5분거리', '왕십리로 309', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:57:36.239433', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:55:06.239444', '500000', NULL, '2', '왕십리역 원룸(1000/50)', '0', '2'),
-- ('14', '서울특별시 성동구', '왕십리역 5분거리', '왕십리로 309', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:57:46.239433', 0, false, true, true, 'ONEROOM', false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:55:06.239444', '500000', NULL, '2', '왕십리역 원룸(1000/50)', '0', '2');
INSERT INTO  rent_article (rent_article_id, address, address_description, address_detail, available_from, content, contract_expires_at, contract_type, created_at, deposit,
                           house_type, is_completed, is_deleted, latitude, longitude, maintenance_fee, maintenance_fee_description,
                           max_floor, modified_at, rent_fee, status, this_floor, title, view_count, user_id, house_facility_id)
VALUES
    ('1', '서울특별시 성동구', '왕십리역 5분거리', '왕십리로 309', '2022-08-01', '사정이 있어서 계약기간 못 채우고 양도합니다.', '2023-02-02', 'MONTHLY', '2022-08-19 02:55:06.239433', 0,'ONEROOM',
     false, false, '110.1', '1010.1231', '50000', '수도, 가스 별도', '4', '2022-08-19 02:55:06.239444', '500000', NULL, '2', '왕십리역 원룸(1000/50)', '0', '1', 1);



INSERT INTO  house_image (house_image_id, image_url, order_in_list, rent_article_id)
VALUES
('1', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/david-schultz-q3809Vl_XDY-unsplash.jpg', '1', '1'),
('2', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/erick-palacio-_B9J6abAHPA-unsplash.jpg', '2', '1');
-- ('3', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-5-i2KABqYe4-unsplash.jpg', '1', '2'),
-- ('4', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-sjvU0THccQA-unsplash.jpg', '2', '2'),
-- ('5', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/filios-sazeides-uckPy5B7K4o-unsplash.jpg', '1', '3'),
-- ('6', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/im3rd-media-CbZ4EDP__VQ-unsplash.jpg', '2', '3'),
-- ('7', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/im3rd-media-CbZ4EDP__VQ-unsplash.jpg', '1', '4'),
-- ('8', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/filios-sazeides-uckPy5B7K4o-unsplash.jpg', '2', '4'),
-- ('9', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-sjvU0THccQA-unsplash.jpg', '1', '5'),
-- ('10', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-5-i2KABqYe4-unsplash.jpg', '2', '5'),
-- ('11', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/erick-palacio-_B9J6abAHPA-unsplash.jpg', '1', '6'),
-- ('12', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/david-schultz-q3809Vl_XDY-unsplash.jpg', '2', '6'),
-- ('13', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/david-schultz-q3809Vl_XDY-unsplash.jpg', '1', '7'),
-- ('14', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/erick-palacio-_B9J6abAHPA-unsplash.jpg', '2', '7'),
-- ('15', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-5-i2KABqYe4-unsplash.jpg', '1', '8'),
-- ('16', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-sjvU0THccQA-unsplash.jpg', '2', '8'),
-- ('17', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/filios-sazeides-uckPy5B7K4o-unsplash.jpg', '1', '9'),
-- ('18', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/im3rd-media-CbZ4EDP__VQ-unsplash.jpg', '2', '9'),
-- ('19', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/im3rd-media-CbZ4EDP__VQ-unsplash.jpg', '1', '10'),
-- ('20', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/filios-sazeides-uckPy5B7K4o-unsplash.jpg', '2', '10'),
-- ('21', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-sjvU0THccQA-unsplash.jpg', '1', '11'),
-- ('22', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/febrian-zakaria-5-i2KABqYe4-unsplash.jpg', '2', '11'),
-- ('23', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/erick-palacio-_B9J6abAHPA-unsplash.jpg', '1', '12'),
-- ('24', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/david-schultz-q3809Vl_XDY-unsplash.jpg', '2', '12'),
-- ('25', 'https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/david-schultz-q3809Vl_XDY-unsplash.jpg', '1', '14');
--
-- INSERT INTO rent_article_bookmark (id, rent_article_rent_article_id, user_user_id)
-- VALUES
-- (1, 13, 1),
-- (2, 14, 1),
-- (3, 1, 2);

INSERT INTO wanted_article_bookmark (id, wanted_article_id, user_user_id)
VALUES
(1, 13, 1),
(2, 14, 1),
(3, 1, 2);
