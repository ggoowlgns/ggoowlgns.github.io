package hufs.eselab.DynamicProgramming.Optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicProgramming {
    public DynamicProgramming(int n) {
        this.n = n;
        Arrays.fill(dp, -1);
    }



    /*********************예제 삼각형 경로 STR**********************************/
    int n, triangle[][];
    int MAX_NUMBER = 123456789;
    int dp[][][] = new int[100][100][MAX_NUMBER*100 +1];
    /**
     * dp의 크기가 너무 크다, 시간 복잡도 上
     * @param y
     * @param x
     * @param sum 현재까지 합
     * @return
     */
    public int path1(int y, int x, int sum){
        //base case : 맨아래 도달
        if(y == n-1) return sum + triangle[y][x];
        //메모이제이션
        int ret = dp[y][x][sum];
        if(ret != -1) return ret;
        sum += triangle[y][x];
        ret = Math.max(path1(y+1,x+1,sum) , path1(y+1,x,sum));
        return ret;
    }

    /**
     * 뒷부분의 문제를 분리 -> 시간 복잡도 下
     * @param y
     * @param x
     * @return triangle(현재 까지 경로의 합) + ret(나머지 경로의 합)
     */
    int dp2[][] = new int[100][100];
    public int path2(int y,int x){
        int ret = dp2[y][x];
        if(ret != -1) return ret;
        ret = Math.max(path2(y+1,x+1),path2(y+1,x));
        return triangle[y][x]+ret;
    }
    /*********************예제 삼각형 경로 END**********************************/


    /*************************예제 LIS STR*********************************/
    public int lis(List<Integer > A){
        //base case : A가 텅빔
        if(A.isEmpty()) return 0;
        int ret =0;
        for(int i=0; i<A.size();i++){
            List<Integer> B = new ArrayList<>();
            for(int j= i+1 ; j< A.size() ; j++){
                B.add(A.get(j));//뒷 부분 sublist 만들기
            }
            ret = Math.max(ret,1+lis(B));//뒷부분으로 만들어줌
        }
        return ret;
    }

    public DynamicProgramming(List<Integer> s) {
        this.S = s;
        Arrays.fill(dp_lis,-1);
    }

    int dp_lis[] = new int[n];
    List<Integer> S;
    /**
     * DP로 구현하기위해 arg를 List에서 idx로 바꿈
     * @param str S의 시작 idx
     * @return
     */
    //List에서 dp 활용을 위해 시작점 int 로 바꿔줌
    public int lis_dp(int str){
        if(dp_lis[str] != -1) return dp_lis[str];
        int ret =0;
        for(int next= str+1; next<S.size();next++){
            if(S.get(next) >S.get(str)) ret = Math.max(ret,1+lis_dp(next));//뒷부분으로 만들어줌
        }
        return ret;
    }


    /**
     * Stack을 활용하여 더 빠른 알고리즘을 만들수 있다.
     * 더 큰수가 오면 .push 하고,  작은수가 오면 stack내에 적절한 위치에 넣어준다.
     * O(nlogn)으로 빠르게 lis를 구할수 있지만 이때, 어떤 부분집합을 가지는지는 알수가 없다.
     * ref : https://jason9319.tistory.com/113
     */

    /*************************예제 LIS END*********************************/
}
