# Back-End

# **코딩 컨벤션**

> 💡 GOAL : 자바 컨벤션에 대해 알아본다.
> 

코딩 컨벤션(Coding Convention) 이라는 것이 있다. 프로그래머 사이에서 약속한 코드 작성 양식이다. 틀리면 프로그래밍 언어에서 컴파일 에러가 나는 문법 오류와 달리, 컨벤션은 지키지 않아도 오류가 나진 않는다.그러나 컨벤션을 지켜서 코드를 작성하면 코드의 `가독성`을 증진시키고, 여러명이 협업할 때 일관된 코드 스타일을 유지할 수 있어 중요하다.IDE를 사용하여 코드를 작성한다면 이클립스에선 `ctrl + shift + f` 를 통해, 인텔리제이에선 `ctrl + alt + L`을 통해 포맷 기능을 한번쯤은 사용해보았을 것이다.이 포매팅기능이 바로 자바 컨벤션에 맞추어 코드를 수정해주는 것이다.하지만 IDE가 잡아줄 수 있는 건 개행이나 공백 추가 등이고, 단락(paragraph) 의 순서나 변수명 등 까지 수정해주진 않는다.그러므로 프로그래머가 코드 작성단계에서 컨벤션을 고려하여 작성해야한다.

컨벤션은 프로그래머 끼리의 약속이라고 했기 때문에, 속한 팀마다, 조직마다, 회사마다 컨벤션이 다르다. 그렇지만 '어느정도' 통용되는 가이드라인은 있을 것 이다.느낌적인 느낌으로 적당히 좋다고 생각되는 변수명이나 코드 스타일을 해오던 나는 우아한 테크코스 3기 지원하는 과정 중에 구글의 자바 스타일 컨벤션 문서를 소개받았고, 그 중 중요한 내용에 대해 정리해두어야 겠다고 생각했다. 일부 주제에 대해서는 내 생각도 적혀있으므로, 원문이 궁금하신 분들은 아래 링크를 참고하시라.

**원본 자바 컨벤션 가이드 링크** [https://google.github.io/styleguide/javaguide.html](https://google.github.io/styleguide/javaguide.html)

### **용어 정리**

- 클래스는 일반적인 class 및 Enum, Interface, Annotation type을 총칭한다.
- 멤버는 inner class, field, method, constructor을 의미한다.
- 주석은 구현주석을 의미한다. 문서화 주석은 Java Doc에서 따로 다룬다.

## **소스파일 기본사항**

### **파일 이름**

JAVA 파일 이름은 포함된 소스의 최상위 클래스의 이름과 `.java` 확장자로 구성한다.

### **ENCODING**

encoding은 UTF-8이 기본이다!

### **공백문자**

개행 문자를 제외하고, ASCII 코드 공백문자(0x20)는 소스 파일에서 유일한 공백문자이다. 두가지를 의미하는데

1. String이나 문자 리터럴에서 공백문자는 이스케이프 된다.
2. 탭 문자는 들여쓰기에 사용하지 않는다.

### **이스케이프 되는 특수문자**

`\b`, `\t`, `\n`, `\f`, `\r`, `\"`, `\'`, `\\` 에 대해선 octal방식( `\012` )이나 유니코드(`\u000a` ) 보단 앞의 방식을 사용한다.

### **ASCII 코드 외의 문자**

아스키코드가 아닌 문자는 유니코드 케릭터 ( `∞` )나, 유니코드 이스케이프 ( `\u221e`)가 활용된다. 가장 읽기 좋은 방식으로 선택하는 것이 좋다.

Example :

```
String unitAbbrev = "μs";  - Best

String unitAbbrev = "\u03bcs";// "μs" - 허락되지만 이렇게 할 이유가 없음

String unitAbbrev = "\u03bcs";// Greek letter mu, "s" - 허락되지만 이상해보이고, 오해를 살 여지가 있음

String unitAbbrev = "\u03bcs"; - 최악, 이게 뭔지 알 도리가 없음
```

## **소스 파일의 구조**

다음 순서로 구성된다.

- 라이센스 또는 저작권 정보(있을 경우)
- package 명세
- import 명세
- 최상위 클래스 시작그리고 각 섹션들은 하나의 빈 줄로 구분한다.

**라이센스**있는 경우에 적어주면 된다.

**패키지 문**보통 화면 최상단에 위치한 package 문이다. 패키지 문은 개행하지 않고, 다른 내용에 적용되는 열 제한(최대 100자)는 패키지문에는 적용되지 않는다.(길면 긴대로 적는다.)

**import 문**

1. 와일드 카드 (ex. java.util.* 처럼 아스테리스크로 하위 클래스를 다 적용하는 방식)으로는 가져오지 않는다.
    - 이유 1. 중요한 문제는 아니지만, 성능 이슈가 생길 수도 있다. Wildcard Import 는 컴파일 할 때 실제 클래스를 찾기위해 해당 패키지의 클래스를 전부 탐색하는데, 그 시간이 더 걸리니까. 하지만 파일 몇개를 탐색한다고 차이가 더 날지 사실 의문이다. 또한 많은 어플리케이션이 사전에 컴파일된 jar나 war를 사용하는 경우가 많으므로 이 이유는 크게 중요하지 않다.
    - 이유 2. 별도의 두 패키지를 wildcard import 했는데, 두 패키지 모두에 있는 동일한 이름의 클래스를 활용하는 경우이다. 이 때 참조할 클래스를 결정할 수 없으므로 컴파일 할 수 없는 상황이 발생한다. 단순히 클래스를 import 했는데 소스코드를 수정해야 하므로 바람직하지 못하다.
2. import 문도 패키지 문과 마찬가지로 길다고 개행하지 않는다. 열제한(최대 100자) 또한 적용하지 않는다.
3. static import 와 non-static import은 따로 모아서 블록을 만든다. 블록의 순서는 static, non static이다. 블록 사이에는 1줄의 개행을 넣는다.각 블록 내에서의 정렬순서는 ASCII 코드 정렬순서이다.

*❗ 상위 클래스를 import 할 때 포함되므로 스태틱 inner class를 위해 static import하지 않는다.*

## **Class 정의**

소스 내에서 최상위 클래스는 단 하나여야 한다.클래스 멤버들간의 순서는 정답이 없지만, 논리적 순서를 갖추는게 중요하다.**비슷한 역할을 하는 메소드 끼리 뭉쳐놓고, 추상화 단계에 따라 배치하자***❗ overload된 메소드들은 흩어놓지 않는다*

## **포맷**

### **중괄호 `{` `}` (brace)**

괄호는 if, else, for, do 및 while문에 코드가 없거나 단 하나라도 생략하지 않습니다.

> 개인적으로는 코드가 한 줄일때는 중괄호를 생략하는게 더 깔끔하다고 생각해 왔는데, 가이드를 보고 가독성에 대해 다시 한 번 생각해보게 됐다
> 

`{` 의 경우...

- 여는 중괄호 전에는 개행하지 않는다.
- 여는 중괄호 뒤에서는 개행한다.`}` 의 경우...
- 닫는 괄호 앞에서 개행한다.
- 닫는 괄호 뒤의 개행은, 중괄호가 끝나거나 생성자, 메소드, 클래스가 끝날 때 개행한다. 그러므로, else나 `,` 앞에서는 개행하지 않는다.

```
return () -> {
while (condition()) {
    method();
  }
};

returnnew MyClass() {
@Overridepublicvoidmethod() {
if (condition()) {
try {
        something();
      }catch (ProblemException e) {
        recover();
      }
    }elseif (otherCondition()) {
      somethingElse();
    }else {
      lastThing();
    }
  }
};
```

### **비어있는 블록**

빈 블록은 개행하거나, 개행하지 않거나 상관없다. 하지만 멀티 블록일 경우 개행해주어야 한다.

```
// This is acceptablevoiddoNothing() {}

// This is equally acceptablevoiddoNothingElse() {
  }

// This is not acceptable: No concise empty blocks in a multi-block statementtry {
    doSomething();
  }catch (Exception e) {}
```

### **블록의 들여쓰기 (indent)**

블록 들여쓰기는 2공백이다.즉 새로운 블록이 시작하면 2공백을 추가해서 작성하다가, 블록이 끝나면 들여쓰기를 끝내면 된다.

> 보통 1탭은 4공백인데, 나는 이 컨벤션을 따르지 않고 탭을 통해 들여쓰기를 표현한다
> 

### **한 줄의 하나의 상태**

각 상태가 끝나면 개행을 한다. 상태가 끝나는 것은? `;`를 적은 후겠지

### **열 제한**

하나의 문장은 최대 100글자로 제한한다. 문자는 모든 유니코드 포인트를 의미한다. 아래 예외 경우를 제외하고 줄바꿈 규칙에 의거해 줄바꿈을 해야한다.

1. 열 제한을 따를 수 없는 행 (예 : javadoc의 긴 URL 또는 긴 JSNI 메소드 참조)
2. 패캐지 및 임포트 문
3. 주석에서, 쉘에 붙여 넣을 수 있는 command line 내용

### **줄바꿈 (line-wrapping)**

한 줄을 차지할 수 있는 코드를 여러줄 로 나누는 것을 줄바꿈 이라고 한다. 줄바꿈도 여러 방식이 있다.보통 줄 바꿈은 하는 이유는 열 제한(100자)을 초과하지 않기 위해서이지만, 열 제한을 넘지 않아도 가독성을 위하여 줄바꿈을 할 수 있다.

> 💡 메소드나 지역변수를 생성함으로써 줄바꿈 없이 열제한을 해결할 수 있다.
> 

### **줄바꿈 규칙**

줄바꿈의 더 높은 구문 수준(**higher syntactic level**)에서 끊어주는 것을 원칙으로 한다.

1. 연산자 같은 상징들 (operator-like symbol)들 앞에서 끊어준다. operator-like symbol은 다음이 있다
    - `.`
    - 람다의 `::`
    - <T extends Foo & Bar>와 같은 타입에서의 `&`
    - catch(FooException | BarException e) 같은 캐치블록에서의 `|`
2. `,`는 앞에 오는 토큰의 뒤에 적는다.

### **줄바꿈 시 들여쓰기**

줄바꿈을 하고 나면 최초 행보다 최소 4자를 들여쓰기 한다.

### **빈 줄 넣기**

### **세로**

1. 멤버 변수, 생성자 메소드, 이너 클래스, 초기화 사이 사이에는 항상 하나의 빈 줄을 넣는다.
    - 예외 1. 두 개의 연속된 필드 사이에 빈 줄은 옵션이다. 필드 간의 논리적 그룹을 만드는데 활용하면 된다.
    - 예외 2. 열거형 상수(enum) 사이의 빈 줄은 허용된다.
        
        ```
        privateenumAnswer {
        YES {
        @Overridepublic StringtoString() {
        return "yes";
          }
        },
        
        NO,
        MAYBE
        }
        ```
        
        ### **가로**
        
2. if, for, catch와 여는 괄호 `(` 사이에 공백
3. else, catch에 앞에 오는 닫는 중괄호 `}` 사이에 공백
4. `{` 중괄호 앞
    - 예외 1 : `@SomeAnnotation({a,b})`
    - 예외 2 : `String[][] x = {{"foo"}}` 처럼 {{ 사이에 공백이 필요없다
5. 이항 연산자 및 삼항 연산자의 앞 뒤, "operator-like" symbol 도 마찬가지
    1. 타입 영역 사이에 & `<T extends Foo & Bar>`
    2. multi catch 사이에 | `catch (FooException | BarException e)`
    3. foreach 문에 : `for( String bar : foo)`
    4. 람다의 화살표 `(String str) -> str.length()`
    5. 하지만 :: 와 . 은 앞뒤로 공백을 넣지 않는다.
6. `.:;` 의 뒤나 형변환 괄호 뒤
7. 라인이 끝남을 알리는 주석의 앞뒤 `//`
8. 타입선언과 변수명 사이 `List<String> list`
9. 배열 안 쪽은 취향껏
10. 타입 어노테이션과 `[]` 혹은 `...` 의 사이

위의 규칙들은 라인의 시작과 끝에선 적용되지 않는다. 라인의 중간에서만 공백을 넣어주면 된다

### **세로**

일반적으로는 허용되지만, 구글 스타일에선 권장하지 않는다.

```
privateint x;// this is fineprivate Color color;// this tooprivateint   x;// permitted, but future editsprivate Color color;// may leave it unaligned
```

세로 정렬은 가독성을 좋게 만드는 효과가 있으나, 유지보수성에넌 악영향을 미치기 때문!

### **변수선언**

1. 모든 변수 선언은 하나의 한개만`int a, b;` 는 사용하지 않는다.(for문 헤더에선 예외)
2. 필요할 때 선언지역 변수는 습관적으로 블록의 시작 부분에 선언되는 경향이 있다.하지만 지역 변수의 범위를 최소화 하기 위해 처음 사용되는 지점에 가깝게 선언한다.

### **배열**

1. 선언배열 선언은 "block-like"하게 하면 된다.
    
    ```
    newint[] {newint[] {
       0, 1, 2, 3            0,
     }                       1,
                             2,
    newint[] {             3,
       0, 1,               }
       2, 3
     }newint[]
                               {0, 1, 2, 3}
    ```
    
    모두 허용된다.
    
2. C style 배열선언 금지`[]` 는 타입에 붙여준다.`String[] args` O, `String args[]` X

### **스위치문**

1. 들여쓰기는 2 공백
2. 통과하는 경우(break없는 경우) 에는 주석을 달아둔다. 일반적으로는 `//fall though`
    
    ```
    switch (input) {
    case 1:
    case 2:
       prepareOneOrTwo();
    // fall throughcase 3:
       handleOneTwoOrThree();
    break;
    default:
       handleLargeNumber(input);
    }
    ```
    
    예시의 case 1: 처럼 상태가 없을 때는 주석을 안 달아도 된다.
    
3. default 코드가 없더라도 `default:` 블록을 추가한다.

### **어노테이션 (Annotations)**

어노테이션은 각 1줄이다.

```
@Override@Nullablepublic StringgetNameIfPresent() { ... }
```

그러나 인자없는 한줄의 어노테이션은 메소드 시그니쳐와 같이 쓸 수 있다.

```
@OverridepublicinthashCode() { ... }
```

필드 변수에 붙는 주석도 여러개가 한줄에 붙을 수 있다.

```
@Partial@Mock DataLoader loader;
```

### **주석**

JavaDoc을 제외한 주석을 뜻한다. 구현주석 (implementation comments) 이라고 부른다.참고로 주석만 있는 라인은 공백이 없는 것으로 렌더링된다.

### **블록 주석**

여러줄의 /* */ 주석 스타일은 , *로 시작하고 *로 정렬한다.

```
/*
 * This is          // And so           /* Or you can
 * okay.            // is this.          * even do this. */
 */
```

### **제어자(Modifiers)**

클래스와 멤버의 modifiers는 다음과 같은 순서로 작성하는 것이 권장된다.

`public protected private abstract default static final transient volatile synchronized native strictfp`

### **LONG 타입**

long 은 접미사를 대문자로 달아준다. 숫자 1과 l이 헷갈리기에..

## **네이밍**

### **공통 규칙**

식별자(identifier)는 아스키코드 글자와 숫자, 몇몇 예외경우, 언더바 만 허용한다. 따라서 각 식별자는 정규식과 매칭된다 `\w+`구글 스타일에선, 특정 접미사나 접두사는 사용하지 않는다.다음은 구글스타일이 아니다.`name_` , `mName` , `s_name` , `kName`

## **클래스이름**

UpperCamelCase 로 작성한다.UpperCamelCase 란 문장의 공백을 삭제한 후 첫글자를 대문자로 작성하고 다음 단어의 첫글자들을 대문자로 바꾸어주는 것을 의미함. `UpperCamelCase` 처럼. 🐫`명사`나 `명사구`로 작성한다. `Character` , `ImmutableList`interface 도 명사나 명사구로 작성한다. `List` 하지만 형용사나 형용사구로 작성해도 좋다 `Readable`

## **메소드 이름**

lowerCamelCase 🐫 로 작성한다.첫글자 가 소문자인 camel case이다.메소드 이름은 동사로 작성한다. `sendMessage` `stop`

## **상수이름**

상수이름은 `CONSTANT_CASE` 스타일로 작성한다. 그러나 상수가 무엇일까?

static final fields 면서 콘텐츠 들도 immutable 해야한다. 어떤 식으로든 인스턴스가 변화할 수 있다면, 이것은 상수가 아니다.

```
// Constantsstaticfinalint NUMBER = 5;
staticfinal ImmutableList<String> NAMES = ImmutableList.of("Ed", "Ann");
staticfinal ImmutableMap<String,Integer> AGES = ImmutableMap.of("Ed", 35, "Ann", 32);
staticfinal Joiner COMMA_JOINER = Joiner.on(',');// because Joiner is immutablestaticfinal SomeMutableType[] EMPTY_ARRAY = {};
enumSomeEnum { ENUM_CONSTANT }

// Not constantsstaticString nonFinal = "non-final";
finalString nonStatic = "non-static";
staticfinal Set<String> mutableCollection =new HashSet<String>();
staticfinal ImmutableSet<SomeMutableType> mutableElements = ImmutableSet.of(mutable);
staticfinal ImmutableMap<String, SomeMutableType> mutableValues =
    ImmutableMap.of("Ed", mutableInstance, "Ann", mutableInstance2);
staticfinal Logger logger = Logger.getLogger(MyClass.getName());
staticfinalString[] nonEmptyArray = {"these", "can", "change"};
```

## **상수가 아닌 필드 이름**

lowerCamelCase로 작성한다. 보통 명사절로 작성.

## **Parameter 이름**

lowerCamelCase , public method에서 한글자 파라미터는 피한다.

## **지역변수 이름**

지역변수도 lowerCamelCase로 작성한다.

final, immutable 이더라도 지역변수는 상수로 생각하지 않는다.

## **타입 이름**

타입명은 둘 중 하나로 작성한다.

1. 대문자 한글자, 거기에 숫자 하나는 옵션으로 `E`, `T`, `X`, `T2`
2. 혹은 클래스 이름을 사용하는데, 거기에 T를 붙인다. `RequestT` , `FooBarT`

## **Camel Case : 기정의 된 것들**

IPv6 나 IOS를 Camel Case 하려면?

구글 스타일에선 다음과 같이 한다.

1. 문장을 순수 ASCII 코드 로 바꾸고 아포스트로피 `'` 를 제거한다."Müller's algorithm" -> "Muellers algorithm"
2. 1의 결과물을 구두점 및 `` 과 공백을 기준으로 단어들로 나눈다.
3. 2의 결과물을 전체 소문자(약자도 포함!)로 바꾸고, 각 단어의 첫글자를 대문자로 바꾼다.
4. 한 단어로 이어 붙인다.

"XML HTTP request" → XmlHttpRequest

"new customer ID" → newCustomerId

"inner stopwatch" → innerStopwatch

"supports IPv6 on iOS?" → supportsIpv6OnIos

## **프로그래밍 연습**

1. @Override 는 항상 사용하라 (부모 메소드가 Deprecated 인 경우엔 생략하라)
2. catch 블록은 비워놓지 않는다. 만약 비워놓는 것이 의도된 동작이라면, 주석을 활용한다.
    
    ```
    try {
       int i = Integer.parseInt(response);
    return handleNumericResponse(i);
     }catch (NumberFormatException ok) {
    // it's not numeric; that's fine, just continue
     }
    return handleTextResponse(response);
    ```
    
    예외적으로 테스트에선 Exception 이름을 expected라고 작명함으로써 비워놓아도 된다.
    
    ```
    try {
    emptyStack.pop();
    fail();
     }catch (NoSuchElementException expected) {
     }
    ```
    
3. static 멤버는 클래스 이름을 활용하여 호출하라.
    
    ```
     Foo aFoo = ...;
     Foo.aStaticMethod();// good
     aFoo.aStaticMethod();// badsomethingThatYieldsAFoo().aStaticMethod();// very bad
    ```
    
4. Finalizers 는 사용하지 않는다.
    
    매우 강한 어조로 하지말라고 되어있다. 차후 effective Java를 읽게 되면 따로 포스팅 해보겠다.
    

> It is extremely rare to override Object.finalize.Tip: Don't do it. If you absolutely must, first read and understand Effective Java Item 7,(http://books.google.com/books?isbn=8131726592) "Avoid Finalizers," very carefully, and then don't do it.
> 

### **Java Doc**

기본적인 양식은 다음과 같다.

```
/**
 * Multiple lines of Javadoc text are written here,
 * wrapped normally...
 */publicintmethod(String p1) { ... }
```

간단하다면 1줄로 기술해도 된다.

```
/** An especially short bit of Javadoc. */
```

이 기본양식만 지키면 언제든지 용인되지만, 1줄로 기술할때는 `@return` 등의 태그 블록을 활용하면 안 된다.

문단은 한 줄의 공백라인을 넣음으로써 나눌 수 있다.

블록태그의 정석 순서는 `@param` , `@return` , `@throws` , `@deprecated` 이다.

각 Java Doc은 내용에 대한 요약 구문으로 시작해야 한다. 완전한 문장이 아닌 명사구나 동사구로 적어준다.

Java Doc은 최소한 모든 public클래스와, public 및 protected 멤버에 다 붙여주어야 한다.