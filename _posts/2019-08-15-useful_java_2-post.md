---
title:      "유용한 자료들 : 자바 for algorithm - 다중 루프 탈출"
subtitle:   "JAVA multi loop break"
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
> JAVA에서 다중 Loop 탈출하기(feat. goto 그립..)

누구나 JAVA로 코딩을 하다가 조건에 의한 loop 탈출을 시도할때 해당 loop 의 탈출만이 아니라 진행중이는 모든 loop에서 탈출하고 싶을때가 있을 것이다.  


예를들어, 2차원이상의 Matrix에서 원하는 값을 찾았을때 탐색작업을 중단하는 상황은 매우 흔한 상황으로 답답함을 경험 한적이 있을것이다.

C계열의 언어 같은 경우에는 Assembly 언어처럼 Instruction을 원하는 위치로 이동할수가 있다.(goto 등)  

##### C Language
```c
#include <stdio.h>
int main(){
    int[][] matrix = {Something};
    int col = sizeof(matrix[0]) / sizeof(int);
    int row = sizeof(matrix) / sizeof(matrix[0]);
    for (int i = 0; i < row; i++){
        for (int j = 0; j < col; j++){
            if (matrix[i][j] == ?){    // num1이 20이라면
                goto EXIT;     // EXIT로 즉시 이동
            }
        }
    }

EXIT:    
return 0;
}
```


JAVA에는 goto가 없지만 해당 루프에 label을 달아주는 느낌으로 goto와 비슷한 느낌을 살릴 수가 있다.  
위의 코드를 JAVA로 비슷하게 바꾸면 아래와 같다.
##### JAVA Language
```java
public static void main(String[] args){
    int[][] matrix = {Something};
    EXIT:
    for(int i=0;i<matrix.length;i++){
        for(int j=0;j<matrix[0].length;j++){
            if(matrix[i][j] == ?){
                break EXIT;
            }
        }
    }
}
```


`이중 루프 뿐만 아니라 중첩시켜도 Label만 잘 설정 해주면 어떤식으로도 구현할수 있다.`
$End $ $of $ $Posting $ 


#### Reference
 - [[JAVA]이중루프, for문 빠져나가는 방법 - 손쉽게 코딩하기][1]


[1]:[https://docu94.tistory.com/41]
