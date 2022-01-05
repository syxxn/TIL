##  Call By Value & Call By Reference

 #### Call By Value (Pass By Value)

값에 의한 호출 : 함수 호출 시 전달되는 변수의 값을 "복사"하여 함수의 인자로 전달한다. 이 복사된 인자값은 local value의 특성을 가진다.

> Java는 Call By Value 방식으로 인자값을 받는다.
>
> 객체를 전달받고 그 객체를 수정하면 실제 값이 변경되어 Call By Reference라고 생각할 수 있지만, 자바에서는 직접적인 참조를 넘기는 게 아닌 주소값을 복사해서 넘긴다.

```java
private static void main() {
    A a1 = new A(1);
    A a2 = new A(2);
    
    run(a1, a2);
}

public A {
    int value;
    A(int i) {
        this.value = i;
    }
}

private void run(A arg1, A arg2) { //a1과 arg1는 같은 곳을 가리키지만 독립된 변수이다.
    //변수는 stack 영역에 생성되고, 주소는 heap 영역에 생성된다.
    arg1.value = 111;
    arg2 = arg1;
}
```

<br>

#### Call By Reference (Pass By Reference)

참조에 의한 호출 : 함수 호출 시 인자로 전달되는 변수의 주소값을 전달한다. 전달받은 값을 직접 참조한다. 따라서 함수 안에서 인자의 값이 변경되면, 실제 객체의 값도 함께 변경된다.

<br>

C++은 Call By Reference를 사용한다.

