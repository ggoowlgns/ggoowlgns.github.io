package hufs.eselab;

import java.util.ArrayList;

public class BruteForce {

    char[][] m = {};

    int n;
    boolean areFriends[][] = new boolean[10][10];

    int[] dh = {0,-1,0,1};
    int[] dw = {-1,0,1,0};
    int[][] board;
    public BruteForce(){

    }

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


    /**
     * 게임판 덮기 / 내 풀이 - 아이디어는 맞음
     * 문제점 : 구현할때 가능성을 비교할때 칸별로 생각하지 말고 L자형 블록 그대로 생각
     * @param blocks
     * @return
     */
    /*public int cover(String[][] blocks){
        int ret = 0;

        for(int i=0;i<blocks.length;i++){
            for(int j=0; j<blocks[0].length; j++){
                if(blocks[i][j].equals(".")){
                    ArrayList<Integer> num_match = new ArrayList<Integer>();
                    num_match = find_match(blocks,i,j);

                    for(int k : num_match){
                        switch (k) {
                            case 0:blocks[0]
                        }
                    }
                }
            }
        }

        return ret;
    }

    private ArrayList<Integer> find_match(String[][] blocks, int h_idx, int w_idx){


        //위, 왼쪽 블록

        boolean[]  empty_blocks = new boolean[4];
        for(int i=0 ; i<4 ; i++){//네번 경우
            if(((h_idx+dh[i]) >= 0) && ((h_idx+dh[i]) < blocks.length) && ((w_idx+dw[i]) >= 0) && ((w_idx+dw[i]) < blocks[0].length)){
                if(blocks[h_idx+dh[i]][w_idx+dw[i]].equals(".")) empty_blocks[i] = true;
            }
        }
        //1,2,3,4 비교
        ArrayList<Integer> num_match = new ArrayList();
        for(int i=0; i<4 ; i++){
            if(empty_blocks[i] == true ){
                if(empty_blocks[(i+1)%4] == true) { //한방향
                           num_match.add(i);
                }

            }
        }
        return num_match;
    }*/




    int[][][] coverType = {
            {{0,0}, {0,1}, {1,1}},
            {{0,0}, {1,0}, {0,1}},
            {{0,0}, {1,0}, {1,1}},
            {{0,0}, {1,0}, {1,-1}}
    };

    /**
     *
     * @param board 판
     * @param h 높이 idx
     * @param w 너비 idx
     * @param type 어떤 블록 넣을지
     * @param delta 블록을 둘지(+1) 뺼지(-1)
     * @return
     */
    private boolean set(int[][] board, int h, int w,int type, int delta){
        boolean ok = true;
        for(int i=0 ; i<3 ;i++){
            int nh = h + coverType[type][i][0];
            int nw = w + coverType[type][i][1];
            if(nh <0 || nh >= board.length || nw <0 || nw >= board[0].length) ok = false;
            else if( (board[nh][nw] += delta) >1) ok  = false;
        }

        return ok;
    }
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

