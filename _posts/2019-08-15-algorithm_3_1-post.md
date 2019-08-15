---
title:      "알고리즘 문제 해결 전략 : (3) 알고리즘 설계 패러다임 - 무식하게 풀기-1"
subtitle:   "Brute Force Search (완전 탐색) Algorithm"
date:       2019-08-15
author:     "Park Ji Hoon"
header-img: "img/post_introducing_books/algorithm_1.jpg"
header-mask: 0.3
catalog:    True
use_math: true
tags:
    - 책 리뷰
    - 알고리즘 문제 해결 전략
    - brute force search

---
> 알고리즘 설계 시작 : 완전 탐색(feat. 재귀 함수)   
> [%] 의 표시 : 필자의 의견  
> [Algorithm 소스 GIT][1]  

알고리즘의 설계는 한순간의 영감보다 `여러 전략적인 선택`에 달려있다.  
1) `문제의 특성`을 이해  
2) `동작 시간, 사용하는 공간` 이 둘의 상충 관계 이해  
3) 적절한 `자료구조` 선택  

`알고리즘 설계 패러다임` : 문제를 해결 하기위한 일종의 패턴  
이러한 설계 패러다임들은 알고리즘 설계를 위한 좋은 틀이 된다.

## 무식하게 풀기
> 사람들이 가장 많이 하는 실수는 쉬운 문제를 어렵게 푸는 것  
> 우아한 답을 만들고 싶은 마음이 커질수록, 쉬운 길을 못보게된다.  

Brute Force Search (완전 탐색) : 모든 경우를 다 시도해보는 방법 - 무식한 방법이나 컴퓨터를 활용한 가장 좋은 예라고 볼수 있다.  
`더 빠른 알고리즘의 기반이 되는 알고리즘으로 잘 익혀둘 필요가 있다.`





```
3
2 1
0 1
4 5
0 1 1 2 2 3 3 0 0 2 1 3
6 10
0 1 0 2 1 2 1 3 1 4 2 3 2 4 3 4 3 5 4 5
```



[1]: https://github.com/ggoowlgns/ggoowlgns.github.io/tree/master/JavaProject/src/hufs/eselab
