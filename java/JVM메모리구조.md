## JVM(Java Virtual Machine)

자바 프로그램을 실행시킨 다는 것은 JVM을 실행시킨 후 그 위에서 자바 바이트 코드를 실행시킨다는 것이라고 한다.

**JVM은 실제 운영체제와 자바 프로그램 사이에서 중계자 역할**을 한다. 그렇기 때문에 Java는 운영체제에 독립적일 수 있는 것이다.

![image](https://user-images.githubusercontent.com/64132926/173190173-98bc565e-a4d6-4116-846b-192342d4a341.png)


## JVM 구조

![image](https://user-images.githubusercontent.com/64132926/173190193-c39e35b9-6343-4c1d-a781-e9b510fdadea.png)

[https://velog.io/@ditt/JavaJVM-메모리-영역](https://velog.io/@ditt/JavaJVM-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD)

JVM의 구조는 위의 사진과 같다.

**동적 할당**이란, 프로그래밍에서 실행 시간 동안 사용할 메모리 공간을 할당하고 사용이 끝나면 반납하는 것을 말한다.

C언어에서는 동적할당을 개발자가 malloc(), free() 등의 함수를 사용하여 프로그래머가 직접 할당과 해제해야 한다.

반면에 자바는 **JVM의 GC(Garbage Collector)이 담당**합니다. GC는 자바 프로그램이 실행 중에 동적으로 생성한 객체가 사용되었는지의 여부를 판단하여 할당된 메모리를 해제(Garbage Collection)하는 역할을 한다. 

자동으로 메모리 관리가 되기 때문에 사실상 메모리 구조를 몰라도 프로젝트를 진행할 수 있다. 하지만, JVM의 메모리 구조를 아느냐 모르냐에 따라서 메모리를 더 최적화할 수 있느냐 없느냐의 차이가 발생할 수 있다.

Runtime Data Area는 JVM이 실행되면서 운영체제로부터 할당받는 메모리이다.

- **Method Area(Static Area)**
    
    JVM이 읽은 클래스와 인터페이스의 상수 풀(고정 영역)을 말한다. 즉, 클래스의 구성요소인 정적 변수, 생성자, 메소드, 멤버 변수가 이 공간에 저장된다.
    
- **Heap Area**
    
    자바 프로그램이 실행되면서 동적으로 생성된 객체, `new` 연산자로 생성된 객체가 저장되는 공간이다. 이곳에 생성된 객체들은 다른 객체의 필드 또는 스택에 존재하는 다른 메소드에 의해 참조될 수 있다.
    
- **Stack Area**
    
    컴파일 타임에 할당되는 공간이다.
    
    메소드가 호출되면 이 영역에 할당되며, 먼저 들어온 것이 가장 마지막으로 나가는 구조(FILO)이다. 스레드가 생성되면 각 스레드마다 하나의 스택을 할당받게 된다. 
    스택에서는 메소드 내부의 지역변수도 저장이 되며, 힙 영역의 객체를 참조할 수 있다. 
    
- PC Register
    
    현재 스레드가 실행되는 부분의 주소와 명령을 저장하는 영역이다.
    
- Native Method Stack Area
    
    자바 이외의 언어로 작성된 네이티브 코드를 위한 공간이다.
    

![image](https://user-images.githubusercontent.com/64132926/173190226-4e7bd226-b0a6-4ffb-9bed-4e111ed5a10e.png)

Runtime Data Area를 하나의 사진으로 표현하면 다음과 같다.

- 정적 변수는 method area(static area)에 생성되어 모든 메소드에서 접근이 가능하다.
- 객체가 생성되는 장소는 heap area지만, 그것을 참조하는 참조변수는 stack area에 존재한다.
- 참조가 null이 되면, 이 객체는 더 이상 쓰임새가 없다고 여겨서 GC가 슥삭 청소해준다.


추가적으로 자바스크립트는 값을 선언할 때 자동으로 메모리를 할당한다고 한다.

![image](https://user-images.githubusercontent.com/64132926/173190243-ff33e0bc-df11-4442-aec1-ba2b10125844.png)

[https://velog.io/@code-bebop/JS-메모리-구조?fbclid=IwAR1BcXjqyvFLB1jWWUtVrVDRYA3H7gT_0juELF9yHmwqZdIHXapb0juUH-o](https://velog.io/@code-bebop/JS-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EA%B5%AC%EC%A1%B0?fbclid=IwAR1BcXjqyvFLB1jWWUtVrVDRYA3H7gT_0juELF9yHmwqZdIHXapb0juUH-o)

- **Code Area** - Method Area
    
    실행할 JS 코드를 저장한다.
    
- **Call Stack** - Stack Area
    
    실행 중인 함수를 추적하며 계산을 수행하고 지역 변수를 저장한다. 
    
- **Heap** - Heap
    
    참조 타입들이 할당되는 곳이다. LIFO 정책을 따르지 않고 랜덤하게 배치된다. 또한, JS 엔진의 메모리 관리자가 항상 관리(Garbage Collection)한다.
