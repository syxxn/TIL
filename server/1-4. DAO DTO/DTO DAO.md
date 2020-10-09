# DTO DAO

### DTO (Data Transfer Object)

**DB에 접근하여 정보 가져옴** `Repository`

> 추상적임



데이터베이스에 접속하여 명령 전송을 전담하는 클래스

> DTO에 정보를 저장하기 위해 데이터베이스와의 연결을 전담

: **Database의 data에 접근하기 위한 객체**

> DB에 접속하는 객체를 전형으로 커넥션을 하나만 만들고, 모든 페이지에서 그 객체를 호출하여 DB와 연결하는 것이 DAO객체이다

+ Service와 DB를 연결하는 고리 역할

+ **SQL 사용(개발자가 직접 코딩)하여 DB에 접근한 후 적절한 CRUD API를 제공**

  > JPA 대부분 기본적인 <u>CRUD method</u>를 제공하고 있다
  >
  > > CRUD
  > >
  > > : Create(생성) Read(읽기) Update(갱신) Delete(삭제)
  > >
  > > + CrudRepository ≒ JpaRepository
  > > + `(레포지토리 이름)<(기본키가 담긴 패키지),(기본키 자료형)>`
  >
  > `public interface QuestionRepository extend CrudRepository<Question,Long>{}`


> 데이터베이스에 로그인, 입력, 받아오기, 수정, 삭제 등의 작업들을 정의한 클래스를 DAO라고 함
>
> 이 때 매개변수나 반환형을 DTO로 처리하여 코드를 간결하게 만듦



```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDao {
	
    public void add(TestDto dto) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id,name,password) value(?,?,?)");


        preparedStatement.setString(1, dto.getName());
        preparedStatement.setInt(2, dto.getValue());
        preparedStatement.setString(3, dto.getData());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        
        connection.close();

    }//1. DB와 연결할 Connection을 가져옴
    //2. 어떤 DB를 사용할 것이며, 어떤 드라이브와 로그인 정보를 사용할 것인지
    //3. 작업이 끝나면 사용한 리소스를 시스템에 돌려줌
}
```





![image info](C:/Users/user/Desktop/git syxxn/TIL/basic/2. client&server/2. 서버/구조.png)



### DAO(Data Access Object)

**데이터 형태 변환**  `payload` (`request`,  `response`)

> payload는 받거나 주는 데이터 형식을 정함



최종적으로 Entity의 데이터를 수정하는 것으로 DAO에서도 쓸 수 있다

예를 들어 회원가입을 하고, 로그인을 할 때,

회원가입에서는 성명, 나이, 아이디, 비밀번호 등이 필요하지만 로그인에서는 아이디와 비밀번호만 있으면 된다. 



이 때, `Entity(DAO) 필드에 있는 데이터 중 필요한 정보만 사용하는 것을 DTO(payload)`라고 한다

만약, DTO를 사용하지 않는다면, Entity(DAO)에 들어간 모든 정보를 갖고와야 한다



**레코드를 하나의 자료형으로 다루기 위해 정의한 클래스**

+ **계층간 데이터 교환을 위한 객체(Java Beans)**
+ **DB에서 데이터를 얻어 Service나 Controller 등으로 보낼 때 사용하는 객체**
+ DB에서 꺼낸 값을 임의로 변경할 필요가 없기 때문에 DTO 클래스에는 setter가 없음
+ `Reques`t와` Response`용 DTO는 View를 위한 클래스
  + 자주 변경이 필요함
  + Presentation Model
  + `toEntity()`메소드를 통해서 DTO에서 필요한 부분을 이용하여 Entity로 만든다
  + Controller Layer에서 Response DTO 형태로 Client에 전달



##### +VO(value object)

: VO는 DTO와 동일한 개념이지만 read only 속성을 갖는다.

VO는 특정한 비즈니스 값을 담는 객체이고, DTO는 Layer간의 통신 용도로 오고가는 객체



```java
public class TestDto {

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
```

