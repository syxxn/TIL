## HTML 기본 태그

### block 속성 태그

#### div

+ HTML 요소들을 하나로 묶어 CSS를 변경하거나 JS로 특정 작업을 수행하기 위한 일종의 컨테이너를 말한다.

+ 아무 목적 없이 영역을 나눌 때 사용할 수 있지만, 더 세부적으로 역할을 부여하기 위해 section과 article 태그를 사용하는 것을 권장한다.

  ```html
  <div class=""></div>
  <div id=""></div>
  ```

#### section

+ HTML에 포함된 독립적인 영역으로 보통 제목 요소(H1~H6)와 자식 요소를 포함하고 있다.
+ 단순한 스타일링이 목적이라면 div를 사용해야 한다.
+ 논리적으로 관계 있는 문서 혹은 요소를 분리할 때 사용한다.

```html
<section>
	<h2>Section1</h2>
    <p>포항항 우하하</p>
</section>
```

#### article

+ 문서 혹은 요소가 독립적으로 존재할 수 있을 때 사용한다.

```html
<article>
	<h1>ArticleHeading</h1>
    <section>
    	...
    </section>
</article>
```

공지사항, FAQ, 고객센터 같은 게시판 영역에는 article을 써주는 것이 좋고, 그외 논리적인 구분은 모두 section으로 처리하면 된다. 블럭 요소 자체가 필요한 것이라면 div를 사용한다.

<br>

### 문서 정의 태그

#### header

+ 웹 페이지가 어떤 웹 페이지인지를 정의하는 문서 영역이다.

```html
<header>Hello World</header>
```

#### footer

+ footer는 문서 작성자에 대한 정보가 담기는 영역으로, 사업자 번호, 사업자 대표, 사업장 위치 등이 포함되는 영역이다.

```html
<footer>
	<p>개발자 : 이승윤</p>
    <p>사업장 : 대덕소프트웨어마이스터고</p>
</footer>
```

<br>

### 목록 생성 태그

#### ul

+ unordered list : 순서가 필요없는 목록을 만든다.

```html
<ul type="square">
    <li>항목1</li>
    <li>항목2</li>
</ul>
```

#### ol

+ ordered list : 숫자나 알파벳 등 순서가 있는 목록을 만드는 데 사용한다.

```html
<ol type="i" start="1">
    <li>항목1</li>
    <li>항목2</li>
</ul>
```

#### li

+ `<ul>` 혹은 `<ol>` 요소 안의 항목으로서 목록안의 아이템 list item을 나타낸다.



