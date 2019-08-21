package hufs.eselab.OptimizationProblem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int c = Integer.parseInt(br.readLine());

        ArrayList<Integer> ret = new ArrayList<Integer>();

        for(int i = 0 ; i<c ; i++){
            ArrayList<String> cls = new ArrayList<String>(Arrays.asList(br.readLine().split(" ")));
            ArrayList<Integer> cls_int = new ArrayList<Integer>();
            for(String s  : cls){
                cls_int.add(Integer.parseInt(s));
            }
            int[] switch_count = {0,0,0,0,0,0,0,0,0,0};
            OptimizationProblem op = new OptimizationProblem();
            ret.add(op.correctingClock(cls_int,0));
        }


        for(int r : ret){
            System.out.println(r);
        }

    }

}
