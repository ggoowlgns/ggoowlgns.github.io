package hufs.eselab;

import java.util.ArrayList;

public class SearchingMethods {
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
}
