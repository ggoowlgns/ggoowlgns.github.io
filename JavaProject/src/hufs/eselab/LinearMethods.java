package hufs.eselab;

import java.util.ArrayList;
import java.util.List;

public class LinearMethods {

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
}
