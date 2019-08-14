package hufs.eselab;

import java.util.ArrayList;

public class BruteForce {

    char[][] m = {};

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

    public BruteForce(char[][] m) {
        this.m = m;
    }

    int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    int[] dy = {1, 1, 1, 0, 0, -1, -1, -1};

    /**
     * 해당 위치(x,y)에서 시작하는 단어 s가 있는지 판단.
     *
     * @param x
     * @param y
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

    /*public int countPairing(int[] stu, int[] pairs){
        int ret = 0;
        int stu_count = stu[0];
        int p_count = pairs.length;
        ArrayList<Integer> stu_left = new ArrayList<Integer>();
        ArrayList<int[]> pair_left = new ArrayList<int[]>();
        for(int i=0;i<stu_count;i++){
            stu_left.add(i);    // 남은 학생수
        }
        int[] temp = new int[2];
        for(int i = 0; i<p_count ; i++){
            if(i%2 !=0){ // 짝수 번째
                temp[1] = pairs[i];
                pair_left.add(temp);
                temp = new int[2];
            }else{
                temp[0] = pairs[i];
            }
        }


        checkingPair(pair_left,stu_left);

        for(int[] t : pair_left) {  //pair_left : 짝
            System.out.println("("+t[0]+","+ t[1]+")");
        }


        return ret;

    }*/

    /**
     * 소풍
     * 중복을 피하기 위해서 사전순으로 가장 먼저 오는 답 하나만을 센다 * -> firstFree
     * @param taken 이미 짝이 결정난 학생을 나타내는 값
     * @return
     */
    public int countPairing(boolean taken[]) {
        boolean finished = true;
        int ret = 0;

        //남은 학생들중 가장 번호가 빠른 학생을 찾는다.
        int firstFree = -1;
        for (int i = 0; i < n; i++) {
            if (!taken[i]) {
                firstFree = i;
                break;
            }
        }

        if (firstFree == -1) return 1; //모든 학생의 짝이 결정된 상황으로 1 을 ret 즉 횟수 하나를 올려준다.


        //i번째 학생이 이미 짝을 찾으면 false
        for (int i = 0; i < n; i++) if (!taken[i]) finished = false;


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

        for (int pair_with = firstFree + 1; pair_with < n; pair_with++) {
            if (!taken[pair_with] && !taken[firstFree] && areFriends[firstFree][pair_with]) {
                taken[pair_with] = taken[firstFree] = true;
                ret += countPairing(taken); // 1) 처음 1 을 ret 받아도 끝이 아니라 현재까진 같은 방법을 갖지만 뒤로는 다른 방법이 존재 할수도 있다. 즉 그에 대한 결과는 아래 ret 에서 총괄해서 ret 한다.
                taken[pair_with] = taken[firstFree] = false;
            }
        }
        return ret; // 2) 1)에서 누적한 부분을 ret 하고 이를 다시 이전 1) 에서 계속 합친다.
    }
}

