package hufs.eselab.DynamicProgramming.Optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class JoinedLIS {
    List<Integer> A;
    List<Integer> B;
    Integer dp[][] = new Integer[100][100];
    public JoinedLIS(List A , List B) {
        this.A = A;
        this.B = B;
        for(Integer[] temp: dp){
            Arrays.fill(temp,-1);
        }

    }

    public int jlis(int idxA,int idxB){
        if(dp[idxA+1][idxB+1] != -1) return dp[idxA+1][idxB+1];//+1 왜함?

        long a = (idxA == -1 ? -12345678 : A.get(idxA)); //-1이면 neginf else idxA 넣기
        long b = (idxB == -1 ? -12345678 : B.get(idxB));
        long maxElement = Math.max(a,b);

        int ret = 2;
        for(int nextA= idxA+1; nextA<A.size() ; nextA++) {
            if(maxElement < A.get(nextA)) ret = Math.max(ret, 1+jlis(nextA,idxB));
        }
        for(int nextB= idxB+1; nextB<B.size() ; nextB++) {
            if(maxElement < B.get(nextB)) ret = Math.max(ret, 1+jlis(idxA,nextB));
        }
        return ret;

    }
}
