package hufs.eselab;

import java.util.ArrayList;

public class BruteForce {

    char[][] m = {};
    public BruteForce() {

    }
    public BruteForce(char[][] m) {
        this.m = m;
    }

    int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    int[] dy = {1,1,1,0,0,-1,-1,-1};

    /**
     * 해당 위치(x,y)에서 시작하는 단어 s가 있는지 판단.
     *
     * @param x
     * @param y
     */
    public boolean hasWord(int x, int y, String s){
        if((x>4) || (y>4) || (x<0) || (y<0)) return false;

        if(m[x][y]!=s.charAt(0) ){  return false;}

        if(s.length() == 1) return true;

        for(int i=0; i<dx.length; i++){
            int nextX = x+dx[i];
            int nextY = y+dy[i];

            if(hasWord(nextX,nextY,s.substring(1))) return true;    //String.substring(beginidx i)
        }
        return false;
    }

    public int countPairing(int[] stu, int[] pairs){
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


        for(int[] t : pair_left) {  //pair_left : 짝
            System.out.println("("+t[0]+","+ t[1]+")");
        }
        return ret;

    }
    //recursive function
//    private int checkingPair(int[] stu_left, ){
//
//    }
}
