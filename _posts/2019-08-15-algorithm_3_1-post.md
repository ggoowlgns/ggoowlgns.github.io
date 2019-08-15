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

#### 재귀 호출 & 완전 탐색

모든 작업들은 자세히 들여다 보고 범위가 작아질수록 각 조각들의 형태가 유사해지는 작업들을 자주 볼수가 있다.  
이런 상황에서 가장 유용하게 사용할수 있는 개념이 Recursive Function (재귀 함수)이다.  

기본적인 재귀 함수의 사용으로는 문제를 여러 유사한 조각으로 쪼개고, 조각중 하나를 떼어내어 자신이 해결하고(재귀 함수), 나머지 조각들은 하나씩 자기 자신을 호출해서 다시 그대로 조각을 또다시 해결하는 것을 계속 반복하는 방식이다.  

또한, 제일 중요한 부분인 더이상 진행할 조각이 없는 상황에 도달했을때, 답을 반환하는 `기저 사례` 를 반드시 설립해야한다.  

바로 예제를 통해 적용해 보겠다.  

##### 문제 1 : 보글 게임
**문제 설명**  
5x5 크기의 알파벳 격자를 가지고 하는 게임이다.  
상하좌우/대각선으로 인접한 칸들을 이동하면서 입력한 글자의 존재 유무를 찾는 문제이다.  
한 글자가 두번 이상 사용될수도 있다.  
$ex)${: .center}  
![](/img/post_algorithm/boggle_ex.png){: width="20%" height="20%"}{: .center}  


**문제의 분할**
- 한 글자씩 처리
- 함수 호출시 격자의 위치, 남은 단어를 제공


**기저 사례**
- 해당 위치에 원하는 다음 글자가 아니면 바로 return
- 원하는 글자가 한글자이고 찾으면 - 마지막 글자를 찾으면 : 성공 return  

입력이 잘못되거나 범위에서 벗어난 경우도 기저 사례로 택해서 제일 처음에 제거 하는 습관을 들이면 좋다.
구현하면 아래와 같다.

```java
class BruteForce{
  char[][] m = {};
  public BruteForce(char[][] m) {
    this.m = m;
  }
  int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
  int[] dy = {1, 1, 1, 0, 0, -1, -1, -1};
  /**
   * 해당 위치(x,y)에서 시작하는 단어 s가 있는지 판단.
   *
   * @param x 가로
   * @param y 세로
   */
  public boolean hasWord(int x, int y, String s) {
      if ((x > 4) || (y > 4) || (x < 0) || (y < 0)) return false;

      if (m[x][y] != s.charAt(0)) {
          return false;
      }
      if (s.length() == 1) return true;

      for (int i = 0; i < dx.length; i++) {
          int nextX = x + dx[i];
          int nextY = y + dy[i];
          if (hasWord(nextX, nextY, s.substring(1))) return true;    //String.substring(beginidx i)
      }
      return false;
  }
}


public static void main(String[] args){
    char[][] st = {
                    {'u','r','l','p','m'},
                    {'x','p','r','e','t'},
                    {'g','i','a','e','t'},
                    {'x','t','n','z','y'},
                    {'x','o','q','r','s'}
                  };

    BruteForce bf = new BruteForce(st);
    boolean b = false;
    loop:
    for(int i=0;i<st[0].length;i++){
        for(int j=0;j<st.length;j++){

            b = bf.hasWord(i,j,"pretty");

            if(b) { break loop;}
        }
    }
}
```

**시간 복잡도**
8개의 단계 - 상하좌우/대각선 을 각 func 당 8번 돌린다.  
=> n : 입력 단어의 길의  $O(8^n)$



##### 완전 탐색 레시피
완전 탐색으로 해결하기 위해 필요한 과정

- 1) 완전 탐색은 보통 수행시간이 입력크기(n)에서 exp 하게 증가한다.  
- 2) 가능한 모든 답을 여러 개의 선택(조각)으로 나눕니다.  
- 3) 그중 하나의 조각을 선택 답의 일부를 만들고, 나머지 답(`부분 문제`)을 재귀 호출을 통해 완성  
- 4) 조각이 하나밖에 남지 않은 경우, 혹은 하나도 남지 않은 경우에는 `기저 사례`를 통해 빠져나간다.  

*부분 문제 : 기존 문제를 구성하는 조각을 빼낸 원래 문제의 일부*


##### 문제 : 소풍
**문제 설명**  
소풍 때 학생들을 두 명씩 짝을 지어 행동하게 하려 한다.  
항상 서로 친구인 학생들끼리만 짝을 지어주어야 한다.  
입력으로 서로 친구인지 여부가 주어질때, 짝을 지을 수 있는 방법의 수를 출력하시오.  

**입력**  

```bash
3 (case 수)
2 1 (학생수, 친구 여부 수)
0 1 (2명씩 친구 여부)
4 5
0 1 1 2 2 3 3 0 0 2 1 3
6 10
0 1 0 2 1 2 1 3 1 4 2 3 2 4 3 4 3 5 4 5
```  

**출력**  

```bash
1
3
4
```  

**문제 분할**  
- `'짝을 찾지 못한 친구'가 주어질때 친구끼리 둘씩 짝짓는 경우의수 계산` : 조각  
- 중복으로 세는 문제를 해결하기위해 `사전순으로 오는것만 센다.`  

*중복으로 세는 문제를 해결하기 위해서는 `특정 형태`를 갖는 답만을 세는것*  
*흔히 방법으로 `사전순으로 세는 방법`이 있다.*  

**기저 사례**  
- 모든 학생이 짝지어지면 return 1  

```java
public BruteForce{
    int n;
    boolean areFriends[][] = new boolean[10][10];
    public BruteForce(int[] pair_int, int stu_num) {
        int[] temp = new int[2];
        for (int i = 0; i < pair_int.length; i++) {
            if (i % 2 == 0) {
                temp[0] = pair_int[i];
            } else if (i % 2 == 1) {
                temp[1] = pair_int[i];
                this.areFriends[temp[0]][temp[1]] = true;
                this.areFriends[temp[1]][temp[0]] = true;
                temp = new int[2];
            }
        }
        this.n = stu_num;
    }

    /**
     * 소풍
     * 중복을 피하기 위해서 사전순으로 가장 먼저 오는 답 하나만을 센다 * -> firstFree
     * @param taken 이미 짝이 결정난 학생을 나타내는 값
     * @return
     */
    public int countPairing(boolean taken[]) {
        boolean finished = true;
        int ret = 0;

        //중복 문제를 해결 못함
        //i번째 학생이 이미 짝을 찾으면 false
//        for (int i = 0; i < n; i++) if (!taken[i]) finished = false;
        //서로 친구인 두 학생 찾기 -> 중복되는걸 찾지 못함
//        for(int i = 0; i<n ; i++){
//            for(int j=0; j<n ; j++){
//                if(!taken[i] && !taken[j] && areFriends[i][j]){
//                    taken[i] = taken[j] = true;
//                    System.out.println("no");
//                    ret += countPairing(taken);
//                    taken[i] = taken[j] = false;
//                }
//            }
//        }


        //남은 학생들중 가장 번호가 빠른 학생을 찾는다.
        int firstFree = -1;
        for (int i = 0; i < n; i++) {
            if (!taken[i]) {
                firstFree = i;
                break;
            }
        }

        if (firstFree == -1) return 1; //모든 학생의 짝이 결정된 상황으로 1 을 ret 즉 횟수 하나를 올려준다.
        for (int pair_with = firstFree + 1; pair_with < n; pair_with++) {
            if (!taken[pair_with] && !taken[firstFree] && areFriends[firstFree][pair_with]) {
                taken[pair_with] = taken[firstFree] = true;
// 1) 처음 1 을 ret 받아도 끝이 아니라 현재까진 같은 방법을 갖지만 뒤로는 다른 방법이 존재 할수도 있다. 즉 그에 대한 결과는 아래 ret 에서 총괄해서 ret 한다.
                ret += countPairing(taken);
                taken[pair_with] = taken[firstFree] = false;
            }
        }
        return ret; // 2) 1)에서 누적한 부분을 ret 하고 이를 다시 이전 1) 에서 계속 합친다.
    }

}
public static void main(String args[]){
    //입,출력 BufferedReader
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
        int num_case = Integer.parseInt(br.readLine());
        int[] ret = new int[num_case];

        for(int i=0 ;i<num_case;i++){
            String[] stu =  br.readLine().split(" ");
            int[] stu_int = new int[stu.length];
            for(int j=0;j<stu.length;j++){
                stu_int[j] = Integer.parseInt(stu[j]); //그냥 str array to int array
            }
            String[] pairs =  br.readLine().split(" ");
            int[] p_int = new int[pairs.length];
            for(int j=0;j<pairs.length;j++){
                p_int[j] = Integer.parseInt(pairs[j]); //그냥 str array to int array

            }

            BruteForce bruteForce = new BruteForce(p_int,stu_int[0]);
            boolean[] taken = new boolean[stu_int[0]];
            ret[i] = bruteForce.countPairing(taken);
        }
        br.close();
        for(int r : ret) {
            System.out.println(r);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

##### 문제 : 게임판 덮기
**문제 설명**  
H x W 크기의 게임판이 있다.  
검은 칸, 흰 칸으로 구성되어 있다.  
이때, 모든 흰칸을 3칸 짜리 L자형 블록으로 덮고 싶다.  
블록들은 자유롭게 회전이 가능하지만 겹치거나 검은 칸을 덮거나, 게임판 밖으로 나가는 것은 안된다.  
덮는 방법의 수를 출력하시오.  

$ex)$  {: .center}
![](/img/post_algorithm/game_board_ex.png){: width="50%" height="50%"}{: .center}  
**입력**  

```bash
3 (Case 수)
3 7 (H , W)
#.....# (# : 검은칸, . : 흰칸)
#.....#
##...##
3 7
#.....#
#.....#
##..###
8 10
##########
#........#
#........#
#........#
#........#
#........#
#........#
##########
```  

**출력**  

```bash
0
2
1514
```  

**문제 분할**  
- 현재 게임판을 제공 받고 이를 바탕으로 L자 블록 하나를 덮는다.(조각) : 이를 반복  
- 중복 방지를 위해 블록의 탐색 순서를 정한다.(위,왼쪽 부터)  
- 기준 점에서 만들 수 있는 블록 종류(타입)을 설정 한다.(int[][] coverType)  


**기저 사례**  
- 흰 칸의 수가 3의 배수가 아니면 답이 x  
- 모든 칸을 채웠으면 return 1  

```java
class BruteForce{
    //L자 블록 종류
    int[][][] coverType = {
            {{0,0}, {0,1}, {1,1}},
            {{0,0}, {1,0}, {0,1}},
            {{0,0}, {1,0}, {1,1}},
            {{0,0}, {1,0}, {1,-1}}
    };
    /**
     * 겹치거나, 검은 칸을 덮을때 false return
     * @param board 판
     * @param h 높이 idx
     * @param w 너비 idx
     * @param type 어떤 블록 넣을지
     * @param delta 블록을 둘지(+1) 뺼지(-1)
     * @return
     */
    private boolean set(int[][] board, int h, int w,int type, int delta){
        boolean ok = true;
        for(int i=0 ; i<3 ;i++){  //한 L자 block 칸 탐색
            int nh = h + coverType[type][i][0];
            int nw = w + coverType[type][i][1];
            if(nh <0 || nh >= board.length || nw <0 || nw >= board[0].length) ok = false;
            else if( (board[nh][nw] += delta) >1) ok  = false;
        }

        return ok;
    }

    /**
     * 조각
     * @param board 판
     * @return 모든 빈 칸을 덮을 수 있는 방법의 수
     */
    public int cover(int[][] board){
        int h = -1 , w = -1;

        loop:
        for( int i=0 ; i< board.length ; i++){
            for(int j=0 ; j< board[0].length ; j++){
                if(board[i][j] ==0){    //비어있는 블록 찾기
                    h=i;
                    w = j;
                    break loop;
                }
            }
  //            if(h != -1) break;
        }
        if( h == -1) return 1; //기저 사례 : 다 채운경우
        int ret = 0;
        for( int type =0  ; type <4 ; type++){
            if(set(board, h,w,type,1)) ret += cover(board); //블록 덮기
            set(board, h,w,type,-1); //블록 치우기
        }
        return ret;
    }
}

public static void main(String args[]){
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
        int num_case = Integer.parseInt(br.readLine());
        int[] ret = new int[num_case];

        for(int i=0 ;i<num_case;i++){
            String[] H_W =  br.readLine().split(" ");
            int[] H_W_int = new int[H_W.length];
            for(int j=0;j<H_W.length;j++){
                H_W_int[j] = Integer.parseInt(H_W[j]); //그냥 str array to int array
            }
            String[][] blocks  = new String[H_W_int[0]][H_W_int[1]];
            for(int j =0 ;j<H_W_int[0]; j++) {
                blocks[j] = br.readLine().split("");
            }

            BruteForce bruteForce = new BruteForce();
            int[][] board;
            board = new int[blocks.length][blocks[0].length];
            for(int x=0 ; x<blocks.length ; x++){
                for(int y=0 ; y<blocks[0].length ; y++){
                    if(blocks[x][y].equals("#")) board[x][y] = 1;
                }
            }
            ret[i] = bruteForce.cover(board);
        }
        for(int r : ret) {
            System.out.println(r);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

```

##### 정리 후 인상 깊었던 부분  

[%] 인상 깊은 기법으로는 재귀 함수에서 return 하는 값을 `끝에 도달하는 기저 사례`에서 1을 return 하여 계속 +1을 축적하여 최초 recursive function에서는 가능한 모든 경우를 return 하는 방식이다.

[1]: https://github.com/ggoowlgns/ggoowlgns.github.io/tree/master/JavaProject/src/hufs/eselab
