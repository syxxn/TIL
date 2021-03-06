# 뷰(View)

**사용자의 권한에 맞는 데이터들만 빼와서 이루어진 가상 테이블**



### 뷰(View) 테이블이란?

1. 뷰는 사용자에게 접근이 허용된 자료만을 제한적으로 보여주기 위해 하나 이상의 기본 테이블로부터 유도된, 이름을 가지는 가상 테이블이다.
2. 뷰는 실제 데이터가 하드웨어에 저장되는 것은 아니지만 뷰를 통해 데이터를 관리할 수 있다.
3. 뷰는 저장장치 내에 물리적으로 존재하지 않지만 사용자에게 있는 것처럼 간주된다.
4. 뷰는 데이터 보정작업, 처리과정 시험 등 임시적인 작업을 위한 용도로 활용된다.
5. 뷰는 조인문의 사용 최소화로 사용상의 편의성을 최대화 한다.



### 뷰 테이블의 사용 목적?

+ 보안성 : 보안관리를 목적으로 활용한다
+ 편의성 : 사용상의 편의를 목적으로 활용한다
+ 속도 향상 : 수행속도의 향상의 목적으로 활용한다
+ 활용성 : SQL의 성능을 향상시킬 목적으로 활용한다
+ 임시적인 작업을 위해 활용한다



### 뷰(View)의 특징

1. 뷰는 기본테이블로부터 유도된 테이블이기 때문에 기본 테이블과 같은 형태의 구조를 사용하며, 조작도 기본 테이블과 거의 같다.
2. 뷰는 가상 테이블이기 때문에 물리적으로 구현되어 있지 않다.
3. 데이터의 논리적 독립성을 제공할 수 있다.
4. 필요한 데이터만 뷰로 정의해서 처리할 수 있기 때문에 관리가 용이하고 명령문이 간단해진다.
5. 뷰를 통해서만 데이터에 접근하게 되면 뷰에 나타나지 않는 데이터를 안전하게 보호하는 효율적인 기법으로 사용할 수 있다.
6. 기본 테이블의 기본키를 포함한 속성(열) 집합으로 뷰를 구성해야지만 삽입, 삭제, 갱신, 연산이 가능하다.
7. 일단 정의된 뷰는 다른 뷰의 정의에 기초가 될 수 있다.
8. 뷰가 정의된 기본 테이블이나 뷰를 삭제하면 그 테이블이나 뷰를 기초로 정의된 다른 뷰도 자동으로 삭제된다.



### 뷰(View) 사용시 장단점

#### 장점

1. 논리적 데이터 독립성을 제공한다
2. 동일 데이터에 대해 동시에 여러 사용자의 상이한 응용이나 요구를 지원해 준다.
3. 사용자의 데이터관리를 간단하게 해준다.
4. 접근 제어를 통한 자동 보안이 제공된다.

#### 단점

1. 독립적인 인덱스를 가질 수 없다.
2. ALTER VIEW문을 사용할 수 없다. 즉, 뷰의 정의를 변경할 수 없다.
3. 뷰로 구성된 내용에 대한 삽입, 삭제, 갱신, 연산에 제약이 따른다.



### 간단한 예제

#### 뷰 정의문

`CREATE VIEW 뷰이름[(속성이름[,속성이름])] AS SELECT문;`

```SQL
-- 고객 테이블에서 주소가 서울시인 고객들의 성명과 전화번호를 서울고객이라는 뷰로 만들어라
CREATE VIEW 서울고객(성명, 전화번호)
AS SELECT 성명 전화번호
FROM 고객
WHERE 주소 = '서울시';
```

#### 뷰 삭제문

`DROP VIEW 뷰이름 RESTRICT or CASCDE;`

```SQL
-- 서울 고객이라는 뷰를 삭제해라
DROP VIEW 서울고객 RESTRICT;
```

