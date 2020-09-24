# DTO DAO

### DTO (Data Transfer Object)

**DB에 접근하여 정보 가져옴** `Repository`

> 추상적임



### DAO(Data Access Object)

**데이터 형태 변환**  `payload` (`request`,  `response`)

> payload는 받거나 주는 데이터 형식을 정함



최종적으로 Entity의 데이터를 수정하는 것으로 DAO에서도 쓸 수 있다

예를 들어 회원가입을 하고, 로그인을 할 때,

회원가입에서는 성명, 나이, 아이디, 비밀번호 등이 필요하지만 로그인에서는 아이디와 비밀번호만 있으면 된다. 



이 때, `Entity(DAO) 필드에 있는 데이터 중 필요한 정보만 사용하는 것을 DTO(payload)`라고 한다

만약, DTO를 사용하지 않는다면, Entity(DAO)에 들어간 모든 정보를 갖고와야 한다



