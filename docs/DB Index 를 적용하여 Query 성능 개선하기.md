DB Index 를 적용하여 Query 성능 개선하기 <br><br>

1.	인덱스란? <br>
-	인덱스 정의 : 추가적인 쓰기 작업과 저장 공간을 활용하여 데이터베이스 테이블의 검색 속도를 향상시키기 위한 자료구조
-	인덱스 장단점 :
-	장점: 테이블을 검색하는 속도와 성능이 향상된다.
-	단점: <br>
     	1. 인덱스를 관리하기 위한 추가 작업이 필요<br>
     	2. 추가 저장 공간 필요<br>
     	3. 잘못 사용하는 경우 오히려 검색 성능 저하<br>
     	4. 데이터를 가져올때는 성능은 빨라지지만 데이터의 삽입, 변경등이 일어날 때 매번 인덱스가 변경되면 성능이 떨어질 수 있음

2.	인덱스 설계 원칙
-	카디널리티가 높으면(↑) 인덱스 설정에 좋은 컬럼이다.
-	선택도 (Selectivity): 선택도가 낮으면(↓) 인덱스 설정에 좋은 컬럼이다.
-	조회 활용도: 조회 활용도가 높으면(↑) 인덱스 설정에 좋은 컬럼이다.
-	수정 빈도: 수정 빈도가 낮으면(↓) 인덱스 설정에 좋은 컬럼이다.

3.	MySQL B-Tree, Hash 인덱스 구조

      비교 항목 |	B-Tree |	Hash |
      | ----------------------- | ---------------------- | ----------------------- | 
      검색 방식	|트리 구조 탐색 (정렬된 순서 유지) |	해시 함수로 바로 접근 (순서 없음)
      사용 조건	|범위 조회, 정렬, LIKE 'abc%' |	= 또는 IN 같은 정확한 값 조회
       성능 |	O(log N) (트리 탐색)| O(1) (해시 매칭 Key, Value형태의 자료 구조)
        정렬 지원	| (ORDER BY, GROUP BY 가능) |	(순서 유지 불가)
        범위 검색 (BETWEEN, <, >)	| 가능	| 불가능
       공간 효율성	|상대적으로 더 큼 |	더 작음 (간단한 해시 테이블)
      갱신 비용	|높음 (INSERT, UPDATE, DELETE 시 트리 재구성)|낮음 (단순한 해시 테이블 갱신)




4.	단일 컬럼 인덱스 vs 복합 인덱스

      비교 항목	|단일 컬럼 인덱스	|복합(다중) 컬럼 인덱스 |
      | ----------------------- | ---------------------- | ----------------------- |
      인덱스 구성|	하나의 컬럼으로 구성된 인덱스    | 2개이상의 컬럼으로 구성된 인덱스
      사용 예시 |	WHERE user_id = ? | WHERE user_id = ? AND order_date = ?
      성능	|특정 컬럼 검색 최적화	| 특정 컬럼 조합 검색 최적화
      정렬 가능성 |	단일 컬럼 정렬 (ORDER BY user_id)	|첫 번째 컬럼을 기준으로 정렬 (ORDER BY user_id, order_date)
     공간 사용량 |	작음	 | 더 많음
      갱신| 비용	상대적으로 적음 |	더 많음 (여러 컬럼 관리 필요)
      범위 검색 (BETWEEN)|	가능 |	첫 번째 컬럼만 가능



5.	실행 계획 (EXPLAIN / ANALYZE)<br>
      EXPLAIN : 쿼리의 맨 앞에 EXPLAIN을 붙혀 실행하면, 상세한 실행 계획을 확인할 수 있다.  쿼리를 실행하지 않고, 실행 과정을 예측<br><br>
      EXPLAIN ANALYZE : 쿼리의 실행 계획과 단계별 소요된 시간 정보를 확인할 수 있다.<br><br>

6.	커버링 인덱스란?
      인덱스만 읽어서 쿼리를 모두 처리할 수 있을 때(커버링 인덱스) 표시된다.
      인덱스로만 쿼리를 처리할 수 있으면 디스크 접근이 필요 없어지므로 성능이 향상된다.<br><br>
7.	서비스 주요 조회 쿼리
      인덱스만 읽어서 쿼리를 모두 처리할 수 있을 때(커버링 인덱스) 표시된다.
      인덱스로만 쿼리를 처리할 수 있으면 디스크 접근이 필요 없어지므로 성능이 향상된다.
      더미 데이터 1000개를 입력하고 실험을 진행했다. <br><br>

-	코드문: Point findByUserId(long userId);<br><br>
     쿼리: SELECT * FROM user_point WHERE user_id = 1;<br><br>
     설명: 사용자 포인트 조회

인덱스 적용전 <br><br>
15:17:25	SELECT * FROM user_point WHERE user_id = 1 LIMIT 0, 1000	9 row(s) returned	0.000 sec / 0.000 sec <br><br>
인덱스 적용후 <br><br>
인덱스 : CREATE INDEX IDX_USER_POINT ON user_point (user_id, point); <br><br>
15:17:51	SELECT * FROM user_point WHERE user_id = 1 LIMIT 0, 1000	9 row(s) returned	0.016 sec / 0.000 sec <br><br>
결과: 사용자 포인트 조회는 인덱스 적용시 0.016sec 증가했다. 성능이 더 떨어졌다. 선택도가 높은 컬럼으로 구성한 인덱스라서 그런거 같다. <br><br>

- 코드문: List< Coupon > findAll();<br><br>
     쿼리: SELECT * FROM `coupon` WHERE expiration_date = '2024-03-04' AND percent = 20;<br><br>
     설명: 쿠폰 조회<br><br>

인덱스 적용전<br><br>
15:25:31	SELECT * FROM `coupon` WHERE expiration_date = '2024-03-04' AND percent = 20 LIMIT 0, 1000	0 row(s) returned	0.015 sec / 0.000 sec<br><br>

인덱스 적용후  <br><br>
인덱스 : CREATE INDEX IDX_COUPON_EXPIRATION ON coupon (expiration_date, percent);<br><br>

15:25:48	SELECT * FROM `coupon` WHERE expiration_date = '2024-03-04' AND percent = 20 LIMIT 0, 1000	0 row(s) returned	0.000 sec / 0.000 sec<br><br>
결과: 쿠폰 조회는 인덱스 적용시 무려 0.015초가 단축되었다. Expiration_date 같은 카디널리티가 높은 컬럼이 포함되어서 그런거 같다.<br><br>

- 코드문: List<UserCoupon> getUserCoupon(@Param("userId")long userId);<br><br>
     쿼리: SELECT * FROM `user_coupon` WHERE coupon_id = 1 AND statement = 'USED';<br><br>
     설명: 사용자 쿠폰 조회<br><br>

인덱스 적용전 <br><br>
15:08:56	SELECT * FROM `user_coupon` WHERE coupon_id = 1 AND statement = 'USED' LIMIT 0, 1000	4 row(s) returned	0.031 sec / 0.000 sec <br><br>

인덱스 적용후 <br><br>
인덱스 : CREATE INDEX IDX_USER_COUPON ON user_coupon (coupon_id, statement); <br><br>
15:10:37	SELECT * FROM `user_coupon` WHERE coupon_id = 1 AND statement = 'USED' LIMIT 0, 1000	4 row(s) returned	0.016 sec / 0.000 sec <br><br>
결과: 인덱스 적용후가 조회시간이 무려 0.015 sec 감소했다.<br><br>

- 코드문: List< Product > findAll(); <br><br>
쿼리: SELECT * FROM `product` WHERE name = 'apple' AND price = 1000; <br><br>
설명: 상품 조회 <br><br>

인덱스 적용전 <br><br>
15:32:10	SELECT * FROM `product` WHERE name = 'apple' AND price = 1000	1 row(s) returned	0.016 sec / 0.000 sec<br><br>
인덱스 적용후
15:32:22	SELECT * FROM `product` WHERE name = 'apple' AND price = 1000	1 row(s) returned	0.000 sec / 0.000 sec<br><br>

결과: 상품조회는 인덱스 적용 적용후 조회시간이 0.016sec 감소했다. 이름과 가격 컬럼은 비교적 카디널리티가 높은편이라고 생각이든다. -> 그대로였다로 변경<br><br>

- 코드문: List< ProductSale > findTop5(); <br><br>
     쿼리: SELECT * FROM `product_sales` WHERE product_id = 1 AND quantity_sold = 2;<br><br>
     설명: Top5 상품 조회<br><br>

인덱스 적용전<br><br>
21:07:41	SELECT * FROM `product_sales` WHERE product_id = 1 AND quantity_sold = 2 LIMIT 0, 1000	1 row(s) returned	0.000 sec / 0.000 sec<br><br>

인덱스 적용후<br><br>
인덱스 : CREATE INDEX IDX_PRODUCT_SALES ON product_sales (product_id, quantity_sold);<br><br>
21:07:56	SELECT * FROM `product_sales` WHERE product_id = 1 AND quantity_sold = 2 LIMIT 0, 1000	1 row(s) returned	0.000 sec / 0.000 sec<br><br>
결과: 인덱스 적용전과 후 시간이 동일했다. 두차례 실험해도 똑같았다. 수량은 카디널리티가 낮아서 그런가 보다.<br><br>

- 코드문: List< Order > findByUserId(@Param("userId") long userId); <br><br>
     쿼리: SELECT * FROM order_history WHERE product_id = 1 AND order_status = 'ORDERED';<br><br>
     설명: 주문 목록 조회 <br><br>

인덱스 적용전<br><br>
16:03:10	SELECT * FROM order_history WHERE product_id = 1 AND order_status = 'ORDERED' LIMIT 0, 1000	0 row(s) returned	0.016 sec / 0.000 sec<br><br>
인덱스 적용후<br><br>
인덱스 : CREATE INDEX IDX_ORDER_HISTORY ON order_history (product_id, order_status);<br><br>
16:03:44	SELECT * FROM order_history WHERE product_id = 1 AND order_status = 'ORDERED' LIMIT 0, 1000	0 row(s) returned	0.000 sec / 0.000 sec<br><br>
결과: 인덱스 적용 후가 0.016 sec 감소했다.<br><br>

-	코드문: List< OrderProduct > findByUserId(@Param("userId") long userId);<br><br>
     쿼리: SELECT * FROM order_product WHERE productId = 12 AND userId = 12;<br><br>
     설명: 주문 목록 조회(수량 포함)<br><br>

인덱스 적용전
16:11:00	SELECT * FROM order_product WHERE productId = 12 AND userId = 12 LIMIT 0, 1000	0 row(s) returned	0.000 sec / 0.000 sec<br><br>

인덱스 적용후
인덱스 : CREATE INDEX IDX_ORDER_PRODUCT ON order_product (userId, productId);<br><br>
16:11:44	SELECT * FROM order_product WHERE productId = 12 AND userId = 12 LIMIT 0, 1000	0 row(s) returned	0.015 sec / 0.000 sec<br><br>

결과: 인덱스 적용 전후가 똑같거나 0.015sec 더 오래 걸렸다. <br><br>

-	코드문: List< Payment > findByUserId(long userId);<br><br>
     쿼리: SELECT * FROM payment WHERE user_id = 1 AND payment_status = 'PAYED'<br><br>
     설명: 결제 목록 조회<br><br>

인덱스 적용전<br><br>
16:13:27	SELECT * FROM payment WHERE user_id = 1 AND payment_status = 'PAYED' LIMIT 0, 1000	0 row(s) returned	0.015 sec / 0.000 sec<br><br>

인덱스 적용후<br><br>
인덱스 : CREATE INDEX IDX_PAYMENT ON payment (user_id, payment_status, created_at);<br><br>
16:13:51	SELECT * FROM payment WHERE user_id = 1 AND payment_status = 'PAYED' LIMIT 0, 1000	0 row(s) returned	0.000 sec / 0.000 sec<br><br>
결과: 인덱스를 적용하니 0.015초가 단축했다. 때론 같을때도 있다.<br><br>
