---
title:      "알고리즘 문제 해결 전략 : (2) 알고리즘 분석 - 시간 복잡도-2"
subtitle:   "Ch2 알고리즘 분석"
date:       2019-08-08
author:     "Park Ji Hoon"
header-img: "img/post_introducing_books/algorithm_1.jpg"
header-mask: 0.3
catalog:    True
use_math: true
tags:
    - 책 리뷰
    - 알고리즘 문제 해결 전략

---
> 알고리즘 평가의 꽃 시간복잡도(feat. 공간복잡도)  
> [%] 의 표시 : 필자의 의견  
> [Algorithm 소스 GIT][1]  


## 시간 복잡도
시간 복잡도(time complexity)란 기본적인 연산의 수를 입력의 크기에 대한 함수로 표현한것 (기본적인 연산 : 더 작게 쪼갤 수 없는 최소 크기의 연산)  
* 시간 복잡도가 높다 : 입력의 크기가 증가할때 수행 시간이 더 빠르게 증가한다.  
`즉, 시간 복잡도가 낮다고 항상 더 빠르게 동작하는것은 아님`  
* 입력의 종류에 따른 수행 시간 고려 (ex 선형 탐색)  
  * 최선의 수행 시간 (제일 처음에 찾는 값 : 1)
  * 최악의 수행 시간 (찾는 값이 없을때 : N)
  * 평균적인 경우의 수행시간 (평균 : N/2)

#### 점근적 시간 표기 : O()
대문자 O 표기법(Big-O Notation) : 가장 빨리 증가하는 항만을 남긴 채 나머지를 다 버림  
$ ex)  f(N) = \frac 5 3N^2 - Nlg\frac N 2 + 16 N -7 $
$ f(N) = O(N^2) $

| 문제 | 반복문의 수행 횟수 | O 표기|
|---- | -----------------| ------|
| $ movingAverage $ | $ N $ | $ O(N) $ |
| $binarySearch$ | $lgN$ | $O(lgN)$ |
| $selectMenu   (집합 덮개)$ | $NM2^M$ | $O(NM2^M)$   `N, M미지수 but, 상수는 삭제` |

* $f(N,M) = N^2M + NlgM + NM^2 = O(N^2M + NM^2)$  
 `빠르게 증가하는 것만 살아남음 (N,M 둘중 어느쪽이 빠르게 증가하는건지 판단 불가)`
* $f(N) = 42 = O(1)$
* O 표기법은 수행 시간의 상한을 나타내는 것 일뿐, 최악의 수행 시간은 아니다.
* [%] 정리하면, 한 알고리즘에는 최악,최선,평균 세종류의 시간 복잡도가 존재하고, 이는 Big-O 표기법으로 표시가 가능하다.  

#### 시간 복잡도 분석 연습
선택 정렬 : SelectionSort() - i~N-1의 idx 에서 제일 작은 수를 li[i]에 넣는다.  
삽입 정렬 : insertSort() - li[i]를 0~i-1 중에 적절한 위치에 넣는다.  

```java
/**
 * 선택 정렬 -> 앞에서 하나씩 비교해 가면서 작으면 바꾼다.
 *  (N)(N-1)/2 = O(N^2)
 * @param li
 */
public void selectioonSort(ArrayList<Integer> li){
    for(int i = 0 ; i < li.size() ; i++){
        int minIndex = i;

        for(int j = i+1 ; j < li.size() ;j++){  //이번 루프중에서 가장 작은 수의 idx 찾기
            if(li.get(minIndex) > li.get(j)) {
                minIndex = j;
            }
        }
        swap(li, minIndex,i);
    }
}

/**
 * 삽입 정렬
 * 최선 : O(N) , 최악 : O(N^2)
 * 즉 거의 대부분의 경우 : 삽입정렬 > 선택정렬 (O(N^2) 정렬 중 가장 빠른 알고리즘)
 * @param li
 */
public void insertionSort(ArrayList<Integer> li){
    for(int i =0; i<li.size() ; i++){
        int j = i;
        while(j > 0 && li.get(j-1)>li.get(j)){
            swap(li, j-1,j);
            --j;    //이전에 있는 것들이랑 비교
        }
    }
}
/**
 * 어쩔수가 없이 ArrayList를 같이 줘야함 -> C 처럼 Call by Ref가 안된다..
 * 이럴땐 C가 그립다..
 * @param li
 * @param i
 * @param j
 */
private void swap(ArrayList<Integer> li ,int i, int j){
    int temp =0;
    temp = li.get(i);
    li.set(i,li.get(j));
    li.set(j, temp);

}
```
위와 같이 선택 정렬을 계산하면 : $ (N-1) + (N-2) + ... +1  
 = N(N-1)/2 = \frac 1 2 N^2 - \frac 1 2 N  = O(N^2)$ 이다.  
 하지만 삽입 정렬은 선택 정렬과 다르게 while 문이 몇번 반복되는지 알수가 없다.   
 즉 최악, 최선의 상황을 나눠서 생각을 해보게 되면  
 * 최선 : 처음 준 값이 이미 정렬된 상태 - $ O(N) $
 * 최악 : 매 번 마지막에 삽입(역순으로 값이 정렬되어 있음) - $ O(N^2) $  
`결론 : 삽입 정렬이 대체적으로 선택 정렬 보다 빠르다.`  

#### 수행 시간 어림짐작하기
수행 시간을 예측하기란 쉽지가 않다. 하지만 어림짐작 하는 방법을 소개한다.  
주먹구구 법칙 : 예측한 수행 횟수가 기준의 10% 이하 혹은 기준의 10배 넘는 경우에만 적용   
(그 이상은 오차를 고려하면 의미가 없다)

**시간 복잡도 외에 고려할 요소들**
* 시간 복잡도가 실제 수행 속도를 반영하지 못한 경우 : 최고차항의 날라간 상수값 문제
* 반복문의 내부가 복잡한 경우 : 반복문의 내부는 단순하게(반복이 많이되는 부분일수록)
* (\*고급)메모리 사용 패턴이 복잡한 경우 : CPU는 Main Memory 에서 가져올때 인근 메모리 까지 가져와서 Cache 메모리에 옮겨둔 뒤 처리한다. 즉, 인접한 자료들을 취급하게 되면 수행 속도는 훨씬 빨라진다.
* 언어,컴파일러 차이 / 컴퓨터 성능 차이

#### 실제 적용해 보기
**문제 : 주어진 1차원 배열중 연속된 부분 구간 중 합이 최대인 구간의 합을 찾아라**
$ ex) \begin{pmatrix} -7,4,-3,6,3,-8,3,4\end{pmatrix} -> Ans : (4,-3,6,3) = 10$

1) [%]문제 보자마자 떠오르는 생각 : 그냥 다 확인해보자..(무식..) - $ O(N^3) $
```java
int MIN =  Integer.MIN_VALUE;
/**
 * ** 최악 **
 * 묶어서 합한값의 MAX
 * O(N^3)
 * @param li
 * @return
 */
public int inefficientMaxSum(ArrayList<Integer> li){
    int N = li.size();
    int ret = MIN;
    for(int i=0; i<N; i++){
        for(int j=i; j<N;j++){
            int sum =0;
            for(int k = i; k<=j ;k++){
                sum += li.get(k);
            }
//                if(sum>ret) ret= sum;
            ret = Math.max(ret,sum);
        }
    }
    return ret;
}
```


2) 반복되는 계산 피하기 (마지막 for문 제거) [%]여기까진 가능 - $ O(N^2) $
```java
int MIN =  Integer.MIN_VALUE;
/**
 * **좀더 좋음**
 * 묶어서 합한값의 MAX
 * O(N^2)
 * @param li
 * @return
 */
public int bettertMaxSum(ArrayList<Integer> li){
    int N = li.size();
    int ret = MIN;
    for(int i=0; i<N; i++){
        int sum =0;
        for(int j=i; j<N;j++){
            sum += li.get(j);
//                if(sum>ret) ret= sum;
            ret = Math.max(ret,sum);
        }
    }
    return ret;
}
```


3) 재귀호출, 탐욕법 이용 한 `분할정복`[%]스킬 연마 -$ O(NlgN) $  
[재귀 함수] 절반을 나누고 좌,우,좌우겹 세가지의 상황에서 Max값 return

```java
/**
 * recursive, greedy 기법 사용
 * 반으로 나누고 좌,우,겹친 상황 을 비교함
 * O(NlogN) -> 반씩 나누면서 생각 log, 재귀 함수 불릴때마다(loop마다) N
 * @param li
 * @param lo 시작하는 idx
 * @param hi 마지막 idx
 * @return
 */
public int fastMaxSum(ArrayList<Integer> li, int lo, int hi){
    if(lo==hi) return li.get(lo);
    int mid = (lo+hi)/2; //중간값
    //왼쪽의 중 가장 큰 값
    int sum =0, left=0,right=0;
    for (int i =mid ; i>=lo;i--){
        sum += li.get(i);
        left = Math.max(sum,left);
    }
    sum=0; //init
    //오른쪽 가장 큰값
    for(int i=mid+1;i<=hi ;i++){
        sum += li.get(i);
        right = Math.max(right,sum);
    }
    //왼쪽 혹은 오른쪽 끼리 따로 계산 했을때 더큰 상황 -> 겹처지는 상황보다 독단적으로 계산할때 더 큰상황
    int single = Math.max(fastMaxSum(li, lo,mid), fastMaxSum(li, mid+1, hi));
    return Math.max(single, left+right);//left+right은 좌우 겹처서 계산할때 상황
}
```


4)  문제의 특성을 활용한 점화식 설립 [%]지능 level -$ O(N) $  
maxAt(i)는 0~i까지 중 최대 합 즉 점화식으로 나타내면  
$ maxAt(i) = max(0, maxAt(i-1)) + li[i] $ 이다.
[%] 이전게 음수가 아니기만 하면 그냥 살리는게 이득/음수면 새로 시작

```java
/**
 * 점화식 생각 -> maxAt(i) = max(0, maxAt(i-1)) + A[i] (maxAt(li[i])은 0부터 i까지 중 최대합
 * 문제의 특성을 생각 : 이전까지 더한것이 음수면 버리고, 양수면 가져다가 쓴다, 결국 ret 값은 합한 비교해서 큰
 * O(N)
 * @param li
 * @return
 */
int fastestMaxSum(ArrayList<Integer> li){
    int N = li.size();
    int ret=0,presum = 0;

    for(int i =0; i<N; i++){
        presum = Math.max(presum,0) + li.get(i); //max(0,maxAt(i-1)) +A[i]
        ret = Math.max(presum,ret); //결과 : 이전값과 현재값 어떤게 더 큰지
    }
    return ret;
}
```

#### 계산 복잡도 클래스: P, PN, NP-완비
시간 복잡도는 해당 알고리즘의 특성일뿐, 문제의 특성은 아니다.  
`어려운 문제, 쉬운 문제는 빠른 수행 시간의 알고리즘의 존재 유무로 결정한다  `
* P (Polynomial)문제 : 다항 시간 알고리즘이 존재하는 문제들의 집합
* NP (non-Polynomial)문제 : 답이 주어졌을때 이것이 정답인지를 다항 시간 내에 확인할 수 있는 문제


[1]:[https://github.com/ggoowlgns/ggoowlgns.github.io/tree/master/JavaProject/src/hufs/eselab]
