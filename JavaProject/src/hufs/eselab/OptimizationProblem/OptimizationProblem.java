package hufs.eselab.OptimizationProblem;


import java.util.ArrayList;

public class OptimizationProblem {
    int n , MAX =  999999;
    double dist[][] = new double[10][10];


    private int[][] s = new int[10][];

    public OptimizationProblem() {
        this.s = new int[][]{
                {0, 1, 2},
                {3, 7, 9, 11},
                {4,10,14,15},
                {0,4,5,6,7},
                {6,7,8,10,12},
                {0,2,14,15},
                {3,14,15},
                {4,5,7,14,15},
                {1,2,3,4,5},
                {3,4,5,9,13}
        };
    }


    //For shortestPath
    public OptimizationProblem(double dist[][], int n) {
        this.dist = dist;
        this.n = n;
    }

    /**
     * **나머지 도시를 방문하는 경로들 중 가장 짧은 것의 길이 반환**
     * 기저: 모든 도시 전부 방문했을때
     * @param path 지금까지 만든 경로
     * @param visited 각 도시의 방문 여부
     * @param currentLength 지금까지 만든 경로의 길이
     * @return 나머지 도시를 방문하는 경로들 중 가장 짧은 것의 길이 반환
     */
    private double shortestPath(ArrayList<Integer> path, ArrayList<Boolean> visited, double currentLength){
        //기저 사례: 모든 도시를 다 방문하고 ret
        if(path.size() == n){
            return currentLength + dist[path.get(0)][path.get(path.size()-1)];
        }
        double ret = MAX;

        for(int next = 0; next<n ; next++){
            if(visited.get(next)) continue; //방문한적이 있다면 다음 loop
            int here = path.get(path.size()-1); //현재 위치
            path.add(next);
            visited.set(next,true);
            double cand = shortestPath(path,visited, currentLength+dist[here][next]);
            ret = Math.min(cand,ret);   //작은값을 가져간다
            visited.set(next,false);    //
            path.remove(path.size()-1);
        }

        return ret;
    }



    public int correctingClock(ArrayList<Integer> clock,int switch_num){
        int ret = MAX;


        if(switch_num == 10){ //방법없음
            if(areAligned(clock)){
                return 0;
            }
            else return MAX;
        }

        /**
         * 책에서 제시한 방법
         * for 를 '어차피 각 스위치는 4번 키면 똑같기 때문에 의미가 없다' 라는 아이디어를 기반으로 각 스위치 당 4번만 돌림
         */
        for(int i=0 ; i<4 ; i++){
//            ArrayList<Integer> temp = clone_arraylist(clock);
            int count_ret = correctingClock(clock,switch_num+1);
            ret = Math.min(ret,i+count_ret);    //현재 스위치 누른 횟수 + 현재 스위치 제외하고 총 누른횟수
//           PUSH
           push(clock,switch_num);
        }


        /**
         * 처음 내가 생각한 방법
         * for 를 스위치 번호 기준으로 하나씩 돌림 (10^n) - 엄청난 시간복잡도로 거의 구할수 없다
         */
        //검증
//        for(int i=1 ; i<s.length ; i++){
//            ArrayList<Integer> temp = clone_arraylist(clock);

//            for(int j=0;j<s[i].length ; j++){   //스위치 누르기
//                int hour = clock.get(s[i][j]);
//                hour = changeClock(hour);
//                clock.set(s[i][j] , hour);
//            }
//            if(switch_count[i] <3){ switch_count[i]++;count++;}
//            else continue;
//            int count_ret = correctingClock(clock, count,switch_count);
////            System.out.println(count);
//            ret = Math.min(ret,count_ret);
//
//            count--;
//            clock = clone_arraylist(temp);//원상복귀
//            switch_count[i]--;//원상복귀
//
//
//        }

        return  ret;
    }
    private void push(ArrayList<Integer> clock, int swtch_num){
        for(int j=0;j<s[swtch_num].length ; j++){   //스위치 누르기
            int hour = clock.get(s[swtch_num][j]);
            hour = changeClock(hour);
            clock.set(s[swtch_num][j] , hour);
        }
    }

    private boolean areAligned(ArrayList<Integer> clock){

        for(int i = 0 ; i<clock.size() ; i++){
            if(clock.get(i) != 12) {return false;}
        }
        return true;
    }

    private ArrayList<Integer> clone_arraylist(ArrayList<Integer> al){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for(Integer r : al){
            ret.add(r);
        }
        return ret;
    }

    /**
     * 시간 바꾸기
     * @param hour 현재 시간
     * @return 이동시킨 시간
     */
    private int changeClock(int hour){
        int ret = 0;
        switch (hour){
            case 3: ret = 6;break;
            case 6 : ret = 9; break;
            case 9 : ret = 12; break;
            case 12 : ret = 3; break;

        }
        return ret;
    }

}
