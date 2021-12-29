## @GeneratedValue와 @GenericGenerator

[EntryDsm의 새 프로젝트](https://github.com/EntryDSM/Raise-Percent/blob/main/src/main/java/kr/hs/entrydsm/raisepercent/domain/notification/domain/Notification.java)에서 Entity 설정하는 코드를 보다가 기존 @Id를 설정했던 어노테이션을 다르게 사용하길래 어떤 의미로 사용하는 것인지 찾아보았다.

```java
@Id
@GeneratedValue(generator = "uuid2")
@GenericGenerator(name = "uuid2", strategy = "uuid2")
@Column(columnDefinition = "BINARY(16)") //uuid는 보통 binary로 저장한다.
private UUID uuid;
```

<br>

#### @GeneratedValue

generator와 strategy는 생성된 값을 얻는 방법을 설명한다.

+ generator : @GenericGenerator에서 설정한 name을 말한다.

+ strategy : 기본키를 할당해주는 방법 설정하기

  > 1. GenerationType.IDENTITY
  > 2. GenerationType.SEQUNCE
  > 3. GenerationType.TABLE
  > 4. GenerationType.AUTO

#### @GenericGenerator

엔티티 저장 시 strategy에 지정한 클래스에서 생성한 기본키를 사용하도록 한다.

+ name : @GeneratedValue의 generator modifier(생성기 수식어)에서 사용할 이름

+ strategy : IdentifierGenerator 인터페이스를 구현한 클래스 이름

  > uuid2로 설정하면 org.hibernate.id.UUIDGenerator를 이용해서 키값을 생성한다.

  > hibernate에서 제공하는 다른 전략은 *[DefaultIdentifierGeneratorFactory](https://github.com/manuelbernhardt/hibernate-core/blob/master/hibernate-core/src/main/java/org/hibernate/id/factory/DefaultIdentifierGeneratorFactory.java)*에서 확인할 수 있다.

<br>

#### 둘의 차이점은 무엇인가?

```
The @GeneratedValue annotation denotes that a value for a column, which must be annotated with @Id is generated. The elements strategy and generator on the annotation describe how the generated value is obtained.

The generator element of the @GeneratedValue annotation denotes the name of the primary key generator. Increment is then defined in the next annotation @GenericGenerator. @GenericGenerator is a hibernate annotation used to denote a custom generator, which can be a class or shortcut to a generator supplied by Hibernate.


[출처] https://stackoverflow.com/questions/18205574/difference-between-generatedvalue-and-genericgenerator
```

@GeneratedValue는 @Id를 달아야 하는 칼럼에 대한 값을  `generator`와 `strategy` 요소로 얻는 방법을 선택한다. @GenericGenerator에서 PK의 생성기의 이름을 나타낸다. @GenericGenerator는 커스텀 생성기를 나타내기 위한 hibernate의 주석으로, 생성기의 클래스 또는 지름길이 된다. 아이디 필드와 키생성 전략을 매핑해주는 데 사용한다.

> IdGenerator를 상속하여 Custom Generator를 만들 수 있다.