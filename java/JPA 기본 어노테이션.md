## JPA 기본 어노테이션

#### @Entity

데이터베이스의 테이블과 일대일로 매칭되는 객체 단위이다.

Entity 객체의 인스턴스 하나가 테이블에서 하나의 레코드 값을 가진다.

+ `spring.jpa.hibernate.ddl-auto` 설정이 create 혹은 update로 되어 있을 경우 EntityManager가 자동으로 DDL을 수행해 테이블을 생성한다. 그렇지 않은 경우엔 테이블을 미리 생성해 두어야 한다.

+ public 또는 protected 접근을 가진 기본생성자가 필수로 필요하다.
  hibernate 등의 프레임워크에서 reflection을 하여 객체를 생성할 때 기본 생성자를 호출하기 때문이다.
+ under_score로 테이블명을 설정하기 위해서는 @Table을 사용해야 한다.

<br>

#### @Column

테이블의 컬럼과 일대일로 매칭되는 내부변수를 가리킨다.

+ Entity 클래스에 정의된 모든 내부변수는 기본적으로 @Column을 포함하고 있기 때문에, 별다르게 설정할 것이 없는 경우에는 사용하지 않아도 된다.

+ String의 경우 length를 설정하지 않으면, varchar(255)로 설정된다.
+ under_score로 칼럼명을 설정하기 위해서는 name=속성으로 설정해야 한다.

<br>

#### @Id

테이블의 PK값을 명시적으로 지정하는 어노테이션이다.

+ 복합키의 경우 모든 PK에 설정해주면 된다.

<br>

#### @GeneratedValue

PK를 자동생성하기 위해 사용하는 어노테이션이다. 데이터베이스의 AI 속성을 가진다.

+ MySQL의 경우 `strategy=GenerationType.IDENTITY`, 
  ORACLE의 경우 `strategy=GenerationType.SEQUENCE`를 사용한다. > @SequenceGenerator가 필요하다.
+ IDENTITY : 마지막 PK 값에서 자동으로 +1을 해준다.

<br>

#### @Enumerated

Java의 enum 형태를 어떤 형태로 저장할 것인지 설정할 대 사용한다.

+ `EnumType.STRING`을 사용하여 저장하면 데이터를 한 눈에 알아보기 쉽다. 하지만 데이터가 많은 경우에는 문제(메모리 용량)가 될 수 있다.
+ enum의 인덱스는 1부터 저장된다. 0은 유효하지 않은 값의 index를 나타낸다.

<br>

#### @Transient

Entity 객체에서는 필요하지만 데이터베이스에서는 필요 없는 속성일 때 ignore하기 위해 사용하는 어노테이션이다.