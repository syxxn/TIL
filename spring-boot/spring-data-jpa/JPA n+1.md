## JPA n+1

X:N 관계에서 JPA의 Entity 조회 시 1번, 다른 연관관계에 접근할 때 n번의 쿼리가 발생하는 비효율적인 상황을 n+1 문제라고 한다.

n+1 문제는 다음 상황에서 발생한다.

+ 즉시 로딩으로 모든 데이터를 가져오는 경우
+ 지연 로딩으로 데이터를 가져온 이후에 하위 엔티티를 다시 조회하는 경우

### 해결방법

#### 1) Batch Fetch Size

BatchSize는 JPA의 성능 개선을 위한 옵션 중 하나로서, 여러 개의 프록시 객체를 조회할 때 where 절이 같은 여러 select 쿼리들을 하나의 in 쿼리로 만들어준다.

```yaml
# application.yml

spring:
	jpa:
		properties:
			hibernate.default_batch_fetch_size: 1000
```

```java
# Entity.class

@BatchSize(size=1000) // in절에 들어갈 요소의 최대 개수
@OneToMany(mappedBy = "parent")
private List<Child> children = new ArrayList<>(); //결과가 null인 것보다 list가 empty인 것이 직관적임
```

배치 사이즈를 정해주지 않으면, 각각의 쿼리가 날라갈 것이다.

```sql
select * from parent;
select * from child where child.parent_id=1
select * from child where child.parent_id=2
```

한번에 불러오는 요소의 갯수를 정할 수 있다.

```sql
select * from parent
select * from child where child.parent_id in(1,2)
```

batch fetch가 활성화되면 hibernate는 많은 쿼리를 준비하게 된다. 따라서 전역적인 batch fetch size는 작은 숫자로하고, 커다란 batch 작업이 필요할 때는 @BatchSize를 이용해 개별적으로 설정하는 것이 이상적이다. 

#### 2) Fetch Join

JPQL에서 성능 최적화를 위해 제공하는 기능으로, 한 번의 쿼리로 연관 엔티티도 함께 select하여 모두 영속화시키는 방법이다.

```sql
@Query("SELECT distinct t FROM Team t join fetch t.members") // JPA가 제공하는 Pageable 기능 사용 불가
```

```java
queryFactory.selectFrom(member)
                .join(member.team, team)
                .on(team.eq(member.team))
                .fetch();
```

#### 3) EntityGraph

Spring Data JPA에서 fetch join을 어노테이션으로 사용할 수 있도록 만들어 준 기능이다.

```
@EntityGraph(attributePaths = {"addresses"}, type = EntityGraph.EntityGraphType.LOAD)
Optional<User> findWithAddressesById(Long userId);
```

+ attributePaths는 매핑된 객체의 필드명을 적으면 된다.
+ type은 LOAD와 FETCH 두가지가 있다.
  + LOAD : entity graph에 명시하지 않은 속성은 명시한 fetch type대로,
  + FETCH : entity graph에 명시하지 않은 속성은 LAZY로

left outer 조인으로 연관관계를 불러온다.