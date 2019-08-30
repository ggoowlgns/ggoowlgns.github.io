package hufs.eselab.DynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        int[][] map = {
                {2,5,1,6,1,4,1},
                {6,1,1,2,2,9,3},
                {7,2,3,2,1,3,1},
                {1,1,3,1,7,1,2},
                {4,1,2,3,4,1,2},
                {3,3,1,2,3,4,1},
                {1,5,2,9,4,7,0}
        };
        long startTime ;
        long endTime ;
        long duration;

        /*DynamicProgramming dp = new DynamicProgramming(map);
        startTime = System.nanoTime();
        int ret = dp.jumpGame(0,0,0);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println(ret);
        System.out.println("duration without dp : "+ (double)duration/1000000 + "ms");
        System.out.println("function call count : " + dp.getFunc_call_count());

        DynamicProgramming dp1 = new DynamicProgramming(map);
        startTime = System.nanoTime();
        int ret_dp = dp1.jumpGame_dp(0,0,0);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println(ret_dp);
        System.out.println("duration with dp: "+ (double)duration/1000000 + "ms");
        System.out.println("function call count : " + dp1.getFunc_call_count());*/



        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        DynamicProgramming dp;
        int c = Integer.parseInt(br.readLine());
        List<List<String>> ret = new ArrayList<List<String>>();
        for(int i = 0; i<c ;i++){
            String w = br.readLine();
            dp = new DynamicProgramming(w);
            int n = Integer.parseInt(br.readLine());
            List<String> words = new ArrayList<String>();
            for(int j = 0; j<n ;j++){
                words.add(br.readLine());
            }
            List<String> ret_words = new ArrayList<String>();
            ret.add(dp.wild_card(words));
        }

        for(List<String> l : ret){
            for(String word : l){
                System.out.println(word);
            }
        }


    }
}
