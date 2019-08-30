package hufs.eselab.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicProgramming {
    int[][] map;

    public int getFunc_call_count() {
        return func_call_count;
    }

    int func_call_count;

    private int[][] dp;
    private String pattern;
    private String word;
    public DynamicProgramming() {
    }

    //for jumpGame
    public DynamicProgramming(int[][] map) {
        this.map = map;
        this.dp = new int[map.length][map[0].length];
        this.func_call_count = 0;
    }

    int dp_word[][] = new int[101][101];
    //for wildCard
    public DynamicProgramming(String w){
        this.pattern = w;
        for(int i=0; i<dp_word.length;i++) {
            Arrays.fill(dp_word[i], -1);
        }
    }




    public int jumpGame(int y, int x, int count){

        int ret = 0;
        if((y >= map.length) || (x>=map[0].length)) return 999999; //범위 이탈
        if(y== (map.length-1) && x==(map[0].length-1)) return count;
        func_call_count++;
        int jump_len = map[y][x];
        count++;
        ret =  Math.min(jumpGame(y+jump_len,x,count), jumpGame(y,x+jump_len, count)); //아래로 이동


        return ret;
    }

    /**
     * 처리 시간은 감소했지만, 막상 function call 횟수는 크게 감소하지 x
     * @param y 행
     * @param x 렬
     * @param count 몇번 뛰었나
     * @return
     */
    public int jumpGame_dp(int y, int x, int count){

        int ret = 0;
        if((y >= map.length) || (x>=map[0].length)) return 999999; //범위 이탈
        if(y== (map.length-1) && x==(map[0].length-1)) return count;

        if(dp[y][x] != 0) return dp[y][x];  //dp 확인
        func_call_count++;

        int jump_len = map[y][x];
        count++;
        ret =  Math.min(jumpGame_dp(y+jump_len,x,count), jumpGame_dp(y,x+jump_len, count)); //아래로 이동
        dp[y][x] = ret;

        return ret;
    }


    public List<String> wild_card(List<String> words){
        List<String> ret = new ArrayList<String>();


        long startTime ;
        long endTime ;
        long duration;

        startTime = System.nanoTime();
        for(String word : words){
            this.word = word;
            boolean isMatch = match(pattern, word);
            if(match(pattern,word)) ret.add(word);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println(duration);


        startTime = System.nanoTime();
        String pat_temp = this.pattern;
        for(String word : words){
            this.word = word;
            this.pattern = pat_temp;
            int p = 0;
            int w = 0;
//            boolean isMatch = match_dp(p, w);
            if(match_dp(p,w) == 1) ret.add(word);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println(duration);

        return ret;
    }

    private boolean match(String p, String s){
        boolean ret = false;
        int idx = 0;

        //해당 while 문에서 탈출한 상황의 idx를 잡는다.
        while(idx <p.length() && idx<s.length() && (p.charAt(idx)=='?' || p.charAt(idx)== s.charAt(idx))){  //만 하면 ?는 처리됨
            idx++;
        }
        //패턴 끝에 도달 : 문자열도 끝났어야 대응
        if(idx == p.length()) return idx==s.length();

        //*를 만나서 끝난경우
        if(p.charAt(idx)== '*'){
            for(int skip =0; idx+skip <= s.length() ; skip++){
                if(match(p.substring(idx+1),s.substring(idx+skip))) return true;

            }
        }

        return ret;
    }



    private int match_dp(int p, int s){
        int ret;

        ret = dp_word[p][s];
        if(ret != -1) return ret;

        //해당 while 문에서 탈출한 상황의 p,s를 잡는다.
        while(p <pattern.length() && s<word.length() && (pattern.charAt(p)=='?' || pattern.charAt(p)== word.charAt(s))){  //만 하면 ?는 처리됨
            p++;
            s++;
        }

        //패턴 끝에 도달 : 문자열도 끝났어야 대응
        if(p == pattern.length()) {dp_word[p][s] = s==word.length()? 1:0; return dp_word[p][s];}

        //*를 만나서 끝난경우
        if(pattern.charAt(p)== '*'){
            for(int skip =0; p+skip <= word.length() ; skip++){
                if(match(pattern.substring(p+1),word.substring(s+skip))) {dp_word[p][s] = 1; return 1;}

            }
        }

        return 1;
    }




}
