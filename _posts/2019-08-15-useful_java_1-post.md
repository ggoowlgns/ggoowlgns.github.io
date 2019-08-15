---
title:      "유용한 자료들 : 자바 for algorithm - I/O"
subtitle:   "JAVA I/O"
date:       2019-08-15
author:     "Park Ji Hoon"
header-img: "img/java_back.png"
header-mask: 0.3
catalog:    True
use_math: true
tags:
    - 유용한 시리즈
    - JAVA


---
> JAVA I/O 비교(feat. BufferedReader/BufferWriter)

자바 코딩시에 입력문을 받아올때 보통 Scanner Lib를 사용하기 마련이다.  
일단 두 클래스 java.util.Scanner와 java.io.BufferedReader의 차이점을 살펴보자

### Scanner
Scanner는 입력 받은 contents를 파싱을 해서 입력된 content가 어떤 형식인지 파악을 하게된다.  
즉 이러한 작업을 거치는 만큼 속도가 떨어진다고 할수 있다.  
하지만 입력된 값의 상태를 알고 싶다면 유용한 Class라고 할수 있고 사용할 수 있는 method는 아래와 같다.

| Method | 설명 |
| ------ | ------------------- |
|nextLine() | String value Data를 읽어들인다|
|nextByte() | Byte value Data를 읽어들인다|
|nextDouble() | Double value Data를 읽어들인다|
|nextFloat() | Float value Data를 읽어들인다|
|nextInt() | Int value Data를 읽어들인다|
|nextLong() | Long value Data를 읽어들인다|
|nextShort() | Short value Data를 읽어들인다|

### BufferedReader
Scanner와는 다르게 어떠한 처리를 하지 않고, 데이터가 입력되면 중간에 버퍼링을 거치고 전달이 된다.(이때 버퍼의 크기는 설정이 가능하다.)  
BufferedReader는 개행을 통해서만 스트림을 읽어온다. 받은 데이터 역시 String으로 고정되어 있기 때문에 추가적인 작업을 위해서는 따로 파싱을 해줘야하는 번거로움이 있다.  
하지만 많은 양의 데이터를 입력받을경우 Scanner에 비해서 효율적인 부분에서 엄청난 이득이 생기기 때문에 작업 속도 차이가 많이 나게된다.  
`Algorithm 테스트 시에는 BufferedReader를 사용하는 편이 훨씬 좋다.(입력 량이 많으니까)`  
#### 사용법
```java
BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
try {
    String read_line = bf.readLine();
} catch (IOException e) {
    e.printStackTrace();
}
```
위와 같이 try&catch 혹은 throws IOException을 통해서 예외 처리를 해준다.  
대표적인 가공 방법으로는 `StringTokenizer - StringTokenizer.nextToken()` 혹은 `String.split()` 를 사용한다.
```java
String read_line = bf.readLine();
StringTokenizer stringTokenizer = new StringTokenizer(read_line);
String a = (String)stringTokenizer.nextToken();
String b = (String)stringTokenizer.nextToken();


String[] s_split = read_line.split(" ");
String a = s_split[0];
String b = s_split[1];
```
[%]필자는 split을 애용한다..

Input Stream 관련한 Method 들은 아래와 같다.

|Method|설명|
|------|------------------|
|BufferedReader(Reader rd)|rd에 연결되는 문자입력 버퍼스트림 생성|
|int read()|스트림으로부터 한 문자를 읽어서 int 형으로 리턴|
| int read(char[] buf, int offset, int length)|buf의 offset위치에서부터 length 길이만큼 문자를 스트림으로부터 읽어들임​|
|String readLine()|스트림으로부터 한 줄을 읽어 문자열로 리턴​​|
|void mark()|현재우치를 마킹, 차 후 reset() 을 이용하여 마킹위치부터 시작함|
|void reset() |마킹이 있으면 그 위치에서부터 다시시각, 그렇지 않으면 처음부터 다시시작|
|long skip(int n)|n 개의 문자를 건너 뜀|
|void close()|스트림 닫음|


### 속도차이
![성능비교](https://t1.daumcdn.net/cfile/tistory/22647A355850D38003)  
*출처 : algospot*  
Scanner의 버퍼 사이즈는 1024 chars, BufferedReader의 버퍼 사이즈는 8192 chars 이므로 BufferedReader가 성능 상 훨씬 빠르다.




#### Reference
 - [[Java] BufferedReader, BufferedWriter를 활용한 빠른 입출력][1]
 - [Difference between Scanner and BufferReader Class in Java][2]
 - [Scanner와 BufferedReader 차이][3]


[1]:[https://coding-factory.tistory.com/251]
[2]:[https://www.geeksforgeeks.org/difference-between-scanner-and-bufferreader-class-in-java/]
[3]:[https://cocomo.tistory.com/507]
