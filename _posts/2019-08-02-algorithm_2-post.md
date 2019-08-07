---
title:      "알고리즘 문제 해결 전략 : (2) 알고리즘 분석 - 시간 복잡도-1"
subtitle:   "Ch2 알고리즘 분석"
date:       2019-08-02
author:     "Park Ji Hoon"
header-img: "img/post_introducing_books/algorithm_1.jpg"
header-mask: 0.3
catalog:    True
tags:
    - 책 리뷰
    - 알고리즘 문제 해결 전략

---
> 알고리즘 평가의 꽃 시간복잡도(feat. 공간복잡도)

`책에서는 C++로 코드를 제시했지만 필자는 JAVA 개발자 이므로 JAVA 코드로 게시하겠다.`

## 개관
시간 : 프로그램을 보다 빠르게 작동시키기 위함
공간 : 적은 용량의 메모리를 사용


## 알고리즘의 시간 복잡도 분석
#### 도입
알고리즘의 속도를 결정하는 요인은 너무나도 다양하다.  
(ex  사용 프로그래밍 언어, 하드웨어, 운영체제, 컴파일러, 등등..)즉, 같은 코드를 돌려도 속도는 제각각이다.
또한 같은 작업환경, 같은 코드 라 하여도, input의 차이에 따라 속도를 달리한다.  
그렇다면, 수행 시간에 대한 표준 지표를 어떻게 선택 하는지 의문이 들기 마련이다.  
#### 반복문이 지배한다
책에서 제시한 굉장히 재미있는 비유를 그대로 가져오겠다.  

| 항목                   |️ 자동차     | 자전거 ️             |
| --------------------------------- | ----------- | ------------------- |
| 시동 거는 시간 | 3분                             | 5초 |
| 문 열었다 닫는 시간 | 1분                             | 0초 |
| 운전석 등받이 조절 시간 | 5분                             | 0초 |
| 최대 시속 | 200km/h                         | 40km/h |

위와 같을때 자전거와 자동차가 경주를 하게될때 위 세개의 항목은 한번씩만 적용이 되지만, 최대 시속은 `반복`해서 적용되는 요인이므로 가장 중요하다.  
코드에서도 마찬가지로 `반복문`이 수행되는 횟수로 알고리즘의 효능을 판단한다.

#### 선형 알고리즘 시간
문제 : 몸무계 이동 평균 계산하기 - N개의 측정값을 가지고  이전, 현재 값 포함  M개의 평균
* 보편적인 인간의 경우 바로 떠오르는 방법은 이중 for문을 활용한 방법으로 아래와 같다.
N,M은 입력값(미지수)이므로 계산을 하게되면 N-(M-1)의 반복문이 M번 반복이 되므로 `N*M - M^2 + M`

  ```java
  /**
   * 이동 평면 : 시간 복잡 M*(N-M+1)
   * @param weight
   * @param M
   * @return
   */
  public ArrayList movingAverage1(ArrayList<Integer> weight, int M){
      ArrayList res = new ArrayList();
      int N = weight.size();
      for(int i = M-1 ; i < N ; i++){

          int sum=0;
          for(int j = 0 ; j<M;j++){
              sum += weight.get( i + j - M + 1);
          }
          res.add((double)sum/M);
      }
      return res;
  }
  ```
* `중복해서 계산하는 부분을 제거`한다면 더 좋은 방법이 있다.  
  i번째의 평균을 구하게 될때 제일 처음에 더했던 값을 빼고 i+1번째의 값을 더하게되면 중복되는 계산은 피할수가 있다.  
  두개의 for문이 돌아가므로 `M-1+(N-M+1) = N`  
  이와같이 수행시간이 N에 정비례하게 되는 알고리즘을 `선형 시간 알고리즘` 이라 한다.

  ```java
   /**
     * 중복 연산 삭제 -> 먼저 더한것만
     * 시간 복잡도 : M-1 + N-M+1
     * @param weight
     * @param M
     * @return
     */
  public ArrayList movingAverage2(ArrayList<Integer> weight, int M){
      ArrayList res = new ArrayList();
      int N = weight.size();
      int partialSum=0;
      for(int i = 0 ; i<M-1;i++){
          partialSum += weight.get(i );
      }
      for(int i = M-1 ; i < N ; i++){
          partialSum += weight.get(i);

          res.add((double)partialSum/M);
          partialSum -= weight.get(i-M+1);
      }
      return res;
  }
  ```

#### 선형 이하 시간 알고리즘
선형 시간 알고리즘이 제일 빠를것 같지만, 입력 값보다도 적게 반복되는 알고리즘도 존재한다.  
`입력으로 주어진 자료에 대해 우리가 미리 알고 있다면 가능하다.`  
예를들어, 주어진 int array에서 특정한 값을 찾는 알고리즘을 만드려고 한다.  
이때 **주어진 값이 오름차순으로 정렬되있다는 사실** 을 알고 있다면 굳이 처음부터 하나하나 확인할 필요가 없다.  
Loop 마다 절반을 나눠서 찾으면 훨씬 빠르게 찾을수 있다.  
이를 이진 탐색(Binary Search) 한다.

이처럼 매번 절반씩 나누니 밑이 2인 log<sub>2</sub><sup>N</sup> 이다. (앞으로는 편의를 위해lgN으로 표기 하겠다.)

#### 지수 시간 알고리즘
* 다항시간 알고리즘 : N, N<sup>2</sup> 와 같이 N의 거듭 제곱으로 수행시간이 표현되는 알고리즘  
ex) 아래 표 와 같이 사람별로 못먹는 음식을 표현 했을때 M가지의 음식중 최소한 몇가지의 음식을 해야 모두가 먹을수 있는지

|    | 갈비찜 | 피자 | 잡채 | 떡볶이 | 탕수육 | 닭강정 |  
| ---|------|-----|------|------|------|--- |--- |  
| 페이|X|O|O|O|X|X|  
| 지아|X|X|X|X|O|O|  
| 민|O|X|O|X|O|X|  
| 수지|O|O|X|X|X|O|  

재귀 호출을 이용하면 간단하다.

```java
/**
 * canEverybodyEat func 의 시간 복잡도는 N*M 이라 가정한다
 * 시간 복잡도 : N*M * 2^M
 *
 * @param menu
 * @param food
 * @return
 */
private int selectMenu(ArrayList<Integer> menu, int food){
    if(food == M){ //모든 음식을 만들경우
        if(canEverybodyEat(menu)) return menu.size();
        else return INF;
    }

    //이 음식을 만들지 않는 경우의 답
    int ret = selectMenu(menu, food+1);

    //이 음식을 만들경우
    menu.add(food);
    ret = Math.min(ret, selectMenu(menu, food+1));//더 작은걸로
    menu.remove(menu.size()-1);
    return ret;
}
```
위 프로그램은 모든 답을 한 번씩 다 확인하기 때문에, 2<sup>M</sup>번 반복을 하게되고 반복마다 canEverybodyEat()이 돌아간다.(구현은 하지 않았지만 수행시간을 N*M이라 가정한다.) 즉, 전체 수행시간은 `N*M*2^M`  이다.
