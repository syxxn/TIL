## Spring Data JPA에서 GroupBy 처리하기

Query 메소드에서는 Group By 키워드를 찾아볼 수가 없어서 찾아보았다.

> https://docs.spring.io/spring-data/jpa/docs/1.10.1.RELEASE/reference/html/#jpa.sample-app.finders.strategies

<br>

Spring Data JPA에서 Group By를 사용하기 위해서는 다음과 같은 방법이 있다.

1. Stream의 collect() 사용하기
2. JPQL(`@Query`)에서 직접 GROUP BY 선언하기
3. Native Query(`@Query("", nativeQuery = true)`)에서 GROUP BY 사용하기
4. QueryDSL에서 .groupBy() 사용하기

<br>

데이터가 많은 경우 모든 데이터를 가지고 비교를 해야하는 Stream은 성능이 좋지 않다.

 JPQL, Native Query, QueryDSL은 실제로 같은 쿼리를 사용하기 때문에 응답시간이 비슷하다.

QueryDSL은 초기 쿼리 파싱 때문에 초반에는 시간이 조금 더 걸릴 수 있다.



